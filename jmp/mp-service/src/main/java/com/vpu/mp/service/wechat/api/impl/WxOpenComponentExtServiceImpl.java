package com.vpu.mp.service.wechat.api.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	static final Logger logger = LoggerFactory.getLogger(WxOpenComponentExtServiceImpl.class);

	static final String COMPONENT_TOKEN_KEY = "component_access_token";
	static final String ACCESS_TOKEN_KEY = "access_token";
	static final String METHOD_GET = "get";
	static final String METHOD_POST = "post";

	protected WxOpenService openService;

	public WxOpenComponentExtServiceImpl(WxOpenService openService) {
		this.openService = openService;
	}

	@Override
	public WxOpenAuthorizerListResult getAuthorizerList(String componentAppid, Integer offset, Integer count)
			throws WxErrorException {
		JsonObject param = new JsonObject();
		param.addProperty("component_appid", componentAppid);
		param.addProperty("offset", offset);
		param.addProperty("count", count);
		String json = post(GET_AUTHORIZER_LIST_URL, param.toString(), ACCESS_TOKEN_KEY);
		return WxOpenAuthorizerListResult.fromJson(json);
	}

	@Override
	public WxOpenGetResult getOpenAccount(String appId) throws WxErrorException {
		JsonObject param = new JsonObject();
		param.addProperty("appid", appId);
		String json = post(CREATE_OPEN_GET_URL, param.toString(), ACCESS_TOKEN_KEY);
		return WxOpenGetResult.fromJson(json);
	}

	@Override
	public WxOpenResult bindOpenAppId(String appId, String openAppId) throws WxErrorException {
		JsonObject param = new JsonObject();
		param.addProperty("appid", appId);
		param.addProperty("open_appid", openAppId);
		String json = post(BIND_OPEN_PLATFORM, param.toString(), ACCESS_TOKEN_KEY);
		return WxOpenGetResult.fromJson(json);
	}

	@Override
	public WxOpenResult unbindOpenAppId(String appId, String openAppId) throws WxErrorException {
		JsonObject param = new JsonObject();
		param.addProperty("appid", appId);
		param.addProperty("open_appid", openAppId);
		String json = post(UNBIND_OPEN_PLATFORM, param.toString(), ACCESS_TOKEN_KEY);
		return WxOpenGetResult.fromJson(json);
	}

	/**
	 * 请求WxgetOpenComponentServiceImpl私有方法
	 * 
	 * @param method         WxgetOpenComponentServiceImpl的私有方法get或者post
	 * @param uri
	 * @param data
	 * @param accessTokenKey
	 * @return
	 * @throws WxErrorException
	 */
	public String request(String method, String uri, String data, String accessTokenKey) throws WxErrorException {
		Class<?>[] getParams = { String.class, String.class };
		Class<?>[] postParams = { String.class, String.class, String.class };
		Method action;
		try {
			action = getOpenComponentService().getClass().getDeclaredMethod(method,
					METHOD_POST.equals(method) ? postParams : getParams);
			action.setAccessible(true);
			return METHOD_POST.equals(method)
					? (String) action.invoke(getOpenComponentService(), uri, data, accessTokenKey)
					: (String) action.invoke(getOpenComponentService(), uri, accessTokenKey);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			if (e instanceof InvocationTargetException) {
				Throwable cause = ((InvocationTargetException) e).getCause();
				if (cause instanceof WxErrorException) {
					throw new WxErrorException(((WxErrorException) cause).getError(), cause);
				}
			}
			WxError error = WxError.builder().errorCode(-2).errorMsg(e.getMessage()).build();
			throw new WxErrorException(error, e);
		}
	}

	protected WxOpenComponentService getOpenComponentService() {
		return this.openService.getWxOpenComponentService();
	}

	public String post(String uri, String postData, String accessTokenKey) throws WxErrorException {
		return this.request(METHOD_POST, uri, postData, accessTokenKey);
	}

	public String get(String uri, String accessTokenKey) throws WxErrorException {
		return this.request(METHOD_GET, uri, null, accessTokenKey);
	}

}
