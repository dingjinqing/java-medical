package com.vpu.mp.mq.listener;

import com.rabbitmq.client.Channel;
import com.vpu.mp.config.mq.RabbitConfig;
import com.vpu.mp.service.foundation.jedis.data.label.MqEsGoodsLabel;
import com.vpu.mp.service.foundation.mq.handler.BaseRabbitHandler;
import com.vpu.mp.service.saas.SaasApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * ElasticSearch update goods label data
 * @author luguangyao
 * @date 2019/11/21
 *
*/
@Slf4j
@Component
@RabbitListener(queues = { RabbitConfig.QUEUE_ES_LABEL }, containerFactory = "simpleRabbitListenerContainerFactory")
public class EsGoodsLabelListener implements BaseRabbitHandler {

    @Autowired
    private SaasApplication saasApplication;


    @RabbitHandler
    public void success(@Payload MqEsGoodsLabel param, Message message, Channel channel) throws IOException {
        Integer shopId = param.getShopId();
        log.info("\n【ES商品标签】开始处理来自{}店铺的数据",shopId);
        List<Integer> goodsIds = param.getGoodsIds();
        List<Integer> labelIds = param.getLabelIds();
        if( goodsIds != null && !goodsIds.isEmpty() ){
            saasApplication.getShopApp(shopId).esGoodsLabelCreateService.createEsLabelIndexForGoodsId(goodsIds,param.getOperating());
        }
        if( labelIds != null && !labelIds.isEmpty() ){
            saasApplication.getShopApp(shopId).esGoodsLabelCreateService.createEsLabelIndexForLabelId(labelIds.get(0),param.getOperating());
        }
    }

    @Override
    public void executeException(Object[] datas, Throwable throwable) {

    }
}
