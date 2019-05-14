/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.main.tables;


import com.vpu.mp.db.main.Indexes;
import com.vpu.mp.db.main.Keys;
import com.vpu.mp.db.main.MiniMain;
import com.vpu.mp.db.main.tables.records.B2cGoodsBrandRecord;

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
public class B2cGoodsBrand extends TableImpl<B2cGoodsBrandRecord> {

    private static final long serialVersionUID = 1424238755;

    /**
     * The reference instance of <code>mini_main.b2c_goods_brand</code>
     */
    public static final B2cGoodsBrand B2C_GOODS_BRAND = new B2cGoodsBrand();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<B2cGoodsBrandRecord> getRecordType() {
        return B2cGoodsBrandRecord.class;
    }

    /**
     * The column <code>mini_main.b2c_goods_brand.id</code>.
     */
    public final TableField<B2cGoodsBrandRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_main.b2c_goods_brand.brand_name</code>. 品牌名称
     */
    public final TableField<B2cGoodsBrandRecord, String> BRAND_NAME = createField("brand_name", org.jooq.impl.SQLDataType.VARCHAR(500).nullable(false), this, "品牌名称");

    /**
     * The column <code>mini_main.b2c_goods_brand.e_name</code>. 品牌英文名称
     */
    public final TableField<B2cGoodsBrandRecord, String> E_NAME = createField("e_name", org.jooq.impl.SQLDataType.VARCHAR(500).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "品牌英文名称");

    /**
     * The column <code>mini_main.b2c_goods_brand.logo</code>. 品牌Logo
     */
    public final TableField<B2cGoodsBrandRecord, String> LOGO = createField("logo", org.jooq.impl.SQLDataType.VARCHAR(255), this, "品牌Logo");

    /**
     * The column <code>mini_main.b2c_goods_brand.first</code>. 优先级
     */
    public final TableField<B2cGoodsBrandRecord, Byte> FIRST = createField("first", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "优先级");

    /**
     * The column <code>mini_main.b2c_goods_brand.add_time</code>.
     */
    public final TableField<B2cGoodsBrandRecord, Timestamp> ADD_TIME = createField("add_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * The column <code>mini_main.b2c_goods_brand.update_time</code>.
     */
    public final TableField<B2cGoodsBrandRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>mini_main.b2c_goods_brand.is_delete</code>. 0为未删除 1为删除
     */
    public final TableField<B2cGoodsBrandRecord, Byte> IS_DELETE = createField("is_delete", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "0为未删除 1为删除");

    /**
     * The column <code>mini_main.b2c_goods_brand.desc</code>. 品牌介绍
     */
    public final TableField<B2cGoodsBrandRecord, String> DESC = createField("desc", org.jooq.impl.SQLDataType.CLOB, this, "品牌介绍");

    /**
     * Create a <code>mini_main.b2c_goods_brand</code> table reference
     */
    public B2cGoodsBrand() {
        this(DSL.name("b2c_goods_brand"), null);
    }

    /**
     * Create an aliased <code>mini_main.b2c_goods_brand</code> table reference
     */
    public B2cGoodsBrand(String alias) {
        this(DSL.name(alias), B2C_GOODS_BRAND);
    }

    /**
     * Create an aliased <code>mini_main.b2c_goods_brand</code> table reference
     */
    public B2cGoodsBrand(Name alias) {
        this(alias, B2C_GOODS_BRAND);
    }

    private B2cGoodsBrand(Name alias, Table<B2cGoodsBrandRecord> aliased) {
        this(alias, aliased, null);
    }

    private B2cGoodsBrand(Name alias, Table<B2cGoodsBrandRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> B2cGoodsBrand(Table<O> child, ForeignKey<O, B2cGoodsBrandRecord> key) {
        super(child, key, B2C_GOODS_BRAND);
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
        return Arrays.<Index>asList(Indexes.B2C_GOODS_BRAND_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<B2cGoodsBrandRecord, Integer> getIdentity() {
        return Keys.IDENTITY_B2C_GOODS_BRAND;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<B2cGoodsBrandRecord> getPrimaryKey() {
        return Keys.KEY_B2C_GOODS_BRAND_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<B2cGoodsBrandRecord>> getKeys() {
        return Arrays.<UniqueKey<B2cGoodsBrandRecord>>asList(Keys.KEY_B2C_GOODS_BRAND_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cGoodsBrand as(String alias) {
        return new B2cGoodsBrand(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cGoodsBrand as(Name alias) {
        return new B2cGoodsBrand(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cGoodsBrand rename(String name) {
        return new B2cGoodsBrand(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cGoodsBrand rename(Name name) {
        return new B2cGoodsBrand(name, null);
    }
}
