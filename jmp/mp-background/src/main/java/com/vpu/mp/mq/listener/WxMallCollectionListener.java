package com.vpu.mp.mq.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.vpu.mp.config.mq.RabbitConfig;
import com.vpu.mp.service.foundation.mq.handler.BaseRabbitHandler;
import com.vpu.mp.service.pojo.shop.recommend.SendCollectBean;
import com.vpu.mp.service.saas.SaasApplication;

import lombok.extern.slf4j.Slf4j;

/**
 * 好物圈收藏
 * @author zhaojianqiang
 *
 * 2019年11月18日 下午1:30:45
 */
@Slf4j
@Component
@RabbitListener(queues = { RabbitConfig.QUEUE_WX_MALL_ADDSHOPPINGLIST }, containerFactory = "simpleRabbitListenerContainerFactory")
public class WxMallCollectionListener implements BaseRabbitHandler {

	@Autowired
	private SaasApplication saas;

	@RabbitHandler
	public void handler(@Payload SendCollectBean param, Message message, Channel channel) {
		if(param.getStatus().equals(1)) {
			//插入
			log.info("更新收藏");
			saas.getShopApp(param.getShopId()).recommendService.collectionMallService.addshoppinglistAdd(param.getBean());
		}
		if(param.getStatus().equals(2)) {
			//删除
			log.info("删除收藏");
			saas.getShopApp(param.getShopId()).recommendService.collectionMallService.addshoppinglistDel(param.getBean());
		}
		
	}

	@Override
	public void executeException(Object[] datas, Throwable throwable) {

	}
}