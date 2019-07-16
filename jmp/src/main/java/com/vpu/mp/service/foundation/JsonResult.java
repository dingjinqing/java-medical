package com.vpu.mp.service.foundation;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;

/**
 * 
 * @author 新国
 *
 */
@Data
public class JsonResult {

	private static final String LANGUAGE_TYPE_MSG = "message";


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
		 result(null, JsonResultCode.CODE_SUCCESS, null);
	}


	public JsonResult(String language, JsonResultCode resultCode, Object content,Object ...args) {
		result(language, resultCode, content,args);
	}

	public JsonResult result(String language, JsonResultCode resultCode, Object content,Object ...args) {
		this.language = language = StringUtils.isBlank(language) ? "zh_CN" : language;
		this.error = resultCode.getCode();
		this.content = content;
		this.message = String.format(Util.translateMessage(language, resultCode.getMessage(),LANGUAGE_TYPE_MSG),args);
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
		assert (resultCode.getCode() != JsonResultCode.CODE_SUCCESS.getCode());
		return result(language, resultCode, content,args);
	}

	@Override
	public String toString() {
		return Util.toJson(this);
	}
}
