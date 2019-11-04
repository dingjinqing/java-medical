package com.vpu.mp.service.shop.activity.factory;

import com.vpu.mp.service.pojo.wxapp.activity.capsule.AbstractCapsule;
import com.vpu.mp.service.shop.activity.processor.ActivityProcessor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author 李晓冰
 * @date 2019年10月30日
 */
@Service
public abstract class AbstractProcessorFactory<P extends ActivityProcessor,T extends AbstractCapsule> {
    @Autowired(required = false)
    protected List<P> processors;

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

    public abstract void doProcess(List<T> capsules,Integer userId);
}
