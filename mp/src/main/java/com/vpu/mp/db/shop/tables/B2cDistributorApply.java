/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables;


import com.vpu.mp.db.shop.Indexes;
import com.vpu.mp.db.shop.Keys;
import com.vpu.mp.db.shop.MiniShop_471752;
import com.vpu.mp.db.shop.tables.records.B2cDistributorApplyRecord;

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
import org.jooq.types.UByte;
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
public class B2cDistributorApply extends TableImpl<B2cDistributorApplyRecord> {

    private static final long serialVersionUID = -979396887;

    /**
     * The reference instance of <code>mini_shop_471752.b2c_distributor_apply</code>
     */
    public static final B2cDistributorApply B2C_DISTRIBUTOR_APPLY = new B2cDistributorApply();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<B2cDistributorApplyRecord> getRecordType() {
        return B2cDistributorApplyRecord.class;
    }

    /**
     * The column <code>mini_shop_471752.b2c_distributor_apply.id</code>.
     */
    public final TableField<B2cDistributorApplyRecord, UInteger> ID = createField("id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_distributor_apply.user_id</code>.
     */
    public final TableField<B2cDistributorApplyRecord, UInteger> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGERUNSIGNED)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_distributor_apply.add_time</code>.
     */
    public final TableField<B2cDistributorApplyRecord, Timestamp> ADD_TIME = createField("add_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>mini_shop_471752.b2c_distributor_apply.status</code>.
     */
    public final TableField<B2cDistributorApplyRecord, UByte> STATUS = createField("status", org.jooq.impl.SQLDataType.TINYINTUNSIGNED.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINTUNSIGNED)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_distributor_apply.msg</code>. 审核内容
     */
    public final TableField<B2cDistributorApplyRecord, String> MSG = createField("msg", org.jooq.impl.SQLDataType.CLOB, this, "审核内容");

    /**
     * The column <code>mini_shop_471752.b2c_distributor_apply.update_time</code>.
     */
    public final TableField<B2cDistributorApplyRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_distributor_apply.del_flag</code>.
     */
    public final TableField<B2cDistributorApplyRecord, Byte> DEL_FLAG = createField("del_flag", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_distributor_apply.activation_fields</code>. 审核校验
     */
    public final TableField<B2cDistributorApplyRecord, String> ACTIVATION_FIELDS = createField("activation_fields", org.jooq.impl.SQLDataType.VARCHAR(1000), this, "审核校验");

    /**
     * The column <code>mini_shop_471752.b2c_distributor_apply.config_fields</code>. 审核字段
     */
    public final TableField<B2cDistributorApplyRecord, String> CONFIG_FIELDS = createField("config_fields", org.jooq.impl.SQLDataType.VARCHAR(500), this, "审核字段");

    /**
     * Create a <code>mini_shop_471752.b2c_distributor_apply</code> table reference
     */
    public B2cDistributorApply() {
        this(DSL.name("b2c_distributor_apply"), null);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_distributor_apply</code> table reference
     */
    public B2cDistributorApply(String alias) {
        this(DSL.name(alias), B2C_DISTRIBUTOR_APPLY);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_distributor_apply</code> table reference
     */
    public B2cDistributorApply(Name alias) {
        this(alias, B2C_DISTRIBUTOR_APPLY);
    }

    private B2cDistributorApply(Name alias, Table<B2cDistributorApplyRecord> aliased) {
        this(alias, aliased, null);
    }

    private B2cDistributorApply(Name alias, Table<B2cDistributorApplyRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> B2cDistributorApply(Table<O> child, ForeignKey<O, B2cDistributorApplyRecord> key) {
        super(child, key, B2C_DISTRIBUTOR_APPLY);
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
        return Arrays.<Index>asList(Indexes.B2C_DISTRIBUTOR_APPLY_PRIMARY, Indexes.B2C_DISTRIBUTOR_APPLY_USER_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<B2cDistributorApplyRecord, UInteger> getIdentity() {
        return Keys.IDENTITY_B2C_DISTRIBUTOR_APPLY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<B2cDistributorApplyRecord> getPrimaryKey() {
        return Keys.KEY_B2C_DISTRIBUTOR_APPLY_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<B2cDistributorApplyRecord>> getKeys() {
        return Arrays.<UniqueKey<B2cDistributorApplyRecord>>asList(Keys.KEY_B2C_DISTRIBUTOR_APPLY_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cDistributorApply as(String alias) {
        return new B2cDistributorApply(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cDistributorApply as(Name alias) {
        return new B2cDistributorApply(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cDistributorApply rename(String name) {
        return new B2cDistributorApply(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cDistributorApply rename(Name name) {
        return new B2cDistributorApply(name, null);
    }
}
