package com.vpu.mp.service.pojo.shop.auth;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author zhaojianqiang
 *
 */
@Data
@NoArgsConstructor
public class ShopRenewReq {
	private Integer shopId;
	private Integer sysId;
	private String mobile;
	private BigDecimal renewMoney;
	private Timestamp expireTime;
	private String renewDesc;
	/**
	 * 续费类型
	 */
	private Byte renewType;
	/**
	 * 续费时长的年
	 */
	private Integer year;
	/**
	 * 续费时长的月
	 */
	private Integer month;
	/**
	 * 赠送类型
	 */
	private Byte sendType;
	/**
	 * 续费时长的年
	 */
	private Integer sendYear;
	/**
	 * 续费时长的月
	 */
	private Integer sendMonth;
	
	
	

}
