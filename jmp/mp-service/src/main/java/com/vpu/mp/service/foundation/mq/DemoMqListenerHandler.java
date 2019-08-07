package com.vpu.mp.service.foundation.mq;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.mq.handler.BaseRabbitHandler;

import lombok.extern.slf4j.Slf4j;

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
   

}
