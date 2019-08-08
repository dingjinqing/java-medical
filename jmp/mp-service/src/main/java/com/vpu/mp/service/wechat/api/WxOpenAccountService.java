package com.vpu.mp.service.wechat.api;

import com.google.gson.JsonObject;
import com.vpu.mp.service.wechat.bean.open.WxOpenGetResult;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.bean.WxOpenCreateResult;
import me.chanjar.weixin.open.bean.result.WxOpenResult;

/**
 * 
 * @author lixinguo
 *
 */
public interface WxOpenAccountService extends WxOpenMaMpHttpBase {
	/**
	 * 开放平台帐号相关
	 */
	static final String CREATE_OPEN_ACCOUNT_URL = "https://api.weixin.qq.com/cgi-bin/open/create";
	static final String CREATE_OPEN_GET_URL = "https://api.weixin.qq.com/cgi-bin/open/get";
	static final String BIND_OPEN_PLATFORM = "https://api.weixin.qq.com/cgi-bin/open/bind";
	static final String UNBIND_OPEN_PLATFORM = "https://api.weixin.qq.com/cgi-bin/open/unbind";
	

	/**
	 * 获取公众号/小程序所绑定的开放平台帐号
	 */
	default WxOpenCreateResult createOpenAccount(String appId) throws WxErrorException {
		JsonObject param = new JsonObject();
		param.addProperty("appid", appId);
		String json = post(appId,CREATE_OPEN_ACCOUNT_URL, param.toString());
		return WxOpenCreateResult.fromJson(json);
	}

	/**
	 * 获取公众号/小程序所绑定的开放平台帐号
	 */
	default WxOpenGetResult getOpenAccount(String appId) throws WxErrorException{
		JsonObject param = new JsonObject();
		param.addProperty("appid", appId);
		String json = post(appId,CREATE_OPEN_GET_URL, param.toString());
		return WxOpenGetResult.fromJson(json);
	}

	/**
	 * 将 公众号/小程序绑定到开放平台帐号下
	 */
	default WxOpenResult bindOpenAppId(String appId, String openAppId) throws WxErrorException{
		JsonObject param = new JsonObject();
		param.addProperty("appid", appId);
		param.addProperty("open_appid", openAppId);
		String json = post(appId,BIND_OPEN_PLATFORM, param.toString());
		return WxOpenGetResult.fromJson(json);
	}

	/**
	 * 将公众号/小程序从开放平台帐号下解绑
	 */
	default WxOpenResult unbindOpenAppId(String appId, String openAppId) throws WxErrorException{
		JsonObject param = new JsonObject();
		param.addProperty("appid", appId);
		param.addProperty("open_appid", openAppId);
		String json = post(appId,UNBIND_OPEN_PLATFORM, param.toString());
		return WxOpenGetResult.fromJson(json);
	}

}
