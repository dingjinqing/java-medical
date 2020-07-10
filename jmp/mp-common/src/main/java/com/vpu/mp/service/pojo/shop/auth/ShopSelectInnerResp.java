package com.vpu.mp.service.pojo.shop.auth;

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
public class ShopSelectInnerResp {

	private Integer shopId;
	private Integer sysId;
	private String shopName;
	private String shopAvatar;
	private Timestamp created;
	private Byte state;
	private Byte businessState;
	private Byte isEnabled;
	private String shopType;
	private Timestamp expireTime;
	private String expireTimeStatus;
	/** 币种*/
	private String currency;
	/** 语言*/
	private String shopLanguage;
}
