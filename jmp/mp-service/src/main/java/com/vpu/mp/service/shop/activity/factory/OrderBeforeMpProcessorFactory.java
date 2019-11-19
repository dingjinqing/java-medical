package com.vpu.mp.service.shop.activity.factory;

import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeVo;
import com.vpu.mp.service.shop.activity.processor.OrderBeforeProcessor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 跳转结算页所需要的营销数据处理
 * @author: 王兵兵
 * @create: 2019-11-14 17:24
 **/
@Service
public class OrderBeforeMpProcessorFactory extends AbstractProcessorFactory<OrderBeforeProcessor, OrderBeforeParam> {
    @Override
    public void doProcess(List<OrderBeforeParam> capsules, Integer userId) {
    }

    @PostConstruct
    protected void init(){

    }

    public void doProcess(OrderBeforeParam orderBeforeParam, OrderBeforeVo vo) {
        if(orderBeforeParam.getActivityType().equals(BaseConstant.ACTIVITY_TYPE_SEC_KILL)){
            //processors.get
        }


        for (OrderBeforeProcessor processor : processors) {
            processor.processOrderBefore(orderBeforeParam,vo);
        }
    }
}
