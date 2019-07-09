package com.vpu.mp.service.pojo.shop.auth;

import javax.validation.constraints.NotBlank;

import com.vpu.mp.service.foundation.JsonResultMessage;

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
	@NotBlank(message = JsonResultMessage.MSG_ACCOUNT_USERNAME_NOT_NULL)
	private Integer shopId;
	private Integer sysId;
	private String mobile;
	private String shopType;
	private String receiveMobile;
	private String shopName;
	private String shopPhone;
	private String shopNotice;
	private String shopWx;
	private String shopEmail;
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
