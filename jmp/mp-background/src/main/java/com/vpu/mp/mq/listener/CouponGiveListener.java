package com.vpu.mp.mq.listener;

import java.util.List;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.vpu.mp.config.mq.RabbitConfig;
import com.vpu.mp.service.foundation.mq.handler.BaseRabbitHandler;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveQueueParam;
import com.vpu.mp.service.saas.SaasApplication;

@Component
@RabbitListener(queues = RabbitConfig.QUEUE_COUPON_SEND,
    containerFactory = "simpleRabbitListenerContainerFactory")
public class CouponGiveListener implements BaseRabbitHandler {
	
	@Autowired
	protected SaasApplication saas;
	
	@RabbitHandler
    public void handler(@Payload CouponGiveQueueParam param, Message message, Channel channel) {
		List<Integer> rowsList = saas.getShopApp(param.getShopId()).coupon.couponGiveService.handlerGouonGive(param);
        //更新taskJob进度和状态
        saas.taskJobMainService.updateProgress(Util.toJson(param),param.getTaskJobId(),(param.getUserIds().size()*param.getCouponArray().length-rowsList.size()),param.getUserIds().size()*param.getCouponArray().length);
    }


    @Override
    public void executeException(Object[] datas, Throwable throwable) {

    }
}
