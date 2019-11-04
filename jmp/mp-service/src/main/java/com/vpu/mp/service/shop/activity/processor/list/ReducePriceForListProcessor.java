package com.vpu.mp.service.shop.activity.processor.list;

import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.activity.capsule.AbstractCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.capsule.ActivityGoodsListCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.info.list.ReducePriceForListInfo;
import com.vpu.mp.service.pojo.wxapp.activity.param.ActivityGoodsListMpParam;
import com.vpu.mp.service.shop.activity.dao.ReducePriceProcessorDao;
import com.vpu.mp.service.shop.activity.processor.ActivityGoodsListProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 李晓冰
 * @date 2019年11月01日
 */
@Service
public class ReducePriceForListProcessor extends ReducePriceProcessorDao implements ActivityGoodsListProcessor<ReducePriceForListInfo> {
    @Override
    public int getPriority() {
        return GoodsConstant.ACTIVITY_REDUCE_PRICE_PRIORITY;
    }

    @Override
    public ActivityGoodsListMpParam filterParam(List<ActivityGoodsListCapsule> capsules) {
        // 处理优惠券和满减活动外，未被其他活动处理
        List<Integer> goodsIds = capsules.stream().filter(x -> x.getGoodsType() == GoodsConstant.ACTIVITY_TYPE_REDUCE_PRICE && x.getProcessedTypes().size() == 0)
            .map(AbstractCapsule::getGoodsId).collect(Collectors.toList());
        ActivityGoodsListMpParam param = new ActivityGoodsListMpParam();
        param.setGoodsIds(goodsIds);
        param.setDate(DateUtil.getLocalDateTime());
        return param;
    }

    @Override
    public Map<Integer, ReducePriceForListInfo> getActivityInfo(ActivityGoodsListMpParam param) {
        return getGoodsReduceListInfo(param.getGoodsIds(),param.getDate());
    }

    @Override
    public void process(Map<Integer, ReducePriceForListInfo> activityInfos, List<ActivityGoodsListCapsule> capsules) {
        capsules.forEach(capsule->{
            Integer goodsId = capsule.getGoodsId();
            ReducePriceForListInfo activity = activityInfos.get(goodsId);
            if (activity == null) {
                return;
            }
            capsule.setShopPrice(activity.getActivityPrice());
            capsule.getActivities().add(activity);
            capsule.getProcessedTypes().add(GoodsConstant.ACTIVITY_TYPE_REDUCE_PRICE);
        });
    }
}
