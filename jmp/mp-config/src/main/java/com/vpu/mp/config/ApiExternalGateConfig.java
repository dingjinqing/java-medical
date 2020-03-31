package com.vpu.mp.config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author 李晓冰
 * @date 2020年03月30日
 */
@Configuration
@Data
public class ApiExternalGateConfig {

//    @Value(value = "${api.external.service.sign.key}")
//    private String signKey;
//    @Value(value = "${api.external.service.sms.platform.key}")
//    private String smsPlatformKey;

    /**
     * 服务名称定义
     */
    public static final String SERVICE_GOODS_LIST = "goods_list";
    public static final String SERVICE_SINGLE_GOODS = "single_goods";
    public static final String SERVICE_ORDER_LIST = "order_list";
    public static final String SERVICE_SINGLE_ORDER = "single_order";
    public static final String SERVICE_SYNC_STOCK = "sync_stock";
    public static final String SERVICE_SYNC_LOGISTICS = "sync_logistics";
    public static final String SERVICE_SEND_MESSAGE_BATCH = "send_message_batch";
    public static final String SERVICE_SEND_MESSAGE = "send_message";
    public static final String SERVICE_MOBILE_LIST = "mobile_list";
    public static final String SERVICE_REFUND_ORDER_LIST = "refund_order_list";
    public static final String SERVICE_REFUND_ORDER = "refund_order";
    public static final String SERVICE_SYNC_REFUND_ORDER_RESULT = "sync_refund_result";
    public static final String SERVICE_DELIVER_EXCHANGE_GOODS = "deliver_exchange_goods";
    public static final String SERVICE_POS_SYNC_STOCK = "pos_sync_stock";
    public static final String SERVICE_POS_SYNC_PRODUCT = "pos_sync_product";
    public static final String SERVICE_POS_VERIFY_ORDER = "pos_verify_order";
    public static final String SERVICE_POS_RETURN_GOODS = "pos_return_goods";
    public static final String SERVICE_CRM_SYNC_SCORE = "crm_sync_score";
    public static final String SERVICE_SYNC_USER = "sync_user";
    public static final String SERVICE_CRM_GOODS_SHARE_CODE = "goods_share_code";
    public static final String SERVICE_OPEN_APP_ID = "open_app_id";
    public static final String SERVICE_CREATE_COUPON = "create_coupon";
    public static final String SERVICE_GRANT_COUPON = "grant_coupon";
    public static final String SERVICE_SYNC_COUPON = "sync_coupon";
    public static final String SERVICE_QUERY_COUPON = "query_coupon";

    /**
     * 服务名成集合
     */
    public static final Set<String> SERVICE_NAMES = new HashSet<>(25);
    /**
     * 易快帮需要特殊拦截的服务
     */
    public static final Set<String> E_INTERCEPTOR_SERVICE_NAMES = new HashSet<>(3);

    /**
     * 响应码
     * */
    public static final Integer ERROR_CODE_SUCCESS = 1000;
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
    public static final Map<String, String> APP_NAMES = new HashMap<>(3);

    static {
        // 初始化appid和name的映射
        APP_NAMES.put(APP_ID_ERP, "ERP");
        APP_NAMES.put(APP_ID_POS, "POS");
        APP_NAMES.put(APP_ID_CRM, "CRM");

        // 初始化服务名集合
        SERVICE_NAMES.add(SERVICE_GOODS_LIST);
        SERVICE_NAMES.add(SERVICE_SINGLE_GOODS);
        SERVICE_NAMES.add(SERVICE_ORDER_LIST);
        SERVICE_NAMES.add(SERVICE_SINGLE_ORDER);
        SERVICE_NAMES.add(SERVICE_SYNC_STOCK);
        SERVICE_NAMES.add(SERVICE_SYNC_LOGISTICS);
        SERVICE_NAMES.add(SERVICE_SEND_MESSAGE_BATCH);
        SERVICE_NAMES.add(SERVICE_SEND_MESSAGE);
        SERVICE_NAMES.add(SERVICE_MOBILE_LIST);
        SERVICE_NAMES.add(SERVICE_REFUND_ORDER_LIST);
        SERVICE_NAMES.add(SERVICE_REFUND_ORDER);
        SERVICE_NAMES.add(SERVICE_SYNC_REFUND_ORDER_RESULT);
        SERVICE_NAMES.add(SERVICE_DELIVER_EXCHANGE_GOODS);
        SERVICE_NAMES.add(SERVICE_POS_SYNC_STOCK);
        SERVICE_NAMES.add(SERVICE_POS_SYNC_PRODUCT);
        SERVICE_NAMES.add(SERVICE_POS_VERIFY_ORDER);
        SERVICE_NAMES.add(SERVICE_POS_RETURN_GOODS);
        SERVICE_NAMES.add(SERVICE_CRM_SYNC_SCORE);
        SERVICE_NAMES.add(SERVICE_SYNC_USER);
        SERVICE_NAMES.add(SERVICE_CRM_GOODS_SHARE_CODE);
        SERVICE_NAMES.add(SERVICE_OPEN_APP_ID);
        SERVICE_NAMES.add(SERVICE_CREATE_COUPON);
        SERVICE_NAMES.add(SERVICE_GRANT_COUPON);
        SERVICE_NAMES.add(SERVICE_SYNC_COUPON);
        SERVICE_NAMES.add(SERVICE_QUERY_COUPON);

        //易快帮许屏蔽服务初始化
        E_INTERCEPTOR_SERVICE_NAMES.add(SERVICE_SEND_MESSAGE_BATCH);
        E_INTERCEPTOR_SERVICE_NAMES.add(SERVICE_SEND_MESSAGE);
        E_INTERCEPTOR_SERVICE_NAMES.add(SERVICE_MOBILE_LIST);
    }

}
