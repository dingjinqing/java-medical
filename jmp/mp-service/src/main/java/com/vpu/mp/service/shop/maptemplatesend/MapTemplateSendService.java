package com.vpu.mp.service.shop.maptemplatesend;

import com.vpu.mp.common.foundation.util.Util;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.saas.schedule.TaskJobsConstant;
import com.vpu.mp.service.pojo.shop.config.message.MessageTemplateConfigConstant;
import com.vpu.mp.service.pojo.shop.maptemplate.*;
import com.vpu.mp.service.pojo.shop.market.message.RabbitMessageParam;
import com.vpu.mp.service.pojo.shop.market.message.maconfig.SubcribeTemplateCategory;
import com.vpu.mp.service.pojo.shop.user.message.MaSubscribeData;
import com.vpu.mp.service.pojo.shop.user.message.MaTemplateData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author chenjie
 * @date 2020年08月18日
 */
@Slf4j
@Service
public class MapTemplateSendService extends ShopBaseService {

    /**
     * 提醒医生有新咨询订单
     * @param param
     */
    public void sendConsultationOrderMessage(ConsultationOrderPayParam param){
        // 订阅消息
        String[][] maData = new String[][] {
            {param.getPatientData()},
            {param.getDiseaseDetail()},
            {param.getCreateTime()},
            {param.getRemark()}
        };

        MaSubscribeData data = MaSubscribeData.builder().data47(maData).build();

        // 公众号消息
        String[][] mpData = new String[][] {
            {param.getPatientData()},
            {param.getDiseaseDetail()},
            {param.getCreateTime()},
            {param.getRemark()}
        };
        RabbitMessageParam param2 = RabbitMessageParam.builder()
            .maTemplateData(
                MaTemplateData.builder().config(SubcribeTemplateCategory.CONSULTATION_ORDER_PAY).data(data).build())
//            .mpTemplateData(
//                MpTemplateData.builder().config(MpTemplateConfig.MONEY_CHANGE).data(mpData).build())
            .page("pages/account/account").shopId(getShopId())
            .userIdList(param.getUserIds())
            .type(MessageTemplateConfigConstant.NEW_CONSULTATION).build();
        saas.taskJobMainService.dispatchImmediately(param2, RabbitMessageParam.class.getName(), getShopId(), TaskJobsConstant.TaskJobEnum.SEND_MESSAGE.getExecutionType());
    }

    /**
     * 咨询已超时通知
     * @param param
     */
    public void sendConsultationExprieMessage(ConsultationOrderExpireParam param){
        // 订阅消息
        String[][] maData = new String[][] {
            {param.getConsultationStatus()},
            {param.getRemark()},
        };

        MaSubscribeData data = MaSubscribeData.builder().data47(maData).build();

        // 公众号消息
        String[][] mpData = new String[][] {
            {param.getConsultationStatus()},
            {param.getRemark()},
        };
        RabbitMessageParam param2 = RabbitMessageParam.builder()
            .maTemplateData(
                MaTemplateData.builder().config(SubcribeTemplateCategory.CONSULTATION_ORDER_EXPIRE).data(data).build())
//            .mpTemplateData(
//                MpTemplateData.builder().config(MpTemplateConfig.MONEY_CHANGE).data(mpData).build())
            .page("pages/account/account").shopId(getShopId())
            .userIdList(param.getUserIds())
            .type(MessageTemplateConfigConstant.CONSULTATION_EXPIRE).build();
        saas.taskJobMainService.dispatchImmediately(param2, RabbitMessageParam.class.getName(), getShopId(), TaskJobsConstant.TaskJobEnum.SEND_MESSAGE.getExecutionType());
    }

    /**
     * 咨询回复通知
     * @param param
     */
    public void sendConsultationAnswerMessage(ConsultationAnswerParam param){
        // 订阅消息
        String[][] maData = new String[][] {
            {param.getRemark()},
            {param.getDoctorName()},
            {param.getPatientName()},
            {param.getConsultationContent()},
        };

        MaSubscribeData data = MaSubscribeData.builder().data47(maData).build();

        // 公众号消息
        String[][] mpData = new String[][] {
            {param.getRemark()},
            {param.getDoctorName()},
            {param.getPatientName()},
            {param.getConsultationContent()},
        };
        RabbitMessageParam param2 = RabbitMessageParam.builder()
            .maTemplateData(
                MaTemplateData.builder().config(SubcribeTemplateCategory.CONSULTATION_ANSWER).data(data).build())
//            .mpTemplateData(
//                MpTemplateData.builder().config(MpTemplateConfig.MONEY_CHANGE).data(mpData).build())
            .page("pages/account/account").shopId(getShopId())
            .userIdList(param.getUserIds())
            .type(MessageTemplateConfigConstant.CONSULTATION_ANSWER).build();
        saas.taskJobMainService.dispatchImmediately(param2, RabbitMessageParam.class.getName(), getShopId(), TaskJobsConstant.TaskJobEnum.SEND_MESSAGE.getExecutionType());
    }

    /**
     * 医生已接诊提醒
     * @param param
     */
    public void sendConsultationSuccessMessage(ConsultationSuccessParam param){
        // 订阅消息
        String[][] maData = new String[][] {
            {param.getPatientName()},
            {param.getDiseaseDetail()},
            {param.getDoctorName()},
            {param.getRemark()},
            {param.getDepartmentName()},
        };

        MaSubscribeData data = MaSubscribeData.builder().data47(maData).build();

        // 公众号消息
        String[][] mpData = new String[][] {
            {param.getPatientName()},
            {param.getDiseaseDetail()},
            {param.getDoctorName()},
            {param.getRemark()},
            {param.getDepartmentName()},
        };
        RabbitMessageParam param2 = RabbitMessageParam.builder()
            .maTemplateData(
                MaTemplateData.builder().config(SubcribeTemplateCategory.CONSULTATION_SUCCESS).data(data).build())
//            .mpTemplateData(
//                MpTemplateData.builder().config(MpTemplateConfig.MONEY_CHANGE).data(mpData).build())
            .page("pages/account/account").shopId(getShopId())
            .userIdList(param.getUserIds())
            .type(MessageTemplateConfigConstant.CONSULTATION_SUCCESS).build();
        saas.taskJobMainService.dispatchImmediately(param2, RabbitMessageParam.class.getName(), getShopId(), TaskJobsConstant.TaskJobEnum.SEND_MESSAGE.getExecutionType());
    }

    /**
     * 发货成功提醒
     * @param param
     */
    public void sendOrderDeliverMessage(OrderDeliverParam param){
        // 订阅消息
        String[][] maData = new String[][] {
            {param.getOrderSn()},
            {param.getOrderDetail()},
            {param.getDeliverDate()},
        };

        MaSubscribeData data = MaSubscribeData.builder().data47(maData).build();

        // 公众号消息
        String[][] mpData = new String[][] {
            {param.getOrderSn()},
            {param.getOrderDetail()},
            {param.getDeliverDate()},
        };
        RabbitMessageParam param2 = RabbitMessageParam.builder()
            .maTemplateData(
                MaTemplateData.builder().config(SubcribeTemplateCategory.ORDER_DELIVER).data(data).build())
//            .mpTemplateData(
//                MpTemplateData.builder().config(MpTemplateConfig.MONEY_CHANGE).data(mpData).build())
            .page("pages/account/account").shopId(getShopId())
            .userIdList(param.getUserIds())
            .type(MessageTemplateConfigConstant.ORDER_SEND).build();
        saas.taskJobMainService.dispatchImmediately(param2, RabbitMessageParam.class.getName(), getShopId(), TaskJobsConstant.TaskJobEnum.SEND_MESSAGE.getExecutionType());
    }

    /**
     * 退款成功通知
     * @param param
     */
    public void sendOrderRefundSuccessMessage(OrderRefundSuccessParam param){
        // 订阅消息
        String[][] maData = new String[][] {
            {param.getOrderSn()},
            {param.getRefundTime()},
            {param.getRefundMoney()},
            {param.getRefundReason()},
        };

        MaSubscribeData data = MaSubscribeData.builder().data47(maData).build();

        // 公众号消息
        String[][] mpData = new String[][] {
            {param.getOrderSn()},
            {param.getRefundTime()},
            {param.getRefundMoney()},
            {param.getRefundReason()},
        };
        RabbitMessageParam param2 = RabbitMessageParam.builder()
            .maTemplateData(
                MaTemplateData.builder().config(SubcribeTemplateCategory.REFUND_RESULT).data(data).build())
//            .mpTemplateData(
//                MpTemplateData.builder().config(MpTemplateConfig.MONEY_CHANGE).data(mpData).build())
            .page("pages/account/account").shopId(getShopId())
            .userIdList(param.getUserIds())
            .type(MessageTemplateConfigConstant.STATUS_RETURN_MONEY).build();
        saas.taskJobMainService.dispatchImmediately(param2, RabbitMessageParam.class.getName(), getShopId(), TaskJobsConstant.TaskJobEnum.SEND_MESSAGE.getExecutionType());
    }

    /**
     * 新订单提醒
     * @param param
     */
    public void sendNewOrderMessage(OrderNewParam param){
        // 订阅消息
        String[][] maData = new String[][] {
            {param.getOrderSn()},
            {param.getUserName()},
            {param.getUserRemark()},
        };

        MaSubscribeData data = MaSubscribeData.builder().data47(maData).build();

        // 公众号消息
        String[][] mpData = new String[][] {
            {param.getOrderSn()},
            {param.getUserName()},
            {param.getUserRemark()},
        };
        RabbitMessageParam param2 = RabbitMessageParam.builder()
            .maTemplateData(
                MaTemplateData.builder().config(SubcribeTemplateCategory.ORDER_NEW).data(data).build())
//            .mpTemplateData(
//                MpTemplateData.builder().config(MpTemplateConfig.MONEY_CHANGE).data(mpData).build())
            .page("pages/account/account").shopId(getShopId())
            .userIdList(param.getUserIds())
            .type(MessageTemplateConfigConstant.ORDER_NEW).build();
        saas.taskJobMainService.dispatchImmediately(param2, RabbitMessageParam.class.getName(), getShopId(), TaskJobsConstant.TaskJobEnum.SEND_MESSAGE.getExecutionType());
    }
}
