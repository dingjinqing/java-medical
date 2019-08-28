package com.vpu.mp.service.pojo.wxapp.login;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

/**
 * 
 * @author lixinguo
 *
 */
@Data
public class WxAppSessionUser {
	/**
	 * 是否开启地理位置授权
	 */
	@JsonProperty(value = "geographic_location")
	Byte geoLocation = 10;
	
	/**
	 * 是否隐藏底部导航
	 */
	@JsonProperty(value = "hid_bottom")
	Byte hideBottom = 0;
	
	/**
	 * 微信用户信息
	 */
	@JsonProperty(value = "res")
	WxUserInfo wxUser;
	
	/**
	 * 店铺标记
	 */
	@JsonProperty(value = "shop_flag")
	Byte shopFlag;
	
	
	@JsonProperty(value = "shop_id")
	Integer shopId;
	
	String token;
	
	@JsonProperty(value = "user_avatar")
	String userAvatar;
	
	@JsonProperty(value = "user_id")
	Integer userId;
	
	String username;

	
	/**
	 * 用户登录session信息
	 * @author lixinguo
	 *
	 */
	@Data
	@Builder
	public static  class WxUserInfo{
		@JsonProperty(value = "open_id")
		String openId;
		String mobile;
		String unionid;
	}
}
