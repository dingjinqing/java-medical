package com.vpu.mp.service.wechat.api.impl;

import com.vpu.mp.service.wechat.api.WxOpenAccountService;
import com.vpu.mp.service.wechat.api.WxOpenMaMpHttpBase;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.open.api.WxOpenService;

/**
 * 用于公众号未实现的接口
 * 
 * @author lixinguo
 *
 */
public class WxOpenMpServiceExtraImpl implements WxOpenMaMpHttpBase,WxOpenAccountService {

	protected WxOpenService openService;
	
	public WxOpenMpServiceExtraImpl(WxOpenService openService) {
		this.openService = openService;
	}

	@Override
	public String post(String appId, String url, String data) throws WxErrorException {
		WxMpService mpService = openService.getWxOpenComponentService().getWxMpServiceByAppid(appId);
		return mpService.post(url, data);
	}

	@Override
	public String get(String appId, String url,String queryParam) throws WxErrorException {
		WxMpService mpService = openService.getWxOpenComponentService().getWxMpServiceByAppid(appId);
		return mpService.get(url, queryParam);
	}

}
