package com.vpu.mp.service.pojo.wxapp.order.Inquiry;

import lombok.Data;
import lombok.NonNull;

@Data
public class InquiryOrderOnParam {
    @NonNull
    private Integer orderId;
    private Byte orderStatus;
}
