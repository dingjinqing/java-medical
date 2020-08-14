package com.vpu.mp.service.shop.task.order;

import com.vpu.mp.common.foundation.util.DateUtils;
import com.vpu.mp.common.pojo.shop.table.InquiryOrderDo;
import com.vpu.mp.dao.shop.order.InquiryOrderDao;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.wxapp.order.inquiry.InquiryOrderConstant;
import com.vpu.mp.service.shop.im.ImSessionService;
import com.vpu.mp.service.shop.order.inquiry.InquiryOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * 问诊订单定时任务
 *
 * @author yangpengcheng
 */
@Service
public class InquiryOrderTaskService extends ShopBaseService {
    @Autowired
    private InquiryOrderService inquiryOrderService;
    @Autowired
    private InquiryOrderDao inquiryOrderDao;
    @Autowired
    private ImSessionService imSessionService;

    /**
     * 自动任务关闭待支付的问诊订单
     */
    public void close() {
        logger().info("问诊订单关闭定时任务start,shop:{}", getShopId());
        List<InquiryOrderDo> orderList = inquiryOrderService.getCanceledToPaidCloseOrder();
        orderList.forEach(order -> {
            order.setOrderStatus(InquiryOrderConstant.ORDER_CANCELED);
            order.setCancelledTime(DateUtils.getLocalDateTime());
            inquiryOrderDao.update(order);
        });
        logger().info("问诊订单关闭定时任务end");
    }






    /**
     * 待接诊订单自动关闭
     */
    public void closeToWaitingInquiryOrder()  {
        logger().info("待接诊问诊订单关闭定时任务start,shop:{}", getShopId());
        autoCloseToWaitingInquiryOrder();
        logger().info("待接诊问诊订单关闭定时任务end");
    }

    /**
     * 自动任务关闭待接诊的问诊订单退款
     */
    public void autoCloseToWaitingInquiryOrder()  {
        List<InquiryOrderDo> orderList = inquiryOrderService.getCanceledToWaitingCloseOrder();
        orderList.forEach(order -> {
            if(order.getOrderAmount().compareTo(BigDecimal.ZERO)<=0){
                order.setOrderStatus(InquiryOrderConstant.ORDER_CANCELED);
                order.setCancelledTime(DateUtils.getLocalDateTime());
                inquiryOrderDao.update(order);
                List<String> orderSns=new ArrayList<>();
                orderSns.add(order.getOrderSn());
                imSessionService.batchCancelSession(orderSns);
                logger().info("问诊订单自动任务,待接诊0元订单取消,orderSn:{}", order.getOrderSn());
                return;
            }
            try {
                refundExecute(order);
                logger().info("问诊订单自动任务,待接诊订单退款成功,orderSn:{}", order.getOrderSn());
            } catch (MpException e) {
                e.printStackTrace();
                logger().error("问诊订单自动任务,待接诊订单退款失败,orderSn:{},错误信息{}{}", order.getOrderSn(), e.getErrorCode(), e.getMessage());
            }
        });
    }

    /**
     * 退款
     *
     * @param order
     * @return
     */
    public void refundExecute(InquiryOrderDo order) throws MpException {
        inquiryOrderService.refundInquiryOrder(order,order.getRefundMoney(),InquiryOrderConstant.REFUND_REASON);
    }

    /**
     * 接诊中超时自动结束的问诊订单
     */
    public void finishedCloseOrder(){
        logger().info("接诊中问诊订单超时自动结束定时任务start,shop:{}", getShopId());
        List<InquiryOrderDo> list=inquiryOrderDao.getFinishedCloseOrder();
        list.forEach(order -> {
            order.setOrderStatus(InquiryOrderConstant.ORDER_FINISHED);
            order.setFinishedTime(DateUtils.getLocalDateTime());
            inquiryOrderDao.update(order);
            List<String> orderSnList=new ArrayList<>();
            orderSnList.add(order.getOrderSn());
            imSessionService.batchCloseSession(orderSnList);
            logger().info("接诊中问诊订单超时自动结束,成功,orderSn:{}", order.getOrderSn());
        });
        logger().info("接诊中问诊订单超时自动结束定时任务end");
    }
}
