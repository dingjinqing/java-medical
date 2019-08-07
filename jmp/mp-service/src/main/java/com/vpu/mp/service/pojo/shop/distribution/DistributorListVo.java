package com.vpu.mp.service.pojo.shop.distribution;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class DistributorListVo {
	public Integer userId;
	public String username;
	public String mobile;
	public Timestamp createDate;
	public String realName;
	public String groupName;
	public String levelName;
	
	/**
	 * 直接邀请人
	 */
	public String sublayerNumber; 
	
	/**
	 * 间接邀请人数
	 */
	public Integer nextNumber;
	
	/**
	 * 累积返利商品总额
	 */
	public BigDecimal totalCanFanliMoney; 
	
	/**
	 * 累积返利佣金金额
	 */
	public BigDecimal totalFanliMoney;
	
	/**
	 * 待返利佣金金额
	 */
	public BigDecimal waitFanliMoney;
}
