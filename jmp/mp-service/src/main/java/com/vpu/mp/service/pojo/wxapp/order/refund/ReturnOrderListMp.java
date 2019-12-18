package com.vpu.mp.service.pojo.wxapp.order.refund;

import com.vpu.mp.service.pojo.shop.order.refund.OperatorRecord;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

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
    /**申请时间*/
    private Timestamp createTime;
    /**完成时间、撤销时间、拒绝时间*/
    private Timestamp finishTime;
    private Byte returnType;
    private Byte reasonType;
    private BigDecimal money;
    private BigDecimal shippingFee;
    private List<OperatorRecord> operatorRecord;
    /**拒绝原因*/
    private String refundRefuseReason;
}
