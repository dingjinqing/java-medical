package com.vpu.mp.service.pojo.shop.store.service.order;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author 王兵兵
 *
 * 2019年7月18日
 */
@Data
public class ServiceOrderAddParam {
    private Integer    storeId;
    private String     orderSn;
    @NotNull
    private Integer    userId;
    private Byte       orderStatus;
    private String     orderStatusName;
    @NotNull
    private String     subscriber;
    @NotNull
    private String     mobile;
    @NotNull
    private Integer    serviceId;
    @NotNull
    private Integer    technicianId;
    @NotNull
    private String     technicianName;
    @NotNull
    private String     serviceDate;
    @NotNull
    private String     servicePeriod;
    private String     addMessage;
    private String     adminMessage;
    private String     verifyCode;
    private String     verifyAdmin;
    private String     payCode;
    private String     payName;
    private String     paySn;
    private BigDecimal moneyPaid;
    private BigDecimal discount;
    private Integer    couponId;
    private BigDecimal orderAmount;
    private Timestamp  payTime;
    private Timestamp  cancelledTime;
    private Timestamp  finishedTime;
    private String     prepayId;
    private Byte       delFlag;
    private Byte       verifyType;
    private String     cancelReason;
    private Byte       type;
    private Byte       verifyPay;
    private String     aliTradeNo;

}
