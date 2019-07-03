/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.service.pojo.saas.shop;


import java.sql.Timestamp;


import lombok.Data;

// TODO: 需根据情况删除无用字段
@Data
public class ShopAccountPojo {
//    private Integer   sysId;
    private String    userName;
    private String    password;
    private Byte      state;
    private Byte      businessState;
    private Byte      shopGrade;
    private Integer   maxSkuNum;
    private Integer   maxShopNum;
    private Timestamp addTime;
    private Timestamp buyTime;
    private Timestamp endTime;
    private Integer   lastLoginShopId;
    private String    mobile;
    private String    accountName;
    private String    shopAvatar;
    private String    company;
    private String    salesperson;
    private String    provinceCode;
    private String    cityCode;
    private String    districtCode;
    private String    address;
    private Byte      baseSale;
    private String    backlog;
    private Byte      addCommentSwitch;
    private String    officialOpenId;
    private Byte      isBind;

}
