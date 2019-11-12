package com.vpu.mp.service.shop.activity.factory;

import com.vpu.mp.service.shop.activity.processor.ProcessorPriority;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author 李晓冰
 * @date 2019年10月30日
 */
@Service
@Slf4j
public abstract class AbstractProcessorFactory<P,T> {

    protected List<P> processors = new ArrayList<>();

    @Autowired(required = false)
    private List<ProcessorPriority> sortProcessors;

    protected void sort(){
        sortProcessors.sort(Comparator.comparing(ProcessorPriority::getPriority));
    }

    @PostConstruct
    @SuppressWarnings("unchecked")
    protected void init(){
        getProcessorImplClass();
        if (sortProcessors == null || sortProcessors.size() == 0) {
            LoggerFactory.getLogger(this.getClass()).error("{}处理器工厂初始化失败", this.getClass());
            processors = new ArrayList<>();
        } else {
            sort();
            sortProcessors.forEach(processorPriority -> {
                try {
                    Class<?> clazz =  getProcessorImplClass();
                    if (clazz.isAssignableFrom(processorPriority.getClass())) {
                        processors.add((P) processorPriority);
                    }
                }catch (Exception e){

                }
            });
            LoggerFactory.getLogger(this.getClass()).debug(processors.toString());
        }
    }

    private Class<?> getProcessorImplClass(){
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        if (!(genericSuperclass instanceof ParameterizedType)) {
            throw new IllegalArgumentException();
        }
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;

        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        return (Class<?>) actualTypeArguments[0];
    }

    public abstract void doProcess(List<T> capsules,Integer userId);
}
