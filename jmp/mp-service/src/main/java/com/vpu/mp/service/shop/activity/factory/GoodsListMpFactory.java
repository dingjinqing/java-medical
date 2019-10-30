package com.vpu.mp.service.shop.activity.factory;

import com.vpu.mp.service.pojo.wxapp.activity.capsule.ActivityGoodsListCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.info.ActivityForListInfo;
import com.vpu.mp.service.pojo.wxapp.activity.param.ActivityGoodsListMpParam;
import com.vpu.mp.service.shop.activity.processor.ActivityGoodsListProcessor;
import com.vpu.mp.service.shop.activity.processor.ActivityProcessor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @author 李晓冰
 * @date 2019年10月29日
 */
@Service
public class GoodsListMpFactory {
    @Autowired
    List<ActivityGoodsListProcessor> processors;


    @PostConstruct
    public void init(){
        if (processors == null || processors.size() == 0) {
            LoggerFactory.getLogger(this.getClass()).error("{}处理器工厂初始化失败", this.getClass());
            processors = new ArrayList<>();
        } else {
            processors.sort(Comparator.comparing(ActivityProcessor::getPriority));
            LoggerFactory.getLogger(this.getClass()).debug(processors.toString());
        }
    }

    public void doProcess(List<ActivityGoodsListCapsule> capsules) {
        for (ActivityProcessor<ActivityGoodsListMpParam, ActivityGoodsListCapsule, ActivityForListInfo> processor : processors) {
            ActivityGoodsListMpParam param = processor.filterParam(capsules);
            Map<Integer, ActivityForListInfo> activityInfo = processor.getActivityInfo(param);
            processor.process(activityInfo,capsules);
//            processor.autoProcess(capsules);
        }
    }

}
