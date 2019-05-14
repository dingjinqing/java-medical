/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables;


import com.vpu.mp.db.shop.Indexes;
import com.vpu.mp.db.shop.Keys;
import com.vpu.mp.db.shop.MiniShop_471752;
import com.vpu.mp.db.shop.tables.records.B2cSpecValsRecord;

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
public class B2cSpecVals extends TableImpl<B2cSpecValsRecord> {

    private static final long serialVersionUID = -1681358060;

    /**
     * The reference instance of <code>mini_shop_471752.b2c_spec_vals</code>
     */
    public static final B2cSpecVals B2C_SPEC_VALS = new B2cSpecVals();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<B2cSpecValsRecord> getRecordType() {
        return B2cSpecValsRecord.class;
    }

    /**
     * The column <code>mini_shop_471752.b2c_spec_vals.specvalid</code>.
     */
    public final TableField<B2cSpecValsRecord, UInteger> SPECVALID = createField("specvalid", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_spec_vals.spec_id</code>.
     */
    public final TableField<B2cSpecValsRecord, Integer> SPEC_ID = createField("spec_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_spec_vals.specvalname</code>.
     */
    public final TableField<B2cSpecValsRecord, String> SPECVALNAME = createField("specvalname", org.jooq.impl.SQLDataType.VARCHAR(60).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_spec_vals.del_flag</code>.
     */
    public final TableField<B2cSpecValsRecord, Byte> DEL_FLAG = createField("del_flag", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_spec_vals.shop_id</code>. 店铺ID
     */
    public final TableField<B2cSpecValsRecord, Integer> SHOP_ID = createField("shop_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "店铺ID");

    /**
     * Create a <code>mini_shop_471752.b2c_spec_vals</code> table reference
     */
    public B2cSpecVals() {
        this(DSL.name("b2c_spec_vals"), null);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_spec_vals</code> table reference
     */
    public B2cSpecVals(String alias) {
        this(DSL.name(alias), B2C_SPEC_VALS);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_spec_vals</code> table reference
     */
    public B2cSpecVals(Name alias) {
        this(alias, B2C_SPEC_VALS);
    }

    private B2cSpecVals(Name alias, Table<B2cSpecValsRecord> aliased) {
        this(alias, aliased, null);
    }

    private B2cSpecVals(Name alias, Table<B2cSpecValsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> B2cSpecVals(Table<O> child, ForeignKey<O, B2cSpecValsRecord> key) {
        super(child, key, B2C_SPEC_VALS);
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
        return Arrays.<Index>asList(Indexes.B2C_SPEC_VALS_PRIMARY, Indexes.B2C_SPEC_VALS_SPEC_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<B2cSpecValsRecord, UInteger> getIdentity() {
        return Keys.IDENTITY_B2C_SPEC_VALS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<B2cSpecValsRecord> getPrimaryKey() {
        return Keys.KEY_B2C_SPEC_VALS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<B2cSpecValsRecord>> getKeys() {
        return Arrays.<UniqueKey<B2cSpecValsRecord>>asList(Keys.KEY_B2C_SPEC_VALS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cSpecVals as(String alias) {
        return new B2cSpecVals(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cSpecVals as(Name alias) {
        return new B2cSpecVals(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cSpecVals rename(String name) {
        return new B2cSpecVals(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cSpecVals rename(Name name) {
        return new B2cSpecVals(name, null);
    }
}
