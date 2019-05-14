/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.B2cTemplateConfig;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record17;
import org.jooq.Row17;
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
public class B2cTemplateConfigRecord extends UpdatableRecordImpl<B2cTemplateConfigRecord> implements Record17<Integer, String, Byte, String, Integer, String, String, String, String, Byte, Byte, Timestamp, Timestamp, Byte, Timestamp, Timestamp, Integer> {

    private static final long serialVersionUID = -1536973855;

    /**
     * Setter for <code>mini_shop_471752.b2c_template_config.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_template_config.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_template_config.name</code>. 消息名称
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_template_config.name</code>. 消息名称
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_template_config.action</code>. 消息类型： 1： 业务处理通知 2： 商家活动通知 3： 活动加入成功提醒
     */
    public void setAction(Byte value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_template_config.action</code>. 消息类型： 1： 业务处理通知 2： 商家活动通知 3： 活动加入成功提醒
     */
    public Byte getAction() {
        return (Byte) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_template_config.title</code>. 业务标题
     */
    public void setTitle(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_template_config.title</code>. 业务标题
     */
    public String getTitle() {
        return (String) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_template_config.template_id</code>. 选择的模板ID
     */
    public void setTemplateId(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_template_config.template_id</code>. 选择的模板ID
     */
    public Integer getTemplateId() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_template_config.content</code>. 业务内容
     */
    public void setContent(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_template_config.content</code>. 业务内容
     */
    public String getContent() {
        return (String) get(5);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_template_config.page_link</code>. 页面链接
     */
    public void setPageLink(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_template_config.page_link</code>. 页面链接
     */
    public String getPageLink() {
        return (String) get(6);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_template_config.send_condition</code>. 筛选发送人条件
     */
    public void setSendCondition(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_template_config.send_condition</code>. 筛选发送人条件
     */
    public String getSendCondition() {
        return (String) get(7);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_template_config.to_user</code>. 发送人，逗号分隔，* 代表全部
     */
    public void setToUser(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_template_config.to_user</code>. 发送人，逗号分隔，* 代表全部
     */
    public String getToUser() {
        return (String) get(8);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_template_config.send_action</code>. 发送方式： 1：立即发送  2： 持续发送  3：定时发送
     */
    public void setSendAction(Byte value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_template_config.send_action</code>. 发送方式： 1：立即发送  2： 持续发送  3：定时发送
     */
    public Byte getSendAction() {
        return (Byte) get(9);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_template_config.send_status</code>. 发送完成 1： 完成
     */
    public void setSendStatus(Byte value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_template_config.send_status</code>. 发送完成 1： 完成
     */
    public Byte getSendStatus() {
        return (Byte) get(10);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_template_config.start_time</code>. 发送起始时间
     */
    public void setStartTime(Timestamp value) {
        set(11, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_template_config.start_time</code>. 发送起始时间
     */
    public Timestamp getStartTime() {
        return (Timestamp) get(11);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_template_config.end_time</code>. 发送终止时间
     */
    public void setEndTime(Timestamp value) {
        set(12, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_template_config.end_time</code>. 发送终止时间
     */
    public Timestamp getEndTime() {
        return (Timestamp) get(12);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_template_config.del_flag</code>. 删除标识
     */
    public void setDelFlag(Byte value) {
        set(13, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_template_config.del_flag</code>. 删除标识
     */
    public Byte getDelFlag() {
        return (Byte) get(13);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_template_config.add_time</code>.
     */
    public void setAddTime(Timestamp value) {
        set(14, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_template_config.add_time</code>.
     */
    public Timestamp getAddTime() {
        return (Timestamp) get(14);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_template_config.update_time</code>.
     */
    public void setUpdateTime(Timestamp value) {
        set(15, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_template_config.update_time</code>.
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(15);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_template_config.del_time</code>.
     */
    public void setDelTime(Integer value) {
        set(16, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_template_config.del_time</code>.
     */
    public Integer getDelTime() {
        return (Integer) get(16);
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
    // Record17 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row17<Integer, String, Byte, String, Integer, String, String, String, String, Byte, Byte, Timestamp, Timestamp, Byte, Timestamp, Timestamp, Integer> fieldsRow() {
        return (Row17) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row17<Integer, String, Byte, String, Integer, String, String, String, String, Byte, Byte, Timestamp, Timestamp, Byte, Timestamp, Timestamp, Integer> valuesRow() {
        return (Row17) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return B2cTemplateConfig.B2C_TEMPLATE_CONFIG.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return B2cTemplateConfig.B2C_TEMPLATE_CONFIG.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field3() {
        return B2cTemplateConfig.B2C_TEMPLATE_CONFIG.ACTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return B2cTemplateConfig.B2C_TEMPLATE_CONFIG.TITLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return B2cTemplateConfig.B2C_TEMPLATE_CONFIG.TEMPLATE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return B2cTemplateConfig.B2C_TEMPLATE_CONFIG.CONTENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return B2cTemplateConfig.B2C_TEMPLATE_CONFIG.PAGE_LINK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return B2cTemplateConfig.B2C_TEMPLATE_CONFIG.SEND_CONDITION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return B2cTemplateConfig.B2C_TEMPLATE_CONFIG.TO_USER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field10() {
        return B2cTemplateConfig.B2C_TEMPLATE_CONFIG.SEND_ACTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field11() {
        return B2cTemplateConfig.B2C_TEMPLATE_CONFIG.SEND_STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field12() {
        return B2cTemplateConfig.B2C_TEMPLATE_CONFIG.START_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field13() {
        return B2cTemplateConfig.B2C_TEMPLATE_CONFIG.END_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field14() {
        return B2cTemplateConfig.B2C_TEMPLATE_CONFIG.DEL_FLAG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field15() {
        return B2cTemplateConfig.B2C_TEMPLATE_CONFIG.ADD_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field16() {
        return B2cTemplateConfig.B2C_TEMPLATE_CONFIG.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field17() {
        return B2cTemplateConfig.B2C_TEMPLATE_CONFIG.DEL_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component3() {
        return getAction();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getTitle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component5() {
        return getTemplateId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getContent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getPageLink();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component8() {
        return getSendCondition();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component9() {
        return getToUser();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component10() {
        return getSendAction();
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
    public Timestamp component12() {
        return getStartTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component13() {
        return getEndTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component14() {
        return getDelFlag();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component15() {
        return getAddTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component16() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component17() {
        return getDelTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value3() {
        return getAction();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getTitle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getTemplateId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getContent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getPageLink();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getSendCondition();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value9() {
        return getToUser();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value10() {
        return getSendAction();
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
    public Timestamp value12() {
        return getStartTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value13() {
        return getEndTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value14() {
        return getDelFlag();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value15() {
        return getAddTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value16() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value17() {
        return getDelTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cTemplateConfigRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cTemplateConfigRecord value2(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cTemplateConfigRecord value3(Byte value) {
        setAction(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cTemplateConfigRecord value4(String value) {
        setTitle(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cTemplateConfigRecord value5(Integer value) {
        setTemplateId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cTemplateConfigRecord value6(String value) {
        setContent(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cTemplateConfigRecord value7(String value) {
        setPageLink(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cTemplateConfigRecord value8(String value) {
        setSendCondition(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cTemplateConfigRecord value9(String value) {
        setToUser(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cTemplateConfigRecord value10(Byte value) {
        setSendAction(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cTemplateConfigRecord value11(Byte value) {
        setSendStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cTemplateConfigRecord value12(Timestamp value) {
        setStartTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cTemplateConfigRecord value13(Timestamp value) {
        setEndTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cTemplateConfigRecord value14(Byte value) {
        setDelFlag(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cTemplateConfigRecord value15(Timestamp value) {
        setAddTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cTemplateConfigRecord value16(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cTemplateConfigRecord value17(Integer value) {
        setDelTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cTemplateConfigRecord values(Integer value1, String value2, Byte value3, String value4, Integer value5, String value6, String value7, String value8, String value9, Byte value10, Byte value11, Timestamp value12, Timestamp value13, Byte value14, Timestamp value15, Timestamp value16, Integer value17) {
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
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached B2cTemplateConfigRecord
     */
    public B2cTemplateConfigRecord() {
        super(B2cTemplateConfig.B2C_TEMPLATE_CONFIG);
    }

    /**
     * Create a detached, initialised B2cTemplateConfigRecord
     */
    public B2cTemplateConfigRecord(Integer id, String name, Byte action, String title, Integer templateId, String content, String pageLink, String sendCondition, String toUser, Byte sendAction, Byte sendStatus, Timestamp startTime, Timestamp endTime, Byte delFlag, Timestamp addTime, Timestamp updateTime, Integer delTime) {
        super(B2cTemplateConfig.B2C_TEMPLATE_CONFIG);

        set(0, id);
        set(1, name);
        set(2, action);
        set(3, title);
        set(4, templateId);
        set(5, content);
        set(6, pageLink);
        set(7, sendCondition);
        set(8, toUser);
        set(9, sendAction);
        set(10, sendStatus);
        set(11, startTime);
        set(12, endTime);
        set(13, delFlag);
        set(14, addTime);
        set(15, updateTime);
        set(16, delTime);
    }
}
