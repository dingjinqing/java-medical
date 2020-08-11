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
     * 订单自动关闭
     */
    public void close() {
        logger().info("问诊订单关闭定时任务start,shop:{}", getShopId());
        autoCloseInquiryOrder();
        logger().info("问诊订单关闭定时任务end");
    }

    /**
     * 自动任务关闭待支付的问诊订单
     */
    public void autoCloseInquiryOrder() {
        List<InquiryOrderDo> orderList = inquiryOrderService.getCanceledToPaidCloseOrder();
        orderList.forEach(order -> {
            closeExecute(order);
        });
    }

    public void closeExecute(InquiryOrderDo order) {
        boolean successFlag=true;
        transaction(() -> {
            order.setOrderStatus(InquiryOrderConstant.ORDER_CANCELED);
            order.setCancelledTime(DateUtils.getLocalDateTime());
            inquiryOrderDao.update(order);
        });

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
            try {
                refundExecute(order);
                logger().info("问诊订单自动任务,关闭订单成功,orderSn:{}", order.getOrderSn());
            } catch (MpException e) {
                e.printStackTrace();
                logger().error("问诊订单自动任务,关闭订单失败,orderSn:{},错误信息{}{}", order.getOrderSn(), e.getErrorCode(), e.getMessage().toString());
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
        inquiryOrderService.refundInquiryOrder(order,order.getRefundMoney(),"");
    }
}
