package com.vpu.mp.service.foundation.exception;

import com.vpu.mp.service.foundation.data.JsonResultCode;

/**
 * @author 
 * @date 
 */
public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = -8931074513310486813L;
	JsonResultCode code;

    public BusinessException(JsonResultCode code){
        this.code = code;
    }

    public JsonResultCode getCode(){
        return code;
    }
}
