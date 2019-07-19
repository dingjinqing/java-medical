package com.vpu.mp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.saas.SaasApplication;

/**
 * 
 * @author 新国
 *
 */

public class BaseController {
	@Autowired
	protected SaasApplication saas ;

	@Autowired
	protected HttpServletRequest request;

	@Autowired
	protected Environment env;
	

	/**
	 * Json输出
	 * @param resultCode
	 * @param content
	 * @return
	 */
	public JsonResult result(JsonResultCode resultCode, Object content,Object ...args) {
		String language = getLang();
		return (new JsonResult()).result(language, resultCode, content,args);
	}

	public JsonResult success() {
		return result(JsonResultCode.CODE_SUCCESS, null);
	}

	public JsonResult success(Object content) {
		return result(JsonResultCode.CODE_SUCCESS, content);
	}

	public JsonResult fail(JsonResultCode resultCode,Object ...args) {
		return result(resultCode, null,args);
	}


	public JsonResult fail() {
		return result(JsonResultCode.CODE_FAIL, null);
	}

	protected boolean isPost() {
		return "POST".equals(request.getMethod());
	}

	protected String post(String key) {
		return isPost() ? request.getParameter(key) : null;
	}

	protected String input(String key) {
		return request.getParameter(key);
	}

	protected Map<String, String[]> input() {
		return request.getParameterMap();
	}

	protected String getLang() {
		return request.getHeader("V-Lang");
	}

	/**
	 * 响应错误信息
	 * @param apiMessageKey 错误信息key，对应JsonResultCode的message
	 * @return
	 */
	public JsonResult fail(String apiMessageKey) {
		for (JsonResultCode code : JsonResultCode.values()) {
			if (code.getMessage().equals(apiMessageKey)) {
				return result(code, null);
			}
		}
		throw new RuntimeException(apiMessageKey + " not defined in JsonResultCode.");
	}

	/**
	 * 输入参数Map
	 * @param delim
	 * @return
	 */
	protected Map<String, String> inputMap(String delim) {
		Map<String, String> result = new HashMap<String, String>(0);
		Map<String, String[]> maps = this.input();
		for (String key : maps.keySet()) {
			String value = StringUtils.arrayToDelimitedString(maps.get(key), delim);
			result.put(key, value);
		}
		return result;
	}

	/**
	 * 输入参数Map
	 * @return
	 */
	protected Map<String, String> inputMap() {
		return inputMap(",");
	}

	/**
	 * 主站路径URL
	 * @param path
	 * @return
	 */
	public String mainUrl(String path) {
		return Util.imageUrl(path, request.getScheme());
	}

	/**
	 * 图片路径URL
	 * @param path
	 * @return
	 */
	public String imageUrl(String path) {
		return Util.imageUrl(path, request.getScheme());
	}
}
