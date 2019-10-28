package com.vpu.mp.service.foundation.exception;

import com.vpu.mp.service.foundation.data.JsonResultCode;

/**
 * @author liufei
 * @date
 */
public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = -8931074513310486813L;
    String errorMessage;
	JsonResultCode code;

    public BusinessException(JsonResultCode code){
        this.code = code;
    }

    public BusinessException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public JsonResultCode getCode(){
        return code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
