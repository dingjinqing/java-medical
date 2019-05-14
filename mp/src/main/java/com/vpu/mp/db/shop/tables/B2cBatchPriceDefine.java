/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables;


import com.vpu.mp.db.shop.Indexes;
import com.vpu.mp.db.shop.Keys;
import com.vpu.mp.db.shop.MiniShop_471752;
import com.vpu.mp.db.shop.tables.records.B2cBatchPriceDefineRecord;

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
public class B2cBatchPriceDefine extends TableImpl<B2cBatchPriceDefineRecord> {

    private static final long serialVersionUID = -1014250016;

    /**
     * The reference instance of <code>mini_shop_471752.b2c_batch_price_define</code>
     */
    public static final B2cBatchPriceDefine B2C_BATCH_PRICE_DEFINE = new B2cBatchPriceDefine();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<B2cBatchPriceDefineRecord> getRecordType() {
        return B2cBatchPriceDefineRecord.class;
    }

    /**
     * The column <code>mini_shop_471752.b2c_batch_price_define.id</code>. 批量改价活动ID
     */
    public final TableField<B2cBatchPriceDefineRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "批量改价活动ID");

    /**
     * The column <code>mini_shop_471752.b2c_batch_price_define.name</code>. 活动名称
     */
    public final TableField<B2cBatchPriceDefineRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false), this, "活动名称");

    /**
     * The column <code>mini_shop_471752.b2c_batch_price_define.start_time</code>. 开始时间
     */
    public final TableField<B2cBatchPriceDefineRecord, Timestamp> START_TIME = createField("start_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "开始时间");

    /**
     * The column <code>mini_shop_471752.b2c_batch_price_define.end_time</code>. 结束时间
     */
    public final TableField<B2cBatchPriceDefineRecord, Timestamp> END_TIME = createField("end_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "结束时间");

    /**
     * The column <code>mini_shop_471752.b2c_batch_price_define.file_name</code>. 文件名称
     */
    public final TableField<B2cBatchPriceDefineRecord, String> FILE_NAME = createField("file_name", org.jooq.impl.SQLDataType.VARCHAR(200).nullable(false), this, "文件名称");

    /**
     * The column <code>mini_shop_471752.b2c_batch_price_define.del_flag</code>.
     */
    public final TableField<B2cBatchPriceDefineRecord, Byte> DEL_FLAG = createField("del_flag", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_batch_price_define.status</code>. 状态： 1：启用  0： 禁用
     */
    public final TableField<B2cBatchPriceDefineRecord, Byte> STATUS = createField("status", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.TINYINT)), this, "状态： 1：启用  0： 禁用");

    /**
     * The column <code>mini_shop_471752.b2c_batch_price_define.add_time</code>.
     */
    public final TableField<B2cBatchPriceDefineRecord, Timestamp> ADD_TIME = createField("add_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_batch_price_define.update_time</code>.
     */
    public final TableField<B2cBatchPriceDefineRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_batch_price_define.del_time</code>.
     */
    public final TableField<B2cBatchPriceDefineRecord, Integer> DEL_TIME = createField("del_time", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_batch_price_define.is_start_end</code>. 0:未开始，1：已开始，2：已结束（商品价格修改）
     */
    public final TableField<B2cBatchPriceDefineRecord, Byte> IS_START_END = createField("is_start_end", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "0:未开始，1：已开始，2：已结束（商品价格修改）");

    /**
     * Create a <code>mini_shop_471752.b2c_batch_price_define</code> table reference
     */
    public B2cBatchPriceDefine() {
        this(DSL.name("b2c_batch_price_define"), null);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_batch_price_define</code> table reference
     */
    public B2cBatchPriceDefine(String alias) {
        this(DSL.name(alias), B2C_BATCH_PRICE_DEFINE);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_batch_price_define</code> table reference
     */
    public B2cBatchPriceDefine(Name alias) {
        this(alias, B2C_BATCH_PRICE_DEFINE);
    }

    private B2cBatchPriceDefine(Name alias, Table<B2cBatchPriceDefineRecord> aliased) {
        this(alias, aliased, null);
    }

    private B2cBatchPriceDefine(Name alias, Table<B2cBatchPriceDefineRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> B2cBatchPriceDefine(Table<O> child, ForeignKey<O, B2cBatchPriceDefineRecord> key) {
        super(child, key, B2C_BATCH_PRICE_DEFINE);
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
        return Arrays.<Index>asList(Indexes.B2C_BATCH_PRICE_DEFINE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<B2cBatchPriceDefineRecord, Integer> getIdentity() {
        return Keys.IDENTITY_B2C_BATCH_PRICE_DEFINE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<B2cBatchPriceDefineRecord> getPrimaryKey() {
        return Keys.KEY_B2C_BATCH_PRICE_DEFINE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<B2cBatchPriceDefineRecord>> getKeys() {
        return Arrays.<UniqueKey<B2cBatchPriceDefineRecord>>asList(Keys.KEY_B2C_BATCH_PRICE_DEFINE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cBatchPriceDefine as(String alias) {
        return new B2cBatchPriceDefine(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cBatchPriceDefine as(Name alias) {
        return new B2cBatchPriceDefine(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cBatchPriceDefine rename(String name) {
        return new B2cBatchPriceDefine(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cBatchPriceDefine rename(Name name) {
        return new B2cBatchPriceDefine(name, null);
    }
}
