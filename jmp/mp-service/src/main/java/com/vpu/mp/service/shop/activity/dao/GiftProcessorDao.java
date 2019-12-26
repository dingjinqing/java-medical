package com.vpu.mp.service.shop.activity.dao;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.market.gift.GiftVo;
import com.vpu.mp.service.pojo.shop.market.gift.RuleVo;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.wxapp.order.goods.OrderGoodsBo;
import com.vpu.mp.service.pojo.wxapp.order.marketing.gift.OrderGiftProductVo;
import com.vpu.mp.service.shop.config.GiftConfigService;
import com.vpu.mp.service.shop.market.gift.GiftService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 赠品processor
 * @author 王帅
 */
@Service
public class GiftProcessorDao extends GiftService {

    @Autowired
    private GiftConfigService giftConfig;

    /**
     * 下单获取赠品
     * @param userId
     * @param goodsBo
     */
    public void getGifts(Integer userId, List<OrderGoodsBo> goodsBo, List<Byte> orderType){
        //googsBo转map,聚合相同规格(k->prdId;v->数量)
        Map<Integer, Integer> goodsMapCount = goodsBo.stream().collect(Collectors.toMap(OrderGoodsBo::getProductId, OrderGoodsBo::getGoodsNumber, (ov, nv) -> ov + nv));
        //商品未参与赠品记录
        Set<Integer> noJoinRecord = goodsMapCount.keySet();
        //0：赠送满足赠品条件的所有赠品;1：只赠送其中优先级最高的活动赠品
        Byte cfg = giftConfig.getCfg();
        //所有进行中的活动
        List<GiftVo> activitys = getActiveActivity();
        activitys.forEach(activity->{
            if(CONDITION_PRIORITY.equals(cfg) && noJoinRecord.size() == 0){
                //只送最高优先级：如果当前未参与商品为0则
                return;
            }
            final int[] number = {0};
            final BigDecimal[] price = {BigDecimal.ZERO};
            //当前活动参与商品
            Set<Integer> joinRecord = Sets.newHashSet();
            //转化
            transformVo(activity);
            goodsBo.forEach(goods->{
                if(CollectionUtils.isEmpty(activity.getGoodsIds())|| activity.getGoodsIds().contains(goods.getGoodsId())){
                    number[0] = number[0] + goods.getGoodsNumber();
                    price[0] = price[0].add(goods.getDiscountedTotalPrice());
                    joinRecord.add(goods.getGoodsId());
                }
            });
            if(number[0] > 0){
                List<OrderGoodsBo> orderGoodsBos = packageAndCheckGift(userId, activity, price[0], number[0], goodsMapCount, orderType, noJoinRecord);
                if(CollectionUtils.isNotEmpty(orderGoodsBos)){
                    goodsBo.addAll(orderGoodsBos);
                    noJoinRecord.removeAll(joinRecord);
                }
            }
        });

    }



    /**
     * 获取所有进行中的活动
     */
    private List<GiftVo> getActiveActivity(){
        Timestamp now = DateUtil.getSqlTimestamp();
        return db().select(TABLE.ID, TABLE.NAME, TABLE.START_TIME, TABLE.END_TIME, TABLE.LEVEL, TABLE.STATUS, TABLE.GOODS_ID, TABLE.RULE, TABLE.EXPLAIN)
            .from(TABLE)
            .where(TABLE.DEL_FLAG.eq(DelFlag.NORMAL.getCode())
                .and(TABLE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL))
                .and(TABLE.START_TIME.le(now))
                .and(TABLE.END_TIME.gt(now)))
            .orderBy(TABLE.LEVEL.desc())
            .fetchInto(GiftVo.class);
    }

    /**
     * 获取当前符合当前活动规则的赠品
     * @param userId
     * @param giftVo
     * @param price
     * @param number
     * @param goodsMapCount
     * @param orderType
     * @param noJoinRecord
     * @return
     */
    private List<OrderGoodsBo> packageAndCheckGift(Integer userId, GiftVo giftVo, BigDecimal price, int number, Map<Integer, Integer> goodsMapCount, List<Byte> orderType, Set<Integer> noJoinRecord) {
        RuleVo rules = giftVo.getRules();
        if(rules.getFullPrice() != null && !orderType.contains(OrderConstant.GOODS_TYPE_EXCHANG_ORDER) && rules.getFullPrice() <= price.doubleValue()){
            logger().info("赠品：满金额满足,活动id:{}", giftVo.getId());
            return packageGift(giftVo.getId(), noJoinRecord, goodsMapCount);
        }
        if(rules.getFullNumber() != null && rules.getFullNumber() <= number){
            logger().info("赠品：满件数满足,活动id:{}", giftVo.getId());
            packageGift(giftVo.getId(), noJoinRecord, goodsMapCount);
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * 打包赠品
     * @param id
     * @param noJoinRecord
     * @param goodsMapCount
     */
    private List<OrderGoodsBo> packageGift(Integer id, Set<Integer> noJoinRecord, Map<Integer, Integer> goodsMapCount) {
        List<OrderGiftProductVo> giftProducts = getOrderGiftProducts(id);
        List<OrderGoodsBo> bos = Lists.newArrayList();
        giftProducts.forEach(gPrd->{
            if(gPrd.getProductNumber() <= 0 && gPrd.getPrdNumber() - (goodsMapCount.get(gPrd.getProductId() == null ? 0 : gPrd.getProductId())) - 1 < 0){
                //规格数量校验失败
                return;
            }
            if(goodsMapCount.get(gPrd.getProductId())!= null){
                //赠品规格与商品规格数量相加->校验数量
                goodsMapCount.put(gPrd.getProductId(), goodsMapCount.get(gPrd.getProductId()) + 1);
            }
            bos.add(gPrd.toOrderGoodsBo());
        });
        return bos;
    }
}
