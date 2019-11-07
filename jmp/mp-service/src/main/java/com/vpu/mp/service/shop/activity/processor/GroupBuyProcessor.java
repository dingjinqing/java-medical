package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.activity.capsule.ActivityGoodsListCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.info.ProcessorDataInfo;
import com.vpu.mp.service.pojo.wxapp.activity.info.GroupBuyProcessorDataInfo;
import com.vpu.mp.service.pojo.wxapp.activity.param.GoodsBaseCapsuleParam;
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
    public GoodsBaseCapsuleParam filterParamForList(List<ActivityGoodsListCapsule> capsules) {
        List<Integer> goodsIds = capsules.stream().filter(x -> GoodsConstant.ACTIVITY_TYPE_GROUP_BUY.equals(x.getGoodsType()))
            .map(ActivityGoodsListCapsule::getGoodsId).collect(Collectors.toList());
        GoodsBaseCapsuleParam param = new GoodsBaseCapsuleParam();
        param.setGoodsIds(goodsIds);
        param.setDate(DateUtil.getLocalDateTime());
        return param;
    }

    @Override
    public Map<Integer, GroupBuyProcessorDataInfo> getActivityInfoForList(GoodsBaseCapsuleParam param) {
        return getGoodsGroupBuyListInfo(param.getGoodsIds(),param.getDate());
    }

    @Override
    public void processForList(Map<Integer,? extends ProcessorDataInfo> activityInfos, List<ActivityGoodsListCapsule> capsules) {
        capsules.forEach(capsule->{
            Integer goodsId = capsule.getGoodsId();
            ProcessorDataInfo activity = activityInfos.get(goodsId);
            if (activity == null) {
                return;
            }
            capsule.setRealPrice(activity.getDataPrice());
            capsule.getActivities().add(activity);
            capsule.getProcessedTypes().add(GoodsConstant.ACTIVITY_TYPE_GROUP_BUY);
        });
    }
}
