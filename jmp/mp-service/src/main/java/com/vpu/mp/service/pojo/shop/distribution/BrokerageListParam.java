package com.vpu.mp.service.pojo.shop.distribution;

import java.sql.Timestamp;

import com.vpu.mp.service.foundation.util.Page;

import lombok.Data;

/**
 * 分销员佣金统计入参
 * @author 常乐
 * 2019年8月8日
 */
@Data
public class BrokerageListParam {
	/**
	 * 分销员名称
	 */
	public String distributorName;
	/**
	 * 分销员电话
	 */
	public String distributorMobile;
	/**
	 * 下单用户名
	 */
	public String username;
	/**
	 * 下单用户手机号
	 */
	public String mobile;
	/**
	 * 下单开始时间
	 */
	public Timestamp startCreateTime;
	/**
	 * 下单结束时间
	 */
	public Timestamp endCreateTime;
	/**
	 * 订单号
	 */
	public String orderSn;
	/**
	 * 开始返利时间
	 */
	public Timestamp startRebateTime;
	/**
	 * 返利结束时间
	 */
	public Timestamp endRebateTime;
	/**
	 * 返利状态
	 */
	public Byte rebateStatus;
	/**
	 * 分销员分组
	 */
	public Integer distributorGroup;
	/**
	 * 返利关系
	 */
	public Byte rebateLevel;
	
	public Integer currentpage = Page.DEFAULT_CURRENT_PAGE;
	public Integer pageRows = Page.DEFAULT_PAGE_ROWS;
	
}
