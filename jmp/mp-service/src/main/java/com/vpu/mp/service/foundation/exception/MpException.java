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

	public MpException(JsonResultCode errorCode, String message) {
		super(message);
		this.setErrorCode(errorCode);
	}

	public MpException(JsonResultCode errorCode) {
		this.setErrorCode(errorCode);
	}

	public JsonResultCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(JsonResultCode errorCode) {
		this.errorCode = errorCode;
	}
}
