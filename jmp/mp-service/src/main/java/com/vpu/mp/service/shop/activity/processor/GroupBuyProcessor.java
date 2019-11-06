package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.activity.capsule.ActivityGoodsListCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.info.ActivityForListInfo;
import com.vpu.mp.service.pojo.wxapp.activity.info.list.GroupBuyForListInfo;
import com.vpu.mp.service.pojo.wxapp.activity.param.ActivityGoodsListMpParam;
import com.vpu.mp.service.shop.activity.dao.GroupBuyProcessorDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 李晓冰
 * @date 2019年10月29日
 */
@Service
public class GroupBuyProcessor extends GroupBuyProcessorDao implements ActivityGoodsListProcessor {

    @Override
    public Byte getPriority() {
        return GoodsConstant.ACTIVITY_GROUP_BUY_PRIORITY;
    }

    @Override
    public ActivityGoodsListMpParam filterParamForList(List<ActivityGoodsListCapsule> capsules) {
        List<Integer> goodsIds = capsules.stream().filter(x -> GoodsConstant.ACTIVITY_TYPE_GROUP_BUY.equals(x.getGoodsType()))
            .map(ActivityGoodsListCapsule::getGoodsId).collect(Collectors.toList());
        ActivityGoodsListMpParam param = new ActivityGoodsListMpParam();
        param.setGoodsIds(goodsIds);
        param.setDate(DateUtil.getLocalDateTime());
        return param;
    }

    @Override
    public Map<Integer, GroupBuyForListInfo> getActivityInfoForList(ActivityGoodsListMpParam param) {
        return getGoodsGroupBuyListInfo(param.getGoodsIds(),param.getDate());
    }

    @Override
    public void processForList(Map<Integer,? extends ActivityForListInfo> activityInfos, List<ActivityGoodsListCapsule> capsules) {
        capsules.forEach(capsule->{
            Integer goodsId = capsule.getGoodsId();
            ActivityForListInfo activity = activityInfos.get(goodsId);
            if (activity == null) {
                return;
            }
            capsule.setRealPrice(activity.getActivityPrice());
            capsule.getActivities().add(activity);
            capsule.getProcessedTypes().add(GoodsConstant.ACTIVITY_TYPE_GROUP_BUY);
        });
    }
}
