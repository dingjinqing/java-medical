package com.vpu.mp.service.pojo.saas.shop.mp;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * 
 * @author zhaojianqiang
 *
 * 2019年8月12日 下午5:45:41
 */
@Data
public class MpAuthShopToAdminVo {

    private String    appId;
    private Integer   shopId;
    private String    nickName;
    private String    userName;
    private String    alias;
    private String    verifyTypeInfo;
    private String    headImg;
    private String    funcInfo;
    private Byte      isAuthOk;
    private Timestamp lastAuthTime;
    private Timestamp lastCancelAuthTime;
    private Timestamp createTime;
    private String    qrcodeUrl;
    private Byte      openPay;
    private Byte      openCard;
    @JsonIgnore
    private String    authorizerInfo;
    @JsonIgnore
    private String    authorizationInfo;
    @JsonIgnore
    private String    payMchId;
    @JsonIgnore
    private String    payKey;
    @JsonIgnore
    private String    payCertContent;
    @JsonIgnore
    private String    payKeyContent;
    private Byte      isModifyDomain;
    private Integer   bindTemplateId;
    private Byte      uploadState;
    private Timestamp lastUploadTime;
    private Long   auditId;
    private Byte      auditState;
    private Timestamp submitAuditTime;
    private Timestamp auditOkTime;
    private String    auditFailReason;
    private Byte      publishState;
    private Timestamp publishTime;
    private String    tester;
    private String    testQrPath;
    private String    category;
    private String    pageCfg;
    private String    principalName;
    private String    bindOpenAppId;
    private String    linkOfficialAppId;
    private Byte      isSubMerchant;
    @JsonIgnore
    private String    unionPayAppId;
    @JsonIgnore
    private String    unionPayCusId;
    @JsonIgnore
    private String    unionPayAppKey;

}
