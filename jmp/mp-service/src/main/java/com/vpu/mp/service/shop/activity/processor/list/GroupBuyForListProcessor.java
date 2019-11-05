package com.vpu.mp.service.shop.activity.processor.list;

import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.activity.capsule.AbstractCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.capsule.ActivityGoodsListCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.info.list.GroupBuyForListInfo;
import com.vpu.mp.service.pojo.wxapp.activity.param.ActivityGoodsListMpParam;
import com.vpu.mp.service.shop.activity.dao.GroupBuyProcessorDao;
import com.vpu.mp.service.shop.activity.processor.ActivityGoodsListProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 李晓冰
 * @date 2019年10月29日
 */
@Service
public class GroupBuyForListProcessor extends GroupBuyProcessorDao implements
    ActivityGoodsListProcessor<GroupBuyForListInfo> {

    @Override
    public int getPriority() {
        return GoodsConstant.ACTIVITY_GROUP_BUY_PRIORITY;
    }

    @Override
    public ActivityGoodsListMpParam filterParam(List<ActivityGoodsListCapsule> capsules) {
        List<Integer> goodsIds = capsules.stream().filter(x -> x.getGoodsType() == GoodsConstant.ACTIVITY_TYPE_GROUP_BUY)
            .map(AbstractCapsule::getGoodsId).collect(Collectors.toList());
        ActivityGoodsListMpParam param = new ActivityGoodsListMpParam();
        param.setGoodsIds(goodsIds);
        param.setDate(DateUtil.getLocalDateTime());
        return param;
    }

    @Override
    public Map<Integer, GroupBuyForListInfo> getActivityInfo(ActivityGoodsListMpParam param) {
        return getGoodsGroupBuyListInfo(param.getGoodsIds(),param.getDate());
    }

    @Override
    public void process(Map<Integer, GroupBuyForListInfo> activityInfos, List<ActivityGoodsListCapsule> capsules) {
        capsules.forEach(capsule->{
            Integer goodsId = capsule.getGoodsId();
            GroupBuyForListInfo activity = activityInfos.get(goodsId);
            if (activity == null) {
                return;
            }
            capsule.setRealPrice(activity.getActivityPrice());
            capsule.getActivities().add(activity);
            capsule.getProcessedTypes().add(GoodsConstant.ACTIVITY_TYPE_GROUP_BUY);
        });
    }
}
