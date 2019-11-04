package com.vpu.mp.service.shop.activity.processor.list;

import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.activity.capsule.ActivityGoodsListCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.info.list.FirstSpecialForListInfo;
import com.vpu.mp.service.pojo.wxapp.activity.param.ActivityGoodsListMpParam;
import com.vpu.mp.service.shop.activity.dao.FirstSpecialProcessorDao;
import com.vpu.mp.service.shop.activity.processor.ActivityGoodsListProcessor;
import com.vpu.mp.service.shop.activity.processor.ActivityProcessor;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 李晓冰
 * @date 2019年11月01日
 */
@Service
public class FirstSpecialForListProcessor extends FirstSpecialProcessorDao implements ActivityGoodsListProcessor<FirstSpecialForListInfo> {

    @Autowired OrderInfoService orderInfoService;

    @Override
    public int getPriority() {
        return GoodsConstant.ACTIVITY_FIRST_SPECIAL_PRIORITY;
    }

    @Override
    public ActivityGoodsListMpParam filterParam(List<ActivityGoodsListCapsule> capsules, Integer userId) {
        if (!orderInfoService.isNewUser(userId, true)) {
            return null;
        } else {
            return filterParam(capsules);
        }
    }

    @Override
    public ActivityGoodsListMpParam filterParam(List<ActivityGoodsListCapsule> capsules) {
        ActivityGoodsListMpParam param = new ActivityGoodsListMpParam();
        param.setDate(DateUtil.getLocalDateTime());

        List<Integer> goodsIds = new ArrayList<>();
        capsules.forEach(capsule->{
            if (ActivityProcessor.isGoodsTypeIn13510(capsule.getGoodsType()) ||
                capsule.getProcessedTypes().contains(GoodsConstant.ACTIVITY_TYPE_MEMBER_EXCLUSIVE)) {
                return;
            }
            goodsIds.add(capsule.getCapsuleId());
        });
        param.setGoodsIds(goodsIds);
        return param;
    }

    @Override
    public Map<Integer, FirstSpecialForListInfo> getActivityInfo(ActivityGoodsListMpParam param) {
        return getGoodsFirstSpecialForListInfo(param.getGoodsIds(),param.getDate());
    }

    @Override
    public void process(Map<Integer, FirstSpecialForListInfo> activityInfos, List<ActivityGoodsListCapsule> capsules) {
        capsules.forEach(capsule->{
            Integer goodsId = capsule.getCapsuleId();
            FirstSpecialForListInfo activity = activityInfos.get(goodsId);
            if (activity == null) {
                return;
            }
            capsule.setGoodsPrice(activity.getActivityPrice());
            capsule.getActivities().add(activity);
            capsule.getProcessedTypes().add(GoodsConstant.ACTIVITY_TYPE_FIRST_SPECIAL);
        });
    }
}
