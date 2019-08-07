package com.vpu.mp.service.wechat.api.impl;

import com.vpu.mp.service.wechat.api.WxOpenAccountService;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.open.api.WxOpenMaService;
import me.chanjar.weixin.open.api.WxOpenService;

/**
 * 用于小程序未实现的接口
 * 
 * @author lixinguo
 *
 */
public class WxOpenMaServiceExtraImpl implements WxOpenAccountService {

	protected WxOpenService openService;
	
	public WxOpenMaServiceExtraImpl(WxOpenService openService) {
		this.openService = openService;
	}

	@Override
	public String post(String appId, String url, String data) throws WxErrorException {
		WxOpenMaService maService = openService.getWxOpenComponentService().getWxMaServiceByAppid(appId);
		return maService.post(url, data);
	}

	@Override
	public String get(String appId, String url,String queryParam) throws WxErrorException {
		WxOpenMaService maService = openService.getWxOpenComponentService().getWxMaServiceByAppid(appId);
		return maService.get(url, queryParam);
	}

}
