package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.pojo.wxapp.activity.info.ActivityBaseInfo;
import com.vpu.mp.service.pojo.wxapp.activity.capsule.AbstractCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.param.ActivityParam;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * 商品活动处理器接口 P:获取相应活动信息的过滤参数，
 * @author 李晓冰
 * @date 2019年10月29日
 */
public interface ActivityProcessor<P extends ActivityParam,C extends AbstractCapsule,V extends ActivityBaseInfo>{
    int getPriority();

    default P filterParam(List<C> capsules){
        return filterParam();
    }

    default P filterParam(){
        P param=null;

        Class<? extends ActivityProcessor> clazz = this.getClass();
        Class<?>[] interfaces = clazz.getInterfaces();
        int index = 0;
        for (Class<?> anInterface : interfaces) {
            if (anInterface.equals(ActivityProcessor.class)) {
                break;
            }
            index++;
        }
        if (index == interfaces.length) {
            LoggerFactory.getLogger(ActivityProcessor.class).error("商品活动处理器{0}未实现{1}接口",clazz,ActivityProcessor.class);
            return param;
        }

        Type[] genericInterfaces = clazz.getGenericInterfaces();
        ParameterizedType parameterizedType = (ParameterizedType) genericInterfaces[index];
        Class paramClazz = (Class) parameterizedType.getActualTypeArguments()[0];
        try {
            param = (P)paramClazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            LoggerFactory.getLogger(ActivityProcessor.class).error("商品活动处理器{0}实例化参数{1}错误",clazz,paramClazz);
        }
        return param;
    }

    Map<Integer,V> getActivityInfo(P p);

    void process(Map<Integer,V> activityInfos,List<C> capsules);

    default void autoProcess(List<C> capsules){
        P p = filterParam(capsules);
        Map<Integer, V> activityInfo = getActivityInfo(p);
        process(activityInfo,capsules);
    }
}
