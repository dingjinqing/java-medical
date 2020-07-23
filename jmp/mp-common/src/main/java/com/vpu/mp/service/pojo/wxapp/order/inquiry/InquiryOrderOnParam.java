package com.vpu.mp.service.pojo.wxapp.order.inquiry;

import lombok.Data;

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
