package com.vpu.mp.controller;

import cn.hutool.json.JSONUtil;
import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.data.JsonResultCode;
import com.vpu.mp.common.pojo.saas.api.ApiExternalConstant;
import com.vpu.mp.common.pojo.saas.api.ApiExternalGateParam;
import com.vpu.mp.common.pojo.saas.api.ApiExternalRequestParam;
import com.vpu.mp.common.pojo.saas.api.ApiExternalRequestResult;
import com.vpu.mp.controller.admin.AdminBaseController;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.saas.api.ApiExternalBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 孔德成
 * @date 2020/7/17 8:51
 */
@RestController
@Slf4j
public class TestApiExternalGateController extends AdminBaseController {

    @Autowired
    protected  ApiExternalBaseService apiExternalBaseService;

    /**
     * 模拟医院接口
     * @return
     */
    @PostMapping("/test/api/hospital")
    public ApiExternalRequestResult test(ApiExternalRequestParam param){
        String serviceName = param.getServiceName();
        String sign = apiExternalBaseService.generateSign(param.getAppId(),param.getAppSecret(),param.getSessionKey(),serviceName,param.getContent(),param.getCurSecond());
        ApiExternalRequestResult apiExternalRequestResult=new ApiExternalRequestResult();
        if (param.getSign().equals(sign)){
            log.info("解析成功");
            switch (param.getServiceName()){

                case ApiExternalConstant.SERVICE_NAME_FETCH_PATIENT_INFO:
                    log.info("拉取患者信息{}", param.getContent());
                    apiExternalRequestResult.setError(0);
                    apiExternalRequestResult.setMsg("success");
                    apiExternalRequestResult.setData("{\"code\":\"1\",\"name\":\"小明\",\"phone\":\"135\",\"birthday\":\"2020-03-04\",\"state\":1,\"remarks\":\"介绍111\",\"sex\":1,\"age\":13,\"identityType\":1,\"identityNo\":\"411527\",\"visitNo\":\"111\",\"carteVitalNo\":\"112221\"}");
                    return apiExternalRequestResult;
                default:
                    log.info("没有对应服务:[{}]",param.getServiceName());
            }

        }
        apiExternalRequestResult.setError(1);
        apiExternalRequestResult.setMsg("");
        return apiExternalRequestResult;
    }

}
