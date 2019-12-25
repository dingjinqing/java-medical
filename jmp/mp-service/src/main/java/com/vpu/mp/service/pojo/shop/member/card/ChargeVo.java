package com.vpu.mp.service.pojo.shop.member.card;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

/**
* @author 黄壮壮
* @Date: 2019年9月26日
* @Description: 充值明细-出参
*/
@Data
public class ChargeVo {
	// 用户名
	private String username;
	// 电话
	private String mobile;
	// 余额变动明细
	private BigDecimal money;
	// 充值原因
	private String reason;
	// 备注
	private String message;
	// 变动时间
	private Timestamp createTime;
	
	
	private BigDecimal charge;
	private Byte type;
	private Short count;
	private Short exchangCount;
}
