package com.vpu.mp.service.pojo.shop.member.card;

import java.math.BigDecimal;

import lombok.Getter;

/**
* @author 黄壮壮
* @Date: 2019年8月26日
* @Description: 会员卡消费数据 
*/
@Getter
public class CardConsumpData {
	/** 会员id */
	private Integer userId;
	/** 会员卡id */
	private Integer cardId;
	/** 会员卡号 */
	private String cardNo;
	/** 余额变动金额 区分正负号 */
	private BigDecimal money;
	/** 消费原因 */
	private String reason ;
	/** 消费类型 {@link com.vpu.mp.service.pojo.shop.member.card.CardConstant} */
	private Byte type;
	/** 订单编号 */
	private String orderSn;
	/** 兑换次数 */
	private Short exchangeCount;
	/** 消费次数 */
	private Integer count;
	/** 备注 */
	private String message;
	/** 支付方式 */
	private String payment;
	
	/**-----------为记录充值充值记录表charge_money的字段----------------------*/
	/** 微信支付id，用于发送模板消息 */
	private String prepayId;
	/** 订单状态 0：待支付，1：已取消，2：已完成 */
	private Byte orderStatus;
	/** 订单应付金额 */
	private BigDecimal moneyPaid;
	/** 0按规则 1自定义 {@link com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.CHARGE_TYPE_BY_RULE } */
	private Byte chargeType;
	/** 支付宝交易单号 */
	private String aliTradeNo;
	
	public CardConsumpData setUserId(Integer userId) {
		this.userId = userId;
		return this;
	}
	public CardConsumpData setCardId(Integer cardId) {
		this.cardId = cardId;
		return this;
	}
	public CardConsumpData setCardNo(String cardNo) {
		this.cardNo = cardNo;
		return this;
	}
	public CardConsumpData setMoney(BigDecimal money) {
		this.money = money;
		return this;
	}
	public CardConsumpData setReason(String reason) {
		this.reason = reason;
		return this;
	}
	public CardConsumpData setType(Byte type) {
		this.type = type;
		return this;
	}
	public CardConsumpData setOrderSn(String orderSn) {
		this.orderSn = orderSn;
		return this;
	}
	public CardConsumpData setExchangeCount(Short exchangeCount) {
		this.exchangeCount = exchangeCount;
		return this;
	}
	public CardConsumpData setCount(Integer count) {
		this.count = count;
		return this;
	}
	public CardConsumpData setMessage(String message) {
		this.message = message;
		return this;
	}
	public CardConsumpData setPayment(String payment) {
		this.payment = payment;
		return this;
	}
	public CardConsumpData setPrepayId(String prepayId) {
		this.prepayId = prepayId;
		return this;
	}
	public CardConsumpData setOrderStatus(Byte orderStatus) {
		this.orderStatus = orderStatus;
		return this;
	}
	public CardConsumpData setMoneyPaid(BigDecimal moneyPaid) {
		this.moneyPaid = moneyPaid;
		return this;
	}
	public CardConsumpData setChargeType(Byte chargeType) {
		this.chargeType = chargeType;
		return this;
	}
	public CardConsumpData setAliTradeNo(String aliTradeNo) {
		this.aliTradeNo = aliTradeNo;
		return this;
	}
	
}
