package com.vpu.mp.service.saas.api;

import cn.hutool.crypto.SecureUtil;
import com.vpu.mp.common.foundation.util.Util;
import com.vpu.mp.config.ApiExternalConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author 李晓冰
 * @date 2020年07月15日
 */
@Service
public class ApiExternalBaseService {
    @Autowired
    ApiExternalConfig apiExternalConfig;
    /**
     * 生成对应的签名
     * @param appId
     * @param appSecret
     * @param sessionKey
     * @param serviceName 请求服务方法名称
     * @param content 请求体内容
     * @param curSecond 当前时间 秒为单位 字符串
     * @return
     */
    protected String generateSign(String appId,String appSecret,String sessionKey,String serviceName,String content,String curSecond) {
        List<String> list = new ArrayList<>(10);
        list.add(appId);
        list.add(appSecret);
        list.add(sessionKey);
        list.add(serviceName);
        list.add(content);
        list.sort(Comparator.naturalOrder());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i != list.size() - 1) {
                sb.append("&");
            }
        }
        String s = SecureUtil.md5(sb.toString());
        s = s + curSecond + apiExternalConfig.getSignKey();
        s = Util.md5(s);
        return s;
    }

    /**
     * 当前时间，秒
     * @return
     */
    protected Long getCurSecond() {
        return System.currentTimeMillis() / 1000;
    }


    /**
     * app_id码值定义
     */
    public static final String APP_ID_ERP = "200000";
    public static final String APP_ID_POS = "200001";
    public static final String APP_ID_CRM = "200002";
    public static final String APP_ID_HIS = "200003";

    /**
     * 响应码
     */
    /**成功*/
    public static final Integer ERROR_CODE_SUCCESS = 0;
    /**店铺未配置相应权限*/
    public static final Integer ERROR_CODE_NOT_AUTH = 1001;
    /**解析返回值异常*/
    public static final Integer ERROR_CODE_PARSE_RETVAL = 1002;
    /**网络请求失败*/
    public static final Integer ERROR_CODE_NET_ILLEGAL = 2001;

    /**
     * 可用方法名
     */
    public static final String SERVICE_NAEM_FETCH_MEDICAL_INFOS = "fetchMedicalInfos";
}
