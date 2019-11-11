package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.activity.capsule.ActivityGoodsListCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.info.FirstSpecialProcessorDataInfo;
import com.vpu.mp.service.pojo.wxapp.activity.info.ProcessorDataInfo;
import com.vpu.mp.service.pojo.wxapp.activity.param.GoodsBaseCapsuleParam;
import com.vpu.mp.service.pojo.wxapp.cart.list.WxAppCartBo;
import com.vpu.mp.service.shop.activity.dao.FirstSpecialProcessorDao;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.user.user.UserService;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 李晓冰
 * @date 2019年11月01日
 */
@Service
public class FirstSpecialProcessor implements ActivityGoodsListProcessor, ActivityCartListStrategy {

    @Autowired
    FirstSpecialProcessorDao firstSpecialProcessorDao;

    @Autowired
    OrderInfoService orderInfoService;
    @Autowired
    private UserService userService;

    @Override
    public Byte getPriority() {
        return GoodsConstant.ACTIVITY_FIRST_SPECIAL_PRIORITY;
    }

    @Override
    public GoodsBaseCapsuleParam filterParamForList(List<ActivityGoodsListCapsule> capsules, Integer userId) {
        if (userId == null || orderInfoService.isNewUser(userId, true)) {
            return filterParamForList(capsules);
        } else {
            return null;
        }
    }

    @Override
    public GoodsBaseCapsuleParam filterParamForList(List<ActivityGoodsListCapsule> capsules) {
        GoodsBaseCapsuleParam param = new GoodsBaseCapsuleParam();
        param.setDate(DateUtil.getLocalDateTime());

        List<Integer> goodsIds = new ArrayList<>();
        capsules.forEach(capsule -> {
            if (GoodsConstant.isGoodsTypeIn13510(capsule.getGoodsType()) ||
                    capsule.getProcessedTypes().contains(GoodsConstant.ACTIVITY_TYPE_MEMBER_EXCLUSIVE)) {
                return;
            }
            goodsIds.add(capsule.getGoodsId());
        });
        param.setGoodsIds(goodsIds);
        return param;
    }

    @Override
    public Map<Integer, FirstSpecialProcessorDataInfo> getActivityInfoForList(GoodsBaseCapsuleParam param) {
        if (param.getGoodsIds().size() == 0) {
            return new HashMap<>();
        } else {
            return firstSpecialProcessorDao.getGoodsFirstSpecialForListInfo(param.getGoodsIds(), param.getDate());
        }
    }

    @Override
    public void processForList(Map<Integer, ? extends ProcessorDataInfo> activityInfos, List<ActivityGoodsListCapsule> capsules) {
        capsules.forEach(capsule -> {
            Integer goodsId = capsule.getGoodsId();
            ProcessorDataInfo activity = activityInfos.get(goodsId);
            if (activity == null) {
                return;
            }
            capsule.setRealPrice(activity.getDataPrice());
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
            List<FirstSpecialProcessorDataInfo> specialPrdIdList = firstSpecialProcessorDao.getGoodsFirstSpecialPrdId(cartBo.getProductIdList(), cartBo.getDate());
            specialPrdIdList.forEach(specialPrdId -> {
                cartBo.getCartGoodsList().forEach(goods -> {
                    if (specialPrdId.getPrdId().equals(goods.getGoodsId())) {
                        goods.getActivityList().add(specialPrdId);
                    }
                });
            });
        }
    }
}
