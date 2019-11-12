package com.vpu.mp.mq.listener;

import com.rabbitmq.client.Channel;
import com.vpu.mp.config.mq.RabbitConfig;
import com.vpu.mp.db.shop.tables.records.ServiceOrderRecord;
import com.vpu.mp.service.foundation.mq.handler.BaseRabbitHandler;
import com.vpu.mp.service.saas.SaasApplication;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @author liufei
 * @date 11/8/19
 */
@Component
@RabbitListener(queues = RabbitConfig.QUEUE_RESERVATION_ORDER_MESSAGE,
    containerFactory = "simpleRabbitListenerContainerFactory")
public class ReservationPayListener implements BaseRabbitHandler {

    @Autowired
    protected SaasApplication saas;

    @RabbitHandler
    public void handler(@Payload ServiceOrderRecord param, Message message, Channel channel) {
        System.out.println(param.toString());
//        sendReservationPaySuccessMessage(param);
//        List<Integer> rowsList = saas.getShopApp(param.getShopId()).coupon.couponGiveService.handlerGouonGive(param);
//        //更新taskJob进度和状态
//        saas.taskJobMainService.updateProgress(Util.toJson(param),param.getTaskJobId(),(param.getUserIds().size()*param.getCouponArray().length-rowsList.size()),param.getUserIds().size()*param.getCouponArray().length);
    }

    @Override
    public void executeException(Object[] datas, Throwable throwable) {

    }
}
