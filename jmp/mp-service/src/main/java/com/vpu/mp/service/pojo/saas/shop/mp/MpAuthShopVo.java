package com.vpu.mp.service.pojo.saas.shop.mp;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class MpAuthShopVo {

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
    private String    authorizerInfo;
    private String    authorizationInfo;
    private String    payMchId;
    private String    payKey;
    private String    payCertContent;
    private String    payKeyContent;
    private Byte      isModifyDomain;
    private Integer   bindTemplateId;
    private Byte      uploadState;
    private Timestamp lastUploadTime;
    private Integer   auditId;
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
    private String    unionPayAppId;
    private String    unionPayCusId;
    private String    unionPayAppKey;

}
