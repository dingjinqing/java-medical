/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.WxpMessage;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record16;
import org.jooq.Row16;
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
public class WxpMessageRecord extends UpdatableRecordImpl<WxpMessageRecord> implements Record16<UInteger, Integer, Byte, String, String, String, String, Byte, String, String, Integer, Integer, Byte, Byte, Integer, Timestamp> {

    private static final long serialVersionUID = 209941855;

    /**
     * Setter for <code>mini_shop_1.b2c_wxp_message.rec_id</code>.
     */
    public void setRecId(UInteger value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_wxp_message.rec_id</code>.
     */
    public UInteger getRecId() {
        return (UInteger) get(0);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_wxp_message.shop_id</code>. 店铺ID
     */
    public void setShopId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_wxp_message.shop_id</code>. 店铺ID
     */
    public Integer getShopId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_wxp_message.type</code>. 消息收发类型 0：接收，1自动回复 2 客服回复
     */
    public void setType(Byte value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_wxp_message.type</code>. 消息收发类型 0：接收，1自动回复 2 客服回复
     */
    public Byte getType() {
        return (Byte) get(2);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_wxp_message.customer_user</code>.
     */
    public void setCustomerUser(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_wxp_message.customer_user</code>.
     */
    public String getCustomerUser() {
        return (String) get(3);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_wxp_message.msgtype</code>. type=0时，取值text,image,voice,video,location,link,subscribe,unsubscribe,SCAN,LOCATION,CLICK,VIEW
     */
    public void setMsgtype(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_wxp_message.msgtype</code>. type=0时，取值text,image,voice,video,location,link,subscribe,unsubscribe,SCAN,LOCATION,CLICK,VIEW
     */
    public String getMsgtype() {
        return (String) get(4);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_wxp_message.content</code>. 原始发送或接收的内容，type=0 1时，是xmldata，2时是json数据
     */
    public void setContent(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_wxp_message.content</code>. 原始发送或接收的内容，type=0 1时，是xmldata，2时是json数据
     */
    public String getContent() {
        return (String) get(5);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_wxp_message.menu_name</code>. 菜单名称,type=0 &amp;&amp; msgtype=CLICK,VIEW有效
     */
    public void setMenuName(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_wxp_message.menu_name</code>. 菜单名称,type=0 &amp;&amp; msgtype=CLICK,VIEW有效
     */
    public String getMenuName() {
        return (String) get(6);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_wxp_message.res_cfg_type</code>. 0 关注时回复，1关键词回复，2默认回复,3菜单回复
     */
    public void setResCfgType(Byte value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_wxp_message.res_cfg_type</code>. 0 关注时回复，1关键词回复，2默认回复,3菜单回复
     */
    public Byte getResCfgType() {
        return (Byte) get(7);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_wxp_message.res_key_words</code>. 关键词,关键词回复有效
     */
    public void setResKeyWords(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_wxp_message.res_key_words</code>. 关键词,关键词回复有效
     */
    public String getResKeyWords() {
        return (String) get(8);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_wxp_message.res_content</code>. 回复时的回复内容
     */
    public void setResContent(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_wxp_message.res_content</code>. 回复时的回复内容
     */
    public String getResContent() {
        return (String) get(9);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_wxp_message.res_rec_id</code>. 素材wxp_material的rec_id
     */
    public void setResRecId(Integer value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_wxp_message.res_rec_id</code>. 素材wxp_material的rec_id
     */
    public Integer getResRecId() {
        return (Integer) get(10);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_wxp_message.res_parent_rec_id</code>. 对某个用户某条消息进行回复，这个是消息对的rec_id
     */
    public void setResParentRecId(Integer value) {
        set(11, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_wxp_message.res_parent_rec_id</code>. 对某个用户某条消息进行回复，这个是消息对的rec_id
     */
    public Integer getResParentRecId() {
        return (Integer) get(11);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_wxp_message.is_responsed</code>. 对type=0有意义，当有客服消息回复了，代表已回复
     */
    public void setIsResponsed(Byte value) {
        set(12, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_wxp_message.is_responsed</code>. 对type=0有意义，当有客服消息回复了，代表已回复
     */
    public Byte getIsResponsed() {
        return (Byte) get(12);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_wxp_message.star_flag</code>. 是否星标收藏
     */
    public void setStarFlag(Byte value) {
        set(13, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_wxp_message.star_flag</code>. 是否星标收藏
     */
    public Byte getStarFlag() {
        return (Byte) get(13);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_wxp_message.micro_id</code>. 所属微信公众号ID,关联b2c_wxp_list的micro_id
     */
    public void setMicroId(Integer value) {
        set(14, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_wxp_message.micro_id</code>. 所属微信公众号ID,关联b2c_wxp_list的micro_id
     */
    public Integer getMicroId() {
        return (Integer) get(14);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_wxp_message.create_time</code>.
     */
    public void setCreateTime(Timestamp value) {
        set(15, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_wxp_message.create_time</code>.
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(15);
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
    // Record16 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row16<UInteger, Integer, Byte, String, String, String, String, Byte, String, String, Integer, Integer, Byte, Byte, Integer, Timestamp> fieldsRow() {
        return (Row16) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row16<UInteger, Integer, Byte, String, String, String, String, Byte, String, String, Integer, Integer, Byte, Byte, Integer, Timestamp> valuesRow() {
        return (Row16) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UInteger> field1() {
        return WxpMessage.WXP_MESSAGE.REC_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return WxpMessage.WXP_MESSAGE.SHOP_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field3() {
        return WxpMessage.WXP_MESSAGE.TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return WxpMessage.WXP_MESSAGE.CUSTOMER_USER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return WxpMessage.WXP_MESSAGE.MSGTYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return WxpMessage.WXP_MESSAGE.CONTENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return WxpMessage.WXP_MESSAGE.MENU_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field8() {
        return WxpMessage.WXP_MESSAGE.RES_CFG_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return WxpMessage.WXP_MESSAGE.RES_KEY_WORDS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field10() {
        return WxpMessage.WXP_MESSAGE.RES_CONTENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field11() {
        return WxpMessage.WXP_MESSAGE.RES_REC_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field12() {
        return WxpMessage.WXP_MESSAGE.RES_PARENT_REC_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field13() {
        return WxpMessage.WXP_MESSAGE.IS_RESPONSED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field14() {
        return WxpMessage.WXP_MESSAGE.STAR_FLAG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field15() {
        return WxpMessage.WXP_MESSAGE.MICRO_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field16() {
        return WxpMessage.WXP_MESSAGE.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger component1() {
        return getRecId();
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
        return getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getCustomerUser();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getMsgtype();
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
        return getMenuName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component8() {
        return getResCfgType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component9() {
        return getResKeyWords();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component10() {
        return getResContent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component11() {
        return getResRecId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component12() {
        return getResParentRecId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component13() {
        return getIsResponsed();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component14() {
        return getStarFlag();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component15() {
        return getMicroId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component16() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger value1() {
        return getRecId();
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
        return getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getCustomerUser();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getMsgtype();
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
        return getMenuName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value8() {
        return getResCfgType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value9() {
        return getResKeyWords();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value10() {
        return getResContent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value11() {
        return getResRecId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value12() {
        return getResParentRecId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value13() {
        return getIsResponsed();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value14() {
        return getStarFlag();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value15() {
        return getMicroId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value16() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WxpMessageRecord value1(UInteger value) {
        setRecId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WxpMessageRecord value2(Integer value) {
        setShopId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WxpMessageRecord value3(Byte value) {
        setType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WxpMessageRecord value4(String value) {
        setCustomerUser(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WxpMessageRecord value5(String value) {
        setMsgtype(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WxpMessageRecord value6(String value) {
        setContent(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WxpMessageRecord value7(String value) {
        setMenuName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WxpMessageRecord value8(Byte value) {
        setResCfgType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WxpMessageRecord value9(String value) {
        setResKeyWords(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WxpMessageRecord value10(String value) {
        setResContent(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WxpMessageRecord value11(Integer value) {
        setResRecId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WxpMessageRecord value12(Integer value) {
        setResParentRecId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WxpMessageRecord value13(Byte value) {
        setIsResponsed(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WxpMessageRecord value14(Byte value) {
        setStarFlag(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WxpMessageRecord value15(Integer value) {
        setMicroId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WxpMessageRecord value16(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WxpMessageRecord values(UInteger value1, Integer value2, Byte value3, String value4, String value5, String value6, String value7, Byte value8, String value9, String value10, Integer value11, Integer value12, Byte value13, Byte value14, Integer value15, Timestamp value16) {
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
     * Create a detached WxpMessageRecord
     */
    public WxpMessageRecord() {
        super(WxpMessage.WXP_MESSAGE);
    }

    /**
     * Create a detached, initialised WxpMessageRecord
     */
    public WxpMessageRecord(UInteger recId, Integer shopId, Byte type, String customerUser, String msgtype, String content, String menuName, Byte resCfgType, String resKeyWords, String resContent, Integer resRecId, Integer resParentRecId, Byte isResponsed, Byte starFlag, Integer microId, Timestamp createTime) {
        super(WxpMessage.WXP_MESSAGE);

        set(0, recId);
        set(1, shopId);
        set(2, type);
        set(3, customerUser);
        set(4, msgtype);
        set(5, content);
        set(6, menuName);
        set(7, resCfgType);
        set(8, resKeyWords);
        set(9, resContent);
        set(10, resRecId);
        set(11, resParentRecId);
        set(12, isResponsed);
        set(13, starFlag);
        set(14, microId);
        set(15, createTime);
    }
}
