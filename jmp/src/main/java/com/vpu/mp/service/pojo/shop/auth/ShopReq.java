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
public class ShopReq {
	private Integer shopId;
	private Integer sysId;
	private String mobile;
	private String receiveMobile;
	private String shopName;
	private String shopPhone;
	private String shopNotice;
	private String shopWx;
	private String shopEmail;
	private Timestamp created;
	private Byte isEnabled;
	private String shopQq;
	private Byte shopFlag;
	private String memberKey;
	private String tenancyName;
	private String userName;
	private String password;
	private Byte hidBottom;
	private String dbConfig;

}
