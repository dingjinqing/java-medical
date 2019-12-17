package com.vpu.mp.mq.listener;

import com.rabbitmq.client.Channel;
import com.vpu.mp.config.mq.RabbitConfig;
import com.vpu.mp.service.foundation.jedis.data.DBOperating;
import com.vpu.mp.service.foundation.mq.handler.BaseRabbitHandler;
import com.vpu.mp.service.pojo.shop.goods.es.EsGoodsMqParam;
import com.vpu.mp.service.saas.SaasApplication;
import com.vpu.mp.service.shop.goods.es.EsGoodsCreateService;
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
    public void handler(@Payload EsGoodsMqParam param, Message message, Channel channel) {
        log.info("\n消费{}",param.getShopId());
        Integer shopId = param.getShopId();
        List<Integer> ids= param.getIdList();
        EsGoodsCreateService esGoodsCreateService = saas.getShopApp(param.getShopId()).esGoodsCreateService;
        if( DBOperating.DELETE.equals(param.getOperate()) ){
            esGoodsCreateService.deleteEsGoods(ids,shopId);
        }else{
            esGoodsCreateService.batchUpdateEsGoodsIndex(ids,shopId);
        }

    }

    @Override
    public void executeException(Object[] datas, Throwable throwable) {

    }
}
