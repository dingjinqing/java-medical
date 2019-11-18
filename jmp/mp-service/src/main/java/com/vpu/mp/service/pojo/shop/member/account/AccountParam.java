package com.vpu.mp.service.pojo.shop.member.account;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 余额修改接收参数
 * @author 黄壮壮
 * 2019-07-17 18:03
 */
@Data
public class AccountParam {
	/** 原有金额 */
	private BigDecimal account;
	/** 更新金额  区分正负号 */
	private BigDecimal amount;
	/** 用户id */
	private Integer userId;
	/** 备注 */
	private String remark;
	/** 订单编号 */
	private String orderSn;
	/** 支付方式 */
	private String payment;
	/** 支付类型，0：充值，1：消费  如： {@link com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.RECHARGE }*/
	private Byte isPaid;
}
