package com.vpu.mp.service.foundation.exception;

import com.vpu.mp.service.foundation.data.JsonResultCode;

/**
 * @author liufei
 * @date 9/17/2019
 */
public class BusinessException extends RuntimeException {
    JsonResultCode code;

    public BusinessException(JsonResultCode code){
        this.code = code;
    }

    public JsonResultCode getCode(){
        return code;
    }
}
