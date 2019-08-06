package com.vpu.mp.service.pojo.wxapp.login;

import lombok.Data;

@Data
public class WxAppSessionUser {
	Integer userId;
	String openId;
	String token;
	Integer shopId;
	String avatar;
	String username;
}
