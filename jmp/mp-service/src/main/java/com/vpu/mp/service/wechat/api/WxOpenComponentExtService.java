package com.vpu.mp.service.wechat.api;

import com.vpu.mp.service.wechat.bean.open.WxOpenAuthorizerListResult;

import me.chanjar.weixin.common.error.WxErrorException;

public interface WxOpenComponentExtService extends WxOpenComponentHttpBase {

	static final String GET_AUTHORIZER_LIST = "https://api.weixin.qq.com/cgi-bin/component/api_get_authorizer_list"; 
	
	/**
	 * 第三方平台可以使用接口拉取当前所有已授权的帐号基本信息。
	 * 
	 * @param offset
	 * @param count
	 * @return
	 * @throws WxErrorException
	 */
	WxOpenAuthorizerListResult getAuthorizerList(Integer offset,Integer count) throws WxErrorException;
	
}
