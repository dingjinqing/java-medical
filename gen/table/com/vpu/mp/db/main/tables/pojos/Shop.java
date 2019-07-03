/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.main.tables.pojos;


import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Shop implements Serializable {

    private static final long serialVersionUID = 1657321501;

    private Integer    shopId;
    private Integer    sysId;
    private String     mobile;
    private String     receiveMobile;
    private String     shopName;
    private String     shopAvatar;
    private String     shopBgPath;
    private String     shopPhone;
    private String     shopNotice;
    private String     shopWx;
    private String     shopEmail;
    private Timestamp  created;
    private Byte       isEnabled;
    private Integer    provinceCode;
    private String     provinceName;
    private Integer    cityCode;
    private String     cityName;
    private Integer    districtCode;
    private String     districtName;
    private String     address;
    private String     completeAddress;
    private Integer    shopSellType;
    private String     shopQq;
    private String     lastLoginIp;
    private Byte       state;
    private Byte       businessState;
    private BigDecimal manageFee;
    private BigDecimal surplus;
    private String     dbConfig;
    private String     shopType;
    private String     versionConfig;
    private Byte       shopFlag;
    private String     memberKey;
    private String     tenancyName;
    private String     userName;
    private String     password;
    private String     smsAccount;
    private Byte       kuajinggou;
    private Byte       orderRealName;
    private Byte       hidBottom;
    private String     shopStyle;
    private String     logo;
    private String     shareConfig;

    public Shop() {}

    public Shop(Shop value) {
        this.shopId = value.shopId;
        this.sysId = value.sysId;
        this.mobile = value.mobile;
        this.receiveMobile = value.receiveMobile;
        this.shopName = value.shopName;
        this.shopAvatar = value.shopAvatar;
        this.shopBgPath = value.shopBgPath;
        this.shopPhone = value.shopPhone;
        this.shopNotice = value.shopNotice;
        this.shopWx = value.shopWx;
        this.shopEmail = value.shopEmail;
        this.created = value.created;
        this.isEnabled = value.isEnabled;
        this.provinceCode = value.provinceCode;
        this.provinceName = value.provinceName;
        this.cityCode = value.cityCode;
        this.cityName = value.cityName;
        this.districtCode = value.districtCode;
        this.districtName = value.districtName;
        this.address = value.address;
        this.completeAddress = value.completeAddress;
        this.shopSellType = value.shopSellType;
        this.shopQq = value.shopQq;
        this.lastLoginIp = value.lastLoginIp;
        this.state = value.state;
        this.businessState = value.businessState;
        this.manageFee = value.manageFee;
        this.surplus = value.surplus;
        this.dbConfig = value.dbConfig;
        this.shopType = value.shopType;
        this.versionConfig = value.versionConfig;
        this.shopFlag = value.shopFlag;
        this.memberKey = value.memberKey;
        this.tenancyName = value.tenancyName;
        this.userName = value.userName;
        this.password = value.password;
        this.smsAccount = value.smsAccount;
        this.kuajinggou = value.kuajinggou;
        this.orderRealName = value.orderRealName;
        this.hidBottom = value.hidBottom;
        this.shopStyle = value.shopStyle;
        this.logo = value.logo;
        this.shareConfig = value.shareConfig;
    }

    public Shop(
        Integer    shopId,
        Integer    sysId,
        String     mobile,
        String     receiveMobile,
        String     shopName,
        String     shopAvatar,
        String     shopBgPath,
        String     shopPhone,
        String     shopNotice,
        String     shopWx,
        String     shopEmail,
        Timestamp  created,
        Byte       isEnabled,
        Integer    provinceCode,
        String     provinceName,
        Integer    cityCode,
        String     cityName,
        Integer    districtCode,
        String     districtName,
        String     address,
        String     completeAddress,
        Integer    shopSellType,
        String     shopQq,
        String     lastLoginIp,
        Byte       state,
        Byte       businessState,
        BigDecimal manageFee,
        BigDecimal surplus,
        String     dbConfig,
        String     shopType,
        String     versionConfig,
        Byte       shopFlag,
        String     memberKey,
        String     tenancyName,
        String     userName,
        String     password,
        String     smsAccount,
        Byte       kuajinggou,
        Byte       orderRealName,
        Byte       hidBottom,
        String     shopStyle,
        String     logo,
        String     shareConfig
    ) {
        this.shopId = shopId;
        this.sysId = sysId;
        this.mobile = mobile;
        this.receiveMobile = receiveMobile;
        this.shopName = shopName;
        this.shopAvatar = shopAvatar;
        this.shopBgPath = shopBgPath;
        this.shopPhone = shopPhone;
        this.shopNotice = shopNotice;
        this.shopWx = shopWx;
        this.shopEmail = shopEmail;
        this.created = created;
        this.isEnabled = isEnabled;
        this.provinceCode = provinceCode;
        this.provinceName = provinceName;
        this.cityCode = cityCode;
        this.cityName = cityName;
        this.districtCode = districtCode;
        this.districtName = districtName;
        this.address = address;
        this.completeAddress = completeAddress;
        this.shopSellType = shopSellType;
        this.shopQq = shopQq;
        this.lastLoginIp = lastLoginIp;
        this.state = state;
        this.businessState = businessState;
        this.manageFee = manageFee;
        this.surplus = surplus;
        this.dbConfig = dbConfig;
        this.shopType = shopType;
        this.versionConfig = versionConfig;
        this.shopFlag = shopFlag;
        this.memberKey = memberKey;
        this.tenancyName = tenancyName;
        this.userName = userName;
        this.password = password;
        this.smsAccount = smsAccount;
        this.kuajinggou = kuajinggou;
        this.orderRealName = orderRealName;
        this.hidBottom = hidBottom;
        this.shopStyle = shopStyle;
        this.logo = logo;
        this.shareConfig = shareConfig;
    }

    public Integer getShopId() {
        return this.shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getSysId() {
        return this.sysId;
    }

    public void setSysId(Integer sysId) {
        this.sysId = sysId;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getReceiveMobile() {
        return this.receiveMobile;
    }

    public void setReceiveMobile(String receiveMobile) {
        this.receiveMobile = receiveMobile;
    }

    public String getShopName() {
        return this.shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAvatar() {
        return this.shopAvatar;
    }

    public void setShopAvatar(String shopAvatar) {
        this.shopAvatar = shopAvatar;
    }

    public String getShopBgPath() {
        return this.shopBgPath;
    }

    public void setShopBgPath(String shopBgPath) {
        this.shopBgPath = shopBgPath;
    }

    public String getShopPhone() {
        return this.shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    public String getShopNotice() {
        return this.shopNotice;
    }

    public void setShopNotice(String shopNotice) {
        this.shopNotice = shopNotice;
    }

    public String getShopWx() {
        return this.shopWx;
    }

    public void setShopWx(String shopWx) {
        this.shopWx = shopWx;
    }

    public String getShopEmail() {
        return this.shopEmail;
    }

    public void setShopEmail(String shopEmail) {
        this.shopEmail = shopEmail;
    }

    public Timestamp getCreated() {
        return this.created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Byte getIsEnabled() {
        return this.isEnabled;
    }

    public void setIsEnabled(Byte isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Integer getProvinceCode() {
        return this.provinceCode;
    }

    public void setProvinceCode(Integer provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return this.provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Integer getCityCode() {
        return this.cityCode;
    }

    public void setCityCode(Integer cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return this.cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getDistrictCode() {
        return this.districtCode;
    }

    public void setDistrictCode(Integer districtCode) {
        this.districtCode = districtCode;
    }

    public String getDistrictName() {
        return this.districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompleteAddress() {
        return this.completeAddress;
    }

    public void setCompleteAddress(String completeAddress) {
        this.completeAddress = completeAddress;
    }

    public Integer getShopSellType() {
        return this.shopSellType;
    }

    public void setShopSellType(Integer shopSellType) {
        this.shopSellType = shopSellType;
    }

    public String getShopQq() {
        return this.shopQq;
    }

    public void setShopQq(String shopQq) {
        this.shopQq = shopQq;
    }

    public String getLastLoginIp() {
        return this.lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Byte getState() {
        return this.state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Byte getBusinessState() {
        return this.businessState;
    }

    public void setBusinessState(Byte businessState) {
        this.businessState = businessState;
    }

    public BigDecimal getManageFee() {
        return this.manageFee;
    }

    public void setManageFee(BigDecimal manageFee) {
        this.manageFee = manageFee;
    }

    public BigDecimal getSurplus() {
        return this.surplus;
    }

    public void setSurplus(BigDecimal surplus) {
        this.surplus = surplus;
    }

    public String getDbConfig() {
        return this.dbConfig;
    }

    public void setDbConfig(String dbConfig) {
        this.dbConfig = dbConfig;
    }

    public String getShopType() {
        return this.shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public String getVersionConfig() {
        return this.versionConfig;
    }

    public void setVersionConfig(String versionConfig) {
        this.versionConfig = versionConfig;
    }

    public Byte getShopFlag() {
        return this.shopFlag;
    }

    public void setShopFlag(Byte shopFlag) {
        this.shopFlag = shopFlag;
    }

    public String getMemberKey() {
        return this.memberKey;
    }

    public void setMemberKey(String memberKey) {
        this.memberKey = memberKey;
    }

    public String getTenancyName() {
        return this.tenancyName;
    }

    public void setTenancyName(String tenancyName) {
        this.tenancyName = tenancyName;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSmsAccount() {
        return this.smsAccount;
    }

    public void setSmsAccount(String smsAccount) {
        this.smsAccount = smsAccount;
    }

    public Byte getKuajinggou() {
        return this.kuajinggou;
    }

    public void setKuajinggou(Byte kuajinggou) {
        this.kuajinggou = kuajinggou;
    }

    public Byte getOrderRealName() {
        return this.orderRealName;
    }

    public void setOrderRealName(Byte orderRealName) {
        this.orderRealName = orderRealName;
    }

    public Byte getHidBottom() {
        return this.hidBottom;
    }

    public void setHidBottom(Byte hidBottom) {
        this.hidBottom = hidBottom;
    }

    public String getShopStyle() {
        return this.shopStyle;
    }

    public void setShopStyle(String shopStyle) {
        this.shopStyle = shopStyle;
    }

    public String getLogo() {
        return this.logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getShareConfig() {
        return this.shareConfig;
    }

    public void setShareConfig(String shareConfig) {
        this.shareConfig = shareConfig;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Shop (");

        sb.append(shopId);
        sb.append(", ").append(sysId);
        sb.append(", ").append(mobile);
        sb.append(", ").append(receiveMobile);
        sb.append(", ").append(shopName);
        sb.append(", ").append(shopAvatar);
        sb.append(", ").append(shopBgPath);
        sb.append(", ").append(shopPhone);
        sb.append(", ").append(shopNotice);
        sb.append(", ").append(shopWx);
        sb.append(", ").append(shopEmail);
        sb.append(", ").append(created);
        sb.append(", ").append(isEnabled);
        sb.append(", ").append(provinceCode);
        sb.append(", ").append(provinceName);
        sb.append(", ").append(cityCode);
        sb.append(", ").append(cityName);
        sb.append(", ").append(districtCode);
        sb.append(", ").append(districtName);
        sb.append(", ").append(address);
        sb.append(", ").append(completeAddress);
        sb.append(", ").append(shopSellType);
        sb.append(", ").append(shopQq);
        sb.append(", ").append(lastLoginIp);
        sb.append(", ").append(state);
        sb.append(", ").append(businessState);
        sb.append(", ").append(manageFee);
        sb.append(", ").append(surplus);
        sb.append(", ").append(dbConfig);
        sb.append(", ").append(shopType);
        sb.append(", ").append(versionConfig);
        sb.append(", ").append(shopFlag);
        sb.append(", ").append(memberKey);
        sb.append(", ").append(tenancyName);
        sb.append(", ").append(userName);
        sb.append(", ").append(password);
        sb.append(", ").append(smsAccount);
        sb.append(", ").append(kuajinggou);
        sb.append(", ").append(orderRealName);
        sb.append(", ").append(hidBottom);
        sb.append(", ").append(shopStyle);
        sb.append(", ").append(logo);
        sb.append(", ").append(shareConfig);

        sb.append(")");
        return sb.toString();
    }
}
