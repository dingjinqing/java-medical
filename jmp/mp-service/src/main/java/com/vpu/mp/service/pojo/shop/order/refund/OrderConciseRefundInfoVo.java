package com.vpu.mp.service.pojo.shop.order.refund;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * 订单详情页面展示退货款信息列表
 * @author 王帅
 *
 */
@Data
public class OrderConciseRefundInfoVo {
	private Integer retId;
	private Integer orderId;
	private String orderSn;
	private String returnOrderSn;
	private String returnType;
	private Byte refundStatus;
	private BigDecimal money;
	private Timestamp apply;
	private Timestamp success;
	private List<OrderReturnGoodsVo> orderReturnGoodsVo;
	/**退货时的申请时间*/
	@JsonIgnore
	private Timestamp applyTime;
	/**退款时的申请时间*/
	@JsonIgnore
	private Timestamp shippingOrRefundTime;
}
