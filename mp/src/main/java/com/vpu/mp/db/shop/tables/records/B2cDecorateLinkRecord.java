/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.B2cDecorateLink;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record11;
import org.jooq.Row11;
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
public class B2cDecorateLinkRecord extends UpdatableRecordImpl<B2cDecorateLinkRecord> implements Record11<Integer, Integer, Byte, String, String, String, String, Timestamp, Timestamp, Byte, Integer> {

    private static final long serialVersionUID = -1015316628;

    /**
     * Setter for <code>mini_shop_471752.b2c_decorate_link.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_decorate_link.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_decorate_link.shop_id</code>.
     */
    public void setShopId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_decorate_link.shop_id</code>.
     */
    public Integer getShopId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_decorate_link.link_action</code>. 1: 网页跳转  2： 小程序跳转
     */
    public void setLinkAction(Byte value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_decorate_link.link_action</code>. 1: 网页跳转  2： 小程序跳转
     */
    public Byte getLinkAction() {
        return (Byte) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_decorate_link.title</code>. 小程序名称
     */
    public void setTitle(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_decorate_link.title</code>. 小程序名称
     */
    public String getTitle() {
        return (String) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_decorate_link.path_name</code>. 链接名称
     */
    public void setPathName(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_decorate_link.path_name</code>. 链接名称
     */
    public String getPathName() {
        return (String) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_decorate_link.link_path</code>. 跳转链接
     */
    public void setLinkPath(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_decorate_link.link_path</code>. 跳转链接
     */
    public String getLinkPath() {
        return (String) get(5);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_decorate_link.appid</code>. 小程序appid
     */
    public void setAppid(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_decorate_link.appid</code>. 小程序appid
     */
    public String getAppid() {
        return (String) get(6);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_decorate_link.add_time</code>.
     */
    public void setAddTime(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_decorate_link.add_time</code>.
     */
    public Timestamp getAddTime() {
        return (Timestamp) get(7);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_decorate_link.up_time</code>.
     */
    public void setUpTime(Timestamp value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_decorate_link.up_time</code>.
     */
    public Timestamp getUpTime() {
        return (Timestamp) get(8);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_decorate_link.del_flag</code>. 0:未删除，1:已删除
     */
    public void setDelFlag(Byte value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_decorate_link.del_flag</code>. 0:未删除，1:已删除
     */
    public Byte getDelFlag() {
        return (Byte) get(9);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_decorate_link.del_time</code>.
     */
    public void setDelTime(Integer value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_decorate_link.del_time</code>.
     */
    public Integer getDelTime() {
        return (Integer) get(10);
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
    // Record11 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row11<Integer, Integer, Byte, String, String, String, String, Timestamp, Timestamp, Byte, Integer> fieldsRow() {
        return (Row11) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row11<Integer, Integer, Byte, String, String, String, String, Timestamp, Timestamp, Byte, Integer> valuesRow() {
        return (Row11) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return B2cDecorateLink.B2C_DECORATE_LINK.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return B2cDecorateLink.B2C_DECORATE_LINK.SHOP_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field3() {
        return B2cDecorateLink.B2C_DECORATE_LINK.LINK_ACTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return B2cDecorateLink.B2C_DECORATE_LINK.TITLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return B2cDecorateLink.B2C_DECORATE_LINK.PATH_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return B2cDecorateLink.B2C_DECORATE_LINK.LINK_PATH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return B2cDecorateLink.B2C_DECORATE_LINK.APPID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return B2cDecorateLink.B2C_DECORATE_LINK.ADD_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field9() {
        return B2cDecorateLink.B2C_DECORATE_LINK.UP_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field10() {
        return B2cDecorateLink.B2C_DECORATE_LINK.DEL_FLAG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field11() {
        return B2cDecorateLink.B2C_DECORATE_LINK.DEL_TIME;
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
    public Integer component2() {
        return getShopId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component3() {
        return getLinkAction();
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
    public String component5() {
        return getPathName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getLinkPath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getAppid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component8() {
        return getAddTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component9() {
        return getUpTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component10() {
        return getDelFlag();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component11() {
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
    public Integer value2() {
        return getShopId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value3() {
        return getLinkAction();
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
    public String value5() {
        return getPathName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getLinkPath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getAppid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value8() {
        return getAddTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value9() {
        return getUpTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value10() {
        return getDelFlag();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value11() {
        return getDelTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cDecorateLinkRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cDecorateLinkRecord value2(Integer value) {
        setShopId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cDecorateLinkRecord value3(Byte value) {
        setLinkAction(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cDecorateLinkRecord value4(String value) {
        setTitle(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cDecorateLinkRecord value5(String value) {
        setPathName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cDecorateLinkRecord value6(String value) {
        setLinkPath(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cDecorateLinkRecord value7(String value) {
        setAppid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cDecorateLinkRecord value8(Timestamp value) {
        setAddTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cDecorateLinkRecord value9(Timestamp value) {
        setUpTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cDecorateLinkRecord value10(Byte value) {
        setDelFlag(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cDecorateLinkRecord value11(Integer value) {
        setDelTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cDecorateLinkRecord values(Integer value1, Integer value2, Byte value3, String value4, String value5, String value6, String value7, Timestamp value8, Timestamp value9, Byte value10, Integer value11) {
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
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached B2cDecorateLinkRecord
     */
    public B2cDecorateLinkRecord() {
        super(B2cDecorateLink.B2C_DECORATE_LINK);
    }

    /**
     * Create a detached, initialised B2cDecorateLinkRecord
     */
    public B2cDecorateLinkRecord(Integer id, Integer shopId, Byte linkAction, String title, String pathName, String linkPath, String appid, Timestamp addTime, Timestamp upTime, Byte delFlag, Integer delTime) {
        super(B2cDecorateLink.B2C_DECORATE_LINK);

        set(0, id);
        set(1, shopId);
        set(2, linkAction);
        set(3, title);
        set(4, pathName);
        set(5, linkPath);
        set(6, appid);
        set(7, addTime);
        set(8, upTime);
        set(9, delFlag);
        set(10, delTime);
    }
}
