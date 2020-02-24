package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.cart.list.CartActivityInfo;
import com.vpu.mp.service.pojo.wxapp.cart.list.WxAppCartBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailCapsuleParam;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsListMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.promotion.PurchasePricePromotion;
import com.vpu.mp.service.shop.activity.dao.PurchasePriceProcessorDao;
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
    @Override
    public void doCartOperation(WxAppCartBo cartBo) {

        Map<Integer,Result<Record4<Integer, Integer, BigDecimal, BigDecimal>>> purchaseRulesMap =new HashMap<>();
        cartBo.getCartGoodsList().forEach(goods->{
            Result<Record4<Integer, Integer, BigDecimal, BigDecimal>>  purchaseRulesList =null;
            if (purchaseRulesMap.size()>0&&purchaseRulesMap.get(goods.getGoodsId())!=null){
                purchaseRulesList= purchaseRulesMap.get(goods.getGoodsId());
            }else {
                //获取商品的活动规则
                purchaseRulesList = purchasePriceProcessorDao.getPurchasePriceInfo(goods.getGoodsId(), cartBo.getDate());
                purchaseRulesMap.put(goods.getGoodsId(),purchaseRulesList);
            }
            if (purchaseRulesList != null &&purchaseRulesList.size() != 0) {
                CartActivityInfo cartActivityInfo =new CartActivityInfo();
                cartActivityInfo.setActivityType(BaseConstant.ACTIVITY_TYPE_PURCHASE_PRICE);
                purchaseRulesList.forEach(purchaseRules->{
                    cartActivityInfo.setActivityId(purchaseRules.get(PURCHASE_PRICE_DEFINE.ID));
                    CartActivityInfo.PurchasePriceRule cartPurchasePriceRule =new CartActivityInfo.PurchasePriceRule();
                    cartPurchasePriceRule.setFullPrice(purchaseRules.get(PURCHASE_PRICE_RULE.FULL_PRICE));
                    cartPurchasePriceRule.setPurchasePrice(purchaseRules.get(PURCHASE_PRICE_RULE.PURCHASE_PRICE));
                    });
            }
        });
    }
}
