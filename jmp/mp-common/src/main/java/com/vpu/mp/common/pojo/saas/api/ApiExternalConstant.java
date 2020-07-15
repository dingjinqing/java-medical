package com.vpu.mp.common.pojo.saas.api;

/**
 * @author 李晓冰
 * @date 2020年07月15日
 */
public class ApiExternalConstant {
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
