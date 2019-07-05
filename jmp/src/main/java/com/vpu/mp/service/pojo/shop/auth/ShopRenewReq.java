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
}
