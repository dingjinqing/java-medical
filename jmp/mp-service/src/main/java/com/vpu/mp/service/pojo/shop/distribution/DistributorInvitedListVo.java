package com.vpu.mp.service.pojo.shop.distribution;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import lombok.Data;

/**
 * 分销员已邀请用户列表出参类
 * @author 常乐
 * 2019年8月8日
 */
@Data
public class DistributorInvitedListVo {
    private Integer userId;
	private String username;
	private String mobile;
	private Timestamp createTime;
	/**
	 * 累积返利订单数
	 */
	private Integer orderNumber;
	/**
	 * 累积订单返利商品总金额
	 */
	private BigDecimal totalCanFanliMoney;
	/**
	 * 累积返利佣金
	 */
	private BigDecimal totalFanliMoney;
	/**
	 * 返利有效日期
	 */
	private Date inviteExpiryDate;
	/**
	 * 邀请保护日期
	 */
	private Date inviteProtectDate;
    /**
     * 邀请时间
     */
	private Timestamp inviteTime;
}
