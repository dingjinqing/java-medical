/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables;


import com.vpu.mp.db.shop.Indexes;
import com.vpu.mp.db.shop.Keys;
import com.vpu.mp.db.shop.MiniShop_471752;
import com.vpu.mp.db.shop.tables.records.B2cShareRecordRecord;

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
public class B2cShareRecord extends TableImpl<B2cShareRecordRecord> {

    private static final long serialVersionUID = 596107222;

    /**
     * The reference instance of <code>mini_shop_471752.b2c_share_record</code>
     */
    public static final B2cShareRecord B2C_SHARE_RECORD = new B2cShareRecord();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<B2cShareRecordRecord> getRecordType() {
        return B2cShareRecordRecord.class;
    }

    /**
     * The column <code>mini_shop_471752.b2c_share_record.id</code>.
     */
    public final TableField<B2cShareRecordRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_share_record.activity_id</code>. 活动id或商品id
     */
    public final TableField<B2cShareRecordRecord, Integer> ACTIVITY_ID = createField("activity_id", org.jooq.impl.SQLDataType.INTEGER, this, "活动id或商品id");

    /**
     * The column <code>mini_shop_471752.b2c_share_record.user_id</code>.
     */
    public final TableField<B2cShareRecordRecord, Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>mini_shop_471752.b2c_share_record.activity_type</code>. 活动类型
     */
    public final TableField<B2cShareRecordRecord, Integer> ACTIVITY_TYPE = createField("activity_type", org.jooq.impl.SQLDataType.INTEGER, this, "活动类型");

    /**
     * The column <code>mini_shop_471752.b2c_share_record.add_time</code>. 浏览时间
     */
    public final TableField<B2cShareRecordRecord, Timestamp> ADD_TIME = createField("add_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "浏览时间");

    /**
     * The column <code>mini_shop_471752.b2c_share_record.count</code>. 次数
     */
    public final TableField<B2cShareRecordRecord, Integer> COUNT = createField("count", org.jooq.impl.SQLDataType.INTEGER, this, "次数");

    /**
     * Create a <code>mini_shop_471752.b2c_share_record</code> table reference
     */
    public B2cShareRecord() {
        this(DSL.name("b2c_share_record"), null);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_share_record</code> table reference
     */
    public B2cShareRecord(String alias) {
        this(DSL.name(alias), B2C_SHARE_RECORD);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_share_record</code> table reference
     */
    public B2cShareRecord(Name alias) {
        this(alias, B2C_SHARE_RECORD);
    }

    private B2cShareRecord(Name alias, Table<B2cShareRecordRecord> aliased) {
        this(alias, aliased, null);
    }

    private B2cShareRecord(Name alias, Table<B2cShareRecordRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> B2cShareRecord(Table<O> child, ForeignKey<O, B2cShareRecordRecord> key) {
        super(child, key, B2C_SHARE_RECORD);
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
        return Arrays.<Index>asList(Indexes.B2C_SHARE_RECORD_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<B2cShareRecordRecord, Integer> getIdentity() {
        return Keys.IDENTITY_B2C_SHARE_RECORD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<B2cShareRecordRecord> getPrimaryKey() {
        return Keys.KEY_B2C_SHARE_RECORD_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<B2cShareRecordRecord>> getKeys() {
        return Arrays.<UniqueKey<B2cShareRecordRecord>>asList(Keys.KEY_B2C_SHARE_RECORD_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cShareRecord as(String alias) {
        return new B2cShareRecord(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cShareRecord as(Name alias) {
        return new B2cShareRecord(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cShareRecord rename(String name) {
        return new B2cShareRecord(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cShareRecord rename(Name name) {
        return new B2cShareRecord(name, null);
    }
}
