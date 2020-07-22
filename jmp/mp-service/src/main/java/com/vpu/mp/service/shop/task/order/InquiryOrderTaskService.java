package com.vpu.mp.service.shop.task.order;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.data.JsonResultCode;
import com.vpu.mp.common.foundation.util.DateUtils;
import com.vpu.mp.dao.shop.order.InquiryOrderDao;
import com.vpu.mp.db.shop.tables.records.InquiryOrderRecord;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.wxapp.order.Inquiry.InquiryOrderConstant;
import com.vpu.mp.service.shop.order.Inquiry.InquiryOrderService;
import com.vpu.mp.service.shop.order.action.base.ExecuteResult;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/*
*问诊订单定时任务
 */
@Service
public class InquiryOrderTaskService extends ShopBaseService {
    @Autowired
    private InquiryOrderService inquiryOrderService;
    @Autowired
    private InquiryOrderDao inquiryOrderDao;
    /**
     * 订单自动关闭
     */
    public void close(){
        logger().info("问诊订单关闭定时任务start,shop:{}", getShopId());
        autoCloseInquiryOrder();
        logger().info("问诊订单关闭定时任务end");
    }
    /*
     *自动任务关闭待支付的问诊订单
     */
    public void autoCloseInquiryOrder(){
        Result<InquiryOrderRecord> orderRecords=inquiryOrderService.getCanceledToPaidCloseOrder();
        orderRecords.forEach(order->{
            JsonResult result= closeExecute(order);
            if(JsonResultCode.CODE_SUCCESS.getCode()==result.getError()) {
                logger().info("问诊订单自动任务,关闭订单成功,orderSn:{}", order.getOrderSn());
            }else {
                logger().error("问诊订单自动任务,关闭订单失败,orderSn:{},错误信息{}{}", order.getOrderSn(), result.getError() , result.getMessage().toString());
            }
        });
    }

    public JsonResult closeExecute(InquiryOrderRecord order){
        try {
            transaction(()->{
                order.setOrderStatus(InquiryOrderConstant.ORDER_CANCELED);
                order.setCancelledTime(DateUtils.getLocalDateTime());
                inquiryOrderDao.update(order);
            });
        } catch (Exception e) {
            return new JsonResult().result(null,JsonResultCode.CODE_ORDER_CLOSE_FAIL, null);
        }
        return new JsonResult().success();

    }


    /*
     *自动任务关闭待接诊的问诊订单退款
     */
    public void autoCloseToWaitingInquiryOrder(){
        Result<InquiryOrderRecord> orderRecords=inquiryOrderService.getCanceledToWaitingCloseOrder();
        orderRecords.forEach(order->{
            JsonResult result= refundExecute(order);
            if(JsonResultCode.CODE_SUCCESS.getCode()==result.getError()) {
                logger().info("问诊订单自动任务,关闭订单成功,orderSn:{}", order.getOrderSn());
            }else {
                logger().error("问诊订单自动任务,关闭订单失败,orderSn:{},错误信息{}{}", order.getOrderSn(), result.getError() , result.getMessage().toString());
            }
        });
    }
    public JsonResult refundExecute(InquiryOrderRecord order){
        try {
            inquiryOrderService.refundInquiryOrder(order);
        } catch (MpException e) {
            return new JsonResult().result(null,JsonResultCode.CODE_ORDER_CLOSE_FAIL, null);
        }
        return new JsonResult().success();

    }
}
