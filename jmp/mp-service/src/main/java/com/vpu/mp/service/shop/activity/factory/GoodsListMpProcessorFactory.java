package com.vpu.mp.service.shop.activity.factory;

import com.vpu.mp.service.pojo.wxapp.activity.capsule.ActivityGoodsListCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.info.ProcessorDataInfo;
import com.vpu.mp.service.pojo.wxapp.activity.param.GoodsBaseCapsuleParam;
import com.vpu.mp.service.shop.activity.processor.ActivityGoodsListProcessor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @author 李晓冰
 * @date 2019年10月29日
 */
@Service
public class GoodsListMpProcessorFactory extends AbstractProcessorFactory<ActivityGoodsListProcessor, ActivityGoodsListCapsule> {
    @Override
    public void sortProcessors() {
        processors.sort(Comparator.comparing(ActivityGoodsListProcessor::getPriority));
    }

    @Override
    public void doProcess(List<ActivityGoodsListCapsule> capsules,Integer userId) {
        for (ActivityGoodsListProcessor processor : processors) {
            GoodsBaseCapsuleParam param = processor.filterParamForList(capsules, userId);
            Map<Integer, ? extends ProcessorDataInfo> activityInfoForList = processor.getActivityInfoForList(param);
            processor.processForList(activityInfoForList,capsules);
        }
    }
}
