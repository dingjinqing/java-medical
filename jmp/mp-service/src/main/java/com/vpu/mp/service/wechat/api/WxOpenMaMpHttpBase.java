package com.vpu.mp.service.wechat.api;

import me.chanjar.weixin.common.error.WxErrorException;

/**
 * 小程序 或者公众号 http请求接口
 * 
 * @author lixinguo
 *
 */
public interface WxOpenMaMpHttpBase {

	/**
	 * post请求 
	 * @param appId
	 * @param url
	 * @param data
	 * @return
	 * @throws WxErrorException
	 */
	String post(String appId, String url, String data) throws WxErrorException;

	
	/**
	 * get请求 
	 * @param appId
	 * @param url
	 * @param queryParam
	 * @return
	 * @throws WxErrorException
	 */
	String get(String appId, String url,String queryParam) throws WxErrorException;
}
