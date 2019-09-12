package com.vpu.mp.service.pojo.wxapp.login;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 
 * @author lixinguo
 *
 */
@Data
public class WxAppCommonParam {
	
	@JsonProperty(value = "shop_id")
	Integer shopId;
	
	@JsonProperty(value = "user_id")
	Integer userId;
	
	/**
	 * 小程序版本
	 */
	Integer version = 0;
}
