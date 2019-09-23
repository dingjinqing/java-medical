package com.vpu.mp.service.pojo.shop.order.refund;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.vpu.mp.service.pojo.shop.order.OrderListInfoVo;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author wangshuai
 */
@Getter
@Setter
public class OrderReturnListVo extends OrderListInfoVo {
	private String returnOrderSn;
	private Timestamp applyTime;
	private BigDecimal money;
	private Byte returnType;
	private Byte reasonType;
	private String reasonDesc;
	private Timestamp shippingOrRefundTime;
}
