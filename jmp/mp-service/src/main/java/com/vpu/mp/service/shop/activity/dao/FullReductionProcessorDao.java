package com.vpu.mp.service.shop.activity.dao;

import com.vpu.mp.db.shop.tables.MrkingStrategy;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.database.DslPlus;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.pojo.shop.market.fullcut.MrkingStrategyCondition;
import com.vpu.mp.service.pojo.shop.market.fullcut.MrkingStrategyPageListQueryVo;
import com.vpu.mp.service.pojo.shop.market.fullcut.MrkingStrategyVo;
import com.vpu.mp.service.pojo.shop.member.card.SimpleMemberCardVo;
import com.vpu.mp.service.pojo.wxapp.order.goods.OrderGoodsBo;
import com.vpu.mp.service.shop.market.fullcut.MrkingStrategyService;
import com.vpu.mp.service.shop.member.UserCardService;
import org.apache.commons.collections4.CollectionUtils;
import org.jooq.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.MRKING_STRATEGY;

/**
 * @author 李晓冰
 * @date 2019年10月30日
 */
@Service
public class FullReductionProcessorDao extends MrkingStrategyService {

    /** 全部商品参与活动 */
    private static final byte ACT_TYPE_ALL_GOODS = 0;
    /** 指定条件参与活动 */
    private static final byte ACT_TYPE_POINT_CONDITION = 1;
    @Autowired
    private UserCardService userCard;
    /**
     * 判断根据传入的条件是否存在对应的满折满减活动
     * @param goodsId 商品id
     * @param catId 平台分类id
     * @param sortId 商家分类id
     * @param brandId 品牌分类id
     * @param date 时间
     * @return true 存在对应的活动，false 不存在对应的活动
     */
    public boolean getIsFullReductionListInfo(Integer goodsId, Integer catId, Integer sortId, Integer brandId, Timestamp date) {
        Condition condition =MRKING_STRATEGY.ACT_TYPE.eq(ACT_TYPE_ALL_GOODS)
            .or(MRKING_STRATEGY.ACT_TYPE.eq(ACT_TYPE_POINT_CONDITION).and(
                DslPlus.findInSet(goodsId,MRKING_STRATEGY.RECOMMEND_GOODS_ID)
                .or(DslPlus.findInSet(catId,MRKING_STRATEGY.RECOMMEND_CAT_ID))
                .or(DslPlus.findInSet(sortId,MRKING_STRATEGY.RECOMMEND_SORT_ID))
                .or(DslPlus.findInSet(brandId,MRKING_STRATEGY.RECOMMEND_BRAND_ID))
            ));

        condition = MRKING_STRATEGY.DEL_FLAG.eq(DelFlag.NORMAL.getCode())
            .and(MRKING_STRATEGY.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL)).and(MRKING_STRATEGY.START_TIME.lt(date))
            .and(MRKING_STRATEGY.END_TIME.gt(date)).and(condition);

        int count = db().fetchCount(MRKING_STRATEGY, condition);
        return count > 0;
    }

    /**
     * 获取当前进行中的活动
     * @param date 下单时间
     * @param straId id
     */
    public List<MrkingStrategyPageListQueryVo> getProcessingActivity(Timestamp date, Integer... straId){
        Condition condition = MRKING_STRATEGY.DEL_FLAG.eq(DelFlag.NORMAL.getCode())
            .and(MRKING_STRATEGY.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL))
            .and(MRKING_STRATEGY.START_TIME.lt(date))
            .and(MRKING_STRATEGY.END_TIME.gt(date));
        if(straId != null && straId.length > 0){
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
     * @param activityInfo 活动详情
     * @param cardIds 用户有效会员卡
     */
    public boolean checkMemberCard(MrkingStrategyVo activityInfo, Set<Integer> cardIds){
        if(CollectionUtils.isEmpty(activityInfo.getMemberCards())) {
            return true;
        }
        Set<Integer> ids = activityInfo.getMemberCards().stream().map(SimpleMemberCardVo::getId).collect(Collectors.toSet());
        ids.retainAll(cardIds);
        if(CollectionUtils.isNotEmpty(ids)) {
            return true;
        }
        return false;
    }

    /**
     * 校验
     * @param activityInfo 活动详情
     * @param goods 商品
     */
    public boolean checkGoods(MrkingStrategyVo activityInfo, OrderGoodsBo goods){
        if(ACT_TYPE_ALL_GOODS == activityInfo.getActType()) {
            logger().info("满折满减：支持全部商品,活动id:{}", activityInfo.getId());
            return true;
        }else{
            if(CollectionUtils.isNotEmpty(activityInfo.getRecommendGoodsIds())){
                if(activityInfo.getRecommendGoodsIds().contains(goods.getGoodsId())){
                    logger().info("满折满减：指定商品满足,活动id:{}", activityInfo.getId());
                    return true;
                }
            }
            if(CollectionUtils.isNotEmpty(activityInfo.getRecommendCatIds())){
                if(activityInfo.getRecommendCatIds().contains(goods.getCatId())){
                    logger().info("满折满减：指定普通分类满足,活动id:{}", activityInfo.getId());
                    return true;
                }
            }
            if(CollectionUtils.isNotEmpty(activityInfo.getRecommendSortIds())){
                if(activityInfo.getRecommendSortIds().contains(goods.getGoodsId())){
                    logger().info("满折满减：指定商家分类满足,活动id:{}", activityInfo.getId());
                    return true;
                }
            }
            if(CollectionUtils.isNotEmpty(activityInfo.getRecommendBrandIds())){
                if(activityInfo.getRecommendBrandIds().contains(goods.getGoodsId())){
                    logger().info("满折满减：指定品牌满足,活动id:{}", activityInfo.getId());
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 得到当满折满减优惠金额，如果为0，则不符合条件
     * @param activityInfo 活动信息
     * @param bos 商品
     * @param num 商品总数量
     * @param price 商品总价格
     */
    public BigDecimal calculateFullReduce(MrkingStrategyVo activityInfo, List<OrderGoodsBo> bos, int num, BigDecimal price) {
        // 倒序排列，阶梯满减满折，从最大值金额开始判断
        Collections.sort(activityInfo.getCondition());
        //1每满减 2满减 3满折 4仅第X件打折
        for (MrkingStrategyCondition condition : activityInfo.getCondition()) {
            BigDecimal result = null;
            switch (activityInfo.getType()){
                case 1:
                    //每满减
                    if(condition.getFullMoney() != null && condition.getFullMoney().compareTo(price) < 1) {
                        result = BigDecimalUtil.multiply(
                            condition.getReduceMoney(),
                            new BigDecimal(BigDecimalUtil.divide(price, condition.getFullMoney(), RoundingMode.FLOOR).intValue()));
                    }else if(condition.getAmount() != null && condition.getAmount() <= num) {
                        result = BigDecimalUtil.multiply(
                            condition.getReduceMoney(),
                            new BigDecimal(num / condition.getAmount()));
                    }
                    break;
                case 2:
                    //满减
                    if(condition.getFullMoney() != null && condition.getFullMoney().compareTo(price) < 1) {
                        result = condition.getReduceMoney();
                    }else if(condition.getAmount() != null && condition.getAmount() <= num) {
                        result = condition.getReduceMoney();
                    }
                    break;
                case 3:
                    //满折
                    if((condition.getFullMoney() != null && condition.getFullMoney().compareTo(price) < 1) ||
                        (condition.getAmount() != null && condition.getAmount() <= num)) {
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
                    for (OrderGoodsBo bo: bos) {
                        if(bo.getGoodsNumber() >= condition.getAmount()) {
                            temp = temp == null ? BigDecimal.ZERO : temp;
                            temp.add(
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
            if(result != null){
                return result;
            }
        }
        return BigDecimal.ZERO;
    }
}
