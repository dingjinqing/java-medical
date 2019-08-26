package com.vpu.mp.service.pojo.shop.member.card;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
* @author 黄壮壮
* @Date: 2019年8月26日
* @Description: 会员卡消费数据 
*/
@Getter
@Setter
public class CardConsumpData {
	/** 会员id */
	private Integer userId;
	/** 会员卡id */
	private Integer cardId;
	/** 会员卡号 */
	private String cardNo;
	/** 卡余额  */
	private BigDecimal money;
	/** 消费原因 */
	private String reason ;
	/** 消费类型 0是普通卡 1限次卡 */
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
	/** 微信支付id，用于发送模板消息 */
	private String prepayId;
	/** 订单状态 0：待支付，1：已取消，2：已完成 */
	private Byte orderStatus;
	/** 订单应付金额 */
	private BigDecimal moneyPaid;
	/** 0按规则 1自定义 */
	private Byte chargeType;
	/** 支付宝交易单号 */
	private String aliTradeNo;
}
