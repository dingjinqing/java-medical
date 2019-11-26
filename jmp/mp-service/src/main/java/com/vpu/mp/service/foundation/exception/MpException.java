package com.vpu.mp.service.foundation.exception;

import com.vpu.mp.service.foundation.data.JsonResultCode;

/**
 * MpException:处理
 *
 * @author 王帅
 *
 */
public class MpException extends Exception {

	private static final long serialVersionUID = 6088595731089992032L;
	private JsonResultCode errorCode;
	private String[] codeParam;
	
	public MpException(JsonResultCode errorCode, String message) {
		super(message);
		this.setErrorCode(errorCode);
	}

	public MpException(JsonResultCode errorCode) {
		this.setErrorCode(errorCode);
	}

	public MpException(JsonResultCode errorCode, String message, String... codeParam) {
		super(message);
		this.setErrorCode(errorCode);
		this.setCodeParam(codeParam);
	}
	
	public MpException(Exception e){
		super(e);
	}
	public JsonResultCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(JsonResultCode errorCode) {
		this.errorCode = errorCode;
	}

	public String[] getCodeParam() {
		return codeParam;
	}

	public void setCodeParam(String[] codeParam) {
		this.codeParam = codeParam;
	}
}
