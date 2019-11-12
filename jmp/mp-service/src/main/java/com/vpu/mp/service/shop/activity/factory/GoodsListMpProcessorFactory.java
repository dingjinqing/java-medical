package com.vpu.mp.service.shop.activity.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.wxapp.activity.capsule.ActivityGoodsListCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.info.ProcessorDataInfo;
import com.vpu.mp.service.pojo.wxapp.activity.param.GoodsBaseCapsuleParam;
import com.vpu.mp.service.shop.activity.processor.ActivityGoodsListProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @author 李晓冰
 * @date 2019年10月29日
 */
@Service
@Slf4j
public class GoodsListMpProcessorFactory extends AbstractProcessorFactory<ActivityGoodsListProcessor, ActivityGoodsListCapsule> {
    @Override
    public void sortProcessors() {
        processors.sort(Comparator.comparing(ActivityGoodsListProcessor::getPriority));
    }

    @Override
    public void doProcess(List<ActivityGoodsListCapsule> capsules,Integer userId) {

        for (ActivityGoodsListProcessor processor : processors) {
            GoodsBaseCapsuleParam param=null;
            Map<Integer, ? extends ProcessorDataInfo> activityInfoForList=null;
            try{

                 param = processor.filterParamForList(capsules, userId);
                activityInfoForList= processor.getActivityInfoForList(param);
                processor.processForList(activityInfoForList,capsules);

            }catch (Exception e){
                log.error("日我日日我日日日日1");
                log.error("Processor wrong {}-{}-{}",processor.getClass(), Util.toJson(param),Util.toJson(activityInfoForList));
            }

        }
    }
}
