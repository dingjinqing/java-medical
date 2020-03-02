package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.market.fullcut.FullReductionGoodsBo;
import com.vpu.mp.service.pojo.wxapp.cart.list.CartActivityInfo;
import com.vpu.mp.service.pojo.wxapp.cart.list.WxAppCartBo;
import com.vpu.mp.service.pojo.wxapp.cart.list.WxAppCartGoods;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailCapsuleParam;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsListMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.promotion.PurchasePricePromotion;
import com.vpu.mp.service.shop.activity.dao.PurchasePriceProcessorDao;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Record;
import org.jooq.Record3;
import org.jooq.Record4;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

import static com.vpu.mp.db.shop.Tables.PURCHASE_PRICE_RULE;
import static com.vpu.mp.db.shop.tables.PurchasePriceDefine.PURCHASE_PRICE_DEFINE;

/**
 * 加价购处理器
 * @author 李晓冰
 * @date 2020年01月14日
 */
@Service
@Slf4j
public class PurchasePriceProcessor implements Processor,GoodsDetailProcessor,ActivityCartListStrategy {
    @Autowired
    PurchasePriceProcessorDao purchasePriceProcessorDao;

    @Override
    public Byte getPriority() {
        return GoodsConstant.ACTIVITY_FREE_SHIP_PRIORITY;
    }

    @Override
    public Byte getActivityType() {
        return BaseConstant.ACTIVITY_TYPE_PURCHASE_PRICE;
    }

    @Override
    public void processGoodsDetail(GoodsDetailMpBo capsule, GoodsDetailCapsuleParam param) {
        List<PurchasePricePromotion> promotions = purchasePriceProcessorDao.getPurchasePriceInfoForDetail(capsule.getGoodsId(), DateUtil.getLocalDateTime());
        if (promotions == null) {
            return;
        }
        capsule.getPromotions().put(BaseConstant.ACTIVITY_TYPE_PURCHASE_PRICE,promotions);
    }

    //******************购物车-加价购********
    /**
     *
     * @param cartBo 业务数据类
     */
    @Override
    public void doCartOperation(WxAppCartBo cartBo) {
        log.info("购物车--加价购");
        //缓存
        Map<Integer,Map<Integer, Result<Record4<Integer, Integer, BigDecimal, BigDecimal>>>> activityCacheMap =new HashMap<>();
        //活动记录
        Map<Integer,Result<Record4<Integer, Integer, BigDecimal, BigDecimal>>> activityMap =new HashMap<>();
        //商品活动信息
        for (WxAppCartGoods goods : cartBo.getCartGoodsList()) {
            Map<Integer, Result<Record4<Integer, Integer, BigDecimal, BigDecimal>>> purchaseRulesMap = null;
            //获取商品的活动
            if (activityCacheMap.size() > 0 && activityCacheMap.get(goods.getGoodsId()) != null) {
                purchaseRulesMap = activityCacheMap.get(goods.getGoodsId());
            } else {
                //获取商品的活动规则
                purchaseRulesMap = purchasePriceProcessorDao.getPurchasePriceInfo(goods.getGoodsId(), cartBo.getDate());
                activityCacheMap.put(goods.getGoodsId(),purchaseRulesMap);
            }
            //配置商品符合的活动信息
            if (purchaseRulesMap != null && purchaseRulesMap.size() != 0) {
                purchaseRulesMap.forEach((key,purchaseRules) -> {
                    log.info("加价购商品:{},活动id:{}", goods.getGoodsName(), key);
                    CartActivityInfo cartActivityInfo = new CartActivityInfo();
                    cartActivityInfo.setActivityType(BaseConstant.ACTIVITY_TYPE_PURCHASE_PRICE);
                    cartActivityInfo.setActivityId(key);
                    CartActivityInfo.PurchasePrice purchasePrice = new CartActivityInfo.PurchasePrice();
                    purchasePrice.setPurchasePriceRule(new ArrayList<>());
                    //加价购规则
                    purchaseRules.forEach(rule->{
                        CartActivityInfo.PurchasePriceRule cartPurchasePriceRule = new CartActivityInfo.PurchasePriceRule();
                        cartPurchasePriceRule.setFullPrice(rule.get(PURCHASE_PRICE_RULE.FULL_PRICE));
                        cartPurchasePriceRule.setPurchasePrice(rule.get(PURCHASE_PRICE_RULE.PURCHASE_PRICE));
                        purchasePrice.getPurchasePriceRule().add(cartPurchasePriceRule);
                    });
                    cartActivityInfo.setPurchasePrice(purchasePrice);
                    goods.getCartActivityInfos().add(cartActivityInfo);
                    //当前商品活动
                    if (goods.getType().equals(BaseConstant.ACTIVITY_TYPE_PURCHASE_PRICE)) {
                        if (goods.getExtendId().equals(key)) {
                            goods.setActivityType(BaseConstant.ACTIVITY_TYPE_PURCHASE_PRICE);
                            goods.setActivityId(cartActivityInfo.getActivityId());
                            //当前生效活动
                            activityMap.put(key,purchaseRules);
                        }
                    }
                });
            }
        }
        //活动配置
        activityMap.forEach((k,rules)->{


        });
        //国际化


    }
}
