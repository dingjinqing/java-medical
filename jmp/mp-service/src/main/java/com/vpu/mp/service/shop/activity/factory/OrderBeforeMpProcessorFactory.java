package com.vpu.mp.service.shop.activity.factory;

import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeVo;
import com.vpu.mp.service.shop.activity.processor.OrderBeforeProcessor;
import com.vpu.mp.service.shop.activity.processor.SecKillProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 跳转结算页所需要的营销数据处理
 * @author: 王兵兵
 * @create: 2019-11-14 17:24
 **/
@Service
@Slf4j
public class OrderBeforeMpProcessorFactory extends AbstractProcessorFactory<OrderBeforeProcessor, OrderBeforeParam> {
    @Override
    public void doProcess(List<OrderBeforeParam> capsules, Integer userId) {

    }

    public Map<Class<? extends OrderBeforeProcessor>,OrderBeforeProcessor> processorMap;

    @Override
    @PostConstruct
    protected void init(){
        super.init();
        processorMap = new HashMap<>(processors.size());
        for (OrderBeforeProcessor processor : processors) {
            processorMap.put(processor.getClass(), processor);
        }
        log.debug(processorMap.toString());
    }

    public void doProcess(OrderBeforeParam orderBeforeParam, OrderBeforeVo vo) {
        if(orderBeforeParam.getActivityType().equals(BaseConstant.ACTIVITY_TYPE_SEC_KILL)){
            //秒杀
            processorMap.get(SecKillProcessor.class).processOrderBefore(orderBeforeParam,vo);
        }else if(orderBeforeParam.getActivityType().equals(BaseConstant.ACTIVITY_TYPE_BARGAIN)){
            //砍价

        }
    }
}
