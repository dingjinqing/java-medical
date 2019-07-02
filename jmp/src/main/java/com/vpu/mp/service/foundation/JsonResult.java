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


	public JsonResult(String language, JsonResultCode resultCode, Object content) {
		result(language, resultCode, content);
	}

	public JsonResult result(String language, JsonResultCode resultCode, Object content) {
		this.language = language = StringUtils.isBlank(language) ? "zh_CN" : language;
		this.error = resultCode.getCode();
		this.content = content;
		this.message = Util.translateMessage(language, resultCode.getMessage());
		return this;
	}

	public JsonResult success(String module, String language) {
		return result(language, JsonResultCode.CODE_SUCCESS, null);
	}

	public JsonResult success(String language, Object content) {
		return result(language, JsonResultCode.CODE_SUCCESS, content);
	}

	public JsonResult fail(String language, JsonResultCode resultCode) {
		return result(language, resultCode, null);
	}

	public JsonResult fail(String language, JsonResultCode resultCode, Object content) {
		assert (resultCode.getCode() != JsonResultCode.CODE_SUCCESS.getCode());
		return result(language, resultCode, content);
	}

	@Override
	public String toString() {
		return Util.toJSON(this);
	}
}
