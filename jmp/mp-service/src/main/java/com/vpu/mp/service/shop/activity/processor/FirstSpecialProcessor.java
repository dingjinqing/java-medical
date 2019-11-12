package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.db.shop.tables.records.FirstSpecialProductRecord;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.activity.capsule.ActivityGoodsListCapsule;
import com.vpu.mp.service.pojo.wxapp.cart.list.WxAppCartBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.list.GoodsActivityBaseMpVo;
import com.vpu.mp.service.shop.activity.dao.FirstSpecialProcessorDao;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.user.user.UserService;
import org.jooq.Record3;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.FirstSpecial.FIRST_SPECIAL;
import static com.vpu.mp.db.shop.tables.FirstSpecialProduct.FIRST_SPECIAL_PRODUCT;

/**
 * @author 李晓冰
 * @date 2019年11月01日
 */
@Service
public class FirstSpecialProcessor implements ProcessorPriority,ActivityGoodsListProcessor, ActivityCartListStrategy {

    @Autowired
    FirstSpecialProcessorDao firstSpecialProcessorDao;

    @Autowired
    OrderInfoService orderInfoService;
    @Autowired
    private UserService userService;

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
        Map<Integer, Result<Record3<Integer, Integer, BigDecimal>>> firstSpecialPrds = firstSpecialProcessorDao.getGoodsFirstSpecialForListInfo(gooodsIds,DateUtil.getLocalDateTime());

        availabelCapsules.forEach(capsule->{
            Integer goodsId = capsule.getGoodsId();
            Result<Record3<Integer, Integer, BigDecimal>> result = firstSpecialPrds.get(goodsId);
            if (result == null) {
                return;
            }
            capsule.setRealPrice(result.get(0).get(FIRST_SPECIAL_PRODUCT.PRD_PRICE));
            GoodsActivityBaseMpVo activity = new GoodsActivityBaseMpVo();
            activity.setActivityType(GoodsConstant.ACTIVITY_TYPE_FIRST_SPECIAL);
            activity.setActivityId(result.get(0).get(FIRST_SPECIAL.ID));
            capsule.getActivities().add(activity);
            capsule.getProcessedTypes().add(GoodsConstant.ACTIVITY_TYPE_FIRST_SPECIAL);
        });
    }

    //**********************购物车********************
    /**
     * 购物车首单特惠
     * @param cartBo 业务数据类
     */
    @Override
    public void doCartOperation(WxAppCartBo cartBo) {
        boolean isNewUser = orderInfoService.isNewUser(cartBo.getUserId());
        cartBo.setIsNewUser(isNewUser);
        if (isNewUser) {
            List<FirstSpecialProductRecord> specialPrdIdList = firstSpecialProcessorDao.getGoodsFirstSpecialPrdId(cartBo.getProductIdList(), cartBo.getDate());
            specialPrdIdList.forEach(specialPrdId -> {
                cartBo.getCartGoodsList().forEach(goods -> {
                    if (specialPrdId.getPrdId().equals(goods.getGoodsId())) {

                    }
                });
            });
        }
    }
}
