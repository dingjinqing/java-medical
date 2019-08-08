package com.vpu.mp.service.pojo.shop.distribution;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

/**
 * 分销员已邀请用户列表出参类
 * @author 常乐
 * 2019年8月8日
 */
@Data
public class DistributorInvitedListVo {
	public String username;
	public String mobile;
	public Timestamp createTime;
	/**
	 * 累积返利订单数
	 */
	public Integer orderNumber;
	/**
	 * 累积订单返利商品总金额
	 */
	public BigDecimal totalCanFanliMoney;
	/**
	 * 累积返利佣金
	 */
	public BigDecimal totalFanliMoney;
	/**
	 * 返利有效日期
	 */
	public Timestamp invitExpiryDate;
	/**
	 * 邀请保护日期
	 */
	public Timestamp inviteProtectDate;
}
