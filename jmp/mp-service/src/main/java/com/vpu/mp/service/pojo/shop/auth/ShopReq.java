package com.vpu.mp.service.pojo.shop.auth;

import java.sql.Timestamp;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.vpu.mp.service.foundation.data.JsonResultMessage;

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
	@NotNull(message = JsonResultMessage.MSG_ACCOUNT_USERNAME_NOT_NULL)
	private Integer shopId;
	private Integer sysId;
	@NotBlank(message = JsonResultMessage.MSG_ACCOUNT_MODILE_NOT_NULL)
	private String mobile;
	@NotBlank(message = JsonResultMessage.MSG_ACCOUNT_USERNAME_NOT_NULL)
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
	private Timestamp endTime;
	/**
	 * 数据库
	 */
	public String dbConfigId;

}
