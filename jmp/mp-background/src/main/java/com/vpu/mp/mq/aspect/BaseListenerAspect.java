package com.vpu.mp.mq.aspect;


import com.rabbitmq.client.Channel;
import com.vpu.mp.service.foundation.mq.handler.BaseRabbitHandler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 *
 * @author 卢光耀
 * @date 2019-08-19 15:03
 *
*/
@Aspect
@Component
public class BaseListenerAspect {
    @Pointcut("@annotation(org.springframework.amqp.rabbit.annotation.RabbitHandler)")
    public void mqSuccessListenerAspect(){}


    @Around(value = "mqSuccessListenerAspect()")
    public void successAspect(ProceedingJoinPoint point) throws IOException{
        BaseRabbitHandler handler = (BaseRabbitHandler)point.getThis();
        Object[] dataArray = point.getArgs().clone();
        int len = dataArray.length;
        Message msg = (Message)dataArray[len-1];
        Channel channel = (Channel)dataArray[len-2];
        try {
            point.proceed();
            channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
        } catch (Throwable throwable) {
            handler.executeException(dataArray,throwable);
            channel.basicNack(msg.getMessageProperties().getDeliveryTag(), false, false);
        }
    }
}
