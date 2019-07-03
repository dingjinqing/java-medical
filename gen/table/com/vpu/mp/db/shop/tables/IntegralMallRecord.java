/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables;


import com.vpu.mp.db.shop.Indexes;
import com.vpu.mp.db.shop.Keys;
import com.vpu.mp.db.shop.MiniShop_471752;
import com.vpu.mp.db.shop.tables.records.IntegralMallRecordRecord;

import java.math.BigDecimal;
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
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class IntegralMallRecord extends TableImpl<IntegralMallRecordRecord> {

    private static final long serialVersionUID = -1508654608;

    /**
     * The reference instance of <code>mini_shop_471752.b2c_integral_mall_record</code>
     */
    public static final IntegralMallRecord INTEGRAL_MALL_RECORD = new IntegralMallRecord();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<IntegralMallRecordRecord> getRecordType() {
        return IntegralMallRecordRecord.class;
    }

    /**
     * The column <code>mini_shop_471752.b2c_integral_mall_record.id</code>.
     */
    public final TableField<IntegralMallRecordRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_integral_mall_record.integral_mall_define_id</code>. 积分商城活动定义表ID
     */
    public final TableField<IntegralMallRecordRecord, Integer> INTEGRAL_MALL_DEFINE_ID = createField("integral_mall_define_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "积分商城活动定义表ID");

    /**
     * The column <code>mini_shop_471752.b2c_integral_mall_record.order_sn</code>. 订单编号
     */
    public final TableField<IntegralMallRecordRecord, String> ORDER_SN = createField("order_sn", org.jooq.impl.SQLDataType.VARCHAR(20), this, "订单编号");

    /**
     * The column <code>mini_shop_471752.b2c_integral_mall_record.user_id</code>. 用户ID
     */
    public final TableField<IntegralMallRecordRecord, Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "用户ID");

    /**
     * The column <code>mini_shop_471752.b2c_integral_mall_record.goods_id</code>. 商品ID
     */
    public final TableField<IntegralMallRecordRecord, Integer> GOODS_ID = createField("goods_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "商品ID");

    /**
     * The column <code>mini_shop_471752.b2c_integral_mall_record.product_id</code>. 产品规格ID
     */
    public final TableField<IntegralMallRecordRecord, Integer> PRODUCT_ID = createField("product_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "产品规格ID");

    /**
     * The column <code>mini_shop_471752.b2c_integral_mall_record.score</code>. 消费积分
     */
    public final TableField<IntegralMallRecordRecord, Integer> SCORE = createField("score", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "消费积分");

    /**
     * The column <code>mini_shop_471752.b2c_integral_mall_record.number</code>. 兑换数量
     */
    public final TableField<IntegralMallRecordRecord, Short> NUMBER = createField("number", org.jooq.impl.SQLDataType.SMALLINT.nullable(false), this, "兑换数量");

    /**
     * The column <code>mini_shop_471752.b2c_integral_mall_record.add_time</code>. 兑换时间
     */
    public final TableField<IntegralMallRecordRecord, Timestamp> ADD_TIME = createField("add_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "兑换时间");

    /**
     * The column <code>mini_shop_471752.b2c_integral_mall_record.money</code>. 消耗现金
     */
    public final TableField<IntegralMallRecordRecord, BigDecimal> MONEY = createField("money", org.jooq.impl.SQLDataType.DECIMAL(10, 2).nullable(false), this, "消耗现金");

    /**
     * Create a <code>mini_shop_471752.b2c_integral_mall_record</code> table reference
     */
    public IntegralMallRecord() {
        this(DSL.name("b2c_integral_mall_record"), null);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_integral_mall_record</code> table reference
     */
    public IntegralMallRecord(String alias) {
        this(DSL.name(alias), INTEGRAL_MALL_RECORD);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_integral_mall_record</code> table reference
     */
    public IntegralMallRecord(Name alias) {
        this(alias, INTEGRAL_MALL_RECORD);
    }

    private IntegralMallRecord(Name alias, Table<IntegralMallRecordRecord> aliased) {
        this(alias, aliased, null);
    }

    private IntegralMallRecord(Name alias, Table<IntegralMallRecordRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> IntegralMallRecord(Table<O> child, ForeignKey<O, IntegralMallRecordRecord> key) {
        super(child, key, INTEGRAL_MALL_RECORD);
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
        return Arrays.<Index>asList(Indexes.INTEGRAL_MALL_RECORD_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<IntegralMallRecordRecord, Integer> getIdentity() {
        return Keys.IDENTITY_INTEGRAL_MALL_RECORD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<IntegralMallRecordRecord> getPrimaryKey() {
        return Keys.KEY_B2C_INTEGRAL_MALL_RECORD_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<IntegralMallRecordRecord>> getKeys() {
        return Arrays.<UniqueKey<IntegralMallRecordRecord>>asList(Keys.KEY_B2C_INTEGRAL_MALL_RECORD_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IntegralMallRecord as(String alias) {
        return new IntegralMallRecord(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IntegralMallRecord as(Name alias) {
        return new IntegralMallRecord(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public IntegralMallRecord rename(String name) {
        return new IntegralMallRecord(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public IntegralMallRecord rename(Name name) {
        return new IntegralMallRecord(name, null);
    }
}
