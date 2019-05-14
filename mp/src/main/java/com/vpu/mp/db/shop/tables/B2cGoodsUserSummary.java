/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables;


import com.vpu.mp.db.shop.Indexes;
import com.vpu.mp.db.shop.Keys;
import com.vpu.mp.db.shop.MiniShop_471752;
import com.vpu.mp.db.shop.tables.records.B2cGoodsUserSummaryRecord;

import java.sql.Date;
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
public class B2cGoodsUserSummary extends TableImpl<B2cGoodsUserSummaryRecord> {

    private static final long serialVersionUID = 257503376;

    /**
     * The reference instance of <code>mini_shop_471752.b2c_goods_user_summary</code>
     */
    public static final B2cGoodsUserSummary B2C_GOODS_USER_SUMMARY = new B2cGoodsUserSummary();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<B2cGoodsUserSummaryRecord> getRecordType() {
        return B2cGoodsUserSummaryRecord.class;
    }

    /**
     * The column <code>mini_shop_471752.b2c_goods_user_summary.id</code>.
     */
    public final TableField<B2cGoodsUserSummaryRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_goods_user_summary.ref_date</code>. 2018-09-04
     */
    public final TableField<B2cGoodsUserSummaryRecord, Date> REF_DATE = createField("ref_date", org.jooq.impl.SQLDataType.DATE, this, "2018-09-04");

    /**
     * The column <code>mini_shop_471752.b2c_goods_user_summary.type</code>. 1,7,30
     */
    public final TableField<B2cGoodsUserSummaryRecord, Byte> TYPE = createField("type", org.jooq.impl.SQLDataType.TINYINT, this, "1,7,30");

    /**
     * The column <code>mini_shop_471752.b2c_goods_user_summary.goods_id_number</code>. 在售商品数
     */
    public final TableField<B2cGoodsUserSummaryRecord, Integer> GOODS_ID_NUMBER = createField("goods_id_number", org.jooq.impl.SQLDataType.INTEGER, this, "在售商品数");

    /**
     * The column <code>mini_shop_471752.b2c_goods_user_summary.prd_id_number</code>. 在售规格数
     */
    public final TableField<B2cGoodsUserSummaryRecord, Integer> PRD_ID_NUMBER = createField("prd_id_number", org.jooq.impl.SQLDataType.INTEGER, this, "在售规格数");

    /**
     * The column <code>mini_shop_471752.b2c_goods_user_summary.goods_id_visit</code>. 访问商品数
     */
    public final TableField<B2cGoodsUserSummaryRecord, Integer> GOODS_ID_VISIT = createField("goods_id_visit", org.jooq.impl.SQLDataType.INTEGER, this, "访问商品数");

    /**
     * The column <code>mini_shop_471752.b2c_goods_user_summary.goods_user_visit</code>. UV
     */
    public final TableField<B2cGoodsUserSummaryRecord, Integer> GOODS_USER_VISIT = createField("goods_user_visit", org.jooq.impl.SQLDataType.INTEGER, this, "UV");

    /**
     * The column <code>mini_shop_471752.b2c_goods_user_summary.goods_visit</code>. goods pv
     */
    public final TableField<B2cGoodsUserSummaryRecord, Integer> GOODS_VISIT = createField("goods_visit", org.jooq.impl.SQLDataType.INTEGER, this, "goods pv");

    /**
     * The column <code>mini_shop_471752.b2c_goods_user_summary.cart_user_number</code>. 加购人数
     */
    public final TableField<B2cGoodsUserSummaryRecord, Integer> CART_USER_NUMBER = createField("cart_user_number", org.jooq.impl.SQLDataType.INTEGER, this, "加购人数");

    /**
     * The column <code>mini_shop_471752.b2c_goods_user_summary.cart_goods_number</code>. 加购件数
     */
    public final TableField<B2cGoodsUserSummaryRecord, Integer> CART_GOODS_NUMBER = createField("cart_goods_number", org.jooq.impl.SQLDataType.INTEGER, this, "加购件数");

    /**
     * The column <code>mini_shop_471752.b2c_goods_user_summary.paid_goods_number</code>. 付款人数
     */
    public final TableField<B2cGoodsUserSummaryRecord, Integer> PAID_GOODS_NUMBER = createField("paid_goods_number", org.jooq.impl.SQLDataType.INTEGER, this, "付款人数");

    /**
     * The column <code>mini_shop_471752.b2c_goods_user_summary.paid_user_number</code>. 付费用户数
     */
    public final TableField<B2cGoodsUserSummaryRecord, Integer> PAID_USER_NUMBER = createField("paid_user_number", org.jooq.impl.SQLDataType.INTEGER, this, "付费用户数");

    /**
     * The column <code>mini_shop_471752.b2c_goods_user_summary.add_time</code>.
     */
    public final TableField<B2cGoodsUserSummaryRecord, Timestamp> ADD_TIME = createField("add_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * Create a <code>mini_shop_471752.b2c_goods_user_summary</code> table reference
     */
    public B2cGoodsUserSummary() {
        this(DSL.name("b2c_goods_user_summary"), null);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_goods_user_summary</code> table reference
     */
    public B2cGoodsUserSummary(String alias) {
        this(DSL.name(alias), B2C_GOODS_USER_SUMMARY);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_goods_user_summary</code> table reference
     */
    public B2cGoodsUserSummary(Name alias) {
        this(alias, B2C_GOODS_USER_SUMMARY);
    }

    private B2cGoodsUserSummary(Name alias, Table<B2cGoodsUserSummaryRecord> aliased) {
        this(alias, aliased, null);
    }

    private B2cGoodsUserSummary(Name alias, Table<B2cGoodsUserSummaryRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> B2cGoodsUserSummary(Table<O> child, ForeignKey<O, B2cGoodsUserSummaryRecord> key) {
        super(child, key, B2C_GOODS_USER_SUMMARY);
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
        return Arrays.<Index>asList(Indexes.B2C_GOODS_USER_SUMMARY_PRIMARY, Indexes.B2C_GOODS_USER_SUMMARY_REF_TYPE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<B2cGoodsUserSummaryRecord, Integer> getIdentity() {
        return Keys.IDENTITY_B2C_GOODS_USER_SUMMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<B2cGoodsUserSummaryRecord> getPrimaryKey() {
        return Keys.KEY_B2C_GOODS_USER_SUMMARY_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<B2cGoodsUserSummaryRecord>> getKeys() {
        return Arrays.<UniqueKey<B2cGoodsUserSummaryRecord>>asList(Keys.KEY_B2C_GOODS_USER_SUMMARY_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cGoodsUserSummary as(String alias) {
        return new B2cGoodsUserSummary(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cGoodsUserSummary as(Name alias) {
        return new B2cGoodsUserSummary(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cGoodsUserSummary rename(String name) {
        return new B2cGoodsUserSummary(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cGoodsUserSummary rename(Name name) {
        return new B2cGoodsUserSummary(name, null);
    }
}
