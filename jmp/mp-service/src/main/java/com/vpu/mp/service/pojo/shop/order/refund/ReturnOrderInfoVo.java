package com.vpu.mp.service.pojo.shop.order.refund;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.vpu.mp.service.pojo.shop.order.OrderInfoVo;

import lombok.Data;

@Data
public class ReturnOrderInfoVo {
	private String shippingCode;
	/** 优先级退款信息 */
	private Map<String, BigDecimal> calculateMoney;
	/** 最后一次操作人类型 */
	private Byte operatorLastType;
	private List<OperatorRecord> operatorRecord;
	private Long returnMoneyDays;
	private Long returnAddressDays;
	private Long returnShoppingDays;
	private Long returnAuditPassNotShoppingDays;
	private BigDecimal canReturnShippingFee;
	/** order info */
	private OrderInfoVo orderInfo;
	/** return goods */
	private List<OrderReturnGoodsVo> returnGoods;
	/** return order info */
	private Integer retId;
	private Integer orderId;
	private String orderSn;
	private String returnOrderSn;
	private Byte refundStatus;
	private BigDecimal money;
	private BigDecimal shippingFee;
	private Byte returnType;
	private Byte reasonType;
	private String reasonDesc;
	private String shippingType;
	private String shippingNo;
	private String goodsImages;
	private String voucherImages;
	private String phone;
	private Timestamp applyTime;
	private Timestamp applyPassTime;
	private Timestamp applyNotPassTime;
	private Timestamp shippingOrRefundTime;
	private Timestamp refundSuccessTime;
	private Timestamp refundRefuseTime;
	private Timestamp refundCancelTime;
	private String applyNotPassReason;
	private String refundRefuseReason;
	private String returnAddress;
	private String merchantTelephone;
	private String consignee;
	private String zipCode;
	private String currency;
}
// $can_refund['shipping_fee']
/**
 * 总退款金额：￥<span class="text-warning refund-money">0.00</span> =
 * 
 * @if($order->bk_order_money > 0)退尾款：￥<span class="text-warning
 *                            refund-bk-order-money">0.00</span>@endif +
 *                            退会员卡余额：￥<span class="text-warning
 *                            refund-member-card-money">0.00</span> +
 *                            退余额：￥<span class="text-warning
 *                            refund-balance-money">0.00</span> +
 *                            退积分抵扣：￥<span class="text-warning
 *                            refund-score-money">0.00</span> +
 *                            退支付金额：￥<span class="text-warning
 *                            refund-pay-money">0.00</span>
 */
// $refund_detail退款详情金额详情退款记录表
// 客户电话$return_order->phone
// 物流公司$return_order->shipping_type
// 物流单号shipping_no
// shipping_code
// reason现type
// 说明return_desc
// 退款图片：goods_images---》凭证图片改成
// 申请时间apply_time shipping_or_refund_time
// order_pay_way == 2 需查询好友代付明细

// $change_list协商记录
/**
 * change 申请type卖家买家系统 时间 退款类型 退款原因 退款金额 退款说明 商家拒绝退款申请 refund_refuse_time
 * 商家同意退款，退款成功refund_success_time
 * 商家同意退货退款apply_pass_time退货地址：{{$return_order->return_address}}；
 * 联系人：{{$return_order->consignee}}； 电话：{{$return_order->merchant_telephone}}
 * 您已拒绝退货退款apply_not_pass_time
 * 买家未在商家审核退款申请之后7日内，提交退货物流（或商家未确认收货），退款申请已自动撤销refund_cancel_time
 * 买家已自动撤销refund_cancel_time 提交物流 商家未收货，拒绝退款退货 商家退款，退款成功
 */
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
