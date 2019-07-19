package com.vpu.mp.service.pojo.shop.auth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.vpu.mp.service.foundation.JsonResultMessage;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author 新国
 *
 */
@Data
@NoArgsConstructor
public class ShopLoginParam {
	@NotBlank(message = JsonResultMessage.MSG_ACCOUNT_NAME_NOT_NULL)
	public String username;
	public String subUsername;
	public String password;

	@NotNull(message = JsonResultMessage.MSG_ACCOUNT_ISSUBLOGIN_NOT_NULL)
	public Boolean isSubLogin = false;
}
