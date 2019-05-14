/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.B2cUploadedVideoCategory;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
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
public class B2cUploadedVideoCategoryRecord extends UpdatableRecordImpl<B2cUploadedVideoCategoryRecord> implements Record8<UInteger, Integer, String, Integer, Timestamp, String, Byte, Integer> {

    private static final long serialVersionUID = 1615463056;

    /**
     * Setter for <code>mini_shop_471752.b2c_uploaded_video_category.video_cat_id</code>.
     */
    public void setVideoCatId(UInteger value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_uploaded_video_category.video_cat_id</code>.
     */
    public UInteger getVideoCatId() {
        return (UInteger) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_uploaded_video_category.shop_id</code>. 店铺ID
     */
    public void setShopId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_uploaded_video_category.shop_id</code>. 店铺ID
     */
    public Integer getShopId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_uploaded_video_category.video_cat_name</code>.
     */
    public void setVideoCatName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_uploaded_video_category.video_cat_name</code>.
     */
    public String getVideoCatName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_uploaded_video_category.video_cat_parent_id</code>.
     */
    public void setVideoCatParentId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_uploaded_video_category.video_cat_parent_id</code>.
     */
    public Integer getVideoCatParentId() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_uploaded_video_category.create_time</code>.
     */
    public void setCreateTime(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_uploaded_video_category.create_time</code>.
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_uploaded_video_category.cat_ids</code>. 层级ID串,逗号分隔
     */
    public void setCatIds(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_uploaded_video_category.cat_ids</code>. 层级ID串,逗号分隔
     */
    public String getCatIds() {
        return (String) get(5);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_uploaded_video_category.level</code>. 层级，0开始
     */
    public void setLevel(Byte value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_uploaded_video_category.level</code>. 层级，0开始
     */
    public Byte getLevel() {
        return (Byte) get(6);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_uploaded_video_category.sort</code>. 排序优先级
     */
    public void setSort(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_uploaded_video_category.sort</code>. 排序优先级
     */
    public Integer getSort() {
        return (Integer) get(7);
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
    // Record8 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<UInteger, Integer, String, Integer, Timestamp, String, Byte, Integer> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<UInteger, Integer, String, Integer, Timestamp, String, Byte, Integer> valuesRow() {
        return (Row8) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UInteger> field1() {
        return B2cUploadedVideoCategory.B2C_UPLOADED_VIDEO_CATEGORY.VIDEO_CAT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return B2cUploadedVideoCategory.B2C_UPLOADED_VIDEO_CATEGORY.SHOP_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return B2cUploadedVideoCategory.B2C_UPLOADED_VIDEO_CATEGORY.VIDEO_CAT_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return B2cUploadedVideoCategory.B2C_UPLOADED_VIDEO_CATEGORY.VIDEO_CAT_PARENT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return B2cUploadedVideoCategory.B2C_UPLOADED_VIDEO_CATEGORY.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return B2cUploadedVideoCategory.B2C_UPLOADED_VIDEO_CATEGORY.CAT_IDS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field7() {
        return B2cUploadedVideoCategory.B2C_UPLOADED_VIDEO_CATEGORY.LEVEL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field8() {
        return B2cUploadedVideoCategory.B2C_UPLOADED_VIDEO_CATEGORY.SORT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger component1() {
        return getVideoCatId();
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
    public String component3() {
        return getVideoCatName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component4() {
        return getVideoCatParentId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component5() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getCatIds();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component7() {
        return getLevel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component8() {
        return getSort();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger value1() {
        return getVideoCatId();
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
    public String value3() {
        return getVideoCatName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getVideoCatParentId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value5() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getCatIds();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value7() {
        return getLevel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value8() {
        return getSort();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUploadedVideoCategoryRecord value1(UInteger value) {
        setVideoCatId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUploadedVideoCategoryRecord value2(Integer value) {
        setShopId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUploadedVideoCategoryRecord value3(String value) {
        setVideoCatName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUploadedVideoCategoryRecord value4(Integer value) {
        setVideoCatParentId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUploadedVideoCategoryRecord value5(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUploadedVideoCategoryRecord value6(String value) {
        setCatIds(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUploadedVideoCategoryRecord value7(Byte value) {
        setLevel(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUploadedVideoCategoryRecord value8(Integer value) {
        setSort(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUploadedVideoCategoryRecord values(UInteger value1, Integer value2, String value3, Integer value4, Timestamp value5, String value6, Byte value7, Integer value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached B2cUploadedVideoCategoryRecord
     */
    public B2cUploadedVideoCategoryRecord() {
        super(B2cUploadedVideoCategory.B2C_UPLOADED_VIDEO_CATEGORY);
    }

    /**
     * Create a detached, initialised B2cUploadedVideoCategoryRecord
     */
    public B2cUploadedVideoCategoryRecord(UInteger videoCatId, Integer shopId, String videoCatName, Integer videoCatParentId, Timestamp createTime, String catIds, Byte level, Integer sort) {
        super(B2cUploadedVideoCategory.B2C_UPLOADED_VIDEO_CATEGORY);

        set(0, videoCatId);
        set(1, shopId);
        set(2, videoCatName);
        set(3, videoCatParentId);
        set(4, createTime);
        set(5, catIds);
        set(6, level);
        set(7, sort);
    }
}
