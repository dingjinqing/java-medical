package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.market.firstspecial.FirstSpecialProductBo;
import com.vpu.mp.service.pojo.wxapp.cart.CartConstant;
import com.vpu.mp.service.pojo.wxapp.cart.activity.GoodsActivityInfo;
import com.vpu.mp.service.pojo.wxapp.cart.activity.OrderCartProductBo;
import com.vpu.mp.service.pojo.wxapp.cart.list.CartActivityInfo;
import com.vpu.mp.service.pojo.wxapp.cart.list.WxAppCartBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsActivityBaseMp;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsListMpBo;
import com.vpu.mp.service.shop.activity.dao.FirstSpecialProcessorDao;
import com.vpu.mp.service.shop.config.FirstSpecialConfigService;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.user.cart.CartService;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Record3;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.FirstSpecial.FIRST_SPECIAL;
import static com.vpu.mp.db.shop.tables.FirstSpecialProduct.FIRST_SPECIAL_PRODUCT;

/**
 * @author 李晓冰
 * @date 2019年11月01日
 */
@Service
@Slf4j
public class FirstSpecialProcessor implements ProcessorPriority, ActivityGoodsListProcessor, ActivityCartListStrategy {

    @Autowired
    FirstSpecialProcessorDao firstSpecialProcessorDao;

    @Autowired
    OrderInfoService orderInfoService;
    @Autowired
    private CartService cartService;
    @Autowired
    private FirstSpecialConfigService firstSpecialConfigService;

    /*****处理器优先级*****/
    @Override
    public Byte getPriority() {
        return GoodsConstant.ACTIVITY_FIRST_SPECIAL_PRIORITY;
    }

    /*****************商品列表处理*******************/
    @Override
    /**
     * 商品装修列表-首单特惠
     */
    public void processForList(List<GoodsListMpBo> capsules, Integer userId) {
        if (userId != null && !orderInfoService.isNewUser(userId, true)) {
            return;
        }
        List<GoodsListMpBo> availableCapsules = capsules.stream().filter(x -> !GoodsConstant.isGoodsTypeIn13510(x.getActivityType()) && !x.getProcessedTypes().contains(BaseConstant.ACTIVITY_TYPE_MEMBER_EXCLUSIVE))
                .collect(Collectors.toList());

        List<Integer> goodsIds = availableCapsules.stream().map(GoodsListMpBo::getGoodsId).collect(Collectors.toList());
        Map<Integer, Result<Record3<Integer, Integer, BigDecimal>>> firstSpecialPrds = firstSpecialProcessorDao.getGoodsFirstSpecialForListInfo(goodsIds, DateUtil.getLocalDateTime());

        availableCapsules.forEach(capsule -> {
            Integer goodsId = capsule.getGoodsId();
            Result<Record3<Integer, Integer, BigDecimal>> result = firstSpecialPrds.get(goodsId);
            if (result == null) {
                return;
            }
            capsule.setRealPrice(result.get(0).get(FIRST_SPECIAL_PRODUCT.PRD_PRICE));
            GoodsActivityBaseMp activity = new GoodsActivityBaseMp();
            activity.setActivityType(BaseConstant.ACTIVITY_TYPE_FIRST_SPECIAL);
            activity.setActivityId(result.get(0).get(FIRST_SPECIAL.ID));
            capsule.getGoodsActivities().add(activity);
            capsule.getProcessedTypes().add(BaseConstant.ACTIVITY_TYPE_FIRST_SPECIAL);
        });
    }

    //**********************购物车********************

    /**
     * 购物车首单特惠
     *
     * @param cartBo 业务数据类
     */
    @Override
    public void doCartOperation(WxAppCartBo cartBo) {
        log.debug("WxAppCartBo:"+ Util.toJson(cartBo));
        boolean isNewUser = orderInfoService.isNewUser(cartBo.getUserId());
        cartBo.setIsNewUser(isNewUser);
        if (isNewUser) {
            List<FirstSpecialProductBo> specialPrdIdList = firstSpecialProcessorDao.getGoodsFirstSpecialPrdId(cartBo.getProductIdList(), cartBo.getDate())
                    .into(FirstSpecialProductBo.class);
            if (specialPrdIdList != null && specialPrdIdList.size() > 0) {
                log.debug("新用户触发首单特惠活动FirstSpecialProductBo:"+Util.toJson(specialPrdIdList));
                //全局商品种类限制
                Integer limitGoodsNum = firstSpecialConfigService.getFirstLimitGoods();
                AtomicReference<Integer> goodsNum = new AtomicReference<>(0);
                AtomicReference<Integer> checkedGoodsNum = new AtomicReference<>(0);
                specialPrdIdList.forEach(firstSpecial -> {
                    cartBo.getCartGoodsList().forEach(goods -> {
                        if (goods.getPrdId().equals(firstSpecial.getPrdId())) {
                            log.debug("首单特惠商品[getGoodsName:"+goods.getGoodsName()+",getPrdId:"+goods.getPrdId()+"]");
                            CartActivityInfo firstActivityInfo = new CartActivityInfo();
                            firstActivityInfo.setActivityType(BaseConstant.ACTIVITY_TYPE_FIRST_SPECIAL);
                            firstActivityInfo.setFirstSpecialPrice(firstSpecial.getPrdPrice());
                            if (firstSpecial.getLimitAmount() > 0 && goods.getGoodsNumber() > firstSpecial.getLimitAmount()) {
                                //超出限购数量后，买家不可继续添加购买该商品
                                if (firstSpecial.getLimitFlag().equals(BaseConstant.FIRST_SPECIAL_LIMIT_FLAG_CONTINUE)){
                                    firstActivityInfo.setStatus(CartConstant.ACTIVITY_STATUS_INVALID);
                                }else {
                                    // 修改购物车中商品数量
                                    log.debug("商品数量超过限制,修改购物车中的商品数量[getGoodsName:"+goods.getGoodsName()+",getGoodsNumber:"+goods.getGoodsNumber()+",getLimitAmount:"+firstSpecial.getLimitAmount()+"]");
                                    cartService.changeGoodsNumber(cartBo.getUserId(),goods.getPrdId(),0,firstSpecial.getLimitAmount());
                                    goods.setGoodsNumber(firstSpecial.getLimitAmount());
                                }
                            }
                            if (Objects.equals(goods.getIsChecked(), CartConstant.CART_IS_CHECKED)){
                                log.debug("选中的特惠商品[getGoodsName:"+goods.getGoodsName()+",getPrdId:"+goods.getPrdId()+"]");
                                goodsNum.updateAndGet(v -> v + 1);
                            }
                            if (limitGoodsNum != 0&& goodsNum.get() >limitGoodsNum) {
                                firstActivityInfo.setStatus(CartConstant.ACTIVITY_STATUS_INVALID);
                            }
                            if (limitGoodsNum != 0&& goodsNum.get()>limitGoodsNum&&goods.getIsChecked().equals(CartConstant.CART_IS_CHECKED)){
                                goodsNum.updateAndGet(v -> v + 1);
                            }
                            goods.getCartActivityInfos().add(firstActivityInfo);
                        }
                    });
                });
                if (goodsNum.get() >= limitGoodsNum){
                    log.debug("选中商品过多,触发首单特惠商品数(种类)限制[goodsNum:"+goodsNum+",limitGoodsNum:"+limitGoodsNum+"]");
                    cartBo.getCartGoodsList().forEach(goods->{
                        goods.getCartActivityInfos().forEach(actInfo->{
                            if (BaseConstant.ACTIVITY_TYPE_FIRST_SPECIAL.equals(actInfo.getActivityType())&&actInfo.getStatus().equals(CartConstant.ACTIVITY_STATUS_VALID)){
                                if (Objects.equals(goods.getIsChecked(), CartConstant.CART_IS_CHECKED)) {
                                    checkedGoodsNum.updateAndGet(v->v+1);
                                    if (checkedGoodsNum.get()>limitGoodsNum){
                                        log.debug("超过限制的商品首单特惠不生效,商品价格为原价[getGoodsName:"+goods.getGoodsName()+",getPrdId:"+goods.getPrdId()+"]");
                                        actInfo.setStatus(CartConstant.ACTIVITY_STATUS_INVALID);
                                    }
                                }else {
                                    actInfo.setStatus(CartConstant.ACTIVITY_STATUS_INVALID);
                                }
                            }
                        });
                    });
                }
            }
        }
    }

    /**
     *
     * @param userId
     * @param date
     * @param productBo
     * @param storeId
     */
    public void doOrderOperation(Integer userId, Timestamp date, OrderCartProductBo productBo, Integer storeId){
        boolean isNewUser = orderInfoService.isNewUser(userId);
        if (isNewUser){
            List<Integer> productIds =productBo.getAll().stream().map(OrderCartProductBo.OrderCartProduct::getProductId).collect(Collectors.toList());
            List<FirstSpecialProductBo> specialPrdIdList = firstSpecialProcessorDao.getGoodsFirstSpecialPrdId(productIds,date).into(FirstSpecialProductBo.class);
            if (specialPrdIdList != null && specialPrdIdList.size() > 0) {
                log.debug("新用户触发首单特惠活动FirstSpecialProductBo:"+Util.toJson(specialPrdIdList));
                Integer limitGoodsNum = firstSpecialConfigService.getFirstLimitGoods();
                log.debug("首单特惠全局限制商品种类[limitGoodsNum:"+limitGoodsNum+"]");
                // 商品数量
                AtomicReference<Integer> goodsNum = new AtomicReference<>(0);
                // 选中的商品数量
                AtomicReference<Integer> checkedGoodsNum = new AtomicReference<>(0);
                specialPrdIdList.forEach(firstSpecial -> {
                    productBo.getAll().forEach(product -> {
                        if (firstSpecial.getPrdId().equals(product.getProductId())) {
                            log.debug("首单特惠商品[getPrdId:"+firstSpecial.getPrdId()+"]");
                            GoodsActivityInfo firstActivityInfo = new GoodsActivityInfo();
                            firstActivityInfo.setActivityType(BaseConstant.ACTIVITY_TYPE_FIRST_SPECIAL);
                            firstActivityInfo.setFirstSpecialPrice(firstSpecial.getPrdPrice());
                            firstActivityInfo.setActivityId(firstSpecial.getId());
                            if (firstSpecial.getLimitAmount() > 0 && product.getGoodsNumber() > firstSpecial.getLimitAmount()) {
                                //超出限购数量后，买家不可继续添加购买该商品
                                if (firstSpecial.getLimitFlag().equals(BaseConstant.FIRST_SPECIAL_LIMIT_FLAG_CONTINUE)){
                                    firstActivityInfo.setStatus(CartConstant.ACTIVITY_STATUS_INVALID);
                                }else {
                                    // 修改购物车中商品数量
                                    log.debug("商品数量超过限制,修改购物车中的商品数量[getGoodsNumber:"+product.getGoodsNumber()+",getLimitAmount:"+firstSpecial.getLimitAmount()+"]");
                                    cartService.changeGoodsNumber(userId,product.getProductId(),0,firstSpecial.getLimitAmount());
                                    product.setGoodsNumber(firstSpecial.getLimitAmount());
                                }
                            }
                            if (Objects.equals(product.getIsChecked(), CartConstant.CART_IS_CHECKED)){
                                log.debug("选中的特惠商品[getPrdId:"+product.getProductId()+"]");
                                goodsNum.updateAndGet(v -> v + 1);
                            }
                            if (limitGoodsNum != 0&& goodsNum.get() >limitGoodsNum) {
                                firstActivityInfo.setStatus(CartConstant.ACTIVITY_STATUS_INVALID);
                            }
                            if (limitGoodsNum != 0&& goodsNum.get()>limitGoodsNum&&product.getIsChecked().equals(CartConstant.CART_IS_CHECKED)){
                                goodsNum.updateAndGet(v -> v + 1);
                            }
                            product.getActivityInfo().add(firstActivityInfo);
                        }
                    });
                });
                if (goodsNum.get() >= limitGoodsNum){
                    log.debug("选中商品过多,触发首单特惠商品数(种类)限制[goodsNum:"+goodsNum+",limitGoodsNum:"+limitGoodsNum+"]");
                    productBo.getAll().forEach(goods->{
                        GoodsActivityInfo actInfo = goods.getActivityInfo().stream().filter(activity -> activity.getStatus().equals(BaseConstant.ACTIVITY_TYPE_FIRST_SPECIAL)).findFirst().orElse(null);
                        if (actInfo!=null && BaseConstant.ACTIVITY_TYPE_FIRST_SPECIAL.equals(actInfo.getActivityType()) && Objects.equals(actInfo.getStatus(), CartConstant.ACTIVITY_STATUS_VALID)) {
                            if (Objects.equals(goods.getIsChecked(), CartConstant.CART_IS_CHECKED)) {
                                checkedGoodsNum.updateAndGet(v -> v + 1);
                                if (checkedGoodsNum.get() > limitGoodsNum) {
                                    log.debug("超过限制的商品首单特惠不生效,商品价格为原价[" + "getPrdId:" + goods.getProductId() + "]");
                                    actInfo.setStatus(CartConstant.ACTIVITY_STATUS_INVALID);
                                }
                            } else {
                                actInfo.setStatus(CartConstant.ACTIVITY_STATUS_INVALID);
                            }
                        }

                    });
                }

}
        }
    }
}
