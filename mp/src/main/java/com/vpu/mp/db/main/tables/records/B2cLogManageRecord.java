/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.main.tables.records;


import com.vpu.mp.db.main.tables.B2cLogManage;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record19;
import org.jooq.Row19;
import org.jooq.impl.UpdatableRecordImpl;


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
public class B2cLogManageRecord extends UpdatableRecordImpl<B2cLogManageRecord> implements Record19<Long, Integer, Byte, String, String, String, Byte, Byte, String, String, String, String, String, Timestamp, String, Timestamp, Timestamp, String, Integer> {

    private static final long serialVersionUID = 923297696;

    /**
     * Setter for <code>mini_main.b2c_log_manage.log_id</code>.
     */
    public void setLogId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_main.b2c_log_manage.log_id</code>.
     */
    public Long getLogId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>mini_main.b2c_log_manage.shop_id</code>. 店铺ID
     */
    public void setShopId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_main.b2c_log_manage.shop_id</code>. 店铺ID
     */
    public Integer getShopId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_main.b2c_log_manage.error_level</code>. 错误等级：0正常，1debug，2info，3error
     */
    public void setErrorLevel(Byte value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_main.b2c_log_manage.error_level</code>. 错误等级：0正常，1debug，2info，3error
     */
    public Byte getErrorLevel() {
        return (Byte) get(2);
    }

    /**
     * Setter for <code>mini_main.b2c_log_manage.error_code</code>. 错误编码
     */
    public void setErrorCode(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_main.b2c_log_manage.error_code</code>. 错误编码
     */
    public String getErrorCode() {
        return (String) get(3);
    }

    /**
     * Setter for <code>mini_main.b2c_log_manage.error_msg</code>. 错误说明
     */
    public void setErrorMsg(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_main.b2c_log_manage.error_msg</code>. 错误说明
     */
    public String getErrorMsg() {
        return (String) get(4);
    }

    /**
     * Setter for <code>mini_main.b2c_log_manage.error_content</code>. 错误内容
     */
    public void setErrorContent(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_main.b2c_log_manage.error_content</code>. 错误内容
     */
    public String getErrorContent() {
        return (String) get(5);
    }

    /**
     * Setter for <code>mini_main.b2c_log_manage.error_source</code>. 错误来源：0无，1erp，2crm，3pos，4寺库，5欧派，...
     */
    public void setErrorSource(Byte value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_main.b2c_log_manage.error_source</code>. 错误来源：0无，1erp，2crm，3pos，4寺库，5欧派，...
     */
    public Byte getErrorSource() {
        return (Byte) get(6);
    }

    /**
     * Setter for <code>mini_main.b2c_log_manage.deal_status</code>. 处理结果：0待处理，1已处理
     */
    public void setDealStatus(Byte value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_main.b2c_log_manage.deal_status</code>. 处理结果：0待处理，1已处理
     */
    public Byte getDealStatus() {
        return (Byte) get(7);
    }

    /**
     * Setter for <code>mini_main.b2c_log_manage.action</code>. 动作方法
     */
    public void setAction(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_main.b2c_log_manage.action</code>. 动作方法
     */
    public String getAction() {
        return (String) get(8);
    }

    /**
     * Setter for <code>mini_main.b2c_log_manage.action_name</code>. 动作方法名称
     */
    public void setActionName(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_main.b2c_log_manage.action_name</code>. 动作方法名称
     */
    public String getActionName() {
        return (String) get(9);
    }

    /**
     * Setter for <code>mini_main.b2c_log_manage.request_content</code>. 请求内容 json串
     */
    public void setRequestContent(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_main.b2c_log_manage.request_content</code>. 请求内容 json串
     */
    public String getRequestContent() {
        return (String) get(10);
    }

    /**
     * Setter for <code>mini_main.b2c_log_manage.response_content</code>. 响应内容 json串
     */
    public void setResponseContent(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>mini_main.b2c_log_manage.response_content</code>. 响应内容 json串
     */
    public String getResponseContent() {
        return (String) get(11);
    }

    /**
     * Setter for <code>mini_main.b2c_log_manage.ip</code>. 请求ip
     */
    public void setIp(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>mini_main.b2c_log_manage.ip</code>. 请求ip
     */
    public String getIp() {
        return (String) get(12);
    }

    /**
     * Setter for <code>mini_main.b2c_log_manage.deal_time</code>. 处理时间
     */
    public void setDealTime(Timestamp value) {
        set(13, value);
    }

    /**
     * Getter for <code>mini_main.b2c_log_manage.deal_time</code>. 处理时间
     */
    public Timestamp getDealTime() {
        return (Timestamp) get(13);
    }

    /**
     * Setter for <code>mini_main.b2c_log_manage.deal_person</code>. 处理人员
     */
    public void setDealPerson(String value) {
        set(14, value);
    }

    /**
     * Getter for <code>mini_main.b2c_log_manage.deal_person</code>. 处理人员
     */
    public String getDealPerson() {
        return (String) get(14);
    }

    /**
     * Setter for <code>mini_main.b2c_log_manage.in_time</code>. 添加时间
     */
    public void setInTime(Timestamp value) {
        set(15, value);
    }

    /**
     * Getter for <code>mini_main.b2c_log_manage.in_time</code>. 添加时间
     */
    public Timestamp getInTime() {
        return (Timestamp) get(15);
    }

    /**
     * Setter for <code>mini_main.b2c_log_manage.up_time</code>. 更新时间
     */
    public void setUpTime(Timestamp value) {
        set(16, value);
    }

    /**
     * Getter for <code>mini_main.b2c_log_manage.up_time</code>. 更新时间
     */
    public Timestamp getUpTime() {
        return (Timestamp) get(16);
    }

    /**
     * Setter for <code>mini_main.b2c_log_manage.remark</code>. 备注
     */
    public void setRemark(String value) {
        set(17, value);
    }

    /**
     * Getter for <code>mini_main.b2c_log_manage.remark</code>. 备注
     */
    public String getRemark() {
        return (String) get(17);
    }

    /**
     * Setter for <code>mini_main.b2c_log_manage.identity_id</code>. 关联外部表
     */
    public void setIdentityId(Integer value) {
        set(18, value);
    }

    /**
     * Getter for <code>mini_main.b2c_log_manage.identity_id</code>. 关联外部表
     */
    public Integer getIdentityId() {
        return (Integer) get(18);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record19 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row19<Long, Integer, Byte, String, String, String, Byte, Byte, String, String, String, String, String, Timestamp, String, Timestamp, Timestamp, String, Integer> fieldsRow() {
        return (Row19) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row19<Long, Integer, Byte, String, String, String, Byte, Byte, String, String, String, String, String, Timestamp, String, Timestamp, Timestamp, String, Integer> valuesRow() {
        return (Row19) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return B2cLogManage.B2C_LOG_MANAGE.LOG_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return B2cLogManage.B2C_LOG_MANAGE.SHOP_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field3() {
        return B2cLogManage.B2C_LOG_MANAGE.ERROR_LEVEL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return B2cLogManage.B2C_LOG_MANAGE.ERROR_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return B2cLogManage.B2C_LOG_MANAGE.ERROR_MSG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return B2cLogManage.B2C_LOG_MANAGE.ERROR_CONTENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field7() {
        return B2cLogManage.B2C_LOG_MANAGE.ERROR_SOURCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field8() {
        return B2cLogManage.B2C_LOG_MANAGE.DEAL_STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return B2cLogManage.B2C_LOG_MANAGE.ACTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field10() {
        return B2cLogManage.B2C_LOG_MANAGE.ACTION_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field11() {
        return B2cLogManage.B2C_LOG_MANAGE.REQUEST_CONTENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field12() {
        return B2cLogManage.B2C_LOG_MANAGE.RESPONSE_CONTENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field13() {
        return B2cLogManage.B2C_LOG_MANAGE.IP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field14() {
        return B2cLogManage.B2C_LOG_MANAGE.DEAL_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field15() {
        return B2cLogManage.B2C_LOG_MANAGE.DEAL_PERSON;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field16() {
        return B2cLogManage.B2C_LOG_MANAGE.IN_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field17() {
        return B2cLogManage.B2C_LOG_MANAGE.UP_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field18() {
        return B2cLogManage.B2C_LOG_MANAGE.REMARK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field19() {
        return B2cLogManage.B2C_LOG_MANAGE.IDENTITY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component1() {
        return getLogId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component2() {
        return getShopId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component3() {
        return getErrorLevel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getErrorCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getErrorMsg();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getErrorContent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component7() {
        return getErrorSource();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component8() {
        return getDealStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component9() {
        return getAction();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component10() {
        return getActionName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component11() {
        return getRequestContent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component12() {
        return getResponseContent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component13() {
        return getIp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component14() {
        return getDealTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component15() {
        return getDealPerson();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component16() {
        return getInTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component17() {
        return getUpTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component18() {
        return getRemark();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component19() {
        return getIdentityId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
        return getLogId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getShopId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value3() {
        return getErrorLevel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getErrorCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getErrorMsg();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getErrorContent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value7() {
        return getErrorSource();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value8() {
        return getDealStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value9() {
        return getAction();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value10() {
        return getActionName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value11() {
        return getRequestContent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value12() {
        return getResponseContent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value13() {
        return getIp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value14() {
        return getDealTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value15() {
        return getDealPerson();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value16() {
        return getInTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value17() {
        return getUpTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value18() {
        return getRemark();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value19() {
        return getIdentityId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cLogManageRecord value1(Long value) {
        setLogId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cLogManageRecord value2(Integer value) {
        setShopId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cLogManageRecord value3(Byte value) {
        setErrorLevel(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cLogManageRecord value4(String value) {
        setErrorCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cLogManageRecord value5(String value) {
        setErrorMsg(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cLogManageRecord value6(String value) {
        setErrorContent(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cLogManageRecord value7(Byte value) {
        setErrorSource(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cLogManageRecord value8(Byte value) {
        setDealStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cLogManageRecord value9(String value) {
        setAction(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cLogManageRecord value10(String value) {
        setActionName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cLogManageRecord value11(String value) {
        setRequestContent(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cLogManageRecord value12(String value) {
        setResponseContent(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cLogManageRecord value13(String value) {
        setIp(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cLogManageRecord value14(Timestamp value) {
        setDealTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cLogManageRecord value15(String value) {
        setDealPerson(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cLogManageRecord value16(Timestamp value) {
        setInTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cLogManageRecord value17(Timestamp value) {
        setUpTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cLogManageRecord value18(String value) {
        setRemark(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cLogManageRecord value19(Integer value) {
        setIdentityId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cLogManageRecord values(Long value1, Integer value2, Byte value3, String value4, String value5, String value6, Byte value7, Byte value8, String value9, String value10, String value11, String value12, String value13, Timestamp value14, String value15, Timestamp value16, Timestamp value17, String value18, Integer value19) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        value12(value12);
        value13(value13);
        value14(value14);
        value15(value15);
        value16(value16);
        value17(value17);
        value18(value18);
        value19(value19);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached B2cLogManageRecord
     */
    public B2cLogManageRecord() {
        super(B2cLogManage.B2C_LOG_MANAGE);
    }

    /**
     * Create a detached, initialised B2cLogManageRecord
     */
    public B2cLogManageRecord(Long logId, Integer shopId, Byte errorLevel, String errorCode, String errorMsg, String errorContent, Byte errorSource, Byte dealStatus, String action, String actionName, String requestContent, String responseContent, String ip, Timestamp dealTime, String dealPerson, Timestamp inTime, Timestamp upTime, String remark, Integer identityId) {
        super(B2cLogManage.B2C_LOG_MANAGE);

        set(0, logId);
        set(1, shopId);
        set(2, errorLevel);
        set(3, errorCode);
        set(4, errorMsg);
        set(5, errorContent);
        set(6, errorSource);
        set(7, dealStatus);
        set(8, action);
        set(9, actionName);
        set(10, requestContent);
        set(11, responseContent);
        set(12, ip);
        set(13, dealTime);
        set(14, dealPerson);
        set(15, inTime);
        set(16, upTime);
        set(17, remark);
        set(18, identityId);
    }
}
