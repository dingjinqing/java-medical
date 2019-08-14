package com.vpu.mp.service.pojo.wxapp.login;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author lixinguo
 *
 */
public class WxAppCommonParam {
	@JsonProperty(value = "shop_id")
	Integer shopId;
	
	@JsonProperty(value = "user_id")
	Integer userId;
	
	/**
	 * 版本
	 */
	String version;
}
