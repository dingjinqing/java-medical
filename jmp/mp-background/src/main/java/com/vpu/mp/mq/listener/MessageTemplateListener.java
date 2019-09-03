package com.vpu.mp.mq.listener;


import com.rabbitmq.client.Channel;
import com.vpu.mp.config.mq.RabbitConfig;
import com.vpu.mp.db.main.tables.records.TaskJobMainRecord;
import com.vpu.mp.service.foundation.mq.handler.BaseRabbitHandler;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.market.message.RabbitMessageParam;
import com.vpu.mp.service.pojo.shop.user.user.WxUserInfo;
import com.vpu.mp.service.saas.SaasApplication;
import org.jooq.Result;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 模版消息监听
 * @author 卢光耀
 * @date 2019-08-30 14:27
 *
*/
@Component
@RabbitListener(queues = {RabbitConfig.QUEUE_MESSAGE_SEND},
    containerFactory = "simpleRabbitListenerContainerFactory")
public class MessageTemplateListener implements BaseRabbitHandler {

    @Autowired
    private SaasApplication saas;



    @RabbitHandler
    public void handler(@Payload RabbitMessageParam param, Message message, Channel channel){
        List<Integer> failList = new ArrayList<>();
        List<WxUserInfo> userInfoList = saas.getShopApp(param.getShopId())
            .wechatMessageTemplateService.getUserInfoList(param.getUserIdList(),param.getType(),param.getShopId());
        int allSize = userInfoList.size();

        userInfoList.stream().forEach(info->{
            if( saas.getShopApp(param.getShopId()).wechatMessageTemplateService.sendMessage(param,info)){

            }else{
                failList.add(info.getUserId());
            }

        });

        param.setUserIdList(failList);

        //更新taskJob进度和状态
        saas.taskJobMainService.updateProgress(Util.toJson(param),param.getTaskJobId(),failList.size(),allSize);
    }


    @Override
    public void executeException(Object[] datas, Throwable throwable) {

    }
}
