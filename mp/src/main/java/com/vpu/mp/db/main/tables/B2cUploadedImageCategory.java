/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.main.tables;


import com.vpu.mp.db.main.Indexes;
import com.vpu.mp.db.main.Keys;
import com.vpu.mp.db.main.MiniMain;
import com.vpu.mp.db.main.tables.records.B2cUploadedImageCategoryRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;
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
public class B2cUploadedImageCategory extends TableImpl<B2cUploadedImageCategoryRecord> {

    private static final long serialVersionUID = 1859747328;

    /**
     * The reference instance of <code>mini_main.b2c_uploaded_image_category</code>
     */
    public static final B2cUploadedImageCategory B2C_UPLOADED_IMAGE_CATEGORY = new B2cUploadedImageCategory();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<B2cUploadedImageCategoryRecord> getRecordType() {
        return B2cUploadedImageCategoryRecord.class;
    }

    /**
     * The column <code>mini_main.b2c_uploaded_image_category.img_cat_id</code>.
     */
    public final TableField<B2cUploadedImageCategoryRecord, UInteger> IMG_CAT_ID = createField("img_cat_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_main.b2c_uploaded_image_category.shop_id</code>. 店铺ID
     */
    public final TableField<B2cUploadedImageCategoryRecord, Integer> SHOP_ID = createField("shop_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "店铺ID");

    /**
     * The column <code>mini_main.b2c_uploaded_image_category.img_cat_name</code>.
     */
    public final TableField<B2cUploadedImageCategoryRecord, String> IMG_CAT_NAME = createField("img_cat_name", org.jooq.impl.SQLDataType.VARCHAR(60).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>mini_main.b2c_uploaded_image_category.img_cat_parent_id</code>.
     */
    public final TableField<B2cUploadedImageCategoryRecord, Integer> IMG_CAT_PARENT_ID = createField("img_cat_parent_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>mini_main.b2c_uploaded_image_category.create_time</code>.
     */
    public final TableField<B2cUploadedImageCategoryRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>mini_main.b2c_uploaded_image_category.cat_ids</code>. 层级ID串,逗号分隔
     */
    public final TableField<B2cUploadedImageCategoryRecord, String> CAT_IDS = createField("cat_ids", org.jooq.impl.SQLDataType.VARCHAR(191).nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.VARCHAR)), this, "层级ID串,逗号分隔");

    /**
     * The column <code>mini_main.b2c_uploaded_image_category.level</code>. 层级，0开始
     */
    public final TableField<B2cUploadedImageCategoryRecord, Byte> LEVEL = createField("level", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "层级，0开始");

    /**
     * The column <code>mini_main.b2c_uploaded_image_category.sort</code>. 排序优先级
     */
    public final TableField<B2cUploadedImageCategoryRecord, Integer> SORT = createField("sort", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.INTEGER)), this, "排序优先级");

    /**
     * Create a <code>mini_main.b2c_uploaded_image_category</code> table reference
     */
    public B2cUploadedImageCategory() {
        this(DSL.name("b2c_uploaded_image_category"), null);
    }

    /**
     * Create an aliased <code>mini_main.b2c_uploaded_image_category</code> table reference
     */
    public B2cUploadedImageCategory(String alias) {
        this(DSL.name(alias), B2C_UPLOADED_IMAGE_CATEGORY);
    }

    /**
     * Create an aliased <code>mini_main.b2c_uploaded_image_category</code> table reference
     */
    public B2cUploadedImageCategory(Name alias) {
        this(alias, B2C_UPLOADED_IMAGE_CATEGORY);
    }

    private B2cUploadedImageCategory(Name alias, Table<B2cUploadedImageCategoryRecord> aliased) {
        this(alias, aliased, null);
    }

    private B2cUploadedImageCategory(Name alias, Table<B2cUploadedImageCategoryRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> B2cUploadedImageCategory(Table<O> child, ForeignKey<O, B2cUploadedImageCategoryRecord> key) {
        super(child, key, B2C_UPLOADED_IMAGE_CATEGORY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return MiniMain.MINI_MAIN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.B2C_UPLOADED_IMAGE_CATEGORY_PRIMARY, Indexes.B2C_UPLOADED_IMAGE_CATEGORY_SHOP_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<B2cUploadedImageCategoryRecord, UInteger> getIdentity() {
        return Keys.IDENTITY_B2C_UPLOADED_IMAGE_CATEGORY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<B2cUploadedImageCategoryRecord> getPrimaryKey() {
        return Keys.KEY_B2C_UPLOADED_IMAGE_CATEGORY_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<B2cUploadedImageCategoryRecord>> getKeys() {
        return Arrays.<UniqueKey<B2cUploadedImageCategoryRecord>>asList(Keys.KEY_B2C_UPLOADED_IMAGE_CATEGORY_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUploadedImageCategory as(String alias) {
        return new B2cUploadedImageCategory(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUploadedImageCategory as(Name alias) {
        return new B2cUploadedImageCategory(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cUploadedImageCategory rename(String name) {
        return new B2cUploadedImageCategory(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cUploadedImageCategory rename(Name name) {
        return new B2cUploadedImageCategory(name, null);
    }
}
