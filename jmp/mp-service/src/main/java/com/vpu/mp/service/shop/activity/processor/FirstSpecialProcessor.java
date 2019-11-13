package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.market.firstspecial.FirstSpecialProductBo;
import com.vpu.mp.service.pojo.wxapp.activity.capsule.ActivityGoodsListCapsule;
import com.vpu.mp.service.pojo.wxapp.cart.CartConstant;
import com.vpu.mp.service.pojo.wxapp.cart.list.CartActivityInfo;
import com.vpu.mp.service.pojo.wxapp.cart.list.WxAppCartBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsActivityBaseMp;
import com.vpu.mp.service.shop.activity.dao.FirstSpecialProcessorDao;
import com.vpu.mp.service.shop.config.FirstSpecialConfigService;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.user.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Record3;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
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
    private UserService userService;
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
    public void processForList(List<ActivityGoodsListCapsule> capsules, Integer userId) {
        if (userId != null && !orderInfoService.isNewUser(userId, true)) {
            return;
        }
        List<ActivityGoodsListCapsule> availabelCapsules = capsules.stream().filter(x -> !GoodsConstant.isGoodsTypeIn13510(x.getGoodsType()) && !x.getProcessedTypes().contains(GoodsConstant.ACTIVITY_TYPE_MEMBER_EXCLUSIVE))
                .collect(Collectors.toList());

        List<Integer> gooodsIds = availabelCapsules.stream().map(ActivityGoodsListCapsule::getGoodsId).collect(Collectors.toList());
        Map<Integer, Result<Record3<Integer, Integer, BigDecimal>>> firstSpecialPrds = firstSpecialProcessorDao.getGoodsFirstSpecialForListInfo(gooodsIds, DateUtil.getLocalDateTime());

        availabelCapsules.forEach(capsule -> {
            Integer goodsId = capsule.getGoodsId();
            Result<Record3<Integer, Integer, BigDecimal>> result = firstSpecialPrds.get(goodsId);
            if (result == null) {
                return;
            }
            capsule.setRealPrice(result.get(0).get(FIRST_SPECIAL_PRODUCT.PRD_PRICE));
            GoodsActivityBaseMp activity = new GoodsActivityBaseMp();
            activity.setActivityType(GoodsConstant.ACTIVITY_TYPE_FIRST_SPECIAL);
            activity.setActivityId(result.get(0).get(FIRST_SPECIAL.ID));
            capsule.getActivities().add(activity);
            capsule.getProcessedTypes().add(GoodsConstant.ACTIVITY_TYPE_FIRST_SPECIAL);
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
        log.info("FirstSpecialProcessor->", Util.toJson(cartBo));
        boolean isNewUser = orderInfoService.isNewUser(cartBo.getUserId());
        cartBo.setIsNewUser(isNewUser);
        if (isNewUser) {
            List<FirstSpecialProductBo> specialPrdIdList = firstSpecialProcessorDao.getGoodsFirstSpecialPrdId(cartBo.getProductIdList(), cartBo.getDate())
                    .into(FirstSpecialProductBo.class);
            if (specialPrdIdList != null && specialPrdIdList.size() > 0) {
                //全局商品种类限制
                Integer limitGoodsNum = firstSpecialConfigService.getFirstLimitGoods();
                AtomicReference<Integer> goodsNum = new AtomicReference<>(0);
                specialPrdIdList.forEach(firstSpecial -> {
                    cartBo.getCartGoodsList().forEach(goods -> {
                        if (goods.getPrdId().equals(firstSpecial.getPrdId())) {
                            CartActivityInfo firstActivityInfo = new CartActivityInfo();
                            firstActivityInfo.setActivityType(GoodsConstant.ACTIVITY_TYPE_FIRST_SPECIAL);
                            firstActivityInfo.setFirstSpecialPrice(firstSpecial.getPrdPrice());
                            if (firstSpecial.getLimitAmount() > 0 && goods.getGoodsNumber() > firstSpecial.getLimitAmount()) {
                                //超出限购数量后，买家不可继续添加购买该商品
                                if (firstSpecial.getLimitFlag().equals(BaseConstant.FIRST_SPECIAL_LIMIT_FLAG_CONTINUE)){
                                    firstActivityInfo.setStatus(CartConstant.ACTIVITY_STATUS_INVALID);
                                }else {
                                    //todo 修改购物车商品数量
                                    goods.setGoodsNumber(firstSpecial.getLimitAmount());
                                }
                            }
                            if (limitGoodsNum != 0&& goodsNum.get() >=limitGoodsNum) {
                                firstActivityInfo.setStatus(CartConstant.ACTIVITY_STATUS_INVALID);
                            }
                            if (goods.getIsChecked().equals(CartConstant.CART_IS_CHECKED)){
                                goodsNum.updateAndGet(v -> v + 1);
                            }
                            goods.getCartActivityInfos().add(firstActivityInfo);
                        }
                    });
                });


            }
        }
    }
}
