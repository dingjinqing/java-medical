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
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.recommend.SendOrderBean;
import com.vpu.mp.service.saas.SaasApplication;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.open.bean.result.WxOpenResult;

/**
 * 好物圈订单
 * @author zhaojianqiang
 *
 * 2019年11月18日 下午1:30:45
 */
@Slf4j
@Component
@RabbitListener(queues = { RabbitConfig.QUEUE_WX_MALL_IMPORTORDER }, containerFactory = "simpleRabbitListenerContainerFactory")
public class WxMallOrderListener implements BaseRabbitHandler {

	@Autowired
	private SaasApplication saas;

	@RabbitHandler
	public void handler(@Payload SendOrderBean param, Message message, Channel channel) {
		if(param.getStatus().equals(1)) {
			//插入
			log.info("导入商品");
			WxOpenResult importOrderAdd = saas.getShopApp(param.getShopId()).recommendService.orderMallService.importOrderAdd(param.getBean());
			log.info("导入商品结果："+importOrderAdd.isSuccess());
		}
		if(param.getStatus().equals(2)) {
			//删除
			log.info("更新商品");
			WxOpenResult importOrderUpdate = saas.getShopApp(param.getShopId()).recommendService.orderMallService.importOrderUpdate(param.getBean());
			log.info("更新商品结果："+importOrderUpdate.isSuccess());
		}
		log.info("订单更改状态："+param.getTaskJobId());
		saas.taskJobMainService.updateProgress(Util.toJson(param),param.getTaskJobId(),0,1);
		
	}

	@Override
	public void executeException(Object[] datas, Throwable throwable) {

	}
}