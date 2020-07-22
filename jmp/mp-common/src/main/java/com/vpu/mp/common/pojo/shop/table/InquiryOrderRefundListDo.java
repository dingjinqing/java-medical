package com.vpu.mp.common.pojo.shop.table;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class InquiryOrderRefundListDo {
    private static final long serialVersionUID = 1625068988;

    private Integer    id;
    private String     orderSn;
    private Integer    userId;
    private BigDecimal moneyAmout;
    private Timestamp refundTime;
    private Byte       isSuccess;
}
