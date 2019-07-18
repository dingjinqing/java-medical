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
	/**
	 * 原有金额
	 */
	private BigDecimal account;
	/**
	 * 增加的金额
	 */
	private BigDecimal amount;
	private Integer userId;
	private String remark;
	private String orderSn;
}
