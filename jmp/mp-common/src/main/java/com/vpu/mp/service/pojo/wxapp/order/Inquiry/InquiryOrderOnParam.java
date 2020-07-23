package com.vpu.mp.service.pojo.wxapp.order.Inquiry;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author yangpengcheng
 */
@Data
public class InquiryOrderOnParam {
    private Integer orderId;
    private String orderSn;
    private Byte orderStatus;
    private Integer sessionId;
}
