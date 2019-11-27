package com.vpu.mp.mq.listener;

import com.rabbitmq.client.Channel;
import com.vpu.mp.config.mq.RabbitConfig;
import com.vpu.mp.service.foundation.jedis.data.label.GoodsLabelCacheParam;
import com.vpu.mp.service.foundation.mq.handler.BaseRabbitHandler;
import com.vpu.mp.service.saas.SaasApplication;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.annotation.Annotation;
/**
 * 缓存更新->商品标签
 * @author 卢光耀
 * @date 2019/11/21 10:40 上午
 *
*/
@Component
@RabbitListener(queues = { RabbitConfig.QUEUE_ES_LABEL }, containerFactory = "simpleRabbitListenerContainerFactory")
public class EsGoodsLabelListener implements BaseRabbitHandler {

    @Autowired
    private SaasApplication saasApplication;


    @RabbitHandler
    public void success(@Payload GoodsLabelCacheParam param, Channel channel, Message message) throws IOException {
        if( param.getUpdateAll() ){

        }else{

        }
    }

    @Override
    public void executeException(Object[] datas, Throwable throwable) {

    }
}
