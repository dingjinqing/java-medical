package com.vpu.mp.service.shop.activity.factory;

import com.vpu.mp.service.pojo.wxapp.activity.capsule.ActivityGoodsListCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.info.ActivityForListInfo;
import com.vpu.mp.service.pojo.wxapp.activity.param.ActivityGoodsListMpParam;
import com.vpu.mp.service.shop.activity.processor.ActivityGoodsListProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 李晓冰
 * @date 2019年10月29日
 */
@Service
public class GoodsListMpProcessorFactory extends AbstractProcessorFactory<ActivityGoodsListProcessor, ActivityGoodsListCapsule> {
    @Override
    public void doProcess(List<ActivityGoodsListCapsule> capsules,Integer userId) {
        for (ActivityGoodsListProcessor<ActivityForListInfo> processor : processors) {
            ActivityGoodsListMpParam param = processor.filterParam(capsules,userId);
            Map<Integer, ActivityForListInfo> activityInfo = processor.getActivityInfo(param);
            processor.process(activityInfo, capsules);
        }
    }
}
