package com.vpu.mp.common.pojo.saas.api;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 外部请求小程序常量
 * @author 李晓冰
 * @date 2020年07月17日
 */
public class ApiExternalGateConstant {
    /**
     * 服务名称定义
     */
    public static final String ORDER_AUDIT = "orderAudit";


    /**
     * 服务名成集合
     */
    public static final Set<String> SERVICE_NAMES = new HashSet<>(25);

    /**
     * 响应码
     * */
    public static final Integer ERROR_CODE_SUCCESS = 0;
    public static final Integer ERROR_LACK_PARAM = 2000;
    public static final Integer ERROR_CODE_INVALID_SIGN = 3001;
    public static final Integer ERROR_CODE_OVERTIME = 3002;
    public static final Integer ERROR_CODE_INVALID_TIMESTAMP = 3003;
    public static final Integer ERROR_CODE_INVALID_SHOP = 3004;
    public static final Integer ERROR_CODE_INVALID_APP = 3005;
    public static final Integer ERROR_CODE_INVALID_SERVICE = 3006;
    public static final Integer ERROR_CODE_SYNC_FAIL = 4001;
    public static final Integer ERROR_SYSTEM_FAIL = 9999;

    /**
     * 响应码信息
     */
    public static final String ERROR_CODE_SUCCESS_MSG = "成功";
    public static final String ERROR_LACK_PARAM_MSG = "缺少参数";
    public static final String ERROR_CODE_INVALID_SIGN_MSG = "无效签名";
    public static final String ERROR_CODE_OVERTIME_MSG = "请求超时";
    public static final String ERROR_CODE_INVALID_TIMESTAMP_MSG = "无效时间戳";
    public static final String ERROR_CODE_INVALID_SHOP_MSG = "无效店铺";
    public static final String ERROR_CODE_INVALID_APP_MSG = "未获得商家授权";
    public static final String ERROR_CODE_INVALID_SERVICE_MSG = "无效服务";
    public static final String ERROR_CODE_SYNC_FAIL_MSG = "同步信息失败";
    public static final String ERROR_SYSTEM_FAIL_MSG = "系统错误";

    /**
     * app_id码值定义
     */
    public static final String APP_ID_ERP = "200000";
    public static final String APP_ID_POS = "200001";
    public static final String APP_ID_CRM = "200002";
    public static final String APP_ID_HIS = "200003";
    public static final String APP_ID_STORE = "200004";
    public static final Map<String, String> APP_NAMES = new HashMap<>(3);

    static {
        // 初始化appid和name的映射
        APP_NAMES.put(APP_ID_ERP, "ERP");
        APP_NAMES.put(APP_ID_POS, "POS");
        APP_NAMES.put(APP_ID_CRM, "CRM");
        APP_NAMES.put(APP_ID_HIS, "HIS");

        // 初始化服务名集合
        SERVICE_NAMES.add(ORDER_AUDIT);
    }

}
