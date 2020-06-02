package com.vpu.mp.controller.system;

import com.vpu.mp.config.ApiExternalGateConfig;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.saas.api.ApiExternalGateParam;
import com.vpu.mp.service.pojo.saas.api.ApiJsonResult;
import com.vpu.mp.service.saas.api.ApiExternalGateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 小程序对接POS,ERP服务接口
 * @author 李晓冰
 * @date 2020年03月30日
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class ApiExternalGateController extends ShopBaseService {

    @Autowired
    private ApiExternalGateService gateService;

    @PostMapping("/service/gateWay")
    public ApiJsonResult gateWay(ApiExternalGateParam param){
        try {
            // 日志请求记录
            apiCallLog(param);

            // 必要系统参数验证
            String nullKey = gateService.checkSystemParam(param);
            if (nullKey != null) {
                return response(ApiExternalGateConfig.ERROR_LACK_PARAM,ApiExternalGateConfig.ERROR_LACK_PARAM_MSG+"："+nullKey);
            }

            // 时间戳验证
            if (!gateService.checkTimeStamp(param)) {
                return response(ApiExternalGateConfig.ERROR_CODE_INVALID_TIMESTAMP,ApiExternalGateConfig.ERROR_CODE_INVALID_TIMESTAMP_MSG);
            }

            // 验证签名
            if (!gateService.checkSign(param)) {
                return response(ApiExternalGateConfig.ERROR_CODE_INVALID_SIGN,ApiExternalGateConfig.ERROR_CODE_INVALID_SIGN_MSG);
            }

            // 解析shopId
            Integer shopId = gateService.parseShopId(param);
            if (shopId == -1) {
                return response(ApiExternalGateConfig.ERROR_CODE_INVALID_SHOP,ApiExternalGateConfig.ERROR_CODE_INVALID_SHOP_MSG);
            }
            param.setShopId(shopId);

            if (!gateService.isSmsPlatformApi(param.getSession_key())) {
                // 验证店铺
                if (!gateService.checkShop(param)) {
                    return response(ApiExternalGateConfig.ERROR_CODE_INVALID_SHOP,ApiExternalGateConfig.ERROR_CODE_INVALID_SHOP_MSG);
                }

                // 验证应用店铺授权
                if (!gateService.checkAppAuth(param)) {
                    return response(ApiExternalGateConfig.ERROR_CODE_INVALID_APP,ApiExternalGateConfig.ERROR_CODE_INVALID_APP_MSG);
                }
            } else {
                // TODO:  短信平台解析账号
            }

            if (!gateService.checkService(param)) {
                return response(ApiExternalGateConfig.ERROR_CODE_INVALID_SERVICE,ApiExternalGateConfig.ERROR_CODE_INVALID_SERVICE_MSG);
            }

            ApiJsonResult result = gateService.serviceFunCall(param);
            responseLog(result);
            return result;
        } catch (Exception e) {
           log.error("servcie gateWay error:"+e.getMessage());
           return response(ApiExternalGateConfig.ERROR_SYSTEM_FAIL,ApiExternalGateConfig.ERROR_SYSTEM_FAIL_MSG);
        }
    }

    /**
     * 临时测试使用
     * @param param
     * @return
     */
    @PostMapping("/service/test")
    public ApiJsonResult test(@RequestBody ApiExternalGateParam param){
        ApiJsonResult result = gateService.serviceFunCall(param);
        responseLog(result);
        return result;
    }

    /**
     * 统一返回信息出口
     * @param errorCode 错误码
     * @param errorMsg 错误信息
     * @return {@link ApiJsonResult}
     */
    private ApiJsonResult response(Integer errorCode, String errorMsg){
        ApiJsonResult apiJsonResult = new ApiJsonResult();
        apiJsonResult.setCode(errorCode);
        apiJsonResult.setMsg(errorMsg);
        responseLog(apiJsonResult);
        return apiJsonResult;
    }

    /**
     * 统一日志
     * @param apiJsonResult 接口返回信息
     */
    private void responseLog(ApiJsonResult apiJsonResult){
        log.info("service api response："+ Util.toJson(apiJsonResult));
    }

    private void apiCallLog(ApiExternalGateParam param) {
        log.info("service api call："+Util.toJson(param));
    }
}
