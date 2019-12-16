package com.vpu.mp.service.pojo.shop.member.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @author 黄壮壮
 * 	用户返利
 */
@Data
public class AccountWithdrawVo {
	/**
	 * 可提现金额
	 */
	private BigDecimal withdraw;
	/**
	 * 	余额
	 */
	private BigDecimal account;
	
	/**
	 * 提现开关
	 */
	@JsonProperty(value = "withdraw_status")
	public Byte withdrawStatus;
	/**
	 * 是否强制用户绑定手机号
	 */
	@JsonProperty(value="is_bind_mobile")
	private Byte isBindMobile;
	
	/**
	 * 返利方式
	 */
	@JsonProperty(value = "withdraw_source")
	public String withdrawSource;
}
