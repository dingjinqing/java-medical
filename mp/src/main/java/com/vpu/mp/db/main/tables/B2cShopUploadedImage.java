/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.main.tables;


import com.vpu.mp.db.main.Indexes;
import com.vpu.mp.db.main.Keys;
import com.vpu.mp.db.main.MiniMain;
import com.vpu.mp.db.main.tables.records.B2cShopUploadedImageRecord;

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
public class B2cShopUploadedImage extends TableImpl<B2cShopUploadedImageRecord> {

    private static final long serialVersionUID = -1867906640;

    /**
     * The reference instance of <code>mini_main.b2c_shop_uploaded_image</code>
     */
    public static final B2cShopUploadedImage B2C_SHOP_UPLOADED_IMAGE = new B2cShopUploadedImage();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<B2cShopUploadedImageRecord> getRecordType() {
        return B2cShopUploadedImageRecord.class;
    }

    /**
     * The column <code>mini_main.b2c_shop_uploaded_image.img_id</code>.
     */
    public final TableField<B2cShopUploadedImageRecord, UInteger> IMG_ID = createField("img_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_main.b2c_shop_uploaded_image.img_type</code>.
     */
    public final TableField<B2cShopUploadedImageRecord, String> IMG_TYPE = createField("img_type", org.jooq.impl.SQLDataType.VARCHAR(60).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>mini_main.b2c_shop_uploaded_image.img_size</code>.
     */
    public final TableField<B2cShopUploadedImageRecord, UInteger> IMG_SIZE = createField("img_size", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGERUNSIGNED)), this, "");

    /**
     * The column <code>mini_main.b2c_shop_uploaded_image.img_name</code>. 图片名称，用于前端显示
     */
    public final TableField<B2cShopUploadedImageRecord, String> IMG_NAME = createField("img_name", org.jooq.impl.SQLDataType.VARCHAR(191).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "图片名称，用于前端显示");

    /**
     * The column <code>mini_main.b2c_shop_uploaded_image.img_orig_fname</code>.
     */
    public final TableField<B2cShopUploadedImageRecord, String> IMG_ORIG_FNAME = createField("img_orig_fname", org.jooq.impl.SQLDataType.VARCHAR(191).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>mini_main.b2c_shop_uploaded_image.img_path</code>.
     */
    public final TableField<B2cShopUploadedImageRecord, String> IMG_PATH = createField("img_path", org.jooq.impl.SQLDataType.VARCHAR(191).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>mini_main.b2c_shop_uploaded_image.img_url</code>.
     */
    public final TableField<B2cShopUploadedImageRecord, String> IMG_URL = createField("img_url", org.jooq.impl.SQLDataType.VARCHAR(191).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>mini_main.b2c_shop_uploaded_image.img_cat_id</code>. 图片分类
     */
    public final TableField<B2cShopUploadedImageRecord, Integer> IMG_CAT_ID = createField("img_cat_id", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "图片分类");

    /**
     * The column <code>mini_main.b2c_shop_uploaded_image.img_width</code>.
     */
    public final TableField<B2cShopUploadedImageRecord, Integer> IMG_WIDTH = createField("img_width", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>mini_main.b2c_shop_uploaded_image.img_height</code>.
     */
    public final TableField<B2cShopUploadedImageRecord, Integer> IMG_HEIGHT = createField("img_height", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>mini_main.b2c_shop_uploaded_image.is_refer</code>. 是否引用
     */
    public final TableField<B2cShopUploadedImageRecord, Byte> IS_REFER = createField("is_refer", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否引用");

    /**
     * The column <code>mini_main.b2c_shop_uploaded_image.upload_time</code>.
     */
    public final TableField<B2cShopUploadedImageRecord, Timestamp> UPLOAD_TIME = createField("upload_time", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>mini_main.b2c_shop_uploaded_image.sys_id</code>. 账户ID
     */
    public final TableField<B2cShopUploadedImageRecord, Integer> SYS_ID = createField("sys_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "账户ID");

    /**
     * The column <code>mini_main.b2c_shop_uploaded_image.shop_id</code>. 店铺ID
     */
    public final TableField<B2cShopUploadedImageRecord, Integer> SHOP_ID = createField("shop_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "店铺ID");

    /**
     * The column <code>mini_main.b2c_shop_uploaded_image.del_flag</code>.
     */
    public final TableField<B2cShopUploadedImageRecord, Byte> DEL_FLAG = createField("del_flag", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "");

    /**
     * Create a <code>mini_main.b2c_shop_uploaded_image</code> table reference
     */
    public B2cShopUploadedImage() {
        this(DSL.name("b2c_shop_uploaded_image"), null);
    }

    /**
     * Create an aliased <code>mini_main.b2c_shop_uploaded_image</code> table reference
     */
    public B2cShopUploadedImage(String alias) {
        this(DSL.name(alias), B2C_SHOP_UPLOADED_IMAGE);
    }

    /**
     * Create an aliased <code>mini_main.b2c_shop_uploaded_image</code> table reference
     */
    public B2cShopUploadedImage(Name alias) {
        this(alias, B2C_SHOP_UPLOADED_IMAGE);
    }

    private B2cShopUploadedImage(Name alias, Table<B2cShopUploadedImageRecord> aliased) {
        this(alias, aliased, null);
    }

    private B2cShopUploadedImage(Name alias, Table<B2cShopUploadedImageRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> B2cShopUploadedImage(Table<O> child, ForeignKey<O, B2cShopUploadedImageRecord> key) {
        super(child, key, B2C_SHOP_UPLOADED_IMAGE);
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
        return Arrays.<Index>asList(Indexes.B2C_SHOP_UPLOADED_IMAGE_IMG_ORIG_FNAME, Indexes.B2C_SHOP_UPLOADED_IMAGE_PRIMARY, Indexes.B2C_SHOP_UPLOADED_IMAGE_SHOP_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<B2cShopUploadedImageRecord, UInteger> getIdentity() {
        return Keys.IDENTITY_B2C_SHOP_UPLOADED_IMAGE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<B2cShopUploadedImageRecord> getPrimaryKey() {
        return Keys.KEY_B2C_SHOP_UPLOADED_IMAGE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<B2cShopUploadedImageRecord>> getKeys() {
        return Arrays.<UniqueKey<B2cShopUploadedImageRecord>>asList(Keys.KEY_B2C_SHOP_UPLOADED_IMAGE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cShopUploadedImage as(String alias) {
        return new B2cShopUploadedImage(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cShopUploadedImage as(Name alias) {
        return new B2cShopUploadedImage(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cShopUploadedImage rename(String name) {
        return new B2cShopUploadedImage(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cShopUploadedImage rename(Name name) {
        return new B2cShopUploadedImage(name, null);
    }
}
