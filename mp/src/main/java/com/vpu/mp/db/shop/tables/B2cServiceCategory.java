/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables;


import com.vpu.mp.db.shop.Indexes;
import com.vpu.mp.db.shop.Keys;
import com.vpu.mp.db.shop.MiniShop_471752;
import com.vpu.mp.db.shop.tables.records.B2cServiceCategoryRecord;

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
import org.jooq.types.UShort;


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
public class B2cServiceCategory extends TableImpl<B2cServiceCategoryRecord> {

    private static final long serialVersionUID = -136155514;

    /**
     * The reference instance of <code>mini_shop_471752.b2c_service_category</code>
     */
    public static final B2cServiceCategory B2C_SERVICE_CATEGORY = new B2cServiceCategory();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<B2cServiceCategoryRecord> getRecordType() {
        return B2cServiceCategoryRecord.class;
    }

    /**
     * The column <code>mini_shop_471752.b2c_service_category.cat_id</code>.
     */
    public final TableField<B2cServiceCategoryRecord, UShort> CAT_ID = createField("cat_id", org.jooq.impl.SQLDataType.SMALLINTUNSIGNED.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_service_category.cat_name</code>.
     */
    public final TableField<B2cServiceCategoryRecord, String> CAT_NAME = createField("cat_name", org.jooq.impl.SQLDataType.VARCHAR(90).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_service_category.store_id</code>.
     */
    public final TableField<B2cServiceCategoryRecord, Integer> STORE_ID = createField("store_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_service_category.create_time</code>.
     */
    public final TableField<B2cServiceCategoryRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>mini_shop_471752.b2c_service_category</code> table reference
     */
    public B2cServiceCategory() {
        this(DSL.name("b2c_service_category"), null);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_service_category</code> table reference
     */
    public B2cServiceCategory(String alias) {
        this(DSL.name(alias), B2C_SERVICE_CATEGORY);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_service_category</code> table reference
     */
    public B2cServiceCategory(Name alias) {
        this(alias, B2C_SERVICE_CATEGORY);
    }

    private B2cServiceCategory(Name alias, Table<B2cServiceCategoryRecord> aliased) {
        this(alias, aliased, null);
    }

    private B2cServiceCategory(Name alias, Table<B2cServiceCategoryRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> B2cServiceCategory(Table<O> child, ForeignKey<O, B2cServiceCategoryRecord> key) {
        super(child, key, B2C_SERVICE_CATEGORY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return MiniShop_471752.MINI_SHOP_471752;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.B2C_SERVICE_CATEGORY_CAT_NAME, Indexes.B2C_SERVICE_CATEGORY_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<B2cServiceCategoryRecord, UShort> getIdentity() {
        return Keys.IDENTITY_B2C_SERVICE_CATEGORY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<B2cServiceCategoryRecord> getPrimaryKey() {
        return Keys.KEY_B2C_SERVICE_CATEGORY_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<B2cServiceCategoryRecord>> getKeys() {
        return Arrays.<UniqueKey<B2cServiceCategoryRecord>>asList(Keys.KEY_B2C_SERVICE_CATEGORY_PRIMARY, Keys.KEY_B2C_SERVICE_CATEGORY_CAT_NAME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cServiceCategory as(String alias) {
        return new B2cServiceCategory(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cServiceCategory as(Name alias) {
        return new B2cServiceCategory(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cServiceCategory rename(String name) {
        return new B2cServiceCategory(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cServiceCategory rename(Name name) {
        return new B2cServiceCategory(name, null);
    }
}
