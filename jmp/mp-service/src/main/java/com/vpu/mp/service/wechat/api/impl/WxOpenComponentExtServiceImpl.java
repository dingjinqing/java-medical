package com.vpu.mp.service.wechat.api.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.google.gson.JsonObject;
import com.vpu.mp.service.wechat.api.WxOpenComponentExtService;
import com.vpu.mp.service.wechat.bean.open.WxOpenAuthorizerListResult;
import com.vpu.mp.service.wechat.bean.open.WxOpenGetResult;

import me.chanjar.weixin.common.error.WxError;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.api.WxOpenComponentService;
import me.chanjar.weixin.open.api.WxOpenService;
import me.chanjar.weixin.open.bean.result.WxOpenResult;

/**
 * 开放平台扩展服务，实现原SDK未实现的接口
 * 
 * @author lixinguo
 *
 */
public class WxOpenComponentExtServiceImpl implements WxOpenComponentExtService {

	protected WxOpenService openService;

	public WxOpenComponentExtServiceImpl(WxOpenService openService) {
		this.openService = openService;
	}


	@Override
	public WxOpenAuthorizerListResult getAuthorizerList(String componentAppid,Integer offset, Integer count) throws WxErrorException {
		JsonObject param = new JsonObject();
		param.addProperty("component_appid", componentAppid);
		param.addProperty("offset", offset);
		param.addProperty("count", count);
		String json = post( GET_AUTHORIZER_LIST_URL, param.toString());
		return WxOpenAuthorizerListResult.fromJson(json);
	}

	@Override
	public WxOpenGetResult getOpenAccount(String appId) throws WxErrorException {
		JsonObject param = new JsonObject();
		param.addProperty("appid", appId);
		String json = post(CREATE_OPEN_GET_URL, param.toString());
		return WxOpenGetResult.fromJson(json);
	}

	@Override
	public WxOpenResult bindOpenAppId(String appId, String openAppId)  throws WxErrorException {
		JsonObject param = new JsonObject();
		param.addProperty("appid", appId);
		param.addProperty("open_appid", openAppId);
		String json = post(BIND_OPEN_PLATFORM, param.toString());
		return WxOpenGetResult.fromJson(json);
	}

	@Override
	public WxOpenResult unbindOpenAppId(String appId,String openAppId) throws WxErrorException {
		JsonObject param = new JsonObject();
		param.addProperty("appid", appId);
		param.addProperty("open_appid", openAppId);
		String json = post(UNBIND_OPEN_PLATFORM, param.toString());
		return WxOpenGetResult.fromJson(json);
	}
	
	/**
	 * 请求WxgetOpenComponentServiceImpl私有方法
	 * @param method WxgetOpenComponentServiceImpl的私有方法get或者post
	 * @param uri 
	 * @param postData
	 * @return
	 * @throws WxErrorException
	 */
	public String request(String method, String uri, String postData) throws WxErrorException {
		Class<?>[] params = { String.class, String.class };
		Method post;
		try {
			post = getOpenComponentService().getClass().getDeclaredMethod(method, params);
			post.setAccessible(true);
			return (String) post.invoke(getOpenComponentService(), uri, postData);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			WxError error = WxError.builder().errorCode(-2).errorMsg(e.getMessage()).build();
			throw new WxErrorException(error);
		}
	}
	
	protected WxOpenComponentService getOpenComponentService() {
		return this.openService.getWxOpenComponentService();
	}

	public String post(String uri, String postData) throws WxErrorException {
		return this.request("post", uri, postData);
	}

	public String get(String uri, String queryParam) throws WxErrorException {
		return this.request("get", uri, queryParam);
	}
	
	public String get(String uri) throws WxErrorException {
		return this.request("get", uri, null);
	}
	

}
