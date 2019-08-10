package com.vpu.mp.service.pojo.shop.distribution;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

/**
 * 佣金统计出参
 * @author 常乐
 * 2019年8月8日
 */
@Data
public class BrokerageListVo {
	public String distributorName;
	public String distributorMobile;
	public String realName;
	public String distributorGroup;
	public String ordersn;
	/**
	 * 订单总金额
	 */
	public BigDecimal totalOrderMoney;
	public String userMobile;
	public String orderUserName;
	/**
	 * 返利关系
	 */
	public Integer rebateLevel;
	/**
	 * 返利商品总金额
	 */
	public BigDecimal totalRebateMoney;
	/**
	 * 返利佣金金额
	 */
	public BigDecimal realRebateMoney;
	public Timestamp createTime;
	/**
	 * 返利时间
	 */
	public Timestamp rebateTime;
	
	
}
