/*
 * This file is generated by jOOQ.
 */
package com.vpu.jmd.table.main.tables.records;



import java.sql.Timestamp;

import javax.annotation.Generated;

import com.vpu.jmd.table.main.tables.ShopUploadedImageCategory;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Row10;
import org.jooq.impl.UpdatableRecordImpl;


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
public class ShopUploadedImageCategoryRecord extends UpdatableRecordImpl<ShopUploadedImageCategoryRecord> implements Record10<Integer, Integer, String, Integer, String, Byte, Integer, Integer, Timestamp, Timestamp> {

    private static final long serialVersionUID = -331640980;

    /**
     * Setter for <code>mini_main.b2c_shop_uploaded_image_category.img_cat_id</code>.
     */
    public void setImgCatId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_main.b2c_shop_uploaded_image_category.img_cat_id</code>.
     */
    public Integer getImgCatId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_main.b2c_shop_uploaded_image_category.shop_id</code>. 店铺ID
     */
    public void setShopId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_main.b2c_shop_uploaded_image_category.shop_id</code>. 店铺ID
     */
    public Integer getShopId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_main.b2c_shop_uploaded_image_category.img_cat_name</code>.
     */
    public void setImgCatName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_main.b2c_shop_uploaded_image_category.img_cat_name</code>.
     */
    public String getImgCatName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>mini_main.b2c_shop_uploaded_image_category.img_cat_parent_id</code>.
     */
    public void setImgCatParentId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_main.b2c_shop_uploaded_image_category.img_cat_parent_id</code>.
     */
    public Integer getImgCatParentId() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>mini_main.b2c_shop_uploaded_image_category.cat_ids</code>. 层级ID串,逗号分隔
     */
    public void setCatIds(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_main.b2c_shop_uploaded_image_category.cat_ids</code>. 层级ID串,逗号分隔
     */
    public String getCatIds() {
        return (String) get(4);
    }

    /**
     * Setter for <code>mini_main.b2c_shop_uploaded_image_category.level</code>. 层级，0开始
     */
    public void setLevel(Byte value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_main.b2c_shop_uploaded_image_category.level</code>. 层级，0开始
     */
    public Byte getLevel() {
        return (Byte) get(5);
    }

    /**
     * Setter for <code>mini_main.b2c_shop_uploaded_image_category.sort</code>. 排序优先级
     */
    public void setSort(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_main.b2c_shop_uploaded_image_category.sort</code>. 排序优先级
     */
    public Integer getSort() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>mini_main.b2c_shop_uploaded_image_category.sys_id</code>. 账户ID
     */
    public void setSysId(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_main.b2c_shop_uploaded_image_category.sys_id</code>. 账户ID
     */
    public Integer getSysId() {
        return (Integer) get(7);
    }

    /**
     * Setter for <code>mini_main.b2c_shop_uploaded_image_category.update_time</code>. 更新时间
     */
    public void setUpdateTime(Timestamp value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_main.b2c_shop_uploaded_image_category.update_time</code>. 更新时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(8);
    }

    /**
     * Setter for <code>mini_main.b2c_shop_uploaded_image_category.create_time</code>.
     */
    public void setCreateTime(Timestamp value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_main.b2c_shop_uploaded_image_category.create_time</code>.
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(9);
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
    // Record10 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<Integer, Integer, String, Integer, String, Byte, Integer, Integer, Timestamp, Timestamp> fieldsRow() {
        return (Row10) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<Integer, Integer, String, Integer, String, Byte, Integer, Integer, Timestamp, Timestamp> valuesRow() {
        return (Row10) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return ShopUploadedImageCategory.SHOP_UPLOADED_IMAGE_CATEGORY.IMG_CAT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return ShopUploadedImageCategory.SHOP_UPLOADED_IMAGE_CATEGORY.SHOP_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return ShopUploadedImageCategory.SHOP_UPLOADED_IMAGE_CATEGORY.IMG_CAT_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return ShopUploadedImageCategory.SHOP_UPLOADED_IMAGE_CATEGORY.IMG_CAT_PARENT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return ShopUploadedImageCategory.SHOP_UPLOADED_IMAGE_CATEGORY.CAT_IDS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field6() {
        return ShopUploadedImageCategory.SHOP_UPLOADED_IMAGE_CATEGORY.LEVEL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return ShopUploadedImageCategory.SHOP_UPLOADED_IMAGE_CATEGORY.SORT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field8() {
        return ShopUploadedImageCategory.SHOP_UPLOADED_IMAGE_CATEGORY.SYS_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field9() {
        return ShopUploadedImageCategory.SHOP_UPLOADED_IMAGE_CATEGORY.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field10() {
        return ShopUploadedImageCategory.SHOP_UPLOADED_IMAGE_CATEGORY.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getImgCatId();
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
        return getImgCatName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component4() {
        return getImgCatParentId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getCatIds();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component6() {
        return getLevel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component7() {
        return getSort();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component8() {
        return getSysId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component9() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component10() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getImgCatId();
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
        return getImgCatName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getImgCatParentId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getCatIds();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value6() {
        return getLevel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getSort();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value8() {
        return getSysId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value9() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value10() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopUploadedImageCategoryRecord value1(Integer value) {
        setImgCatId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopUploadedImageCategoryRecord value2(Integer value) {
        setShopId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopUploadedImageCategoryRecord value3(String value) {
        setImgCatName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopUploadedImageCategoryRecord value4(Integer value) {
        setImgCatParentId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopUploadedImageCategoryRecord value5(String value) {
        setCatIds(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopUploadedImageCategoryRecord value6(Byte value) {
        setLevel(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopUploadedImageCategoryRecord value7(Integer value) {
        setSort(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopUploadedImageCategoryRecord value8(Integer value) {
        setSysId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopUploadedImageCategoryRecord value9(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopUploadedImageCategoryRecord value10(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopUploadedImageCategoryRecord values(Integer value1, Integer value2, String value3, Integer value4, String value5, Byte value6, Integer value7, Integer value8, Timestamp value9, Timestamp value10) {
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
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ShopUploadedImageCategoryRecord
     */
    public ShopUploadedImageCategoryRecord() {
        super(ShopUploadedImageCategory.SHOP_UPLOADED_IMAGE_CATEGORY);
    }

    /**
     * Create a detached, initialised ShopUploadedImageCategoryRecord
     */
    public ShopUploadedImageCategoryRecord(Integer imgCatId, Integer shopId, String imgCatName, Integer imgCatParentId, String catIds, Byte level, Integer sort, Integer sysId, Timestamp updateTime, Timestamp createTime) {
        super(ShopUploadedImageCategory.SHOP_UPLOADED_IMAGE_CATEGORY);

        set(0, imgCatId);
        set(1, shopId);
        set(2, imgCatName);
        set(3, imgCatParentId);
        set(4, catIds);
        set(5, level);
        set(6, sort);
        set(7, sysId);
        set(8, updateTime);
        set(9, createTime);
    }
}