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
