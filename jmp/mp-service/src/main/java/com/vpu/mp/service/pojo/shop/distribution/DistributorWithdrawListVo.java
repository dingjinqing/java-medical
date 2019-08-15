package com.vpu.mp.service.pojo.shop.distribution;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

/**
 * 返利提现审核列表出参
 * @author 常乐
 * 2019年8月14日
 */
@Data
public class DistributorWithdrawListVo {
	private String username;
	private String mobile;
	private String realName;
	private Timestamp createTime;
	private String orderSn;
	private BigDecimal withdrawCash;
	private Timestamp checkTime;
	private Byte status;
	private String refuseDesc;
	private String desc;
}
