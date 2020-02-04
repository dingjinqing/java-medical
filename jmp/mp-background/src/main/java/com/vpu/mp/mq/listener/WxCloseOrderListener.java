package com.vpu.mp.mq.listener;

/**
 * 微信接口关闭订单
 * @author: 王兵兵
 * @create: 2020-02-04 11:54
 **/

import com.github.binarywang.wxpay.exception.WxPayException;
import com.rabbitmq.client.Channel;
import com.vpu.mp.config.mq.RabbitConfig;
import com.vpu.mp.service.foundation.mq.handler.BaseRabbitHandler;
import com.vpu.mp.service.pojo.shop.store.service.order.OrderCloseQueenParam;
import com.vpu.mp.service.saas.SaasApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RabbitListener(queues = {RabbitConfig.CLOSE_ORDER},containerFactory = "simpleRabbitListenerContainerFactory")
public class WxCloseOrderListener implements BaseRabbitHandler {
    @Autowired
    private SaasApplication saas;



    @RabbitHandler
    public void handler(@Payload OrderCloseQueenParam param, Message message, Channel channel){
        log.info("关闭订单队列");
        try {
            saas.getShopApp(param.getShopId()).pay.mpPay.wxCloseOrder(param.getOrderSn());
        } catch (WxPayException e) {
            log.error("关闭订单队列执行失败",e);
        }
    }


    @Override
    public void executeException(Object[] datas, Throwable throwable) {

    }
}
