/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables;


import com.vpu.mp.db.shop.Indexes;
import com.vpu.mp.db.shop.Keys;
import com.vpu.mp.db.shop.MiniShop_471752;
import com.vpu.mp.db.shop.tables.records.B2cDistributorLevelRecordRecord;

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
public class B2cDistributorLevelRecord extends TableImpl<B2cDistributorLevelRecordRecord> {

    private static final long serialVersionUID = 43690185;

    /**
     * The reference instance of <code>mini_shop_471752.b2c_distributor_level_record</code>
     */
    public static final B2cDistributorLevelRecord B2C_DISTRIBUTOR_LEVEL_RECORD = new B2cDistributorLevelRecord();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<B2cDistributorLevelRecordRecord> getRecordType() {
        return B2cDistributorLevelRecordRecord.class;
    }

    /**
     * The column <code>mini_shop_471752.b2c_distributor_level_record.id</code>.
     */
    public final TableField<B2cDistributorLevelRecordRecord, UInteger> ID = createField("id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_distributor_level_record.user_id</code>. 用户ID
     */
    public final TableField<B2cDistributorLevelRecordRecord, UInteger> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGERUNSIGNED)), this, "用户ID");

    /**
     * The column <code>mini_shop_471752.b2c_distributor_level_record.is_go_up</code>. 升降：0降，1升
     */
    public final TableField<B2cDistributorLevelRecordRecord, Byte> IS_GO_UP = createField("is_go_up", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "升降：0降，1升");

    /**
     * The column <code>mini_shop_471752.b2c_distributor_level_record.old_level</code>. 旧等级
     */
    public final TableField<B2cDistributorLevelRecordRecord, Byte> OLD_LEVEL = createField("old_level", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.TINYINT)), this, "旧等级");

    /**
     * The column <code>mini_shop_471752.b2c_distributor_level_record.old_level_name</code>. 旧等级名字
     */
    public final TableField<B2cDistributorLevelRecordRecord, String> OLD_LEVEL_NAME = createField("old_level_name", org.jooq.impl.SQLDataType.VARCHAR(32), this, "旧等级名字");

    /**
     * The column <code>mini_shop_471752.b2c_distributor_level_record.new_level</code>. 新等级
     */
    public final TableField<B2cDistributorLevelRecordRecord, Byte> NEW_LEVEL = createField("new_level", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.TINYINT)), this, "新等级");

    /**
     * The column <code>mini_shop_471752.b2c_distributor_level_record.new_level_name</code>. 新等级名字
     */
    public final TableField<B2cDistributorLevelRecordRecord, String> NEW_LEVEL_NAME = createField("new_level_name", org.jooq.impl.SQLDataType.VARCHAR(32), this, "新等级名字");

    /**
     * The column <code>mini_shop_471752.b2c_distributor_level_record.update_note</code>. 更新备注
     */
    public final TableField<B2cDistributorLevelRecordRecord, String> UPDATE_NOTE = createField("update_note", org.jooq.impl.SQLDataType.VARCHAR(120), this, "更新备注");

    /**
     * The column <code>mini_shop_471752.b2c_distributor_level_record.update_time</code>. 更新时间
     */
    public final TableField<B2cDistributorLevelRecordRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "更新时间");

    /**
     * Create a <code>mini_shop_471752.b2c_distributor_level_record</code> table reference
     */
    public B2cDistributorLevelRecord() {
        this(DSL.name("b2c_distributor_level_record"), null);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_distributor_level_record</code> table reference
     */
    public B2cDistributorLevelRecord(String alias) {
        this(DSL.name(alias), B2C_DISTRIBUTOR_LEVEL_RECORD);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_distributor_level_record</code> table reference
     */
    public B2cDistributorLevelRecord(Name alias) {
        this(alias, B2C_DISTRIBUTOR_LEVEL_RECORD);
    }

    private B2cDistributorLevelRecord(Name alias, Table<B2cDistributorLevelRecordRecord> aliased) {
        this(alias, aliased, null);
    }

    private B2cDistributorLevelRecord(Name alias, Table<B2cDistributorLevelRecordRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> B2cDistributorLevelRecord(Table<O> child, ForeignKey<O, B2cDistributorLevelRecordRecord> key) {
        super(child, key, B2C_DISTRIBUTOR_LEVEL_RECORD);
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
        return Arrays.<Index>asList(Indexes.B2C_DISTRIBUTOR_LEVEL_RECORD_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<B2cDistributorLevelRecordRecord, UInteger> getIdentity() {
        return Keys.IDENTITY_B2C_DISTRIBUTOR_LEVEL_RECORD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<B2cDistributorLevelRecordRecord> getPrimaryKey() {
        return Keys.KEY_B2C_DISTRIBUTOR_LEVEL_RECORD_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<B2cDistributorLevelRecordRecord>> getKeys() {
        return Arrays.<UniqueKey<B2cDistributorLevelRecordRecord>>asList(Keys.KEY_B2C_DISTRIBUTOR_LEVEL_RECORD_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cDistributorLevelRecord as(String alias) {
        return new B2cDistributorLevelRecord(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cDistributorLevelRecord as(Name alias) {
        return new B2cDistributorLevelRecord(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cDistributorLevelRecord rename(String name) {
        return new B2cDistributorLevelRecord(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cDistributorLevelRecord rename(Name name) {
        return new B2cDistributorLevelRecord(name, null);
    }
}
