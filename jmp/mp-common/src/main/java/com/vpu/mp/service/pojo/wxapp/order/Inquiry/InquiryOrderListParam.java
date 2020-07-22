package com.vpu.mp.service.pojo.wxapp.order.Inquiry;

import com.vpu.mp.common.pojo.shop.base.BasePageParam;
import lombok.Data;

@Data
public class InquiryOrderListParam extends BasePageParam {
    /*订单状态*/
    private Byte orderStatus;
    /*医师id*/
    private Integer doctorId;
}
