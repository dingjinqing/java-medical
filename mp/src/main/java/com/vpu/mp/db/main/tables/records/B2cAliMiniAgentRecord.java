/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.main.tables.records;


import com.vpu.mp.db.main.tables.B2cAliMiniAgent;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;
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
public class B2cAliMiniAgentRecord extends UpdatableRecordImpl<B2cAliMiniAgentRecord> {

    private static final long serialVersionUID = -326973503;

    /**
     * Setter for <code>mini_main.b2c_ali_mini_agent.rec_id</code>.
     */
    public void setRecId(UInteger value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_mini_agent.rec_id</code>.
     */
    public UInteger getRecId() {
        return (UInteger) get(0);
    }

    /**
     * Setter for <code>mini_main.b2c_ali_mini_agent.shop_id</code>. 店铺Id
     */
    public void setShopId(UInteger value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_mini_agent.shop_id</code>. 店铺Id
     */
    public UInteger getShopId() {
        return (UInteger) get(1);
    }

    /**
     * Setter for <code>mini_main.b2c_ali_mini_agent.account</code>. isv代操作的商户账号，可以是支付宝账号，也可以是pid（2088开头）
     */
    public void setAccount(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_mini_agent.account</code>. isv代操作的商户账号，可以是支付宝账号，也可以是pid（2088开头）
     */
    public String getAccount() {
        return (String) get(2);
    }

    /**
     * Setter for <code>mini_main.b2c_ali_mini_agent.contact_name</code>. 联系人名称
     */
    public void setContactName(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_mini_agent.contact_name</code>. 联系人名称
     */
    public String getContactName() {
        return (String) get(3);
    }

    /**
     * Setter for <code>mini_main.b2c_ali_mini_agent.contact_mobile</code>. 联系人手机号码
     */
    public void setContactMobile(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_mini_agent.contact_mobile</code>. 联系人手机号码
     */
    public String getContactMobile() {
        return (String) get(4);
    }

    /**
     * Setter for <code>mini_main.b2c_ali_mini_agent.contact_email</code>. 联系人邮箱
     */
    public void setContactEmail(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_mini_agent.contact_email</code>. 联系人邮箱
     */
    public String getContactEmail() {
        return (String) get(5);
    }

    /**
     * Setter for <code>mini_main.b2c_ali_mini_agent.order_ticket</code>. 订单授权凭证，填写都则对应事务提交进入预授权模式
     */
    public void setOrderTicket(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_mini_agent.order_ticket</code>. 订单授权凭证，填写都则对应事务提交进入预授权模式
     */
    public String getOrderTicket() {
        return (String) get(6);
    }

    /**
     * Setter for <code>mini_main.b2c_ali_mini_agent.batch_no</code>. 本次代商户操作的全局唯一事务编号
     */
    public void setBatchNo(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_mini_agent.batch_no</code>. 本次代商户操作的全局唯一事务编号
     */
    public String getBatchNo() {
        return (String) get(7);
    }

    /**
     * Setter for <code>mini_main.b2c_ali_mini_agent.create_agent_result</code>. 提交事务接口结果,json
     */
    public void setCreateAgentResult(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_mini_agent.create_agent_result</code>. 提交事务接口结果,json
     */
    public String getCreateAgentResult() {
        return (String) get(8);
    }

    /**
     * Setter for <code>mini_main.b2c_ali_mini_agent.batch_status</code>. ISV 代商户操作事务状态，事务状态包括：init=初始状态，submit=提交状态，cancel=取消状态，timeout=超时状态，init状态的事务，在【1个小时】后会自动超时
     */
    public void setBatchStatus(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_mini_agent.batch_status</code>. ISV 代商户操作事务状态，事务状态包括：init=初始状态，submit=提交状态，cancel=取消状态，timeout=超时状态，init状态的事务，在【1个小时】后会自动超时
     */
    public String getBatchStatus() {
        return (String) get(9);
    }

    /**
     * Setter for <code>mini_main.b2c_ali_mini_agent.create_agent_time</code>. 事务初始时间
     */
    public void setCreateAgentTime(Timestamp value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_mini_agent.create_agent_time</code>. 事务初始时间
     */
    public Timestamp getCreateAgentTime() {
        return (Timestamp) get(10);
    }

    /**
     * Setter for <code>mini_main.b2c_ali_mini_agent.app_name</code>. 代商家创建的小程序应用名称
     */
    public void setAppName(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_mini_agent.app_name</code>. 代商家创建的小程序应用名称
     */
    public String getAppName() {
        return (String) get(11);
    }

    /**
     * Setter for <code>mini_main.b2c_ali_mini_agent.app_english_name</code>. 小程序英文名称，长度3~20个字符	
     */
    public void setAppEnglishName(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_mini_agent.app_english_name</code>. 小程序英文名称，长度3~20个字符	
     */
    public String getAppEnglishName() {
        return (String) get(12);
    }

    /**
     * Setter for <code>mini_main.b2c_ali_mini_agent.app_category_ids</code>. 小程序应用类目，参数格式：一级类目_二级类目。
     */
    public void setAppCategoryIds(String value) {
        set(13, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_mini_agent.app_category_ids</code>. 小程序应用类目，参数格式：一级类目_二级类目。
     */
    public String getAppCategoryIds() {
        return (String) get(13);
    }

    /**
     * Setter for <code>mini_main.b2c_ali_mini_agent.app_slogan</code>. 代商家创建的小程序的简介，请用一句话简要描述小程序提供的服务；应用上架后一个自然月可修改5次（10~32个字符）	
     */
    public void setAppSlogan(String value) {
        set(14, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_mini_agent.app_slogan</code>. 代商家创建的小程序的简介，请用一句话简要描述小程序提供的服务；应用上架后一个自然月可修改5次（10~32个字符）	
     */
    public String getAppSlogan() {
        return (String) get(14);
    }

    /**
     * Setter for <code>mini_main.b2c_ali_mini_agent.service_phone</code>. 商家小程序的客服电话，推荐填写商家小程序客服电话和邮箱，可以二选一填写，但不能同时为空
     */
    public void setServicePhone(String value) {
        set(15, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_mini_agent.service_phone</code>. 商家小程序的客服电话，推荐填写商家小程序客服电话和邮箱，可以二选一填写，但不能同时为空
     */
    public String getServicePhone() {
        return (String) get(15);
    }

    /**
     * Setter for <code>mini_main.b2c_ali_mini_agent.service_email</code>. 商家小程序客服邮箱 
     */
    public void setServiceEmail(String value) {
        set(16, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_mini_agent.service_email</code>. 商家小程序客服邮箱 
     */
    public String getServiceEmail() {
        return (String) get(16);
    }

    /**
     * Setter for <code>mini_main.b2c_ali_mini_agent.app_logo</code>. 商家小程序应用图标，最大256KB,png、jpeg、jpg格式，建议上传像素为180*180
     */
    public void setAppLogo(String value) {
        set(17, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_mini_agent.app_logo</code>. 商家小程序应用图标，最大256KB,png、jpeg、jpg格式，建议上传像素为180*180
     */
    public String getAppLogo() {
        return (String) get(17);
    }

    /**
     * Setter for <code>mini_main.b2c_ali_mini_agent.app_desc</code>. 商家小程序描述信息，简要描述小程序主要功能（20-200个字）
     */
    public void setAppDesc(String value) {
        set(18, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_mini_agent.app_desc</code>. 商家小程序描述信息，简要描述小程序主要功能（20-200个字）
     */
    public String getAppDesc() {
        return (String) get(18);
    }

    /**
     * Setter for <code>mini_main.b2c_ali_mini_agent.create_mini_result</code>. 创建小程序接口结果,json
     */
    public void setCreateMiniResult(String value) {
        set(19, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_mini_agent.create_mini_result</code>. 创建小程序接口结果,json
     */
    public String getCreateMiniResult() {
        return (String) get(19);
    }

    /**
     * Setter for <code>mini_main.b2c_ali_mini_agent.create_mini_time</code>. 创建小程序时间
     */
    public void setCreateMiniTime(Timestamp value) {
        set(20, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_mini_agent.create_mini_time</code>. 创建小程序时间
     */
    public Timestamp getCreateMiniTime() {
        return (Timestamp) get(20);
    }

    /**
     * Setter for <code>mini_main.b2c_ali_mini_agent.user_id</code>. 授权商户的user_id	
     */
    public void setUserId(String value) {
        set(21, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_mini_agent.user_id</code>. 授权商户的user_id	
     */
    public String getUserId() {
        return (String) get(21);
    }

    /**
     * Setter for <code>mini_main.b2c_ali_mini_agent.auth_app_id</code>. 授权商户的appid	
     */
    public void setAuthAppId(String value) {
        set(22, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_mini_agent.auth_app_id</code>. 授权商户的appid	
     */
    public String getAuthAppId() {
        return (String) get(22);
    }

    /**
     * Setter for <code>mini_main.b2c_ali_mini_agent.confirm_agent_result</code>. 提交事务接口结果,json
     */
    public void setConfirmAgentResult(String value) {
        set(23, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_mini_agent.confirm_agent_result</code>. 提交事务接口结果,json
     */
    public String getConfirmAgentResult() {
        return (String) get(23);
    }

    /**
     * Setter for <code>mini_main.b2c_ali_mini_agent.confirm_agent_time</code>. 提交事务时间
     */
    public void setConfirmAgentTime(Timestamp value) {
        set(24, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_mini_agent.confirm_agent_time</code>. 提交事务时间
     */
    public Timestamp getConfirmAgentTime() {
        return (Timestamp) get(24);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<UInteger> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached B2cAliMiniAgentRecord
     */
    public B2cAliMiniAgentRecord() {
        super(B2cAliMiniAgent.B2C_ALI_MINI_AGENT);
    }

    /**
     * Create a detached, initialised B2cAliMiniAgentRecord
     */
    public B2cAliMiniAgentRecord(UInteger recId, UInteger shopId, String account, String contactName, String contactMobile, String contactEmail, String orderTicket, String batchNo, String createAgentResult, String batchStatus, Timestamp createAgentTime, String appName, String appEnglishName, String appCategoryIds, String appSlogan, String servicePhone, String serviceEmail, String appLogo, String appDesc, String createMiniResult, Timestamp createMiniTime, String userId, String authAppId, String confirmAgentResult, Timestamp confirmAgentTime) {
        super(B2cAliMiniAgent.B2C_ALI_MINI_AGENT);

        set(0, recId);
        set(1, shopId);
        set(2, account);
        set(3, contactName);
        set(4, contactMobile);
        set(5, contactEmail);
        set(6, orderTicket);
        set(7, batchNo);
        set(8, createAgentResult);
        set(9, batchStatus);
        set(10, createAgentTime);
        set(11, appName);
        set(12, appEnglishName);
        set(13, appCategoryIds);
        set(14, appSlogan);
        set(15, servicePhone);
        set(16, serviceEmail);
        set(17, appLogo);
        set(18, appDesc);
        set(19, createMiniResult);
        set(20, createMiniTime);
        set(21, userId);
        set(22, authAppId);
        set(23, confirmAgentResult);
        set(24, confirmAgentTime);
    }
}
