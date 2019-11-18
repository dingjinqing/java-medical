package com.vpu.mp.service.shop.activity.factory;

import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeVo;
import com.vpu.mp.service.shop.activity.processor.OrderCreatePayBeforeProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 生成订单时的营销处理
 * @author: 王兵兵
 * @create: 2019-11-18 15:51
 **/
@Service
public class OrderCreatePayBeforeMpProcessorFactory extends AbstractProcessorFactory<OrderCreatePayBeforeProcessor, OrderBeforeVo> {
    @Override
    public void doProcess(List<OrderBeforeVo> capsules, Integer userId) throws MpException {
        if (capsules == null || capsules.size() == 0) {
            return;
        }
        this.doProcess(capsules.get(0));
    }

    private void doProcess(OrderBeforeVo order) throws MpException {
        for (OrderCreatePayBeforeProcessor processor : processors) {
            processor.processPayBefore(order);
        }
    }
}
