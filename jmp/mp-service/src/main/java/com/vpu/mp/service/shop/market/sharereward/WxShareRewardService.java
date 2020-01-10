package com.vpu.mp.service.shop.market.sharereward;

import com.vpu.mp.db.shop.tables.AttendShareUser;
import com.vpu.mp.db.shop.tables.ShareAward;
import com.vpu.mp.db.shop.tables.ShareAwardReceive;
import com.vpu.mp.db.shop.tables.ShareAwardRecord;
import com.vpu.mp.db.shop.tables.records.AttendShareUserRecord;
import com.vpu.mp.db.shop.tables.records.ShareAwardRecordRecord;
import com.vpu.mp.db.shop.tables.records.ShareRecordRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.market.sharereward.ShareRewardInfoVo;
import com.vpu.mp.service.pojo.shop.market.sharereward.ShareRule;
import com.vpu.mp.service.pojo.wxapp.market.sharereward.ShareParam;
import com.vpu.mp.service.shop.user.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.vpu.mp.db.shop.Tables.SHARE_RECORD;
import static org.apache.commons.lang3.math.NumberUtils.*;

/**
 * @author liufei
 * @date 1/9/20
 */
@Slf4j
@Service
public class WxShareRewardService extends ShopBaseService {
    public static ShareAward AWARD = ShareAward.SHARE_AWARD.as("AWARD");
    public static ShareAwardRecord AWARD_RECORD = ShareAwardRecord.SHARE_AWARD_RECORD.as("AWARD_RECORD");
    public static ShareAwardReceive AWARD_RECEIVE = ShareAwardReceive.SHARE_AWARD_RECEIVE.as("AWARD_RECEIVE");
    public static AttendShareUser ATTEND = AttendShareUser.ATTEND_SHARE_USER.as("ATTEND");

    @Autowired
    private ShareRewardService shareReward;
    @Autowired
    private UserService userService;

    // 添加分享记录，同时参与分享有礼活动，两张表b2c_share_record和b2c_share_award_record
    public void addShareRecord(ShareParam param) {
        ShareRecordRecord recordRecord = shareRecord(param);
        int activityId = param.getActivityId();
        int userId = param.getUserId();
        int goodId = param.getGoodId();
        // 活动可用性校验/分享的商品有效性校验
        shareReward.activityAvailable(activityId, goodId);

        Condition exist = AWARD_RECORD.SHARE_ID.eq(activityId).and(AWARD_RECORD.USER_ID.eq(userId)).and(AWARD_RECORD.GOODS_ID.eq(goodId));
        if (shareReward.aRecordIsExist(exist)) {
            // TODO 需要更新活动现在处于等级
            int count = recordRecord.getCount();
//            Map<Byte, ShareRule> rules = shareReward.getShareRules(activityId);
        } else {
            ShareAwardRecordRecord record = db().newRecord(AWARD_RECORD);
            record.setShareId(activityId);
            record.setUserId(userId);
            record.setGoodsId(goodId);
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
    public void autoincrementCount(Condition condition) {
        db().update(SHARE_RECORD).set(SHARE_RECORD.COUNT, SHARE_RECORD.COUNT.add(INTEGER_ONE)).where(condition).execute();
    }

    /**
     * Gets record record.分享记录信息详情
     *
     * @param condition the condition
     * @return the record record
     */
    public ShareRecordRecord getRecordRecord(Condition condition) {
        return db().selectFrom(SHARE_RECORD).where(condition).fetchOne();
    }


    /**
     * Record is exist boolean.分享记录是否存在/是否已分享过
     *
     * @param condition the condition
     * @return the boolean
     */
    public boolean recordIsExist(Condition condition) {
        return db().fetchExists(SHARE_RECORD, condition);
    }

    // 商品详情判定是否为分享有礼的商品，加载的时候判定来源，是否为别人分享的入口
    public void fromShare2GoodsDetail(Integer launchUserId, Integer userId, Integer goodId, Integer activityId) {
        // 是否店铺新用户
        boolean isNew = userService.isNew(userId);

        // 用户点击别人分享的商品链接，进入商品详情页，触发参与别人发起的分享

        // 活动可用性校验/分享的商品有效性校验
        com.vpu.mp.db.shop.tables.records.ShareAwardRecord shareAward = shareReward.activityAvailable(activityId, goodId);

        // 是否已参与该分享链接活动/是否已点击过该链接
        if (isAttend(launchUserId, userId, goodId, activityId)) {
            log.info("用户 {} 已经参与过该分享活动 {} ！", userId, activityId);
            return;
        }

        ShareAwardRecordRecord awardRecord = shareReward.getShareAwardRecord(activityId, userId, goodId);

        AttendShareUserRecord attendRecord = db().newRecord(ATTEND);
        attendRecord.setRecordId(awardRecord.getId());
        attendRecord.setShareId(activityId);
        attendRecord.setGoodsId(goodId);
        attendRecord.setUserId(userId);
        attendRecord.setIsNew(isNew ? BYTE_ONE : BYTE_ZERO);
        attendRecord.setLaunchUserId(launchUserId);
        attendRecord.setLevel(awardRecord.getStatus());
        // 添加参与分享活动记录
        addAttend(attendRecord);

        // 仅邀请未访问过的用户有效；0否，1是
        if (BYTE_ZERO.equals(shareAward.getVisitFirst()) || isNew) {
            // 访问人数自增
            int count = shareReward.autoincrementUserNum(awardRecord.getId());
        }

        // 更新当前活动进行的进度
    }

    public void updateProcess(Integer activityId, int count) {
        ShareRewardInfoVo info = shareReward.getShareRules(activityId);
        List<ShareRule> list = info.getShareRules();

        /*if (Objects.nonNull(first)){
            // 满足一级规则
            if (count >= first.getInviteNum()){
                // 一级奖品库存是否充足
                if (info.getFirstAwardNum() > 0){
                    // TODO 更新分享进度，发送分享有礼奖品，发送消息模板通知用户奖品已发放

                    try {
                        // 发送分享有礼奖品， 更新为已领取
                    }catch (Exception e){
                        // 发送异常，更新为未领取
                    }
                }else {
                    // TODO 库存不足，直接更新为奖品已过期状态
                }
            }
        }*/
    }

    public void process(ShareRule rule, int count, int stock) {
        if (Objects.nonNull(rule)) {
            // 满足规则条件
            if (count >= rule.getInviteNum()) {
                // 奖品库存是否充足
                if (stock > 0) {
                    // TODO 更新分享进度，发送分享有礼奖品，发送消息模板通知用户奖品已发放

                    try {
                        // 发送分享有礼奖品， 更新为已领取
                    } catch (Exception e) {
                        // 发送异常，更新为未领取
                    }
                } else {
                    // TODO 库存不足，直接更新为奖品已过期状态
                }
            }
        }
    }

    /**
     * Is attend.是否已参与该分享链接活动/是否已点击过该链接
     *
     * @param launchUserId the launch user id
     * @param userId       the user id
     * @param goodId       the good id
     * @param activityId   the activity id
     */
    public boolean isAttend(Integer launchUserId, Integer userId, Integer goodId, Integer activityId) {
        return db().fetchExists(ATTEND, ATTEND.SHARE_ID.eq(activityId).and(ATTEND.USER_ID.eq(userId)).and(ATTEND.LAUNCH_USER_ID.eq(launchUserId)).and(ATTEND.GOODS_ID.eq(goodId)));
    }

    /**
     * Add attend.
     *
     * @param record the record
     */
    public void addAttend(AttendShareUserRecord record) {
        db().executeInsert(record);
    }

}
