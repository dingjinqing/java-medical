package com.vpu.mp.service.pojo.shop.store.account;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.vpu.mp.service.foundation.data.JsonResultMessage;

import lombok.Data;

/**
 * 创建的入参
 * 
 * @author zhaojianqiang
 * @time 下午3:36:35
 */
@Data
public class StoreAccountParam {
	@NotNull
	private String mobile;
	@NotNull
	private String accountName;
	@NotNull
	private Byte accountType;
	@NotNull
	@Pattern(regexp = "^[^\\u4e00-\\u9fa5]{6,16}$",message = JsonResultMessage.MSG_ACCOUNT_PASSWD_LENGTH_LIMIT)
	private String accountPasswd;
	@NotNull
	private String storeList;
}
