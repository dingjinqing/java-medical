package com.vpu.mp.service.wechat.api;

import me.chanjar.weixin.common.error.WxErrorException;

public interface WxOpenHttpBase {
	/**
	 * post请求
	 * 
	 * @param url
	 * @param data
	 * @return
	 */
	String post(String appId, String url, String data) throws WxErrorException;

	/**
	 * get请求
	 * 
	 * @param url
	 * @param data
	 * @param queryParam
	 * @return
	 */
	String get(String appId, String url,String queryParam) throws WxErrorException;
}
