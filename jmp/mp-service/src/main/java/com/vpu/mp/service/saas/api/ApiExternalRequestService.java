package com.vpu.mp.service.saas.api;

import cn.hutool.http.HttpUtil;
import com.vpu.mp.common.foundation.util.Util;
import com.vpu.mp.common.pojo.saas.api.ApiExternalRequestParam;
import com.vpu.mp.common.pojo.saas.api.ApiExternalRequestVo;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.pojo.saas.app.vo.AppAuthVo;
import com.vpu.mp.service.saas.external.AppAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * 请求外部对接平台服务
 * @author 李晓冰
 * @date 2020年07月15日
 */
@Slf4j
@Service
public class ApiExternalRequestService extends MainBaseService {
    @Autowired
    private ApiExternalBaseService apiExternalBaseService;
    @Autowired
    private AppAuthService appAuthService;

    /**
     * 请求外部服务统一调用入口
     * @param appId
     * @param shopId
     * @param serviceName
     * @param requestContentJson
     * @return
     */
    public ApiExternalRequestVo externalRequestGate(String appId, Integer shopId, String serviceName, String requestContentJson) {
        AppAuthVo appAuth = appAuthService.getAppAuth(appId, shopId);
        if (appAuth == null) {
            ApiExternalRequestVo vo = new ApiExternalRequestVo();
            vo.setError(ApiExternalBaseService.ERROR_CODE_NOT_AUTH);
            vo.setMessage("店铺授权信息不存在");
            log.warn("请求外部服务：" + vo.getMessage());
            return vo;
        }
        String curSecond =apiExternalBaseService.getCurSecond().toString();
        String sign = apiExternalBaseService.generateSign(appId,appAuth.getAppSecret(),appAuth.getSessionKey(),serviceName,requestContentJson,curSecond);

        ApiExternalRequestParam requestParam = new ApiExternalRequestParam();
        requestParam.setAppId(appId);
        requestParam.setAppSecret(appAuth.getAppSecret());
        requestParam.setSessionKey(appAuth.getSessionKey());
        requestParam.setCurSecond(curSecond);
        requestParam.setServiceName(serviceName);
        requestParam.setContent(requestContentJson);
        requestParam.setSign(sign);
        return post(appAuth.getRequestLocation(),requestParam);
    }

    /**
     * 真正发送http请求
     * @param location
     * @param requestParam
     */
    private ApiExternalRequestVo post(String location, ApiExternalRequestParam requestParam) {
        HashMap<String, Object> param = new HashMap<>(7);
        param.put("appId", requestParam.getAppId());
        param.put("appSecret", requestParam.getAppSecret());
        param.put("sessionKey", requestParam.getSessionKey());
        param.put("serviceName", requestParam.getServiceName());
        param.put("content", requestParam.getContent());
        param.put("curSecond", requestParam.getContent());
        param.put("sign", requestParam.getSign());
        ApiExternalRequestVo vo = null;
        String post=null;

        try {
            post = HttpUtil.post(location, param);
        } catch (Exception e) {
            log.warn("请求外部服务-网络请求：" + e.getMessage());
            vo = new ApiExternalRequestVo();
            vo.setError(ApiExternalBaseService.ERROR_CODE_NET_ILLEGAL);
            vo.setMessage(e.getMessage());
            return vo;
        }

        try {
            vo = Util.parseJson(post, ApiExternalRequestVo.class);
        } catch (Exception e) {
            log.warn("请求外部服务-解析返回值：" + e.getMessage());
            vo = new ApiExternalRequestVo();
            vo.setError(ApiExternalBaseService.ERROR_CODE_PARSE_RETVAL);
            vo.setMessage(e.getMessage());
        }
        return vo;
    }


}
