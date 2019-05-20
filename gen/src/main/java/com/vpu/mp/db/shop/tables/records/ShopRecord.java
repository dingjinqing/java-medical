/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.Shop;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.11"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ShopRecord extends UpdatableRecordImpl<ShopRecord> {

    private static final long serialVersionUID = 685676456;

    /**
     * Setter for <code>mini_shop_1.b2c_shop.shop_id</code>. 店铺ID
     */
    public void setShopId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.shop_id</code>. 店铺ID
     */
    public Integer getShopId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop.sys_id</code>.
     */
    public void setSysId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.sys_id</code>.
     */
    public Integer getSysId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop.mobile</code>.
     */
    public void setMobile(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.mobile</code>.
     */
    public String getMobile() {
        return (String) get(2);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop.receive_mobile</code>.  接收通知手机号码
     */
    public void setReceiveMobile(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.receive_mobile</code>.  接收通知手机号码
     */
    public String getReceiveMobile() {
        return (String) get(3);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop.shop_name</code>. 店铺名称
     */
    public void setShopName(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.shop_name</code>. 店铺名称
     */
    public String getShopName() {
        return (String) get(4);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop.shop_avatar</code>. 店铺头像
     */
    public void setShopAvatar(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.shop_avatar</code>. 店铺头像
     */
    public String getShopAvatar() {
        return (String) get(5);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop.shop_bg_path</code>. 店铺背景图片
     */
    public void setShopBgPath(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.shop_bg_path</code>. 店铺背景图片
     */
    public String getShopBgPath() {
        return (String) get(6);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop.shop_phone</code>. 店铺客服电话
     */
    public void setShopPhone(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.shop_phone</code>. 店铺客服电话
     */
    public String getShopPhone() {
        return (String) get(7);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop.shop_notice</code>. 店铺公告
     */
    public void setShopNotice(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.shop_notice</code>. 店铺公告
     */
    public String getShopNotice() {
        return (String) get(8);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop.shop_wx</code>. 店铺客服微信
     */
    public void setShopWx(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.shop_wx</code>. 店铺客服微信
     */
    public String getShopWx() {
        return (String) get(9);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop.shop_email</code>.
     */
    public void setShopEmail(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.shop_email</code>.
     */
    public String getShopEmail() {
        return (String) get(10);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop.created</code>. 创建时间
     */
    public void setCreated(Timestamp value) {
        set(11, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.created</code>. 创建时间
     */
    public Timestamp getCreated() {
        return (Timestamp) get(11);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop.is_enabled</code>. 0:正常，1：禁用
     */
    public void setIsEnabled(Byte value) {
        set(12, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.is_enabled</code>. 0:正常，1：禁用
     */
    public Byte getIsEnabled() {
        return (Byte) get(12);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop.province_code</code>. 所在省
     */
    public void setProvinceCode(UInteger value) {
        set(13, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.province_code</code>. 所在省
     */
    public UInteger getProvinceCode() {
        return (UInteger) get(13);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop.province_name</code>.
     */
    public void setProvinceName(String value) {
        set(14, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.province_name</code>.
     */
    public String getProvinceName() {
        return (String) get(14);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop.city_code</code>. 所在城市
     */
    public void setCityCode(UInteger value) {
        set(15, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.city_code</code>. 所在城市
     */
    public UInteger getCityCode() {
        return (UInteger) get(15);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop.city_name</code>.
     */
    public void setCityName(String value) {
        set(16, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.city_name</code>.
     */
    public String getCityName() {
        return (String) get(16);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop.district_code</code>. 所在区县
     */
    public void setDistrictCode(UInteger value) {
        set(17, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.district_code</code>. 所在区县
     */
    public UInteger getDistrictCode() {
        return (UInteger) get(17);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop.district_name</code>.
     */
    public void setDistrictName(String value) {
        set(18, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.district_name</code>.
     */
    public String getDistrictName() {
        return (String) get(18);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop.address</code>. 所在地址
     */
    public void setAddress(String value) {
        set(19, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.address</code>. 所在地址
     */
    public String getAddress() {
        return (String) get(19);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop.complete_address</code>. 所在完整地址
     */
    public void setCompleteAddress(String value) {
        set(20, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.complete_address</code>. 所在完整地址
     */
    public String getCompleteAddress() {
        return (String) get(20);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop.shop_sell_type</code>. 经营品类,254：其他
     */
    public void setShopSellType(UByte value) {
        set(21, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.shop_sell_type</code>. 经营品类,254：其他
     */
    public UByte getShopSellType() {
        return (UByte) get(21);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop.shop_qq</code>. 店铺客服QQ
     */
    public void setShopQq(String value) {
        set(22, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.shop_qq</code>. 店铺客服QQ
     */
    public String getShopQq() {
        return (String) get(22);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop.last_login_ip</code>. 上次登录IP
     */
    public void setLastLoginIp(String value) {
        set(23, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.last_login_ip</code>. 上次登录IP
     */
    public String getLastLoginIp() {
        return (String) get(23);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop.state</code>. 0 入驻申请，1审核通过，2审核不通过
     */
    public void setState(Byte value) {
        set(24, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.state</code>. 0 入驻申请，1审核通过，2审核不通过
     */
    public Byte getState() {
        return (Byte) get(24);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop.business_state</code>. 营业状态 0未营业 1营业
     */
    public void setBusinessState(Byte value) {
        set(25, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.business_state</code>. 营业状态 0未营业 1营业
     */
    public Byte getBusinessState() {
        return (Byte) get(25);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop.manage_fee</code>. 平台管理费
     */
    public void setManageFee(BigDecimal value) {
        set(26, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.manage_fee</code>. 平台管理费
     */
    public BigDecimal getManageFee() {
        return (BigDecimal) get(26);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop.shop_license</code>. 营业执照
     */
    public void setShopLicense(String value) {
        set(27, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.shop_license</code>. 营业执照
     */
    public String getShopLicense() {
        return (String) get(27);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop.shop_tax_credential</code>. 税务登记证
     */
    public void setShopTaxCredential(String value) {
        set(28, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.shop_tax_credential</code>. 税务登记证
     */
    public String getShopTaxCredential() {
        return (String) get(28);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop.organization_code_certificate</code>. 组织机构代码证
     */
    public void setOrganizationCodeCertificate(String value) {
        set(29, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.organization_code_certificate</code>. 组织机构代码证
     */
    public String getOrganizationCodeCertificate() {
        return (String) get(29);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop.permit_for_opening_bank_account</code>. 银行开户许可证
     */
    public void setPermitForOpeningBankAccount(String value) {
        set(30, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.permit_for_opening_bank_account</code>. 银行开户许可证
     */
    public String getPermitForOpeningBankAccount() {
        return (String) get(30);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop.financial_registration</code>. 财政登记证
     */
    public void setFinancialRegistration(String value) {
        set(31, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.financial_registration</code>. 财政登记证
     */
    public String getFinancialRegistration() {
        return (String) get(31);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop.shop_bank_name</code>. 开户行姓名
     */
    public void setShopBankName(String value) {
        set(32, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.shop_bank_name</code>. 开户行姓名
     */
    public String getShopBankName() {
        return (String) get(32);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop.shop_bank</code>. 开户行
     */
    public void setShopBank(String value) {
        set(33, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.shop_bank</code>. 开户行
     */
    public String getShopBank() {
        return (String) get(33);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop.shop_bank_branch</code>. 开户行分行
     */
    public void setShopBankBranch(String value) {
        set(34, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.shop_bank_branch</code>. 开户行分行
     */
    public String getShopBankBranch() {
        return (String) get(34);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop.shop_bank_no</code>. 开户行卡号
     */
    public void setShopBankNo(String value) {
        set(35, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.shop_bank_no</code>. 开户行卡号
     */
    public String getShopBankNo() {
        return (String) get(35);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop.surplus</code>. 余额
     */
    public void setSurplus(BigDecimal value) {
        set(36, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.surplus</code>. 余额
     */
    public BigDecimal getSurplus() {
        return (BigDecimal) get(36);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop.reject_reason</code>. 驳回理由
     */
    public void setRejectReason(String value) {
        set(37, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.reject_reason</code>. 驳回理由
     */
    public String getRejectReason() {
        return (String) get(37);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop.shop_stat_code</code>. 第三方统计代码
     */
    public void setShopStatCode(String value) {
        set(38, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.shop_stat_code</code>. 第三方统计代码
     */
    public String getShopStatCode() {
        return (String) get(38);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop.shop_icp</code>. IP信息
     */
    public void setShopIcp(String value) {
        set(39, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.shop_icp</code>. IP信息
     */
    public String getShopIcp() {
        return (String) get(39);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop.shop_copyright</code>. 版权信息
     */
    public void setShopCopyright(String value) {
        set(40, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.shop_copyright</code>. 版权信息
     */
    public String getShopCopyright() {
        return (String) get(40);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop.db_config</code>. db config,json format
     */
    public void setDbConfig(String value) {
        set(41, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.db_config</code>. db config,json format
     */
    public String getDbConfig() {
        return (String) get(41);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop.share_config</code>. 分享设置
     */
    public void setShareConfig(String value) {
        set(42, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop.share_config</code>. 分享设置
     */
    public String getShareConfig() {
        return (String) get(42);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ShopRecord
     */
    public ShopRecord() {
        super(Shop.SHOP);
    }

    /**
     * Create a detached, initialised ShopRecord
     */
    public ShopRecord(Integer shopId, Integer sysId, String mobile, String receiveMobile, String shopName, String shopAvatar, String shopBgPath, String shopPhone, String shopNotice, String shopWx, String shopEmail, Timestamp created, Byte isEnabled, UInteger provinceCode, String provinceName, UInteger cityCode, String cityName, UInteger districtCode, String districtName, String address, String completeAddress, UByte shopSellType, String shopQq, String lastLoginIp, Byte state, Byte businessState, BigDecimal manageFee, String shopLicense, String shopTaxCredential, String organizationCodeCertificate, String permitForOpeningBankAccount, String financialRegistration, String shopBankName, String shopBank, String shopBankBranch, String shopBankNo, BigDecimal surplus, String rejectReason, String shopStatCode, String shopIcp, String shopCopyright, String dbConfig, String shareConfig) {
        super(Shop.SHOP);

        set(0, shopId);
        set(1, sysId);
        set(2, mobile);
        set(3, receiveMobile);
        set(4, shopName);
        set(5, shopAvatar);
        set(6, shopBgPath);
        set(7, shopPhone);
        set(8, shopNotice);
        set(9, shopWx);
        set(10, shopEmail);
        set(11, created);
        set(12, isEnabled);
        set(13, provinceCode);
        set(14, provinceName);
        set(15, cityCode);
        set(16, cityName);
        set(17, districtCode);
        set(18, districtName);
        set(19, address);
        set(20, completeAddress);
        set(21, shopSellType);
        set(22, shopQq);
        set(23, lastLoginIp);
        set(24, state);
        set(25, businessState);
        set(26, manageFee);
        set(27, shopLicense);
        set(28, shopTaxCredential);
        set(29, organizationCodeCertificate);
        set(30, permitForOpeningBankAccount);
        set(31, financialRegistration);
        set(32, shopBankName);
        set(33, shopBank);
        set(34, shopBankBranch);
        set(35, shopBankNo);
        set(36, surplus);
        set(37, rejectReason);
        set(38, shopStatCode);
        set(39, shopIcp);
        set(40, shopCopyright);
        set(41, dbConfig);
        set(42, shareConfig);
    }
}
