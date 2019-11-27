package com.vpu.mp.mq.listener;

import com.rabbitmq.client.Channel;
import com.vpu.mp.config.mq.RabbitConfig;
import com.vpu.mp.service.foundation.mq.handler.BaseRabbitHandler;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveQueueParam;
import com.vpu.mp.service.pojo.shop.goods.es.EsTaskParam;
import com.vpu.mp.service.saas.SaasApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RabbitListener(queues = RabbitConfig.QUEUE_ES_GOODS,
    containerFactory = "currentSimpleRabbitListenerFactory")
public class ESGoodsListener implements BaseRabbitHandler {

    @Autowired
    protected SaasApplication saas;

    @RabbitHandler
    public void handler(@Payload EsTaskParam param, Message message, Channel channel) {
        log.info("\n消费{}",param.getShopId());
        if( param.getIdList().size() > 1 ){
            saas.getShopApp(param.getShopId())
                .esGoodsCreateService.batchCreateEsGoodsIndex(param.getIdList(),param.getShopId());
        }else if( param.getIdList().size() == 1){
            saas.getShopApp(param.getShopId())
                .esGoodsCreateService.createEsGoodsIndex(param.getIdList().get(0),param.getShopId());
        }

    }

    @Override
    public void executeException(Object[] datas, Throwable throwable) {

    }
}
