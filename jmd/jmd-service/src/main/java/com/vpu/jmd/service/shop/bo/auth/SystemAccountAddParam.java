package com.vpu.jmd.service.shop.bo.auth;

import com.vpu.mp.service.foundation.data.JsonResultMessage;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 添加子账户
 *
 * @author zhaojianqiang
 * @date 2020年6月18日下午5:34:02
 */
@Data
public class SystemAccountAddParam {
	/**
	 * 子帐号用户名
	 */
	@NotNull
	@Pattern(regexp = "^[^\\u4e00-\\u9fa5]{1,20}$",message = JsonResultMessage.MSG_ACCOUNT_USERNAME_LENGTH_LIMIT)
	private String accountName;
	/** 手机号*/
	@NotNull
	@Pattern(regexp = "^1[3|7|8]\\d{9}$|^19[8-9]\\d{8}$|^166\\d{8}|^15[0-3|5-9]\\d{8}|^14[5|7]\\d{8}$",message = JsonResultMessage.MSG_ACCOUNT_MOBILE_LENGTH_LIMIT)
	private String mobile;
	/** 子账户密码*/
	@NotNull
	@Pattern(regexp = "^[^\\u4e00-\\u9fa5]{6,16}$",message = JsonResultMessage.MSG_ACCOUNT_PASSWD_LENGTH_LIMIT)
	private String accountPwd;
	/** 角色id*/
	@NotNull
	private Integer roleId;

}
