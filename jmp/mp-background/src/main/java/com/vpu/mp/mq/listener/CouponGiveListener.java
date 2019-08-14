package com.vpu.mp.mq.listener;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.vpu.mp.config.mq.RabbitConfig;
import com.vpu.mp.service.foundation.mq.handler.BaseRabbitHandler;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveQueueParam;
import com.vpu.mp.service.saas.SaasApplication;

@Component
@RabbitListener(queues = RabbitConfig.QUEUE_COUPON_SEND, containerFactory = "simpleRabbitListenerContainerFactory")
public class CouponGiveListener implements BaseRabbitHandler {
	
	@Autowired
	protected SaasApplication saas;
	
	@RabbitHandler
    public void handler(@Payload CouponGiveQueueParam param, Message message, Channel channel) throws IOException {
		try {
			saas.getShopApp(param.getShopId()).coupon.couponGiveService.handlerGouonGive(param);
			this.success(channel, message);
		}catch(Exception e) {
			this.failNotReturn(channel, message);
		}
    }
}
