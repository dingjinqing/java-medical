/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.B2cServiceMessageRecord;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record16;
import org.jooq.Row16;
import org.jooq.impl.TableRecordImpl;


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
public class B2cServiceMessageRecordRecord extends TableRecordImpl<B2cServiceMessageRecordRecord> implements Record16<Integer, String, Byte, String, Byte, String, String, String, String, String, Byte, Byte, Timestamp, Byte, String, Timestamp> {

    private static final long serialVersionUID = 719169982;

    /**
     * Setter for <code>mini_shop_471752.b2c_service_message_record.user_id</code>. 用户ID
     */
    public void setUserId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_service_message_record.user_id</code>. 用户ID
     */
    public Integer getUserId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_service_message_record.mobile</code>. 用户手机号
     */
    public void setMobile(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_service_message_record.mobile</code>. 用户手机号
     */
    public String getMobile() {
        return (String) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_service_message_record.request_action</code>. 请求类型：100:短信平台
     */
    public void setRequestAction(Byte value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_service_message_record.request_action</code>. 请求类型：100:短信平台
     */
    public Byte getRequestAction() {
        return (Byte) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_service_message_record.identity_id</code>. 关联其他表：如：外部请求requestId
     */
    public void setIdentityId(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_service_message_record.identity_id</code>. 关联其他表：如：外部请求requestId
     */
    public String getIdentityId() {
        return (String) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_service_message_record.template_platform</code>. 模板平台：1： 小程序 2：公众号
     */
    public void setTemplatePlatform(Byte value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_service_message_record.template_platform</code>. 模板平台：1： 小程序 2：公众号
     */
    public Byte getTemplatePlatform() {
        return (Byte) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_service_message_record.template_content</code>. 模板内容
     */
    public void setTemplateContent(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_service_message_record.template_content</code>. 模板内容
     */
    public String getTemplateContent() {
        return (String) get(5);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_service_message_record.response_code</code>. 响应code 0:成功 &gt;0 其他
     */
    public void setResponseCode(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_service_message_record.response_code</code>. 响应code 0:成功 &gt;0 其他
     */
    public String getResponseCode() {
        return (String) get(6);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_service_message_record.response_msg</code>. 响应结果
     */
    public void setResponseMsg(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_service_message_record.response_msg</code>. 响应结果
     */
    public String getResponseMsg() {
        return (String) get(7);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_service_message_record.path</code>. 小程序路径
     */
    public void setPath(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_service_message_record.path</code>. 小程序路径
     */
    public String getPath() {
        return (String) get(8);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_service_message_record.path_query</code>. 小程序路径参数
     */
    public void setPathQuery(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_service_message_record.path_query</code>. 小程序路径参数
     */
    public String getPathQuery() {
        return (String) get(9);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_service_message_record.send_status</code>. 1: 发送成功  0：未知
     */
    public void setSendStatus(Byte value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_service_message_record.send_status</code>. 1: 发送成功  0：未知
     */
    public Byte getSendStatus() {
        return (Byte) get(10);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_service_message_record.is_visit</code>. 是否已点击访问 1：是 0： 否
     */
    public void setIsVisit(Byte value) {
        set(11, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_service_message_record.is_visit</code>. 是否已点击访问 1：是 0： 否
     */
    public Byte getIsVisit() {
        return (Byte) get(11);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_service_message_record.visit_time</code>. 访问时间
     */
    public void setVisitTime(Timestamp value) {
        set(12, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_service_message_record.visit_time</code>. 访问时间
     */
    public Timestamp getVisitTime() {
        return (Timestamp) get(12);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_service_message_record.template_type</code>. 模板类型 7：商家自定义
     */
    public void setTemplateType(Byte value) {
        set(13, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_service_message_record.template_type</code>. 模板类型 7：商家自定义
     */
    public Byte getTemplateType() {
        return (Byte) get(13);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_service_message_record.link_identity</code>. 模板消息关联Id
     */
    public void setLinkIdentity(String value) {
        set(14, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_service_message_record.link_identity</code>. 模板消息关联Id
     */
    public String getLinkIdentity() {
        return (String) get(14);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_service_message_record.add_time</code>.
     */
    public void setAddTime(Timestamp value) {
        set(15, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_service_message_record.add_time</code>.
     */
    public Timestamp getAddTime() {
        return (Timestamp) get(15);
    }

    // -------------------------------------------------------------------------
    // Record16 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row16<Integer, String, Byte, String, Byte, String, String, String, String, String, Byte, Byte, Timestamp, Byte, String, Timestamp> fieldsRow() {
        return (Row16) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row16<Integer, String, Byte, String, Byte, String, String, String, String, String, Byte, Byte, Timestamp, Byte, String, Timestamp> valuesRow() {
        return (Row16) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return B2cServiceMessageRecord.B2C_SERVICE_MESSAGE_RECORD.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return B2cServiceMessageRecord.B2C_SERVICE_MESSAGE_RECORD.MOBILE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field3() {
        return B2cServiceMessageRecord.B2C_SERVICE_MESSAGE_RECORD.REQUEST_ACTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return B2cServiceMessageRecord.B2C_SERVICE_MESSAGE_RECORD.IDENTITY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field5() {
        return B2cServiceMessageRecord.B2C_SERVICE_MESSAGE_RECORD.TEMPLATE_PLATFORM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return B2cServiceMessageRecord.B2C_SERVICE_MESSAGE_RECORD.TEMPLATE_CONTENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return B2cServiceMessageRecord.B2C_SERVICE_MESSAGE_RECORD.RESPONSE_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return B2cServiceMessageRecord.B2C_SERVICE_MESSAGE_RECORD.RESPONSE_MSG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return B2cServiceMessageRecord.B2C_SERVICE_MESSAGE_RECORD.PATH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field10() {
        return B2cServiceMessageRecord.B2C_SERVICE_MESSAGE_RECORD.PATH_QUERY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field11() {
        return B2cServiceMessageRecord.B2C_SERVICE_MESSAGE_RECORD.SEND_STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field12() {
        return B2cServiceMessageRecord.B2C_SERVICE_MESSAGE_RECORD.IS_VISIT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field13() {
        return B2cServiceMessageRecord.B2C_SERVICE_MESSAGE_RECORD.VISIT_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field14() {
        return B2cServiceMessageRecord.B2C_SERVICE_MESSAGE_RECORD.TEMPLATE_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field15() {
        return B2cServiceMessageRecord.B2C_SERVICE_MESSAGE_RECORD.LINK_IDENTITY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field16() {
        return B2cServiceMessageRecord.B2C_SERVICE_MESSAGE_RECORD.ADD_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getMobile();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component3() {
        return getRequestAction();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getIdentityId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component5() {
        return getTemplatePlatform();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getTemplateContent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getResponseCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component8() {
        return getResponseMsg();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component9() {
        return getPath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component10() {
        return getPathQuery();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component11() {
        return getSendStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component12() {
        return getIsVisit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component13() {
        return getVisitTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component14() {
        return getTemplateType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component15() {
        return getLinkIdentity();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component16() {
        return getAddTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getMobile();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value3() {
        return getRequestAction();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getIdentityId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value5() {
        return getTemplatePlatform();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getTemplateContent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getResponseCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getResponseMsg();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value9() {
        return getPath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value10() {
        return getPathQuery();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value11() {
        return getSendStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value12() {
        return getIsVisit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value13() {
        return getVisitTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value14() {
        return getTemplateType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value15() {
        return getLinkIdentity();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value16() {
        return getAddTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cServiceMessageRecordRecord value1(Integer value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cServiceMessageRecordRecord value2(String value) {
        setMobile(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cServiceMessageRecordRecord value3(Byte value) {
        setRequestAction(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cServiceMessageRecordRecord value4(String value) {
        setIdentityId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cServiceMessageRecordRecord value5(Byte value) {
        setTemplatePlatform(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cServiceMessageRecordRecord value6(String value) {
        setTemplateContent(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cServiceMessageRecordRecord value7(String value) {
        setResponseCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cServiceMessageRecordRecord value8(String value) {
        setResponseMsg(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cServiceMessageRecordRecord value9(String value) {
        setPath(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cServiceMessageRecordRecord value10(String value) {
        setPathQuery(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cServiceMessageRecordRecord value11(Byte value) {
        setSendStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cServiceMessageRecordRecord value12(Byte value) {
        setIsVisit(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cServiceMessageRecordRecord value13(Timestamp value) {
        setVisitTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cServiceMessageRecordRecord value14(Byte value) {
        setTemplateType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cServiceMessageRecordRecord value15(String value) {
        setLinkIdentity(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cServiceMessageRecordRecord value16(Timestamp value) {
        setAddTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cServiceMessageRecordRecord values(Integer value1, String value2, Byte value3, String value4, Byte value5, String value6, String value7, String value8, String value9, String value10, Byte value11, Byte value12, Timestamp value13, Byte value14, String value15, Timestamp value16) {
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
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached B2cServiceMessageRecordRecord
     */
    public B2cServiceMessageRecordRecord() {
        super(B2cServiceMessageRecord.B2C_SERVICE_MESSAGE_RECORD);
    }

    /**
     * Create a detached, initialised B2cServiceMessageRecordRecord
     */
    public B2cServiceMessageRecordRecord(Integer userId, String mobile, Byte requestAction, String identityId, Byte templatePlatform, String templateContent, String responseCode, String responseMsg, String path, String pathQuery, Byte sendStatus, Byte isVisit, Timestamp visitTime, Byte templateType, String linkIdentity, Timestamp addTime) {
        super(B2cServiceMessageRecord.B2C_SERVICE_MESSAGE_RECORD);

        set(0, userId);
        set(1, mobile);
        set(2, requestAction);
        set(3, identityId);
        set(4, templatePlatform);
        set(5, templateContent);
        set(6, responseCode);
        set(7, responseMsg);
        set(8, path);
        set(9, pathQuery);
        set(10, sendStatus);
        set(11, isVisit);
        set(12, visitTime);
        set(13, templateType);
        set(14, linkIdentity);
        set(15, addTime);
    }
}
