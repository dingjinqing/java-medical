package com.vpu.mp.service.shop.activity.processor;

import com.fasterxml.jackson.databind.JsonSerializable;
import com.vpu.mp.db.shop.tables.records.PurchasePriceDefineRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.cart.CartConstant;
import com.vpu.mp.service.pojo.wxapp.cart.activity.FullReductionGoodsCartBo;
import com.vpu.mp.service.pojo.wxapp.cart.activity.PurchasePriceCartBo;
import com.vpu.mp.service.pojo.wxapp.cart.list.CartActivityInfo;
import com.vpu.mp.service.pojo.wxapp.cart.list.WxAppCartBo;
import com.vpu.mp.service.pojo.wxapp.cart.list.WxAppCartGoods;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailCapsuleParam;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.promotion.PurchasePricePromotion;
import com.vpu.mp.service.shop.activity.dao.PurchasePriceProcessorDao;
import com.vpu.mp.service.shop.user.cart.CartService;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Record4;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.*;

import static com.vpu.mp.db.shop.Tables.PURCHASE_PRICE_RULE;

/**
 * 加价购处理器
 *
 * @author 李晓冰
 * @date 2020年01月14日
 */
@Service
@Slf4j
public class PurchasePriceProcessor implements Processor, GoodsDetailProcessor, ActivityCartListStrategy {
    @Autowired
    PurchasePriceProcessorDao purchasePriceProcessorDao;
    @Autowired
    private CartService cartService;

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
        capsule.getPromotions().put(BaseConstant.ACTIVITY_TYPE_PURCHASE_PRICE, promotions);
    }

    //******************购物车-加价购********

    /**
     * @param cartBo 业务数据类
     */
    @Override
    public void doCartOperation(WxAppCartBo cartBo) {
        log.info("购物车--加价购");
        //缓存
        Map<Integer, Map<Integer, Result<Record4<Integer, Integer, BigDecimal, BigDecimal>>>> activityCacheMap = new HashMap<>();
        //活动记录
        Map<Integer, PurchasePriceCartBo> activityMap = new HashMap<>();
        //商品活动信息
        for (WxAppCartGoods goods : cartBo.getCartGoodsList()) {
            if (goods.getIsChecked().equals(CartConstant.CART_IS_CHECKED)) {
                Map<Integer, Result<Record4<Integer, Integer, BigDecimal, BigDecimal>>> purchaseRulesMap;
                //获取商品的活动
                if (activityCacheMap.size() > 0 && activityCacheMap.get(goods.getGoodsId()) != null) {
                    purchaseRulesMap = activityCacheMap.get(goods.getGoodsId());
                } else {
                    //获取商品的活动规则
                    purchaseRulesMap = purchasePriceProcessorDao.getPurchasePriceInfo(goods.getGoodsId(), cartBo.getDate());
                    activityCacheMap.put(goods.getGoodsId(), purchaseRulesMap);
                }
                //配置商品符合的活动信息
                if (purchaseRulesMap != null && purchaseRulesMap.size() != 0) {
                    purchaseRulesMap.forEach((key, purchaseRules) -> {
                        log.info("加价购商品:{},活动id:{}", goods.getGoodsName(), key);
                        CartActivityInfo cartActivityInfo = new CartActivityInfo();
                        cartActivityInfo.setActivityType(BaseConstant.ACTIVITY_TYPE_PURCHASE_PRICE);
                        cartActivityInfo.setActivityId(key);
                        CartActivityInfo.PurchasePrice purchasePrice = new CartActivityInfo.PurchasePrice();
                        purchasePrice.setPurchasePriceRule(new ArrayList<>());
                        //加价购规则
                        for (Record4<Integer, Integer, BigDecimal, BigDecimal> rule : purchaseRules) {
                            CartActivityInfo.PurchasePriceRule cartPurchasePriceRule = new CartActivityInfo.PurchasePriceRule();
                            cartPurchasePriceRule.setFullPrice(rule.get(PURCHASE_PRICE_RULE.FULL_PRICE));
                            cartPurchasePriceRule.setPurchasePrice(rule.get(PURCHASE_PRICE_RULE.PURCHASE_PRICE));
                            purchasePrice.getPurchasePriceRule().add(cartPurchasePriceRule);
                            if (purchasePrice.getRule() == null) {
                                purchasePrice.setRule(cartPurchasePriceRule);
                            }
                        }
                        //设置
                        cartActivityInfo.setPurchasePrice(purchasePrice);
                        goods.getCartActivityInfos().add(cartActivityInfo);
                        //当前商品活动
                        if (goods.getType().equals(BaseConstant.ACTIVITY_TYPE_PURCHASE_PRICE) && goods.getExtendId().equals(key)) {
                            log.info("参与活动");
                            goods.setActivityType(BaseConstant.ACTIVITY_TYPE_PURCHASE_PRICE);
                            goods.setActivityId(cartActivityInfo.getActivityId());
                            //当前生效活动
                            purchasePricePutMap(activityMap, goods, cartActivityInfo);
                        }
                    });
                }
            }
        }
        //启用的活动配置
        enabledActivity(activityMap);
        //国际化
        activityToString(cartBo);
        //加价购商品校验
        checkActivityGoods(cartBo, activityMap);
    }

    /**
     * 校验加价购商品
     * --超出设置范围的商品删除,商品价格设置为加价购价格
     * --删除多余的加价购的商品的记录
     * @param cartBo      购物车商品列表
     * @param activityMap 活动数据
     */
    private void checkActivityGoods(WxAppCartBo cartBo, Map<Integer, PurchasePriceCartBo> activityMap) {
        for (Map.Entry<Integer, PurchasePriceCartBo> entry : activityMap.entrySet()) {
            Integer k = entry.getKey();
            PurchasePriceCartBo v = entry.getValue();
            CartActivityInfo.PurchasePriceRule rule = v.getPurchasePrice().getRule();
            PurchasePriceDefineRecord purchaseInfo = purchasePriceProcessorDao.getPurchaseInfo(k);
            int goodsNum = 0;
            Iterator<WxAppCartGoods> iterator = cartBo.getCartGoodsList().iterator();
            while (iterator.hasNext()) {
                WxAppCartGoods goods = iterator.next();
                if (goods.getType() != null && goods.getType().equals(BaseConstant.ACTIVITY_TYPE_PURCHASE_GOODS) && goods.getExtendId().equals(k)) {
                    log.info("加价购-加价购商品:{},加价价格:{},数量:{}", goods.getGoodsName(), v.getPurchasePrice().getRule().getPurchasePrice().toString(),goods.getCartNumber());
                    goods.setActivityType(BaseConstant.ACTIVITY_TYPE_PURCHASE_GOODS);
                    goods.setActivityId(k);
                    goods.setPrdPrice(rule.getPurchasePrice());
                    goodsNum+=goodsNum+goods.getCartNumber();
                    if (purchaseInfo.getMaxChangePurchase()!=0&&purchaseInfo.getMaxChangePurchase()<goodsNum){
                        log.info("加价商品数量大于规则数量:{}",purchaseInfo.getMaxChangePurchase());
                        for (int i=goodsNum-purchaseInfo.getMaxChangePurchase();i>0;i--){
                            if (goods.getCartNumber()>1){
                                log.info("商品数量减少:{}",goods.getGoodsName());
                                cartService.changeGoodsNumber(cartBo.getUserId(),0,goods.getCartId(),goods.getProductId(),goods.getCartNumber()-1);
                                goods.setCartNumber(goods.getCartNumber()-1);
                            }else {
                                log.info("删除加价购商品:{}",goods.getGoodsName());
                                cartService.removeCartProductById(cartBo.getUserId(),goods.getCartId());
                                iterator.remove();
                            }
                        }
                        goodsNum =purchaseInfo.getMaxChangePurchase().intValue();
                    }
                }
            }
        }
        //删除无用的数据--无主商品的
        Iterator<WxAppCartGoods> iterator = cartBo.getCartGoodsList().iterator();
        while (iterator.hasNext()){
            WxAppCartGoods goods = iterator.next();
            if (goods.getType().equals(BaseConstant.ACTIVITY_TYPE_PURCHASE_GOODS) && goods.getActivityType() == null) {
                cartService.removeCartProductById(cartBo.getUserId(), goods.getCartId());
                iterator.remove();
            }
        }
    }

    /**
     * 国际化
     *
     * @param cartBo
     */
    private void activityToString(WxAppCartBo cartBo) {
        for (WxAppCartGoods goods : cartBo.getCartGoodsList()) {
            goods.getCartActivityInfos().forEach(cartActivityInfo -> {
                if (cartActivityInfo.getActivityType().equals(BaseConstant.ACTIVITY_TYPE_PURCHASE_PRICE)) {
                    CartActivityInfo.PurchasePrice purchasePrice = cartActivityInfo.getPurchasePrice();
                    CartActivityInfo.PurchasePriceRule enabledRule = purchasePrice.getRule();
                    List<CartActivityInfo.PurchasePriceRule> purchasePriceRule = purchasePrice.getPurchasePriceRule();
                    if (enabledRule != null) {
                        enabledRule.setName("满 " + enabledRule.getFullPrice() + " 加价" + enabledRule.getPurchasePrice() + "换购");
                        purchasePrice.setCondition(enabledRule.getName());
                    }
                    purchasePriceRule.forEach(rule -> {
                        rule.setName("满 " + rule.getFullPrice() + " 加价" + rule.getPurchasePrice() + "换购");
                    });
                }
            });
        }
    }

    /**
     * 启用的活动配置
     * @param activityMap map
     */
    private void enabledActivity(Map<Integer, PurchasePriceCartBo> activityMap) {
        //筛选活动
        activityMap.forEach((k, purchasePriceCartBo) -> {
            log.info("购物车中的加价购活动id{}", k);
            BigDecimal moneySums = purchasePriceCartBo.getMoney().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
            CartActivityInfo.PurchasePrice purchasePrice = purchasePriceCartBo.getPurchasePrice();
            purchasePrice.getPurchasePriceRule().forEach(rule -> {
                if (moneySums.compareTo(rule.getFullPrice()) >= 0) {
                    if (purchasePrice.getRule() == null || purchasePrice.getRule().getFullPrice().compareTo(rule.getFullPrice()) < 0) {
                        purchasePrice.setRule(rule);
                    }
                }
            });
            //没有合适规则,取最小的值
            if (purchasePrice.getRule() == null) {
                purchasePrice.getPurchasePriceRule().forEach(rule -> {
                    if (purchasePrice.getRule() == null || rule.getFullPrice().compareTo(purchasePrice.getRule().getFullPrice()) < 0) {
                        purchasePrice.setRule(rule);
                    }
                });
            }
            log.info("购物车中的加价购活动id{},启用适合的规则为:满{},加{},", k, purchasePrice.getRule().getFullPrice().toString(), purchasePrice.getRule().getPurchasePrice());
        });
    }

    /**
     * 把活动记录下来
     *
     * @param activityMap      活动map
     * @param goods            商品
     * @param cartActivityInfo activityId
     */
    private void purchasePricePutMap(Map<Integer, PurchasePriceCartBo> activityMap, WxAppCartGoods goods, CartActivityInfo cartActivityInfo) {
        PurchasePriceCartBo priceCartBo = activityMap.get(goods.getExtendId()) != null ? activityMap.get(cartActivityInfo.getActivityId()) : new PurchasePriceCartBo();
        priceCartBo.getCartId().add(goods.getCartId());
        priceCartBo.getProductId().add(goods.getProductId());
        priceCartBo.getNum().add(goods.getCartNumber());
        priceCartBo.getMoney().add(goods.getGoodsPrice().multiply(BigDecimal.valueOf(goods.getCartNumber())));
        priceCartBo.setPurchasePrice(cartActivityInfo.getPurchasePrice());
        activityMap.put(cartActivityInfo.getActivityId(), priceCartBo);
    }
}
