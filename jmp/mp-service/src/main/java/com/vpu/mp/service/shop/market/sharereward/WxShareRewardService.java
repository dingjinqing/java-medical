package com.vpu.mp.service.shop.market.sharereward;

import com.vpu.mp.db.shop.tables.AttendShareUser;
import com.vpu.mp.db.shop.tables.ShareAward;
import com.vpu.mp.db.shop.tables.ShareAwardReceive;
import com.vpu.mp.db.shop.tables.ShareAwardRecord;
import com.vpu.mp.db.shop.tables.records.AttendShareUserRecord;
import com.vpu.mp.db.shop.tables.records.ShareAwardReceiveRecord;
import com.vpu.mp.db.shop.tables.records.ShareAwardRecordRecord;
import com.vpu.mp.db.shop.tables.records.ShareRecordRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.database.DslPlus;
import com.vpu.mp.service.foundation.exception.Assert;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.market.sharereward.GoodsShareDetail;
import com.vpu.mp.service.pojo.shop.market.sharereward.ShareRewardInfoVo;
import com.vpu.mp.service.pojo.shop.market.sharereward.ShareRule;
import com.vpu.mp.service.pojo.wxapp.account.UserInfo;
import com.vpu.mp.service.pojo.wxapp.market.enterpolitely.ExtBo;
import com.vpu.mp.service.pojo.wxapp.market.sharereward.ShareParam;
import com.vpu.mp.service.shop.market.award.Award;
import com.vpu.mp.service.shop.market.award.AwardFactory;
import com.vpu.mp.service.shop.market.award.AwardParam;
import com.vpu.mp.service.shop.market.award.SendAwardImpl;
import com.vpu.mp.service.shop.user.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.TableField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.vpu.mp.db.shop.Tables.SHARE_RECORD;
import static com.vpu.mp.service.pojo.shop.market.increasepurchase.PurchaseConstant.BYTE_THREE;
import static com.vpu.mp.service.shop.store.store.StoreWxService.BYTE_TWO;
import static org.apache.commons.lang3.math.NumberUtils.*;

/**
 * The type Wx share reward service.
 *
 * @author liufei
 * @date 1 /9/20
 */
@Slf4j
@Service
public class WxShareRewardService extends ShopBaseService {
    /**
     * The constant AWARD.
     */
    private static ShareAward AWARD = ShareAward.SHARE_AWARD.as("AWARD");
    /**
     * The constant AWARD_RECORD.
     */
    private static ShareAwardRecord AWARD_RECORD = ShareAwardRecord.SHARE_AWARD_RECORD.as("AWARD_RECORD");
    /**
     * The constant AWARD_RECEIVE.
     */
    private static ShareAwardReceive AWARD_RECEIVE = ShareAwardReceive.SHARE_AWARD_RECEIVE.as("AWARD_RECEIVE");
    /**
     * The constant ATTEND.
     */
    private static AttendShareUser ATTEND = AttendShareUser.ATTEND_SHARE_USER.as("ATTEND");

    @Autowired
    private ShareRewardService shareReward;
    @Autowired
    private UserService userService;

    /**
     * Add share record.添加分享记录，同时参与分享有礼活动，两张表b2c_share_record和b2c_share_award_record
     *
     * @param param the param
     */
    public void shareAward(ShareParam param) {
        // 添加分享记录
        shareRecord(param);
        int activityId = param.getActivityId();
        int userId = param.getUserId();
        int goodId = param.getGoodsId();
        // 活动可用性校验/分享的商品有效性校验
        shareReward.activityAvailable(activityId, goodId);
        // 添加分享有礼活动记录
        Condition exist = AWARD_RECORD.SHARE_ID.eq(activityId).and(AWARD_RECORD.USER_ID.eq(userId)).and(AWARD_RECORD.GOODS_ID.eq(goodId));
        if (!shareReward.aRecordIsExist(exist)) {
            ShareAwardRecordRecord record = db().newRecord(AWARD_RECORD);
            record.setShareId(activityId);
            record.setUserId(userId);
            record.setGoodsId(goodId);
            record.setStatus(BYTE_ONE);
            record.insert();
        }
    }

    /**
     * Share record share record record.分享没有礼，仅仅是单纯的记录分享记录
     *
     * @param param the param
     * @return the share record record
     */
    public ShareRecordRecord shareRecord(ShareParam param) {
        int activityId = param.getActivityId();
        int userId = param.getUserId();
        int activityType = param.getActivityType();
        Condition exist = SHARE_RECORD.ACTIVITY_ID.eq(activityId).and(SHARE_RECORD.USER_ID.eq(userId)).and(SHARE_RECORD.ACTIVITY_TYPE.eq(activityType));
        if (recordIsExist(exist)) {
            autoincrementCount(exist);
        } else {
            ShareRecordRecord record = db().newRecord(SHARE_RECORD);
            record.setActivityId(activityId);
            record.setUserId(userId);
            record.setActivityType(activityType);
            record.setCount(INTEGER_ZERO);
            record.insert();
        }
        return getRecordRecord(exist);
    }

    /**
     * Autoincrement count.分享记录自增
     *
     * @param condition the condition
     */
    private void autoincrementCount(Condition condition) {
        db().update(SHARE_RECORD).set(SHARE_RECORD.COUNT, SHARE_RECORD.COUNT.add(INTEGER_ONE)).where(condition).execute();
    }

    /**
     * Gets record record.分享记录信息详情
     *
     * @param condition the condition
     * @return the record record
     */
    private ShareRecordRecord getRecordRecord(Condition condition) {
        return db().selectFrom(SHARE_RECORD).where(condition).fetchOne();
    }


    /**
     * Record is exist boolean.分享记录是否存在/是否已分享过
     *
     * @param condition the condition
     * @return the boolean
     */
    private boolean recordIsExist(Condition condition) {
        return db().fetchExists(SHARE_RECORD, condition);
    }

    /**
     * Gets available share award.获取指定用户，指定商品的可用分享有礼活动
     *
     * @param userId  the user id
     * @param goodsId the goods id
     * @return the available share award 返回 0 表示无可用的分享有礼活动
     */
    public Integer getAvailableShareAward(int userId, int goodsId) {
//     获取指定商品可用的分享有礼活动
        int goodsPv = shareReward.getGoodsPv(goodsId);
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        Condition condition = AWARD.DEL_FLAG.eq(BYTE_ZERO)
            .and(AWARD.STATUS.eq(BYTE_ZERO))
            .and(AWARD.IS_FOREVER.eq(BYTE_ONE).or(AWARD.START_TIME.lessThan(now).and(AWARD.END_TIME.greaterThan(now))));
        Condition condition1 = AWARD.CONDITION.eq(BYTE_ONE);
        Condition condition2 = AWARD.CONDITION.eq(BYTE_TWO).and(DslPlus.findInSet(goodsId, AWARD.GOODS_IDS));
        Condition condition3 = AWARD.CONDITION.eq(BYTE_THREE).and(AWARD.GOODS_PV.greaterThan(goodsPv));
        Integer shareId = db().select(AWARD.ID).from(AWARD).where(condition).and(condition1.or(condition2).or(condition3)).orderBy(AWARD.PRIORITY).limit(INTEGER_ONE).fetchOneInto(Integer.class);
        if (Objects.isNull(shareId)) {
            log.info("无可用的分享有礼活动！");
            return INTEGER_ZERO;
        }
        // 是否参加过当前商品分享有礼活动
        int count = db().fetchCount(ATTEND, AWARD_RECORD.USER_ID.eq(userId)
            .and(AWARD_RECORD.GOODS_ID.eq(goodsId))
            .and(AWARD_RECORD.CREATE_TIME.greaterThan(Timestamp.valueOf(LocalDate.now().atStartOfDay()))));
        if (count == 0) {
            return shareId;
        }
        // 校验每日的分享次数上限
        int limit = shareReward.getDailyShareAwardValue();
        if (count >= limit) {
            log.info("分享有礼活动已达到每日分享次数上限{}！", limit);
            return INTEGER_ZERO;
        }
        return shareId;
    }

    /**
     * Gets share info.商品详情页点击分享按钮弹出分享有礼详情
     *
     * @param userId     the user id
     * @param goodsId    the goods id
     * @param activityId the activity id
     * @return the share info
     */
    public GoodsShareDetail getShareInfo(Integer userId, Integer goodsId, Integer activityId) {
        // 活动可用性校验/分享的商品有效性校验
        shareReward.activityAvailable(activityId, goodsId);
        // 获取活动信息详情
        ShareRewardInfoVo info = shareReward.getShareInfo(activityId);
        List<ShareRule> rules = info.getShareRules();
        rules.forEach(rule -> rule.setUserInfoList(getBatchUserList(getAttendUserList(userId, goodsId, activityId, rule.getRuleLevel()))));
        // 获取每日用户可分享次数上限参数
        int limitNum = shareReward.getDailyShareAwardValue();
        return GoodsShareDetail.builder().dailyShareLimit(limitNum).infoVo(info).build();
    }

    private List<Integer> getAttendUserList(Integer userId, Integer goodsId, Integer activityId, Byte level) {
        return db().select(ATTEND.USER_ID).from(ATTEND)
            .where(ATTEND.SHARE_ID.eq(activityId).and(ATTEND.LAUNCH_USER_ID.eq(userId))
                .and(ATTEND.GOODS_ID.eq(goodsId))).and(ATTEND.LEVEL.eq(level))
            .fetchInto(Integer.class);
    }

    private List<UserInfo> getBatchUserList(List<Integer> userIds) {
        return new ArrayList<UserInfo>(8) {{
            userIds.forEach(id -> add(userService.getUserInfo(id)));
        }};
    }

    /**
     * From share 2 goods detail.
     * 商品详情判定是否为分享有礼的商品，加载的时候判定来源，是否为别人分享的入口
     * 用户点击别人分享的商品链接，进入商品详情页，触发参与别人发起的分享
     *
     * @param launchUserId the launch user id
     * @param userId       the user id
     * @param goodsId      the goods id
     * @param activityId   the activity id
     */
    public void fromShare2GoodsDetail(Integer launchUserId, Integer userId, Integer goodsId, Integer activityId) {
        // 是否店铺新用户
        boolean isNew = userService.isNew(userId);
        // 活动可用性校验/分享的商品有效性校验
        com.vpu.mp.db.shop.tables.records.ShareAwardRecord shareAward = shareReward.activityAvailable(activityId, goodsId);
        // 是否已参与该分享链接活动/是否已点击过该链接
        if (isAttend(launchUserId, userId, goodsId, activityId)) {
            log.info("用户 {} 已经参与过该分享活动 {} ！", userId, activityId);
            return;
        }
        // 查询分享活动的发起用户的分享记录
        ShareAwardRecordRecord awardRecord = shareReward.getShareAwardRecord(activityId, launchUserId, goodsId);
        // 参与用户添加参与分享活动记录
        AttendShareUserRecord attendRecord = db().newRecord(ATTEND);
        attendRecord.setRecordId(awardRecord.getId());
        attendRecord.setShareId(activityId);
        attendRecord.setGoodsId(goodsId);
        attendRecord.setUserId(userId);
        attendRecord.setIsNew(isNew ? BYTE_ONE : BYTE_ZERO);
        attendRecord.setLaunchUserId(launchUserId);
        attendRecord.setLevel(awardRecord.getStatus());
        addAttend(attendRecord);
        // 仅邀请未访问过的用户有效；0否，1是（即是否要求参与用户为店铺新用户）
        int count = awardRecord.getUserNumber();
        if (BYTE_ZERO.equals(shareAward.getVisitFirst()) || isNew) {
            // 活动发起用户的分享链接的访问人数自增加一
            count = shareReward.autoincrementUserNum(awardRecord.getId());
        }
        // 更新当前活动进行的进度
        updateProcess(activityId, count, ExtBo.builder().userId(launchUserId).changeWay(55).goodsId(goodsId).build());
    }

    private void updateProcess(Integer activityId, int count, ExtBo ext) {
        int userId = ext.getUserId();
        int goodsId = ext.getGoodsId();
        // 获取活动信息详情
        ShareRewardInfoVo info = shareReward.getShareInfo(activityId);
        List<ShareRule> list = info.getShareRules();
        // 遍历活动规则
        log.info("用户已分享次数：{}", count);
        list.forEach(rule -> {
            if (Objects.nonNull(rule)) {
                // 满足规则条件
                if (count >= rule.getInviteNum()) {
                    // 更新分享进度(进度加一)
                    updateAwardRecord(userId, activityId, goodsId, AWARD_RECORD.STATUS, AWARD_RECORD.STATUS.add(INTEGER_ONE));
                    // 奖品剩余库存是否充足
                    if (rule.getStock() > 0) {
                        try {
                            this.transaction(() -> {
                                byte level = rule.getRuleLevel();
                                log.info("{} 级分享有礼活动规则完成，开始发放奖品", level);
                                // 更新对应等级的奖品为已领取状态
                                updateAwardRecord(userId, activityId, goodsId, getField(level), BYTE_TWO);
                                // 奖品库存扣减(库存扣减控制不能为负数)
                                Field<Integer> field = getAwardField(level);
                                updateAward(activityId, field, field.sub(INTEGER_ONE), field.sub(INTEGER_ONE).greaterOrEqual(INTEGER_ZERO));
                                // 发送分享有礼奖品
                                sendAward(rule.getRewardType(), AwardParam.builder()
                                    .activityId(info.getId()).rule(rule).changeWay(ext.getChangeWay()).orderSn(ext.getOrderSn()).userId(ext.getUserId()).build());
                                // 添加领奖记录
                                addShareReceive(userId, activityId, goodsId, level);
                                log.info("{} 级分享有礼活动规则下的奖品发放完成！", level);
                            });
                            // todo 发送消息模板通知用户奖品已发放
                            log.info("发送消息模板通知用户奖品已发放");
                        } catch (Exception e) {
                            log.info("分享有礼发送奖品异常，更新对应等级的奖品为未领取状态！当前活动规则信息：{}！报错信息：{}", Util.toJson(rule), ExceptionUtils.getStackTrace(e));
                            // 发送奖品异常，更新对应等级的奖品为未领取状态
                            updateAwardRecord(userId, activityId, goodsId, getField(rule.getRuleLevel()), BYTE_ONE);
                        }
                    } else {
                        // 活动剩余库存不足，直接更新为奖品已过期状态
                        log.info("分享有礼活动剩余库存不足！");
                        updateAwardRecord(userId, activityId, goodsId, getField(rule.getRuleLevel()), BYTE_THREE);
                    }
                }
            }
        });

    }

    /**
     * Send share award.
     *
     * @param param the param
     * @param award the award
     */
    private void sendShareAward(AwardParam param, Award award) {
        SendAwardImpl.builder().setAwardParam(param).build().send(award);
    }

    /**
     * Send award.
     *
     * @param awardType the award type
     * @param param     the param
     */
    private void sendAward(Byte awardType, AwardParam param) {
        Award award = AwardFactory.getAward(awardType);
        sendShareAward(param, award);
    }

    /**
     * Add share receive.添加领奖记录
     *
     * @param userId  the user id
     * @param shareId the share id
     * @param goodsId the goods id
     * @param level   the level
     */
    private void addShareReceive(int userId, int shareId, int goodsId, byte level) {
        ShareAwardReceiveRecord record = db().newRecord(AWARD_RECEIVE);
        record.setUserId(userId);
        record.setShareId(shareId);
        record.setGoodsId(goodsId);
        record.setAwardLevel(level);
        record.insert();
    }

    /**
     * Is attend.是否已参与该分享链接活动/是否已点击过该链接
     *
     * @param launchUserId the launch user id
     * @param userId       the user id
     * @param goodId       the good id
     * @param activityId   the activity id
     * @return the boolean
     */
    private boolean isAttend(Integer launchUserId, Integer userId, Integer goodId, Integer activityId) {
        return db().fetchExists(ATTEND, ATTEND.SHARE_ID.eq(activityId).and(ATTEND.USER_ID.eq(userId)).and(ATTEND.LAUNCH_USER_ID.eq(launchUserId)).and(ATTEND.GOODS_ID.eq(goodId)));
    }

    /**
     * Add attend.
     *
     * @param record the record
     */
    private void addAttend(AttendShareUserRecord record) {
        db().executeInsert(record);
    }

    /**
     * Update award record.
     *
     * @param <T>        the type parameter
     * @param userId     the user id
     * @param activityId the activity id
     * @param goodsId    the goods id
     * @param field      the field
     * @param value      the value
     */
    private <T> void updateAwardRecord(int userId, int activityId, int goodsId, Field<T> field, Field<T> value) {
        db().update(AWARD_RECORD).set(field, value)
            .where(AWARD_RECORD.USER_ID.eq(userId).and(AWARD_RECORD.SHARE_ID.eq(activityId)).and(AWARD_RECORD.GOODS_ID.eq(goodsId)))
            .execute();
    }

    /**
     * Update award record.
     *
     * @param <T>        the type parameter
     * @param userId     the user id
     * @param activityId the activity id
     * @param goodsId    the goods id
     * @param field      the field
     * @param value      the value
     */
    private <T> void updateAwardRecord(int userId, int activityId, int goodsId, Field<T> field, T value) {
        db().update(AWARD_RECORD).set(field, value)
            .where(AWARD_RECORD.USER_ID.eq(userId).and(AWARD_RECORD.SHARE_ID.eq(activityId)).and(AWARD_RECORD.GOODS_ID.eq(goodsId)))
            .execute();
    }

    /**
     * Update award.
     *
     * @param <T>        the type parameter
     * @param activityId the activity id
     * @param field      the field
     * @param value      the value
     * @param condition  the condition
     */
    private <T> void updateAward(int activityId, Field<T> field, Field<T> value, Condition condition) {
        int result = db().update(AWARD).set(field, value).where(AWARD.ID.eq(activityId)).and(condition).execute();
        Assert.isTrue(result > 0, JsonResultCode.CODE_FAIL);
    }

    private TableField<ShareAwardRecordRecord, Byte> getField(Byte ruleLevel) {
        switch (ruleLevel) {
            case 1:
                return AWARD_RECORD.FIRST_AWARD;
            case 2:
                return AWARD_RECORD.SECOND_AWARD;
            case 3:
                return AWARD_RECORD.THIRD_AWARD;
            default:
                throw new IllegalStateException("Unexpected value: " + ruleLevel);
        }
    }

    private TableField<com.vpu.mp.db.shop.tables.records.ShareAwardRecord, Integer> getAwardField(Byte ruleLevel) {
        switch (ruleLevel) {
            case 1:
                return AWARD.FIRST_AWARD_NUM;
            case 2:
                return AWARD.SECOND_AWARD_NUM;
            case 3:
                return AWARD.THIRD_AWARD_NUM;
            default:
                throw new IllegalStateException("Unexpected value: " + ruleLevel);
        }
    }

}
