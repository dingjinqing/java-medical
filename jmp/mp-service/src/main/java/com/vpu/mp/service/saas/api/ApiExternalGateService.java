package com.vpu.mp.service.saas.api;

import com.vpu.mp.config.ApiExternalGateConfig;
import com.vpu.mp.db.main.tables.records.AppAuthRecord;
import com.vpu.mp.db.main.tables.records.AppRecord;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.saas.api.ApiExternalGateParam;
import com.vpu.mp.service.pojo.saas.api.ApiJsonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 小程序对接POS,ERP服务service层
 *
 * @author 李晓冰
 * @date 2020年03月30日
 */
@Service
@Slf4j
public class ApiExternalGateService extends MainBaseService {
    @Autowired
    private ApiExternalGateConfig config;

    /**
     * 验证系统级参数
     * 'app_id', 'app_secret', 'session_key', 'service_name'
     *
     * @param param {@link ApiExternalGateParam}
     * @return null 表示必要字段都存在，否则代表第一个空字段的参数名
     */
    public String checkSystemParam(ApiExternalGateParam param) {
        String nullKey = null;
        if (StringUtils.isBlank(param.getApp_id())) {
            nullKey = "app_id";
        } else if (StringUtils.isBlank(param.getApp_secret())) {
            nullKey = "app_secret";
        } else if (StringUtils.isBlank(param.getSession_key())) {
            nullKey = "session_key";
        } else if (StringUtils.isBlank(param.getService_name())) {
            nullKey = "service_name";
        } else {
            nullKey = null;
        }
        if (nullKey != null) {
            logPrinter(param.getApp_id(), ApiExternalGateConfig.ERROR_LACK_PARAM_MSG + ":" + nullKey);
        }
        return nullKey;
    }

    /**
     * 校验接口调用时间
     *
     * @param param {@link ApiExternalGateParam}
     * @return false时间参数不合法 可能是null或者当前时间和改时间差超过30秒，true
     */
    public boolean checkTimeStamp(ApiExternalGateParam param) {
        if (param.getTimestamp() == null) {
            logPrinter(param.getApp_id(), "timestamp 为空");
            return false;
        }
        Timestamp timestamp = DateUtil.dateFormatToTimeStamp(DateUtil.DATE_FORMAT_API_EXTERNAL, param.getTimestamp());
        Timestamp now = DateUtil.getLocalDateTime();
        // 大于30秒
        if (now.getTime() - timestamp.getTime() > 30000) {
            logPrinter(param.getApp_id(), "timestamp 超时");
            return false;
        }
        return true;
    }

    /**
     * 校验签名是否正确
     *
     * @param param {@link ApiExternalGateParam}
     * @return true合法 false 错误
     */
    public boolean checkSign(ApiExternalGateParam param) {
        List<String> list = new ArrayList<>(5);
        list.add(param.getApp_id());
        list.add(param.getApp_secret());
        list.add(param.getSession_key());
        list.add(param.getService_name());
        list.add(param.getContent());
        list.sort(Comparator.naturalOrder());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i != list.size() - 1) {
                sb.append("&");
            }
        }
        String s = Util.md5(sb.toString());
        s = s + param.getTimestamp() + config.getSignKey();
        s = Util.md5(s);
        if (!s.equals(param.getSign())) {
            logPrinter(param.getApp_id(), "签名错误");
            return false;
        } else {
            return true;
        }
    }

    /**
     * 根据sessionKey解析shopId 最后一个s字符后面的字符表示shopId
     *
     * @param param {@link ApiExternalGateParam}
     * @return shopId -1 解析错误
     */
    public Integer parseShopId(ApiExternalGateParam param) {
        @NotNull String session_key = param.getSession_key();
        boolean ok = Pattern.matches(".*s\\d+", session_key);
        if (!ok) {
            return -1;
        }
        int si = session_key.lastIndexOf('s');
        String shopIdStr = session_key.substring(si + 1);
        return Integer.parseInt(shopIdStr);
    }

    /**
     * 验证店铺是否有效
     *
     * @param param {@link ApiExternalGateParam}
     * @return true有效， false无效
     */
    public boolean checkShop(ApiExternalGateParam param) {
        if (param.getShopId() == null) {
            return false;
        }
        ShopRecord shop = saas.shop.getShopById(param.getShopId());
        if (shop == null) {
            logPrinter(param.getApp_id(), "店铺id无效");
            return false;
        }
        return true;
    }

    /**
     * 验证店铺是否存在有效授权
     *
     * @param param
     * @return true有，false授权失效
     */
    public boolean checkAppAuth(ApiExternalGateParam param) {
        AppRecord appInfo = saas.shop.shopApp.getAppInfo(param.getApp_id(), param.getApp_secret());
        if (appInfo == null) {
            logPrinter(param.getApp_id(), "数据错误 appid：" + param.getApp_id() + "appSecret：" + param.getApp_secret());
            return false;
        }

        AppAuthRecord appAuthRecord = saas.shop.shopApp.getAppAuthInfoBySessionKey(param.getSession_key());
        if (appAuthRecord == null) {
            logPrinter(param.getApp_id(), "店铺无有效授权，sessionKey：" + param.getSession_key());
            return false;
        }
        return true;
    }

    /**
     * 判断是否是短信平台的请求
     *
     * @param sessionKey
     * @return
     */
    public boolean isSmsPlatformApi(String sessionKey) {
        return config.getSmsPlatformKey().equals(sessionKey);
    }


    /**
     * 判断请求的服务名称是否有效
     *
     * @param param {@link ApiExternalGateParam}
     * @return true有效服务 false无效服务
     */
    public boolean checkService(ApiExternalGateParam param) {
        if (ApiExternalGateConfig.E_INTERCEPTOR_SERVICE_NAMES.contains(param.getService_name())
            && !config.getSmsPlatformKey().equals(param.getSession_key())) {
            return false;
        }

        if (!ApiExternalGateConfig.SERVICE_NAMES.contains(param.getService_name())) {
            return false;
        }
        return true;
    }


    /**
     * 转发调用具体的服务提供方法
     *
     * @param param {@link ApiExternalGateParam}
     * @return 服务返回结果
     */
    public ApiJsonResult serviceFunCall(ApiExternalGateParam param) {
        String service_name = param.getService_name();
        ApiJsonResult apiJsonResult = null;
        switch (service_name) {
            case ApiExternalGateConfig.SERVICE_GOODS_LIST:
                apiJsonResult = goodsList(param);
                break;
            default:
                apiJsonResult = new ApiJsonResult();
                apiJsonResult.setCode(ApiExternalGateConfig.ERROR_CODE_INVALID_SERVICE);
                apiJsonResult.setMsg(ApiExternalGateConfig.ERROR_CODE_INVALID_SERVICE_MSG);
        }
        return apiJsonResult;
    }

    private ApiJsonResult goodsList(ApiExternalGateParam gateParam) {
        // param.getContent() 反序列化为对应的类型 =>GoodsListParam param
        // saas.getShopApp(gateParam.getShopId()).goods.goodsList(param)
        // 需要将成功数据或者错误情况的 errorCode，errorMsg data 封装成对应的ApiJsonResult返回

        return new ApiJsonResult();
    }


    private void logPrinter(String app_id, String msg) {
        log.error("数据同步接口：" + ApiExternalGateConfig.APP_NAMES.get(app_id) + "：" + msg);
    }
}
