package com.vpu.mp.service.pojo.wxapp.order.refund;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 小程序售后中心退款列表
 * @author 王帅
 */
@Getter
@Setter
@ToString
public class ReturnOrderListMp {
    /**1买家；0商家（包含定时任务）*/
    private Byte role;
    private Byte refundStatus;
    private String returnOrderSn;
    /**申请时间*/
    private Timestamp createTime;
    /**完成时间、撤销时间、拒绝时间*/
    private Timestamp finishTime;
    private Byte returnType;
    private Byte reasonType;
    private String reasonDesc;
    private BigDecimal money;
    private BigDecimal shippingFee;
    /**申请凭证*/
    private String goodsImages;
    /**退货凭证（物流）*/
    private String voucherImages;
    private String shippingType;
    private String shippingNo;
    private String phone;
    /**拒绝原因*/
    private String refundRefuseReason;
}
