package com.vpu.mp.service.foundation.data;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.vpu.mp.service.foundation.util.Util;

import lombok.Data;

/**
 *
 * @author 新国
 *
 */
@Data
public class JsonResult {

	public static final String LANGUAGE_TYPE_MSG = "messages";


	/**
	 * 错误码：非0为错误
	 */
	private int error = 0;

	/**
	 * 返回内容
	 */
	private Object content;

	/**
	 * 错误消息
	 */
	private Object message;

	/**
	 * 语言：zh_CN en_US
	 */
	private String language;

	public JsonResult() {
//		 result(null, JsonResultCode.CODE_SUCCESS, null);
	}


	public JsonResult(String language, JsonResultCode resultCode, Object content,Object ...args) {
		result(language, resultCode, content,args);
	}

	public JsonResult result(String language, JsonResultCode resultCode, Object content,Object ...args) {
		this.language = language = StringUtils.isBlank(language) ? "zh_CN" : language;
		this.error = resultCode.getCode();
		this.content = content;
		this.message =Util.translateMessage(language, resultCode.getMessage(),LANGUAGE_TYPE_MSG,args);
		return this;
	}

	public JsonResult success(String module, String language) {
		return result(language, JsonResultCode.CODE_SUCCESS, null);
	}

	public JsonResult success(String language, Object content) {
		return result(language, JsonResultCode.CODE_SUCCESS, content);
	}

	public JsonResult fail(String language, JsonResultCode resultCode,Object ...args) {
		return result(language, resultCode, null,args);
	}

	public JsonResult fail(String language, JsonResultCode resultCode, Object content,Object ...args) {
		Assert.isTrue(resultCode.getCode() != JsonResultCode.CODE_SUCCESS.getCode(),"code is not equal to JsonResultCode.CODE_SUCCESS");
		return result(language, resultCode, content,args);
	}

	@Override
	public String toString() {
		return Util.toJson(this);
	}
}
