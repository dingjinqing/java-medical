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
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.Goods.GOODS;
import static com.vpu.mp.db.shop.tables.ShopCfg.SHOP_CFG;
import static com.vpu.mp.db.shop.tables.User.USER;
import static com.vpu.mp.service.pojo.shop.market.form.FormConstant.MAPPER;
import static com.vpu.mp.service.pojo.shop.market.increasepurchase.PurchaseConstant.*;
import static com.vpu.mp.service.pojo.shop.market.sharereward.ShareConstant.*;

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
        //已删除的分享有礼活动不参与查询
        Condition categoryConditon = sa.DEL_FLAG.eq(FLAG_ZERO);
        switch (param.getCategory()) {
            // 所有0
            case PURCHASE_ALL:
                break;
            // 已停用4
            case PURCHASE_TERMINATED:
                categoryConditon = categoryConditon.and(sa.STATUS.eq(FLAG_ONE));
                break;
            // 已过期3
            case PURCHASE_EXPIRED:
                categoryConditon = categoryConditon.and(sa.END_TIME.lessThan(Timestamp.valueOf(LocalDateTime.now()))).and(sa.IS_FOREVER.eq(FLAG_ZERO));
                break;
            // 未开始2
            case PURCHASE_PREPARE:
                categoryConditon = categoryConditon.and(sa.START_TIME.greaterThan(Timestamp.valueOf(LocalDateTime.now()))).and(sa.IS_FOREVER.eq(FLAG_ZERO));
                break;
            // 默认进行中1
            default:
                categoryConditon = categoryConditon.and(sa.IS_FOREVER.eq(FLAG_ONE).or(sa.START_TIME.lessThan(Timestamp.valueOf(LocalDateTime.now()))).and(sa.END_TIME.greaterThan(Timestamp.valueOf(LocalDateTime.now()))));
                break;
        }
        Table<Record12<Integer, String, Byte, Integer, Byte, Timestamp, Timestamp, String, String, String, Integer, Byte>> conditionStep = db().
            select(sa.ID, sa.NAME, sa.CONDITION, sa.GOODS_PV, sa.IS_FOREVER, sa.START_TIME, sa.END_TIME, sa.FIRST_LEVEL_RULE, sa.SECOND_LEVEL_RULE, sa.THIRD_LEVEL_RULE, sa.PRIORITY, sa.STATUS).from(sa).where(categoryConditon).asTable("sa");

        Condition selectConditon = sa.ID.isNotNull();
        // TODO 页面筛选条件待定，此处省略筛选condition

        SelectConditionStep<Record12<Integer, String, Byte, Integer, Byte, Timestamp, Timestamp, String, String, String, Integer, Byte>> resultStep = db().
            select(sa.ID, sa.NAME, sa.CONDITION, sa.GOODS_PV, sa.IS_FOREVER, sa.START_TIME, sa.END_TIME, sa.FIRST_LEVEL_RULE, sa.SECOND_LEVEL_RULE, sa.THIRD_LEVEL_RULE, sa.PRIORITY, sa.STATUS).from(conditionStep).where(selectConditon);
        PageResult<ShareRewardShowVo> pageResult = this.getPageResult(resultStep, param.getCurrentPage(), param.getPageRows(), ShareRewardShowVo.class);

        for (ShareRewardShowVo vo : pageResult.getDataList()) {
            if (vo.getIsForever() == 1) {
                vo.setValidityPeriod("1");
            } else {
                String validityperiod = String.format("%s---%s", vo.getStartTime(), vo.getEndTime());
                log.debug("分享有礼活动有效期格式为：[{}]", validityperiod);
                vo.setValidityPeriod(validityperiod);
            }
            //设置活动的实时具体状态，进行中，未开始，已过期，已停用
            if (PURCHASE_ALL == param.getCategory()) {
                vo.setPageStatus(getPageStatus(vo));
            } else {
                vo.setPageStatus(param.getCategory());
            }
            vo.setRewardType(getRewardType(vo.getFirstLevelRule(), vo.getSecondLevelRule(), vo.getThirdLevelRule()));
            vo.setShareNum(getShareNum(vo.getId()));
            vo.setInviteNum(getInviteNum(vo.getId()));
        }
        return pageResult;
    }

    /**
     * 当查询所有的活动时，需要判定每一个活动的实时具体状态
     */
    private Byte getPageStatus(ShareRewardShowVo vo) {
        if (FLAG_ONE.equals(vo.getStatus())) {
            //已停用状态
            return PURCHASE_TERMINATED;
        }
        if (vo.getEndTime().toLocalDateTime().isBefore(LocalDateTime.now())) {
            //已过期状态
            return PURCHASE_EXPIRED;
        }
        if (vo.getStartTime().toLocalDateTime().isAfter(LocalDateTime.now())) {
            //未开始状态
            return PURCHASE_PREPARE;
        }
        if (vo.getStartTime().toLocalDateTime().isBefore(LocalDateTime.now()) && vo.getEndTime().toLocalDateTime().isAfter(LocalDateTime.now())) {
            //进行中状态
            return PURCHASE_PROCESSING;
        }
        return null;
    }

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
        ShareRewardInfoVo shareRewardInfoVo = db().selectFrom(sa).where(sa.ID.eq(shareId)).fetchOneInto(ShareRewardInfoVo.class);
        ShareRule[] shareRules = new ShareRule[3];
        shareRules[0] = MAPPER.convertValue(shareRewardInfoVo.getFirstLevelRule(),ShareRule.class);
        shareRules[1] = MAPPER.convertValue(shareRewardInfoVo.getSecondLevelRule(),ShareRule.class);
        shareRules[2] = MAPPER.convertValue(shareRewardInfoVo.getThirdLevelRule(),ShareRule.class);
        shareRewardInfoVo.setShareRules(shareRules);
        return shareRewardInfoVo;
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
            if (FLAG_ONE.equals(param.getIsForever())) {
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
     *              0启用，1停用，2删除
     */
    public void changeActivity(ShareRewardStatusParam param) {
        switch (param.getStatus()) {
            case CONDITION_ZERO:
                //启用
                db().update(sa).set(sa.STATUS, FLAG_ZERO).where(sa.ID.eq(param.getShareId())).execute();
                break;
            case CONDITION_ONE:
                //停用
                db().update(sa).set(sa.STATUS, FLAG_ONE).where(sa.ID.eq(param.getShareId())).execute();
                break;
            case CONDITION_TWO:
                //删除
                db().update(sa).set(sa.DEL_FLAG, FLAG_ONE).where(sa.ID.eq(param.getShareId())).execute();
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
            conditionStep = conditionStep.and(USER.MOBILE.like(this.likeReplace(param.getMobile())));
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
                        JsonNode fNode = MAPPER.readTree(vo.getFirstLevelRule());
                        vo.setRewardType(Byte.valueOf(fNode.get(REWARD_TYPE).asText()));
                        vo.setScore(fNode.get(SCORE).asInt());
                        break;
                    case CONDITION_TWO:
                        JsonNode sNode = MAPPER.readTree(vo.getSecondLevelRule());
                        vo.setRewardType(Byte.valueOf(sNode.get(REWARD_TYPE).asText()));
                        vo.setScore(sNode.get(SCORE).asInt());
                        break;
                    case CONDITION_THREE:
                        JsonNode tNode = MAPPER.readTree(vo.getThirdLevelRule());
                        vo.setRewardType(Byte.valueOf(tNode.get(REWARD_TYPE).asText()));
                        vo.setScore(tNode.get(SCORE).asInt());
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
        return db().select(SHOP_CFG.V).from(SHOP_CFG).where(SHOP_CFG.K.eq(DAILY_SHARE_AWARD)).fetchOptionalInto(String.class).orElse("0");
    }
}
