package com.vpu.mp.service.pojo.shop.order.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * @author 王帅
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ApiReturnOrderListVo {
    private String refundStatus;
    private String orderSn;
    private String money;
    private String returnType;
    @JsonIgnore
    private Byte reasonType;
    //
    private String reason;
    private String returnDesc;
    //
    private String shippingType;
    private String shippingNo;
    //
    private String goodsImages;
    //
    private String voucherImages;
    private String phone;
    private Timestamp applyNotPassReason;
    private Timestamp returnTime;
    private Timestamp returnFinishTime;
    private Timestamp refundTime;
    private Timestamp refundFinishTime;
    private Timestamp refuseTime;
    private Timestamp refuseRefundTime;
    private String refundRefuseReason;
    private String returnAddress;
    private String merchantTelephone;
    private String consignee;
    private String zipCode;

}
