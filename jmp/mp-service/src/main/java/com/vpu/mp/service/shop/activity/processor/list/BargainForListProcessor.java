package com.vpu.mp.service.shop.activity.processor.list;

import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.activity.capsule.AbstractCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.capsule.ActivityGoodsListCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.info.list.BargainForListInfo;
import com.vpu.mp.service.pojo.wxapp.activity.param.ActivityGoodsListMpParam;
import com.vpu.mp.service.shop.activity.dao.BargainProcessorDao;
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
public class BargainForListProcessor extends BargainProcessorDao implements ActivityGoodsListProcessor<BargainForListInfo> {
    @Override
    public int getPriority() {
        return GoodsConstant.ACTIVITY_BARGAIN_PRIORITY;
    }

    @Override
    public ActivityGoodsListMpParam filterParam(List<ActivityGoodsListCapsule> capsules) {
        List<Integer> goodsIds = capsules.stream().filter(x -> x.getGoodsType() == GoodsConstant.ACTIVITY_TYPE_BARGAIN)
            .map(AbstractCapsule::getGoodsId).collect(Collectors.toList());
        ActivityGoodsListMpParam param = new ActivityGoodsListMpParam();
        param.setDate(DateUtil.getLocalDateTime());
        param.setGoodsIds(goodsIds);
        return param;
    }

    @Override
    public Map<Integer, BargainForListInfo> getActivityInfo(ActivityGoodsListMpParam param) {
        return getGoodsBargainListInfo(param.getGoodsIds(),param.getDate());
    }

    @Override
    public void process(Map<Integer, BargainForListInfo> activityInfos, List<ActivityGoodsListCapsule> capsules) {
        capsules.forEach(capsule->{
            Integer goodsId = capsule.getGoodsId();
            BargainForListInfo activity = activityInfos.get(goodsId);
            if (activity == null) {
                return;
            }
            capsule.setShopPrice(activity.getActivityPrice());
            capsule.getActivities().add(activity);
            capsule.getProcessedTypes().add(GoodsConstant.ACTIVITY_TYPE_BARGAIN);
        });
    }
}
