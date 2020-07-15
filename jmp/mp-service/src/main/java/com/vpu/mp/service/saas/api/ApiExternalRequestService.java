package com.vpu.mp.service.saas.api;

import cn.hutool.http.HttpUtil;
import com.vpu.mp.common.foundation.util.Util;
import com.vpu.mp.common.pojo.saas.api.ApiExternalRequestParam;
import com.vpu.mp.common.pojo.saas.api.ApiExternalRequestVo;
import com.vpu.mp.config.ApiExternalRequestConfig;
import com.vpu.mp.db.main.tables.records.AppAuthRecord;
import com.vpu.mp.service.foundation.service.MainBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import static com.vpu.mp.db.main.Tables.APP;
import static com.vpu.mp.db.main.tables.AppAuth.APP_AUTH;

/**
 * 请求外部对接平台服务
 * @author 李晓冰
 * @date 2020年07月15日
 */
@Slf4j
@Service
public class ApiExternalRequestService extends MainBaseService {
    @Autowired
    private ApiExternalRequestConfig config;

    /**
     * 请求外部服务统一调用入口
     * @param appId
     * @param shopId
     * @param serviceName
     * @param requestContentJson
     * @return
     */
    public ApiExternalRequestVo externalRequestGate(String appId, Integer shopId, String serviceName, String requestContentJson) {
        AppAuthRecord appAuth = getAppAuth(shopId, appId);
        if (appAuth == null) {
            ApiExternalRequestVo vo = new ApiExternalRequestVo();
            vo.setError(ApiExternalRequestConfig.ERROR_CODE_NOT_AUTH);
            vo.setMessage("店铺授权信息不存在");
            log.warn("请求外部服务：" + vo.getMessage());
            return vo;
        }
        ApiExternalRequestParam requestParam = new ApiExternalRequestParam();
        requestParam.setAppId(appId);
        requestParam.setAppSecret(getAppSecret(appId));
        requestParam.setSessionKey(appAuth.getSessionKey());
        requestParam.setCurSecond(getCurSecond().toString());
        requestParam.setServiceName(serviceName);
        requestParam.setContent(requestContentJson);
        requestParam.setSign(generateSign(requestParam));

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
            vo.setError(ApiExternalRequestConfig.ERROR_CODE_NET_ILLEGAL);
            vo.setMessage(e.getMessage());
            return vo;
        }

        try {
            vo = Util.parseJson(post, ApiExternalRequestVo.class);
        } catch (Exception e) {
            log.warn("请求外部服务-解析返回值：" + e.getMessage());
            vo = new ApiExternalRequestVo();
            vo.setError(ApiExternalRequestConfig.ERROR_CODE_PARSE_RETVAL);
            vo.setMessage(e.getMessage());
        }
        return vo;
    }

    /**
     * 生成对应的签名
     * @param requestParam
     * @return
     */
    private String generateSign(ApiExternalRequestParam requestParam) {
        List<String> list = new ArrayList<>(10);
        list.add(requestParam.getAppId());
        list.add(requestParam.getAppSecret());
        list.add(requestParam.getSessionKey());
        list.add(requestParam.getServiceName());
        list.add(requestParam.getContent());
        list.sort(Comparator.naturalOrder());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i != list.size() - 1) {
                sb.append("&");
            }
        }
        String s = Util.md5(sb.toString());
        s = s + requestParam.getCurSecond() + config.getSignKey();
        s = Util.md5(s);
        return s;
    }

    private Long getCurSecond() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 临时
     * @param appId
     * @return
     */
    private String getAppSecret(String appId) {
        return db().select(APP.APP_SECRET).from(APP).where(APP.APP_ID.eq(appId)).fetchAny(APP.APP_ID);
    }

    /**
     * 临时写在这里
     * @param shopId
     * @param appId
     */
    private AppAuthRecord getAppAuth(Integer shopId, String appId) {
        AppAuthRecord appAuthRecord = db().selectFrom(APP_AUTH).where(APP_AUTH.SHOP_ID.eq(shopId).and(APP_AUTH.APP_ID.eq(appId)).and(APP_AUTH.STATUS.eq((byte) 1))).fetchAny();
        return appAuthRecord;
    }
}
