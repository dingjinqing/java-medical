package com.vpu.mp.service.pojo.shop.distribution;

import java.sql.Timestamp;

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
	private String distributorName;
	/**
	 * 分销员电话
	 */
	private String distributorMobile;
	/**
	 * 下单用户名
	 */
	private String username;
	/**
	 * 下单用户手机号
	 */
	private String mobile;
	/**
	 * 下单开始时间
	 */
	private Timestamp startCreateTime;
	/**
	 * 下单结束时间
	 */
	private Timestamp endCreateTime;
	/**
	 * 订单号
	 */
	private String orderSn;
	/**
	 * 开始返利时间
	 */
	private Timestamp startRebateTime;
	/**
	 * 返利结束时间
	 */
	private Timestamp endRebateTime;
	/**
	 * 返利状态
	 */
	private Byte rebateStatus;
	/**
	 * 分销员分组
	 */
	private Integer distributorGroup;
	/**
	 * 返利关系
	 */
	private Byte rebateLevel;
	
	private Integer currentPage;
	private Integer pageRows;
	
}
