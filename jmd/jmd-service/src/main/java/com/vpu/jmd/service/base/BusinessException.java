package com.vpu.jmd.service.base;

import com.vpu.jmd.database.JsonResultCode;
import lombok.Getter;

/**
 * @author liufei
 * @date
 */
@Getter
public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = -8931074513310486813L;
    String errorMessage;
	JsonResultCode code;
    Object[] args;

    public BusinessException(JsonResultCode code){
        this.code = code;
    }

    public BusinessException(JsonResultCode code, Object... args) {
        this.code = code;
        this.args = args;
    }

    public BusinessException(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
