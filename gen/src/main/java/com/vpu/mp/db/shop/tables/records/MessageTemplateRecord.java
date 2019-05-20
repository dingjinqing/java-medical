/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.MessageTemplate;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
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
public class MessageTemplateRecord extends UpdatableRecordImpl<MessageTemplateRecord> implements Record3<Integer, Byte, String> {

    private static final long serialVersionUID = -1319722177;

    /**
     * Setter for <code>mini_shop_1.b2c_message_template.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_message_template.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_message_template.action</code>. 消息类型： 1： 业务处理通知 2： 商家活动通知 3： 活动加入成功提醒
     */
    public void setAction(Byte value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_message_template.action</code>. 消息类型： 1： 业务处理通知 2： 商家活动通知 3： 活动加入成功提醒
     */
    public Byte getAction() {
        return (Byte) get(1);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_message_template.content</code>.
     */
    public void setContent(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_message_template.content</code>.
     */
    public String getContent() {
        return (String) get(2);
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
    // Record3 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Integer, Byte, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Integer, Byte, String> valuesRow() {
        return (Row3) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return MessageTemplate.MESSAGE_TEMPLATE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field2() {
        return MessageTemplate.MESSAGE_TEMPLATE.ACTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return MessageTemplate.MESSAGE_TEMPLATE.CONTENT;
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
    public Byte component2() {
        return getAction();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getContent();
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
    public Byte value2() {
        return getAction();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getContent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MessageTemplateRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MessageTemplateRecord value2(Byte value) {
        setAction(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MessageTemplateRecord value3(String value) {
        setContent(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MessageTemplateRecord values(Integer value1, Byte value2, String value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached MessageTemplateRecord
     */
    public MessageTemplateRecord() {
        super(MessageTemplate.MESSAGE_TEMPLATE);
    }

    /**
     * Create a detached, initialised MessageTemplateRecord
     */
    public MessageTemplateRecord(Integer id, Byte action, String content) {
        super(MessageTemplate.MESSAGE_TEMPLATE);

        set(0, id);
        set(1, action);
        set(2, content);
    }
}
