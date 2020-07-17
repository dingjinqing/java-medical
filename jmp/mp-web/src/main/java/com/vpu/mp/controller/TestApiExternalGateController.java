package com.vpu.mp.controller;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.pojo.saas.api.ApiExternalGateParam;
import com.vpu.mp.common.pojo.saas.api.ApiExternalRequestParam;
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
public class TestApiExternalGateController extends ShopBaseService {

    @Autowired
    protected  ApiExternalBaseService apiExternalBaseService;

    /**
     * 模拟医院接口
     * @return
     */
    @PostMapping("/test/api/hospital")
    public JsonResult test(@RequestBody ApiExternalRequestParam param){
        String serviceName = param.getServiceName();

        String sign = apiExternalBaseService.generateSign(param.getAppId(),param.getAppSecret(),param.getSessionKey(),serviceName,param.getContent(),param.getCurSecond());
        if (param.equals(sign)){
            log.info("解析成功");
        }



        return JsonResult.success();
    }

}
