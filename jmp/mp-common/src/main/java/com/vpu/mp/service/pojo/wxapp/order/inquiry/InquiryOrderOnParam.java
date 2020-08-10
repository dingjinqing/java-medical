package com.vpu.mp.service.pojo.wxapp.order.inquiry;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author yangpengcheng
 */
@Data
public class InquiryOrderOnParam {
    private Integer orderId;
    private String orderSn;
    private Byte orderStatus;
    private Integer sessionId;
    /**
     *退款金额
     */
    private BigDecimal refundMoney;
}
