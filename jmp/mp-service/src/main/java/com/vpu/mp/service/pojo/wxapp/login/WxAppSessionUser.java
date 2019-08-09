package com.vpu.mp.service.pojo.wxapp.login;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class WxAppSessionUser {
	Integer userId;
	String openId;
	String token;
	Integer shopId;
	String avatar;
	String username;
	String mobile;
	
	@JsonProperty(value = "shop_flag")
	Byte shopFlag;
}
