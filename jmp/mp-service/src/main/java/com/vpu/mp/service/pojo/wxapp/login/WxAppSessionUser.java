package com.vpu.mp.service.pojo.wxapp.login;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 
 * @author lixinguo
 *
 */
@Data
public class WxAppSessionUser {
	@JsonProperty(value = "user_id")
	Integer userId;
	
	@JsonProperty(value = "open_id")
	String openId;
	
	String token;
	
	@JsonProperty(value = "shop_id")
	Integer shopId;
	
	String avatar;
	String username;
	String mobile;
	
	@JsonProperty(value = "shop_flag")
	Byte shopFlag;
}
