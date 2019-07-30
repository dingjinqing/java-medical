package com.vpu.mp.service.pojo.shop.order;

import java.sql.Timestamp;
import java.util.List;

import com.vpu.mp.service.pojo.shop.order.refund.OrderConciseRefundInfoVo;
import com.vpu.mp.service.pojo.shop.order.shipping.ShippingInfoVo;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author wangshuai
 */
@Getter
@Setter
public class OrderInfoVo extends OrderListInfoVo {
	/**支付时间 */
	private Timestamp payTime;
	/**发货时间*/
	private Timestamp shippingTime;
	/**完成时间*/
	private Timestamp finishedTim;
	/**订单关闭时间*/
	private Timestamp closedTime;
	/**订单取消时间*/
	private Timestamp cancelledTime;
	/**确认收货时间*/
	private Timestamp confirmTime;
	/**下单人昵称*/
	private String userName;
	/**下单人手机号*/
	private String userMobile;
	/**完整收货地址*/
	private String completeAddress;
	/**买家留言*/
	private String addMessage;
	/**快递单号:后台判断是否查询配送信息依据之一*/
	private String shippingNo;
	/**快递信息*/
	private List<ShippingInfoVo> shippingList;
	/**退款、货信息*/
	private List<OrderConciseRefundInfoVo> refundList;
	/**退货、款状态*/
	private Byte refundStatus;
	/**其他信息...*/
}
