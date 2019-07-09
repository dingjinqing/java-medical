package com.vpu.mp.service.pojo.shop.auth;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

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
public class ShopManagePwdParam {
	//旧密码
	@NotBlank(message = JsonResultMessage.MSG_ACCOUNT_PASSWD_NOT_NULL)
	@Length(min = 6,max = 16,message = JsonResultMessage.MSG_ACCOUNT_PASSWD_LENGTH_LIMIT)
	public String passwd;
	
	//新密码
	@NotBlank(message = JsonResultMessage.MSG_ACCOUNT_NEWPASSWD_NOT_NULL)
	@Length(min = 6,max = 16,message = JsonResultMessage.MSG_ACCOUNT_PASSWD_LENGTH_LIMIT)
	public String newPasswd;
	
	//确认新密码
	@NotBlank(message = JsonResultMessage.MSG_ACCOUNT_CONFNEWPASSWD_NOT_NULL)
	@Length(min = 6,max = 16,message = JsonResultMessage.MSG_ACCOUNT_PASSWD_LENGTH_LIMIT)
	public String confNewPasswd;
}
