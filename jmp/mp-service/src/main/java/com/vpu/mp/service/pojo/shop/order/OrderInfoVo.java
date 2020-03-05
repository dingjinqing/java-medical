package com.vpu.mp.service.pojo.shop.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vpu.mp.service.pojo.shop.order.refund.OrderConciseRefundInfoVo;
import com.vpu.mp.service.pojo.shop.order.shipping.ShippingInfoVo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

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
	/**确认收货时间*/
	private Timestamp confirmTime;
	/**完成时间*/
	private Timestamp finishedTim;
	/**订单关闭时间*/
	private Timestamp closedTime;
	/**订单取消时间*/
	private Timestamp cancelledTime;
	/**下单人昵称*/
	private String username;
    /**下单人手机号*/
    private String userMobile;
	/**完整收货地址*/
	private String completeAddress;
	/**买家留言*/
	private String addMessage;
	/**快递单号:后台判断是否查询配送信息依据之一*/
	@JsonIgnore
	private String shippingNo;
	/**快递信息*/
	private List<ShippingInfoVo> shippingList;
	/**退款、货信息*/
	private List<OrderConciseRefundInfoVo> refundList;
	/**其他信息...*/
	/**返利类型，0：普通订单，1：分销返利订单，2：返利会员返利订单*/
	private Integer fanliType;
	@JsonIgnore
	private Integer cardId;
	@JsonIgnore
	private Byte orderPayWay;
	/**（券）折扣金额*/
	/**918新增*/
	private BigDecimal discount;
	/**918新增*/
	private Integer storeId;
	private String storeName;
	private Integer verifierId;
	private String verifierName;
	private BigDecimal orderAmount;
	private BigDecimal packageDiscount;
	private BigDecimal memberCardReduce;
	private Byte exchang;
	private Short goodsAmount;
	/**满折满减优惠*/
	private BigDecimal promotionReduce;
	/**团长优惠金额*/
	private BigDecimal grouperCheapReduce;
	/**会员等级优惠*/
	private BigDecimal discountPrice;
	/**搭配立减优惠*/
	private BigDecimal dapeiReduceAmount;
	private String sellerRemark;
	private Byte isCod;
	@JsonIgnore
	private String verifyCode;
	/**退定金模式1:自动退定金0:不退定金*/
	private Byte bkReturnType;
}
