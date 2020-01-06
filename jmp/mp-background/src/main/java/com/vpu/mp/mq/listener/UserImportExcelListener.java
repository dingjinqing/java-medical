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
import com.vpu.mp.service.pojo.shop.member.userImp.UserImportMqParam;
import com.vpu.mp.service.saas.SaasApplication;

/**
 * 用户导入
 * 
 * @author zhaojianqiang
 * @time 下午3:20:15
 */
@Component
@RabbitListener(queues = { RabbitConfig.QUEUE_EXCEL }, containerFactory = "simpleRabbitListenerContainerFactory")
public class UserImportExcelListener implements BaseRabbitHandler {

	@Autowired
	private SaasApplication saas;

	@RabbitHandler
	public void handler(@Payload UserImportMqParam param, Message message, Channel channel) {
		saas.getShopApp(param.getShopId()).member.userImportService.checkList(param.getModels(), param.getCardId(),
				param.getGroupId(), param.getTagId(),param.getLang());
	}

	@Override
	public void executeException(Object[] datas, Throwable throwable) {

	}
}
