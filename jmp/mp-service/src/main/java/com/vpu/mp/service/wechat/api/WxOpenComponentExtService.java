package com.vpu.mp.service.wechat.api;

import com.google.gson.JsonObject;
import com.vpu.mp.service.wechat.bean.open.WxOpenAuthorizerListResult;

import me.chanjar.weixin.common.error.WxErrorException;

public interface WxOpenComponentExtService extends WxOpenComponentHttpBase {

	static final String GET_AUTHORIZER_LIST = "https://api.weixin.qq.com/cgi-bin/component/api_get_authorizer_list"; 
	
	/**
	 * 第三方平台可以使用接口拉取当前所有已授权的帐号基本信息。
	 * 
	 * @param componentAppId
	 * @param offset
	 * @param count
	 * @return
	 * @throws WxErrorException
	 */
	default WxOpenAuthorizerListResult getAuthorizerList(String componentAppId,Integer offset,Integer count) throws WxErrorException {
		JsonObject param = new JsonObject();
		param.addProperty("component_appid", componentAppId);
		param.addProperty("offset", offset);
		param.addProperty("count", count);
		String json = post(GET_AUTHORIZER_LIST, param.toString(),COMPONENT_TOKEN_KEY);
		return WxOpenAuthorizerListResult.fromJson(json);
	}
	
}
