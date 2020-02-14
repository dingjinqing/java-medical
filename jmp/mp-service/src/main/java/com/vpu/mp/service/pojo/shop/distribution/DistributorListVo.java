package com.vpu.mp.service.pojo.shop.distribution;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class DistributorListVo {
	private Integer userId;
	private String username;
	private String mobile;
	private Timestamp createTime;
	private String realName;
	private String groupName;
	private String levelName;
	private Integer inviteId;
	private String inviteName;
	
	/**
	 * 直接邀请人
	 */
	private String sublayerNumber; 
	
	/**
	 * 间接邀请人数
	 */
	private Integer nextNumber;
	
	/**
	 * 累积返利商品总额
	 */
	private BigDecimal totalCanFanliMoney; 
	
	/**
	 * 累积返利佣金金额
	 */
	private BigDecimal totalFanliMoney;
	
	/**
	 * 待返利佣金金额
	 */
	private BigDecimal waitFanliMoney;
}
