package com.vpu.mp.service.pojo.shop.member.card;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;

/**
* @author 黄壮壮
* @Date: 2019年10月24日
* @Description: 会员卡消费 | 充值数据
*/
@Data
@Builder
public class UserCardConsumeBean {
	// 用户Id
	private Integer userId;
	// 会员卡Id
	private Integer cardId;
	// 会员卡号
	private String cardNo;
	//消费类型 0是普通卡 1限次卡
	private Byte type;
	// 门店兑换次数
	private Integer countDis;
	// 原来的钱
	private BigDecimal moneyDis;
	// 卡余额消耗金额
	private BigDecimal money;
	// 创建时间
	private Timestamp createTime;
	// 消费原因模板id
	private String reasonId;
	// 模板消费原因
	private String reason;
	private Integer exchangCount;
	// 订单号
	private String orderSn;
	// 备注
	private String message;

	// 消费次数
	private Integer count;
	// 充值的钱
	private BigDecimal charge;
	// 支付方式
	private String payment;
	// 支付宝交易单号
	private String aliTradeNo;
	// 0按规则 1自定义
	private Byte chargeType;
	// 订单应付金额
	private BigDecimal moneyPaid;
	// 订单状态
	private Byte orderStatus;
	// 微信支付id，用于发送模板消息
	private String prepayId;
	
	
}
