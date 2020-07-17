package com.vpu.mp.common.pojo.saas.api;

/**
 * @author 李晓冰
 * @date 2020年07月15日
 */
public class ApiExternalRequestConstant {
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
    /**请求错误*/
    public static final Integer ERROR_CODE = 1;
    /**店铺未配置相应权限*/
    public static final Integer ERROR_CODE_NOT_AUTH = 1001;
    /**解析返回值异常*/
    public static final Integer ERROR_CODE_PARSE_RETVAL = 2001;
    /**网络请求失败*/
    public static final Integer ERROR_CODE_NET_ILLEGAL = 2002;

    /**
     * 服务方法名称
     */
    /**拉取患者信息*/
    public static final String SERVICE_NAME_FETCH_PATIENT_INFO = "fetchPatientInfo";
    /**拉取药品信息*/
    public static final String SERVICE_NAME_FETCH_MEDICAL_INFOS = "fetchMedicalInfos";
    /**同步订单药品信息*/
    public static final String SERVICE_NAME_SYNC_MEDICAL_ORDER_STATUS = "syncMedicalOrderStatus";
    /**同步处方信息*/
    public static final String SERVICE_NAME_FETCH_PRESCRIPTION_INFOS= "fetchPrescriptionInfos";
    /**同步处方详情*/
    public static final String SERVICE_NAME_FETCH_PRESCRIPTION_DETAIL= "fetchPrescriptionDetail";
    /**上传订单处方信息*/
    public static final String SERVICE_NAME_UPLOAD_ORDER_PRESCRIPTION= "uploadOrderPrescription";
    /**同步医生信息*/
    public static final String SERVICE_NAME_FETCH_DOCTOR_INFOS=  "fetchDoctorInfos";
    /**同步科室信息*/
    public static final String SERVICE_NAME_FETCH_DEPARTMENT_INFOS  = "fetchDepartmentInfos";
    /**同步医师职称信息*/
    public static final String SERVICE_NAME_FETCH_DOCTOR_TITLE_INFOS=  "fetchDoctorTitleInfos";
}
