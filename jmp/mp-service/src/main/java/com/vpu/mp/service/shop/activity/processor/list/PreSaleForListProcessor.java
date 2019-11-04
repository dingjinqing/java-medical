package com.vpu.mp.service.shop.activity.processor.list;

import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.activity.capsule.AbstractCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.capsule.ActivityGoodsListCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.info.list.PreSaleForListInfo;
import com.vpu.mp.service.pojo.wxapp.activity.param.ActivityGoodsListMpParam;
import com.vpu.mp.service.shop.activity.dao.PreSaleProcessorDao;
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
public class PreSaleForListProcessor extends PreSaleProcessorDao implements ActivityGoodsListProcessor<PreSaleForListInfo> {
    @Override
    public int getPriority() {
        return GoodsConstant.ACTIVITY_PRE_SALE_PRIORITY;
    }

    @Override
    public ActivityGoodsListMpParam filterParam(List<ActivityGoodsListCapsule> capsules) {
        List<Integer> goodsIds = capsules.stream().filter(x -> x.getGoodsType() == GoodsConstant.ACTIVITY_TYPE_PRE_SALE)
            .map(AbstractCapsule::getGoodsId).collect(Collectors.toList());
        ActivityGoodsListMpParam param = new ActivityGoodsListMpParam();
        param.setGoodsIds(goodsIds);
        param.setDate(DateUtil.getLocalDateTime());
        return param;
    }

    @Override
    public Map<Integer, PreSaleForListInfo> getActivityInfo(ActivityGoodsListMpParam param) {
        return getGoodsPreSaleListInfo(param.getGoodsIds(),param.getDate());
    }

    @Override
    public void process(Map<Integer, PreSaleForListInfo> activityInfos, List<ActivityGoodsListCapsule> capsules) {
        capsules.forEach(capsule->{
            Integer goodsId = capsule.getGoodsId();
            PreSaleForListInfo activity = activityInfos.get(goodsId);
            if (activity == null) {
                return;
            }
            capsule.setShopPrice(activity.getActivityPrice());
            capsule.getActivities().add(activity);
            capsule.getProcessedTypes().add(GoodsConstant.ACTIVITY_TYPE_PRE_SALE);
        });
    }
}
