package com.vpu.mp.mq.listener;


import java.util.ArrayList;
import java.util.List;

import com.vpu.mp.service.pojo.shop.market.message.RabbitParamConstant;
import com.vpu.mp.service.shop.ShopApplication;
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
import com.vpu.mp.service.pojo.shop.market.message.RabbitMessageParam;
import com.vpu.mp.service.pojo.shop.user.user.WxUserInfo;
import com.vpu.mp.service.saas.SaasApplication;

import lombok.extern.slf4j.Slf4j;

/**
 * 模版消息监听
 * @author 卢光耀
 * @date 2019-08-30 14:27
 *
*/
@Slf4j
@Component
@RabbitListener(queues = {RabbitConfig.QUEUE_MESSAGE_SEND},
    containerFactory = "simpleRabbitListenerContainerFactory")
public class MessageTemplateListener implements BaseRabbitHandler {

    @Autowired
    private SaasApplication saas;



    @RabbitHandler
    public void handler(@Payload RabbitMessageParam param, Message message, Channel channel){
    	log.debug("【消息模板监听】----收到消息");
        ShopApplication shopApplication = saas.getShopApp(param.getShopId());

        List<Integer> failList = new ArrayList<>();
        List<WxUserInfo> userInfoList = shopApplication.wechatMessageTemplateService.
            getUserInfoList(param.getUserIdList(),param.getType(),param.getShopId(),param);
        int allSize = userInfoList.size();
        if( allSize  != param.getUserIdList().size() ){
            log.debug("【消息模板监听】---推送消息接收人数不对");
            log.debug("【消息模板监听】---UserIdSize-->{},UserId--->{}",param.getUserIdList().size(),param.getUserIdList().get(0));
            log.debug("【消息模板监听】---WxUserSize-->{}",allSize);
        }
        for (WxUserInfo info : userInfoList) {
            if (saas.getShopApp(param.getShopId()).wechatMessageTemplateService.sendMessage(param, info)) {
                //自定义模版消息更新发送记录状态
                if (param.getType().equals(RabbitParamConstant.Type.DIY_MESSAGE_TEMPLATE)) {
                    saas.getShopApp(param.getShopId()).messageTemplateService.updateTemplateSendStatus(info.getUserId(), param.getMessageTemplateId());
                }

            } else {
                failList.add(info.getUserId());
            }

        }
        //自定义模版消息需要更新发送状态
        if( RabbitParamConstant.Type.DIY_MESSAGE_TEMPLATE.equals(param.getType()) ){
            saas.getShopApp(param.getShopId()).messageTemplateService.updateTemplateStatus(param.getMessageTemplateId());
        }
        param.setUserIdList(failList);

        //更新taskJob进度和状态
        saas.taskJobMainService.updateProgress(Util.toJson(param),param.getTaskJobId(),failList.size(),allSize);
    }


    @Override
    public void executeException(Object[] datas, Throwable throwable) {

    }
}
