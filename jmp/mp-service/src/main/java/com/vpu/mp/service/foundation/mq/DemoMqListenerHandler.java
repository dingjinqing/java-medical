package com.vpu.mp.service.foundation.mq;

import com.rabbitmq.client.Channel;
import com.vpu.mp.config.mq.RabbitConfig;
import com.vpu.mp.service.foundation.mq.handler.BaseRabbitHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * RabbitMq使用demo
 * @author: 卢光耀
 * @date: 2019-08-01 15:26
 *
*/
@EnableScheduling
@Service
@Slf4j
public class DemoMqListenerHandler implements BaseRabbitHandler {
    @Autowired
    RabbitmqSendService sendService;

    @RabbitListener(queues = RabbitConfig.QUEUE_ERROR_SEND,
            containerFactory = "simpleRabbitListenerContainerFactory")
    @RabbitHandler
    private void doDem1o(Message message, Channel channel) throws IOException {
        log.error("消费了。。。");
        System.out.println(new String(message.getBody(),"UTF-8"));

        this.failReturn(channel,message.getMessageProperties().getDeliveryTag());
    }
    @Scheduled(cron = "0/5 * * * * ?")
    public void taskPerMinute() {
        sendService.sendMessage("hello","test");

    }

}
