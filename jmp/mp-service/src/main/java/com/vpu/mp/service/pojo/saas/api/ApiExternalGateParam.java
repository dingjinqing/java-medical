package com.vpu.mp.service.pojo.saas.api;

import lombok.Data;

/**
 * 小程序-对接Pos,Erp服务接口入参
 * 目前接口参数和php完全一致，避免pos、erp进行二次开发
 * @author 李晓冰
 * @date 2020年03月30日
 */
@Data
public class ApiExternalGateParam {
    /**
     * 固定值
     * 1> ERP服务:
     *         app_id: 200000  app_secret:36a23c125e6fd10420eb3b6ed48ee057
     * 2> POS服务
     *         app_id: 200001  app_secret:b141fbc4bd328822e955aeed011cfc85
     * 3> CRM服务
     *         app_id: 200002  app_secret:2b7212759813c4c03ccc316f8cb1c654
     * */
    private String app_id;
    private String app_secret;
    /**
     * 小程序授权第三方平台对接时生成的sessionKey,
     * 目前仿照php写法，sessionKey最后一个s字符后面的字符串表示店铺id
     * */
    private String session_key;
    /**
     * 服务名称
     * */
    private String service_name;
    private String timestamp;
    private String content;
    private String sign;


    private Integer shopId;
    private String requestId;
}
