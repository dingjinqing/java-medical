package com.vpu.mp.service.wechat.api;

import com.vpu.mp.service.wechat.bean.open.WxOpenAuthorizerListResult;
import com.vpu.mp.service.wechat.bean.open.WxOpenGetResult;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.bean.result.WxOpenResult;

/**
 */
public interface WxOpenComponentExtService {
	/**
	 * 拉取当前所有已授权的帐号基本信息
	 */
	static final String GET_AUTHORIZER_LIST_URL = "https://api.weixin.qq.com/cgi-bin/component/api_get_authorizer_list";

	/**
	 * 开放平台帐号相关
	 */

	static final String CREATE_OPEN_GET_URL = "https://api.weixin.qq.com/cgi-bin/open/get";
	static final String BIND_OPEN_PLATFORM = "https://api.weixin.qq.com/cgi-bin/open/bind";
	static final String UNBIND_OPEN_PLATFORM = "https://api.weixin.qq.com/cgi-bin/open/unbind";

	/**
	 * 拉取当前所有已授权的帐号基本信息
	 */
	WxOpenAuthorizerListResult getAuthorizerList(String componentAppid, Integer offset, Integer count)
			throws WxErrorException;

	/**
	 * 获取公众号/小程序所绑定的开放平台帐号
	 */
	WxOpenGetResult getOpenAccount(String appId) throws WxErrorException;

	/**
	 * 将 公众号/小程序绑定到开放平台帐号下
	 */
	WxOpenResult bindOpenAppId(String appId, String openAppId) throws WxErrorException;

	/**
	 * 将公众号/小程序从开放平台帐号下解绑
	 */
	WxOpenResult unbindOpenAppId(String appId,String openAppId) throws WxErrorException;

}
