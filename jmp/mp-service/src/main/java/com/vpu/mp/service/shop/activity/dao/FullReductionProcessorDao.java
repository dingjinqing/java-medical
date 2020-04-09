package com.vpu.mp.service.shop.activity.dao;

import com.vpu.mp.db.shop.tables.MrkingStrategy;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.database.DslPlus;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.market.fullcut.MrkingStrategyCondition;
import com.vpu.mp.service.pojo.shop.market.fullcut.MrkingStrategyPageListQueryVo;
import com.vpu.mp.service.pojo.shop.market.fullcut.MrkingStrategyVo;
import com.vpu.mp.service.pojo.shop.member.card.SimpleMemberCardVo;
import com.vpu.mp.service.pojo.wxapp.cart.activity.FullReductionGoodsCartBo;
import com.vpu.mp.service.pojo.wxapp.cart.list.CartActivityInfo;
import com.vpu.mp.service.pojo.wxapp.cart.list.WxAppCartBo;
import com.vpu.mp.service.pojo.wxapp.cart.list.WxAppCartGoods;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.promotion.FullReductionPromotion;
import com.vpu.mp.service.pojo.wxapp.order.goods.OrderGoodsBo;
import com.vpu.mp.service.shop.market.fullcut.MrkingStrategyService;
import com.vpu.mp.service.shop.member.UserCardService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.Record7;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.MRKING_STRATEGY;
import static com.vpu.mp.db.shop.Tables.MRKING_STRATEGY_CONDITION;

/**
 * @author 李晓冰
 * @date 2019年10月30日
 */
@Service
public class FullReductionProcessorDao extends MrkingStrategyService {

    /**
     * 全部商品参与活动
     */
    private static final byte ACT_TYPE_ALL_GOODS = 0;
    /**
     * 指定条件参与活动
     */
    private static final byte ACT_TYPE_POINT_CONDITION = 1;
    @Autowired
    private UserCardService userCard;

    /**
     * 判断根据传入的条件是否存在对应的满折满减活动
     *
     * @param goodsId 商品id
     * @param catId   平台分类id
     * @param sortId  商家分类id
     * @param brandId 品牌分类id
     * @param date    时间
     * @return true 存在对应的活动，false 不存在对应的活动
     */
    public boolean getIsFullReductionListInfo(Integer goodsId, Integer catId, Integer sortId, Integer brandId, Timestamp date) {
        Condition condition = MRKING_STRATEGY.ACT_TYPE.eq(ACT_TYPE_ALL_GOODS)
            .or(MRKING_STRATEGY.ACT_TYPE.eq(ACT_TYPE_POINT_CONDITION).and(
                DslPlus.findInSet(goodsId, MRKING_STRATEGY.RECOMMEND_GOODS_ID)
                    .or(DslPlus.findInSet(catId, MRKING_STRATEGY.RECOMMEND_CAT_ID))
                    .or(DslPlus.findInSet(sortId, MRKING_STRATEGY.RECOMMEND_SORT_ID))
                    .or(DslPlus.findInSet(brandId, MRKING_STRATEGY.RECOMMEND_BRAND_ID))
            ));

        condition = MRKING_STRATEGY.DEL_FLAG.eq(DelFlag.NORMAL.getCode())
            .and(MRKING_STRATEGY.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL)).and(MRKING_STRATEGY.START_TIME.lt(date))
            .and(MRKING_STRATEGY.END_TIME.gt(date)).and(condition);

        int count = db().fetchCount(MRKING_STRATEGY, condition);
        return count > 0;
    }

    /**
     * 获取满折满减促销信息
     * @param goodsId 商品ID
     * @param catId 平台分类ID
     * @param brandId 品牌ID
     * @param sortId  商家分类ID
     * @param now 限制时间
     * @return 满折促销信息
     */
    public List<FullReductionPromotion> getFullReductionInfoForDetail(Integer goodsId, Integer catId, Integer brandId, Integer sortId, Timestamp now) {
        Condition condition = MRKING_STRATEGY.DEL_FLAG.eq(DelFlag.NORMAL_VALUE).and(MRKING_STRATEGY.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL))
            .and(MRKING_STRATEGY.START_TIME.le(now)).and(MRKING_STRATEGY.END_TIME.ge(now));

        Condition idCondition = MRKING_STRATEGY.ACT_TYPE.eq((byte) 0).or(DslPlus.findInSet(goodsId, MRKING_STRATEGY.RECOMMEND_GOODS_ID)
            .or(DslPlus.findInSet(catId, MRKING_STRATEGY.RECOMMEND_CAT_ID)).or(DslPlus.findInSet(brandId, MRKING_STRATEGY.RECOMMEND_BRAND_ID))
            .or(DslPlus.findInSet(sortId, MRKING_STRATEGY.RECOMMEND_SORT_ID)));
        logger().debug("小程序-商品详情-获取满折满减促销");
        Map<Integer, List<Record7<Integer, String, Byte, BigDecimal, BigDecimal, Integer, BigDecimal>>> groups =
            db().select(MRKING_STRATEGY.ID, MRKING_STRATEGY.CARD_ID, MRKING_STRATEGY.TYPE, MRKING_STRATEGY_CONDITION.FULL_MONEY, MRKING_STRATEGY_CONDITION.REDUCE_MONEY,
            MRKING_STRATEGY_CONDITION.AMOUNT, MRKING_STRATEGY_CONDITION.DISCOUNT)
            .from(MRKING_STRATEGY).innerJoin(MRKING_STRATEGY_CONDITION).on(MRKING_STRATEGY.ID.eq(MRKING_STRATEGY_CONDITION.STRATEGY_ID))
            .where(condition.and(idCondition)).orderBy(MRKING_STRATEGY.STRATEGY_PRIORITY.desc(), MRKING_STRATEGY.START_TIME.desc())
            .stream().collect(Collectors.groupingBy(x -> x.get(MRKING_STRATEGY.ID)));

        List<FullReductionPromotion> returnList = new ArrayList<>(groups.size());

        for (Map.Entry<Integer, List<Record7<Integer, String, Byte, BigDecimal, BigDecimal, Integer, BigDecimal>>> entry : groups.entrySet()) {
            Integer key = entry.getKey();
            List<Record7<Integer, String, Byte, BigDecimal, BigDecimal, Integer, BigDecimal>> values = entry.getValue();

            FullReductionPromotion promotion = new FullReductionPromotion();
            promotion.setPromotionId(key);
            promotion.setRules(new ArrayList<>(values.size()));

            Record7<Integer, String, Byte, BigDecimal, BigDecimal, Integer, BigDecimal> record = values.get(0);
            promotion.setType(record.get(MRKING_STRATEGY.TYPE));
            if (StringUtils.isNotBlank(record.get(MRKING_STRATEGY.CARD_ID))) {
                promotion.setIsExclusive(true);
            } else {
                promotion.setIsExclusive(false);
            }
            for (Record7<Integer, String, Byte, BigDecimal, BigDecimal, Integer, BigDecimal> value : values) {
                FullReductionPromotion.FullReductionRule rule =new FullReductionPromotion.FullReductionRule();
                rule.setAmount(value.get(MRKING_STRATEGY_CONDITION.AMOUNT));
                rule.setDiscount(value.get(MRKING_STRATEGY_CONDITION.DISCOUNT));
                rule.setFullMoney(value.get(MRKING_STRATEGY_CONDITION.FULL_MONEY));
                rule.setReduceMoney(value.get(MRKING_STRATEGY_CONDITION.REDUCE_MONEY));
                promotion.getRules().add(rule);
            }
            returnList.add(promotion);
        }
        return returnList;
    }

    /**
     * 获取当前进行中的活动
     *
     * @param date   下单时间
     * @param straId id
     */
    public List<MrkingStrategyPageListQueryVo> getProcessingActivity(Timestamp date, Integer... straId) {
        Condition condition = MRKING_STRATEGY.DEL_FLAG.eq(DelFlag.NORMAL.getCode())
            .and(MRKING_STRATEGY.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL))
            .and(MRKING_STRATEGY.START_TIME.lt(date))
            .and(MRKING_STRATEGY.END_TIME.gt(date));
        if (straId != null && straId.length > 0) {
            condition.and(MRKING_STRATEGY.ID.in(straId));
        }
        List<MrkingStrategyPageListQueryVo> result = db().select(MrkingStrategy.MRKING_STRATEGY.ID, MrkingStrategy.MRKING_STRATEGY.ACT_NAME, MrkingStrategy.MRKING_STRATEGY.TYPE, MrkingStrategy.MRKING_STRATEGY.START_TIME, MrkingStrategy.MRKING_STRATEGY.END_TIME, MrkingStrategy.MRKING_STRATEGY.STATUS)
            .from(MrkingStrategy.MRKING_STRATEGY)
            .where(condition)
            .orderBy(MRKING_STRATEGY.STRATEGY_PRIORITY.desc(), MRKING_STRATEGY.CREATE_TIME.desc())
            .fetchInto(MrkingStrategyPageListQueryVo.class);
        return result;
    }

    /**
     * 校验
     *
     * @param activityInfo 活动详情
     * @param cardIds      用户有效会员卡
     */
    public boolean checkMemberCard(MrkingStrategyVo activityInfo, Set<Integer> cardIds) {
        if (CollectionUtils.isEmpty(activityInfo.getMemberCards())) {
            return true;
        }
        Set<Integer> ids = activityInfo.getMemberCards().stream().map(SimpleMemberCardVo::getId).collect(Collectors.toSet());
        ids.retainAll(cardIds);
        if (CollectionUtils.isNotEmpty(ids)) {
            return true;
        }
        return false;
    }

    /**
     * 校验
     *
     * @param activityInfo 活动详情
     * @param goods        商品
     */
    public boolean checkGoods(MrkingStrategyVo activityInfo, OrderGoodsBo goods) {
        if (ACT_TYPE_ALL_GOODS == activityInfo.getActType()) {
            logger().info("满折满减：支持全部商品,活动id:{}", activityInfo.getId());
            return true;
        } else {
            if (CollectionUtils.isNotEmpty(activityInfo.getRecommendGoodsIds())) {
                if (activityInfo.getRecommendGoodsIds().contains(goods.getGoodsId())) {
                    logger().info("满折满减：指定商品满足,活动id:{}", activityInfo.getId());
                    return true;
                }
            }
            if (CollectionUtils.isNotEmpty(activityInfo.getRecommendCatIds())) {
                if (activityInfo.getRecommendCatIds().contains(goods.getCatId())) {
                    logger().info("满折满减：指定普通分类满足,活动id:{}", activityInfo.getId());
                    return true;
                }
            }
            if (CollectionUtils.isNotEmpty(activityInfo.getRecommendSortIds())) {
                if (activityInfo.getRecommendSortIds().contains(goods.getGoodsId())) {
                    logger().info("满折满减：指定商家分类满足,活动id:{}", activityInfo.getId());
                    return true;
                }
            }
            if (CollectionUtils.isNotEmpty(activityInfo.getRecommendBrandIds())) {
                if (activityInfo.getRecommendBrandIds().contains(goods.getGoodsId())) {
                    logger().info("满折满减：指定品牌满足,活动id:{}", activityInfo.getId());
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 得到当满折满减优惠金额，如果为0，则不符合条件
     *
     * @param activityInfo 活动信息
     * @param bos          商品
     * @param num          商品总数量
     * @param price        商品总价格
     */
    public BigDecimal calculateFullReduce(MrkingStrategyVo activityInfo, List<OrderGoodsBo> bos, int num, BigDecimal price) {
        // 倒序排列，阶梯满减满折，从最大值金额开始判断
        Collections.sort(activityInfo.getCondition());
        //1每满减 2满减 3满折 4仅第X件打折
        for (MrkingStrategyCondition condition : activityInfo.getCondition()) {
            BigDecimal result = null;
            switch (activityInfo.getType()) {
                case 1:
                    //每满减
                    if (BigDecimalUtil.compareTo(condition.getFullMoney(), null) > 0 && condition.getFullMoney().compareTo(price) < 1) {
                        result = BigDecimalUtil.multiply(
                            condition.getReduceMoney(),
                            new BigDecimal(BigDecimalUtil.divide(price, condition.getFullMoney(), RoundingMode.FLOOR).intValue()));
                    } else if (condition.getAmount() != null && condition.getAmount() > 0 && condition.getAmount() <= num) {
                        result = BigDecimalUtil.multiply(
                            condition.getReduceMoney(),
                            new BigDecimal(num / condition.getAmount()));
                    }
                    break;
                case 2:
                    //满减
                    if (BigDecimalUtil.compareTo(condition.getFullMoney(), null) > 0 && condition.getFullMoney().compareTo(price) < 1) {
                        result = condition.getReduceMoney();
                    } else if (condition.getAmount() != null && condition.getAmount() > 0 && condition.getAmount() <= num) {
                        result = condition.getReduceMoney();
                    }
                    break;
                case 3:
                    //满折
                    if ((BigDecimalUtil.compareTo(condition.getFullMoney(), null) > 0 && condition.getFullMoney().compareTo(price) < 1) ||
                        (condition.getAmount() != null && condition.getAmount() > 0 && condition.getAmount() <= num)) {
                        result =
                            price.subtract(
                                BigDecimalUtil.multiplyOrDivide(
                                    BigDecimalUtil.BigDecimalPlus.create(price, BigDecimalUtil.Operator.multiply),
                                    BigDecimalUtil.BigDecimalPlus.create(condition.getDiscount(), BigDecimalUtil.Operator.divide),
                                    BigDecimalUtil.BigDecimalPlus.create(BigDecimal.TEN, null)
                                )
                            );
                    }
                    break;
                case 4:
                    BigDecimal temp = null;
                    for (OrderGoodsBo bo : bos) {
                        if (bo.getGoodsNumber() >= condition.getAmount()) {
                            temp = temp == null ? BigDecimal.ZERO : temp;
                            temp = temp.add(
                                bo.getDiscountedGoodsPrice().subtract(
                                    BigDecimalUtil.multiplyOrDivide(
                                        BigDecimalUtil.BigDecimalPlus.create(bo.getDiscountedGoodsPrice(), BigDecimalUtil.Operator.multiply),
                                        BigDecimalUtil.BigDecimalPlus.create(condition.getDiscount(), BigDecimalUtil.Operator.divide),
                                        BigDecimalUtil.BigDecimalPlus.create(BigDecimal.TEN, null)
                                    )
                                )
                            );
                        }
                    }
                    result = temp;
                    break;
                default:
            }
            if (result != null) {
                return result;
            }
        }
        return BigDecimal.ZERO;
    }

    /**
     * 获取商品的满折满减活动--购物车
     *
     * @param goodsId 商品id
     * @param catId 平台分类ID
     * @param brandId 品牌ID
     * @param sortId  商家分类ID
     * @param date 限制时间
     */
    public List<CartActivityInfo> getGoodsFullReductionActivityList(Integer goodsId, Integer catId, Integer brandId, Integer sortId,List<Integer> cardIds, Timestamp date) {
        Condition condition = MRKING_STRATEGY.DEL_FLAG.eq(DelFlag.NORMAL_VALUE).and(MRKING_STRATEGY.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL))
                .and(MRKING_STRATEGY.START_TIME.le(date)).and(MRKING_STRATEGY.END_TIME.ge(date));

        Condition idCondition = MRKING_STRATEGY.ACT_TYPE.eq((byte) 0).or(DslPlus.findInSet(goodsId, MRKING_STRATEGY.RECOMMEND_GOODS_ID)
                .or(DslPlus.findInSet(catId, MRKING_STRATEGY.RECOMMEND_CAT_ID)).or(DslPlus.findInSet(brandId, MRKING_STRATEGY.RECOMMEND_BRAND_ID))
                .or(DslPlus.findInSet(sortId, MRKING_STRATEGY.RECOMMEND_SORT_ID)));
        Map<Integer, List<Record7<Integer, String, Byte, BigDecimal, BigDecimal, Integer, BigDecimal>>> fullReductionMap =
                db().select(MRKING_STRATEGY.ID, MRKING_STRATEGY.CARD_ID, MRKING_STRATEGY.TYPE,
                        MRKING_STRATEGY_CONDITION.FULL_MONEY, MRKING_STRATEGY_CONDITION.REDUCE_MONEY,
                        MRKING_STRATEGY_CONDITION.AMOUNT, MRKING_STRATEGY_CONDITION.DISCOUNT)
                        .from(MRKING_STRATEGY)
                        .innerJoin(MRKING_STRATEGY_CONDITION).on(MRKING_STRATEGY.ID.eq(MRKING_STRATEGY_CONDITION.STRATEGY_ID))
                        .where(condition.and(idCondition))
                        .orderBy(MRKING_STRATEGY.STRATEGY_PRIORITY.desc(), MRKING_STRATEGY.START_TIME.desc())
                        .stream().collect(Collectors.groupingBy(x -> x.get(MRKING_STRATEGY.ID)));

        List<CartActivityInfo> cartActivityInfos =new ArrayList<>(fullReductionMap.keySet().size());
        AA:
        for (Map.Entry<Integer, List<Record7<Integer, String, Byte, BigDecimal, BigDecimal, Integer, BigDecimal>>> entry : fullReductionMap.entrySet()) {
            Integer key = entry.getKey();
            List<Record7<Integer, String, Byte, BigDecimal, BigDecimal, Integer, BigDecimal>> values = entry.getValue();

            CartActivityInfo activityInfo = new CartActivityInfo();
            CartActivityInfo.FullReduction fullReduction =new CartActivityInfo.FullReduction();

            activityInfo.setActivityId(key);
            activityInfo.setActivityType(BaseConstant.ACTIVITY_TYPE_FULL_REDUCTION);
            activityInfo.setFullReduction(fullReduction);

            Record7<Integer, String, Byte, BigDecimal, BigDecimal, Integer, BigDecimal> record = values.get(0);
            fullReduction.setFullReductiontype(record.get(MRKING_STRATEGY.TYPE));
            //会员专享
            if (StringUtils.isNotBlank(record.get(MRKING_STRATEGY.CARD_ID))) {
                fullReduction.setIsExclusive(true);
                for (Integer id : cardIds) {
                    if (!Util.splitValueToList(record.get(MRKING_STRATEGY.CARD_ID)).contains(id)) {
                        break AA;
                    }
                }
            }
            fullReduction.setRules(new ArrayList<>(values.size()));
            for (Record7<Integer, String, Byte, BigDecimal, BigDecimal, Integer, BigDecimal> value : values) {
                CartActivityInfo.FullReductionRule rule = new CartActivityInfo.FullReductionRule();
                rule.setAmount(value.get(MRKING_STRATEGY_CONDITION.AMOUNT));
                rule.setDiscount(value.get(MRKING_STRATEGY_CONDITION.DISCOUNT));
                rule.setFullMoney(value.get(MRKING_STRATEGY_CONDITION.FULL_MONEY));
                rule.setReduceMoney(value.get(MRKING_STRATEGY_CONDITION.REDUCE_MONEY));
                if (fullReduction.getFullReductiontype()<4){
                    if (rule.getAmount()>0){
                        fullReduction.setRulesType((byte) 2);
                    }else{
                        fullReduction.setRulesType((byte) 1);
                    }
                }
                fullReduction.getRules().add(rule);
            }
            cartActivityInfos.add(activityInfo);
        }
        return cartActivityInfos;
    }

    /**
     * 国际化
     *
     * @param fullReduction
     * @param rule 满折满减规则
     * @return 国际化
     */
    public String fullReductionRuleToString(CartActivityInfo.FullReduction fullReduction, CartActivityInfo.FullReductionRule rule) {
        //满减
        switch (fullReduction.getFullReductiontype()){
            case 1://每满减
                if (rule.getFullMoney().compareTo(BigDecimal.ZERO)>0&&rule.getReduceMoney()!=null){
                    return "每满"+rule.getFullMoney()+"元,减"+rule.getReduceMoney()+"元";
                }
                if (rule.getAmount()>0&&rule.getReduceMoney()!=null){
                    return "每满"+rule.getAmount()+"件,减"+rule.getReduceMoney()+"元";
                }
                break;
            case 2://满减
                if (rule.getFullMoney().compareTo(BigDecimal.ZERO)>0&&rule.getReduceMoney()!=null){
                    return "满"+rule.getFullMoney()+"元,减"+rule.getReduceMoney()+"元";
                }
                if (rule.getAmount()>0&&rule.getReduceMoney()!=null){
                    return "满"+rule.getAmount()+"件,减"+rule.getReduceMoney()+"元";
                }
                break;
            case 3://3满折
                if (rule.getFullMoney().compareTo(BigDecimal.ZERO)>0&&rule.getReduceMoney()!=null){
                    return "满"+rule.getFullMoney()+"折,打"+rule.getReduceMoney()+"折";
                }
                if (rule.getAmount()>0&&rule.getReduceMoney()!=null){
                    return "满"+rule.getAmount()+"折,打"+rule.getReduceMoney()+"折";
                }
                break;
            case 4://第几件=
                if (rule.getAmount()>0&&rule.getReduceMoney()!=null){
                    return "满"+rule.getAmount()+"折,打"+rule.getReduceMoney()+"折";
                }
                break;
            default:
        }
        return "";
    }

    /**
     * 活动选择
     * @param ruleCartIdMap
     * @param cartActivityInfos
     */
    public void fullReductionRuleOption(Map<Integer, List<FullReductionGoodsCartBo>> ruleCartIdMap, Set<CartActivityInfo> cartActivityInfos) {
        //判断商品参与活动信息
        for (Map.Entry<Integer, List<FullReductionGoodsCartBo>> entry : ruleCartIdMap.entrySet()) {
            Integer ruleId = entry.getKey();
            List<FullReductionGoodsCartBo> fullReductionGoodsList = entry.getValue();
            Optional<CartActivityInfo> rule = cartActivityInfos.stream().filter(cartActivityInfo -> cartActivityInfo.getActivityId().equals(ruleId)).findFirst();
            if (rule.isPresent()) {
                CartActivityInfo cartActivityInfo = rule.get();
                CartActivityInfo.FullReduction fullReduction = cartActivityInfo.getFullReduction();
                CartActivityInfo.FullReductionRule fullReductionRule = fullReduction.getRules().get(0);
                /**活动类型 1每满减 2满减 3满折 4仅第X件打折*/
                switch (fullReduction.getFullReductiontype()) {
                    case 1:
                        break;
                    case 2://满减
                    case 3://3满折
                        List<CartActivityInfo.FullReductionRule> fullReductionRuleList = fullReduction.getRules();
                        CartActivityInfo.FullReductionRule first = fullReductionRuleList.stream().findFirst().get();
                        if (first.getAmount() != null && first.getAmount() > 0) {
                            //满件数
                            int sum = fullReductionGoodsList.stream().mapToInt(FullReductionGoodsCartBo::getNum).sum();
                            for (CartActivityInfo.FullReductionRule fullRule : fullReductionRuleList) {
                                if (fullRule.getAmount() <= sum) {
                                    // fullRule 可用规则
                                    if (fullReductionRule.getAmount() < sum) {
                                        if (fullRule.getAmount() > fullReductionRule.getAmount()) {
                                            //最好规则
                                            fullReductionRule = fullRule;
                                        }
                                    } else {
                                        fullReductionRule = fullRule;
                                    }
                                }
                            }
                        } else {//满金额
                            BigDecimal moneySums = fullReductionGoodsList.stream().map(FullReductionGoodsCartBo::getMoney)
                                    .reduce(BigDecimal.ZERO, BigDecimal::add);
                            for (CartActivityInfo.FullReductionRule fullRule : fullReductionRuleList) {
                                if (moneySums.compareTo(fullRule.getFullMoney()) >= 0) {
                                    if (moneySums.compareTo(fullReductionRule.getFullMoney()) >= 0) {
                                        if (fullReductionRule.getFullMoney().compareTo(fullRule.getFullMoney()) > 0) {
                                            fullReductionRule = fullRule;
                                        }
                                    } else {
                                        fullReductionRule = fullRule;
                                    }
                                }
                            }
                        }
                        break;
                    case 4:
                        break;
                    default:
                }
                for (FullReductionGoodsCartBo goods : fullReductionGoodsList) {
                    goods.setFullReductionRule(fullReductionRule);
                }
            }
        }

    }

    public void getFullReductionGoodsBo(Map<Integer, List<FullReductionGoodsCartBo>> ruleCartIdMap, WxAppCartGoods goods) {
        List<FullReductionGoodsCartBo> fullGoods = ruleCartIdMap.get(goods.getExtendId()) != null ? ruleCartIdMap.get(goods.getExtendId()) : new ArrayList<>();
        FullReductionGoodsCartBo FullReductionGoodsCartBo =new FullReductionGoodsCartBo();
        FullReductionGoodsCartBo.setCartId(goods.getCartId());
        FullReductionGoodsCartBo.setProductId(goods.getProductId());
        FullReductionGoodsCartBo.setNum(goods.getCartNumber());
        FullReductionGoodsCartBo.setMoney(goods.getGoodsPrice().multiply(BigDecimal.valueOf(goods.getCartNumber())));
        fullGoods.add(FullReductionGoodsCartBo);
        ruleCartIdMap.put(goods.getExtendId(), fullGoods);
    }

    /**
     * 或计划
     * @param cartBo
     */
    public void internationalMessage(WxAppCartBo cartBo) {
        for (WxAppCartGoods goods : cartBo.getCartGoodsList()) {
            goods.getCartActivityInfos().forEach(cartActivityInfo -> {
                if (cartActivityInfo.getActivityType().equals(BaseConstant.ACTIVITY_TYPE_FULL_REDUCTION)){
                    CartActivityInfo.FullReduction fullReduction = cartActivityInfo.getFullReduction();
                    if (fullReduction!=null){
                        CartActivityInfo.FullReductionRule fullReductionRule = fullReduction.getRule();
                        //当前选中的国际化
                        if (fullReductionRule!=null){
                            String message = fullReductionRuleToString(fullReduction,fullReductionRule);
                            fullReduction.setCondition(message);
                        }
                        fullReduction.getRules().forEach(rule->{
                            rule.setName(fullReductionRuleToString(fullReduction,rule));
                        });
                    }
                }
            });
        }
    }


}
