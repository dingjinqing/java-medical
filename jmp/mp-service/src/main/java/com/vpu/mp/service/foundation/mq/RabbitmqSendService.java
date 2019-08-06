package com.vpu.mp.service.foundation.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;


/**
 * rabbitMQ的失败回调的实现
 * @author: 卢光耀
 * @date: 2019-07-31 14:43
 *
*/
@Slf4j
@Service
public class RabbitmqSendService  {

    private final RabbitTemplate rabbitTemplate;

    public RabbitmqSendService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * 发送消息
     * @param object 发送消息
     * @param queueName 在{@link com.vpu.mp.config.mq.RabbitConfig}配置自己的队列
     */
    public  void sendMessage(String queueName,Object object){
        log.info("接收队列---{},MQ发送消息---{}",queueName,object);
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(queueName,object,correlationData);
    }
    /**
     * 发送消息
     * @param object 发送消息
     * @param routingKey 路由键名称,在{@link com.vpu.mp.config.mq.RabbitConfig}配置
     * @param exchangeName 路由名称,在{@link com.vpu.mp.config.mq.RabbitConfig}配置
     */
    public void sendMessage(String exchangeName,String routingKey,Object object){
        log.info("接收路由---{}，路由键---{}",exchangeName,routingKey);
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(exchangeName,routingKey,object,correlationData);
    }

}
