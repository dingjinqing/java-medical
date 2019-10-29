package com.vpu.mp.service.shop.activity.processor.groupbuy;

import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.activity.capsule.ActivityGoodsListCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.info.groupbuy.ActivityGroupBuyListInfo;
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
    ActivityGoodsListProcessor<ActivityGroupBuyListInfo> {

    @Override
    public int getPriority() {
        return GoodsConstant.ACTIVITY_GROUP_BUY_PRIORITY;
    }

    @Override
    public ActivityGoodsListMpParam filterParam(List<ActivityGoodsListCapsule> capsules) {
        List<Integer> goodsIds = capsules.stream().filter(x -> x.getGoodsType() == GoodsConstant.GOODS_TYPE_GROUP_BUY)
            .map(x -> x.getCapsuleId()).collect(Collectors.toList());
        ActivityGoodsListMpParam param = new ActivityGoodsListMpParam();
        param.setGoodsIds(goodsIds);
        param.setDate(DateUtil.getLocalDateTime());
        return param;
    }

    @Override
    public Map<Integer, ActivityGroupBuyListInfo> getActivityInfo(ActivityGoodsListMpParam param) {
        return getGoodsGroupByListInfo(param.getGoodsIds(),param.getDate());
    }

    @Override
    public void process(Map<Integer, ActivityGroupBuyListInfo> activityInfos, List<ActivityGoodsListCapsule> capsules) {
        capsules.forEach(capsule->{
            Integer goodsId = capsule.getCapsuleId();
            ActivityGroupBuyListInfo activity = activityInfos.get(goodsId);
            if (activity == null) {
                return;
            }
            capsule.setLinePrice(activity.getOriginalMaxPrice());
            capsule.setRealPrice(activity.getActivityPrice());
            capsule.getActivities().add(activity);
        });
    }
}
