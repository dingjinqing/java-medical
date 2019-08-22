package com.vpu.mp.service.shop.market.sharereward;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.vpu.mp.db.shop.tables.AttendShareUser;
import com.vpu.mp.db.shop.tables.ShareAward;
import com.vpu.mp.db.shop.tables.ShareAwardReceive;
import com.vpu.mp.db.shop.tables.ShareAwardRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.FieldsUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.sharereward.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.Record11;
import org.jooq.SelectConditionStep;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.Goods.GOODS;
import static com.vpu.mp.db.shop.tables.ShopCfg.SHOP_CFG;
import static com.vpu.mp.db.shop.tables.User.USER;
import static com.vpu.mp.service.pojo.shop.market.form.FormConstant.MAPPER;
import static com.vpu.mp.service.pojo.shop.market.increasepurchase.PurchaseConstant.*;

/**
 * @author liufei
 * @date 2019/8/19
 * @description
 */
@Slf4j
@Service
public class ShareRewardService extends ShopBaseService {
    private static ShareAward sa = ShareAward.SHARE_AWARD.as("sa");
    private static ShareAwardRecord sar = ShareAwardRecord.SHARE_AWARD_RECORD.as("sar");
    private static ShareAwardReceive sare = ShareAwardReceive.SHARE_AWARD_RECEIVE.as("sare");
    private static AttendShareUser asu = AttendShareUser.ATTEND_SHARE_USER.as("asu");

    /**
     * 分页查询分享有礼活动信息
     *
     * @param param 查询条件
     * @return 分页数据
     */
    public PageResult<ShareRewardShowVo> selectByPage(ShareRewardShowParam param) {
        Condition categoryConditon = sa.ID.isNotNull();
        switch (param.getCategory()) {
            // 所有0
            case PURCHASE_ALL:
                break;
            // 已停用1
            case PURCHASE_TERMINATED:
                categoryConditon = categoryConditon.and(sa.STATUS.eq((byte) 1));
                break;
            // 已过期2
            case PURCHASE_EXPIRED:
                categoryConditon = categoryConditon.and(sa.END_TIME.lessThan(new Timestamp(System.currentTimeMillis()))).and(sa.STATUS.notEqual((byte) 1)).and(sa.IS_FOREVER.eq((byte) 0));
                break;
            // 未开始4
            case PURCHASE_PREPARE:
                categoryConditon = categoryConditon.and(sa.START_TIME.greaterThan(new Timestamp(System.currentTimeMillis()))).and(sa.STATUS.notEqual((byte) 1)).and(sa.IS_FOREVER.eq((byte) 0));
                break;
            // 进行中8
            case PURCHASE_PROCESSING:
                categoryConditon = categoryConditon.and(sa.STATUS.eq((byte) 1)).and(sa.IS_FOREVER.eq((byte) 1)).or(sa.START_TIME.lessThan(new Timestamp(System.currentTimeMillis()))).and(sa.END_TIME.greaterThan(new Timestamp(System.currentTimeMillis())));
                break;
            // 默认进行中8
            default:
                categoryConditon = categoryConditon.and(sa.STATUS.eq((byte) 1)).and(sa.IS_FOREVER.eq((byte) 1)).or(sa.START_TIME.lessThan(new Timestamp(System.currentTimeMillis()))).and(sa.END_TIME.greaterThan(new Timestamp(System.currentTimeMillis())));
                break;
        }
        Table<Record11<Integer, String, Byte, Byte, Timestamp, Timestamp, String, String, String, Integer, Byte>> conditionStep = db().
            select(sa.ID, sa.NAME, sa.CONDITION, sa.IS_FOREVER, sa.START_TIME, sa.END_TIME, sa.FIRST_LEVEL_RULE, sa.SECOND_LEVEL_RULE, sa.THIRD_LEVEL_RULE, sa.PRIORITY, sa.STATUS).from(sa).where(categoryConditon).asTable("sa");

        Condition selectConditon = sa.ID.isNotNull();
        // TODO 页面筛选条件待定，此处省略筛选condition

        SelectConditionStep<Record11<Integer, String, Byte, Byte, Timestamp, Timestamp, String, String, String, Integer, Byte>> resultStep = db().
            select(sa.ID, sa.NAME, sa.CONDITION, sa.IS_FOREVER, sa.START_TIME, sa.END_TIME, sa.FIRST_LEVEL_RULE, sa.SECOND_LEVEL_RULE, sa.THIRD_LEVEL_RULE, sa.PRIORITY, sa.STATUS).from(conditionStep).where(selectConditon);
        PageResult<ShareRewardShowVo> pageResult = this.getPageResult(resultStep, param.getCurrentPage(), param.getPageRows(), ShareRewardShowVo.class);

        for (ShareRewardShowVo vo : pageResult.getDataList()) {
            if (vo.getIsForever() == 1) {
                vo.setValidityPeriod("1");
            } else {
                String validityperiod = String.format("%s---%s", vo.getStartTime(), vo.getEndTime());
                log.debug("分享有礼活动有效期格式为：[{}]", validityperiod);
                vo.setValidityPeriod(validityperiod);
            }
            vo.setRewardType(getRewardType(vo.getFirstLevelRule(), vo.getSecondLevelRule(), vo.getThirdLevelRule()));
            vo.setShareNum(getShareNum(vo.getId()));
            vo.setInviteNum(getInviteNum(vo.getId()));
        }
        return pageResult;
    }

    /**
     * 分享规则json串中，奖励类型字段常量
     */
    private static final String REWARD_TYPE = "reward_type";

    /**
     * 根据分享规则获取分享后的奖励类型，包括积分，优惠券，幸运大抽奖等
     *
     * @param rules 分享规则，可以有多个
     */
    private Set<Byte> getRewardType(String... rules) {
        if (rules.length == 0) {
            return null;
        }
        return Arrays.stream(rules).filter(StringUtils::isNotEmpty).map(rule -> {
            try {
                JsonNode node = MAPPER.readTree(rule);
                return Byte.valueOf(node.get(REWARD_TYPE).asText());
            } catch (IOException e) {
                log.debug("[{}] Serialization Exception !", rule);
                throw new RuntimeException("Serialization Exception !");
            }
        }).collect(Collectors.toSet());
    }

    /**
     * 分享人数
     *
     * @param shareId 分享活动id
     */
    private Integer getShareNum(Integer shareId) {
        return db().selectCount().from(sar).where(sar.SHARE_ID.eq(shareId)).fetchOptionalInto(Integer.class).orElse(0);
    }

    /**
     * 邀请人数
     *
     * @param shareId 分享活动id
     */
    private Integer getInviteNum(Integer shareId) {
        return db().selectCount().from(asu).where(asu.SHARE_ID.eq(shareId)).fetchOptionalInto(Integer.class).orElse(0);
    }


    private static final Byte IS_FOREVER = 1;
    private static final byte CONDITION = 0;
    private static final byte CONDITION_ONE = 1;
    private static final byte CONDITION_TWO = 2;
    private static final byte CONDITION_THREE = 3;

    /**
     * 添加分享有礼活动
     *
     * @param param 分享有礼活动详情参数
     */
    public void addShareReward(ShareRewardAddParam param) {
        com.vpu.mp.db.shop.tables.records.ShareAwardRecord awardRecord = buildOptions(param);
        awardRecord.setId(null);
        db().executeInsert(awardRecord);
    }

    /**
     * 获取分享有礼活动详情
     *
     * @param shareId 分享有礼活动id
     */
    public ShareRewardInfoVo getShareRewardInfo(Integer shareId) {
        return db().selectFrom(sa).where(sa.ID.eq(shareId)).fetchOneInto(ShareRewardInfoVo.class);
    }

    /**
     * 更新分享有礼活动
     *
     * @param param 分享有礼活动详情参数
     */
    public void updateShareReward(ShareRewardAddParam param) {
        com.vpu.mp.db.shop.tables.records.ShareAwardRecord awardRecord = buildOptions(param);
        db().executeUpdate(awardRecord);
    }

    /**
     * 构建添加/更新的record
     *
     * @param param 分享有礼活动详情参数
     * @return {@link com.vpu.mp.db.shop.tables.records.ShareAwardRecord}
     */
    private com.vpu.mp.db.shop.tables.records.ShareAwardRecord buildOptions(ShareRewardAddParam param) {
        try {
            com.vpu.mp.db.shop.tables.records.ShareAwardRecord awardRecord = new com.vpu.mp.db.shop.tables.records.ShareAwardRecord();
            if (IS_FOREVER.equals(param.getIsForever())) {
                param.setStartTime(null);
                param.setEndTime(null);
            }
            if (CONDITION_ONE == param.getCondition()) {
                param.setGoodsIds(null);
                param.setGoodsPv(null);
            }
            if (CONDITION_TWO == param.getCondition()) {
                param.setGoodsPv(null);
            }
            if (CONDITION_THREE == param.getCondition()) {
                param.setGoodsIds(null);
            }
            param.setFirstLevelRule(MAPPER.writeValueAsString(param.getFirstRule()));
            param.setSecondLevelRule(MAPPER.writeValueAsString(param.getSecondRule()));
            param.setThirdLevelRule(MAPPER.writeValueAsString(param.getThirdRule()));

            FieldsUtil.assignNotNull(param, awardRecord);

            return awardRecord;
        } catch (JsonProcessingException e) {
            log.debug(e.getMessage());
            throw new RuntimeException("Serialization Exception !");
        }
    }

    /**
     * 停用/启用/删除
     *
     * @param param 活动状态/删除标识
     */
    public void changeActivity(ShareRewardStatusParam param) {
        switch (param.getStatus()) {
            case CONDITION:
                db().update(sa).set(sa.STATUS, CONDITION).where(sa.ID.eq(param.getShareId())).execute();
                break;
            case CONDITION_ONE:
                db().update(sa).set(sa.STATUS, CONDITION_ONE).where(sa.ID.eq(param.getShareId())).execute();
                break;
            case CONDITION_TWO:
                db().update(sa).set(sa.DEL_FLAG, CONDITION_ONE).where(sa.ID.eq(param.getShareId())).execute();
                break;
            default:
                break;
        }
    }

    /**
     * 分享有礼活动奖励领取明细查询
     *
     * @param param 活动id和筛选条件
     * @return 分页数据
     */
    public PageResult<ShareReceiveDetailVo> shareReceiveDetail(ShareReceiveDetailParam param) {
        SelectConditionStep<Record11<Integer, Integer, String, String, Integer, String, Byte, String, String, String, Timestamp>> conditionStep = db().select(sare.SHARE_ID, sare.USER_ID, USER.USERNAME, USER.MOBILE, sare.GOODS_ID, GOODS.GOODS_NAME, sare.AWARD_LEVEL, sa.FIRST_LEVEL_RULE, sa.SECOND_LEVEL_RULE, sa.THIRD_LEVEL_RULE, sare.CREATE_TIME).from(sare).leftJoin(sa).on(sare.SHARE_ID.eq(sa.ID)).leftJoin(GOODS).on(sare.GOODS_ID.eq(GOODS.GOODS_ID)).leftJoin(USER).on(sare.USER_ID.eq(USER.USER_ID)).where(sare.SHARE_ID.eq(param.getShareId()));

        if (StringUtils.isNotEmpty(param.getGoodsName())) {
            conditionStep = conditionStep.and(GOODS.GOODS_NAME.like(this.likeReplace(param.getGoodsName())));
        }
        if (StringUtils.isNotEmpty(param.getMobile())) {
            conditionStep = conditionStep.and(USER.MOBILE.eq(param.getMobile()));
        }
        if (StringUtils.isNotEmpty(param.getUsername())) {
            conditionStep = conditionStep.and(USER.USERNAME.like(this.likeReplace(param.getUsername())));
        }
        if (param.getRewardLevel() != null) {
            conditionStep = conditionStep.and(sare.AWARD_LEVEL.eq(param.getRewardLevel()));
        }

        PageResult<ShareReceiveDetailVo> pageResult = this.getPageResult(conditionStep, param.getCurrentPage(), param.getPageRows(), ShareReceiveDetailVo.class);

        for (ShareReceiveDetailVo vo : pageResult.getDataList()) {
            try {
                Integer shareId = vo.getShareId();
                vo.setInviteUserNum(getInviteUserNum(shareId));
                vo.setInviteNewUserNum(getInviteNewUserNum(shareId));
                switch (vo.getAwardLevel()) {
                    case CONDITION_ONE:
                        vo.setRewardType(Byte.valueOf(MAPPER.readTree(vo.getFirstLevelRule()).get(REWARD_TYPE).asText()));
                        break;
                    case CONDITION_TWO:
                        vo.setRewardType(Byte.valueOf(MAPPER.readTree(vo.getSecondLevelRule()).get(REWARD_TYPE).asText()));
                        break;
                    case CONDITION_THREE:
                        vo.setRewardType(Byte.valueOf(MAPPER.readTree(vo.getThirdLevelRule()).get(REWARD_TYPE).asText()));
                        break;
                    default:
                        break;
                }
            } catch (IOException e) {
                log.debug(e.getMessage());
                throw new RuntimeException("Serialization Exception !");
            }
        }
        return pageResult;
    }

    /**
     * 邀请用户总数
     *
     * @param shareId 分享活动id
     */
    private Integer getInviteUserNum(Integer shareId) {
        return db().select(DSL.countDistinct(asu.USER_ID)).from(asu).where(asu.SHARE_ID.eq(shareId)).fetchOptionalInto(Integer.class).orElse(0);
    }

    /**
     * 邀请新用户数
     *
     * @param shareId 分享活动id
     */
    private Integer getInviteNewUserNum(Integer shareId) {
        return db().select(DSL.countDistinct(asu.USER_ID)).from(asu).where(asu.SHARE_ID.eq(shareId)).and(asu.IS_NEW.eq(CONDITION_ONE)).fetchOptionalInto(Integer.class).orElse(0);
    }

    /**
     * 分享有礼活动-每日用户可分享次数上限参数常量
     */
    private static final String DAILY_SHARE_AWARD = "daily_share_award";

    /**
     * 更新每日用户可分享次数上限参数
     *
     * @param value 每日用户可分享次数上限值
     */
    public void updateDailyShareAward(Integer value) {
        db().update(SHOP_CFG).set(SHOP_CFG.V, String.valueOf(value)).where(SHOP_CFG.K.eq(DAILY_SHARE_AWARD)).execute();
    }

    /**
     * 获取每日用户可分享次数上限参数
     *
     * @return 每日用户可分享次数上限值
     */
    public String getDailyShareAwardValue() {
        return db().select(SHOP_CFG.V).from(SHOP_CFG).where(SHOP_CFG.K.eq(DAILY_SHARE_AWARD)).fetchOneInto(String.class);
    }
}
