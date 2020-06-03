package com.vpu.mp.service.pojo.saas.api;

import com.vpu.mp.config.ApiExternalGateConfig;
import lombok.Data;

/**
 * 小程序-对接Pos,Erp服务接口返回数据
 * @author 李晓冰
 * @date 2020年03月30日
 */
@Data
public class ApiJsonResult {
    private Integer code = ApiExternalGateConfig.ERROR_CODE_SUCCESS;
    private String msg = ApiExternalGateConfig.ERROR_CODE_SUCCESS_MSG;
    private Object data = new Object[]{};

    public ApiJsonResult() {
    }

    public ApiJsonResult(Object data) {
        this.data = data;
    }
    public ApiJsonResult(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
