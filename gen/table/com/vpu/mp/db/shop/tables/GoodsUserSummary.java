/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables;


import com.vpu.mp.db.shop.Indexes;
import com.vpu.mp.db.shop.Keys;
import com.vpu.mp.db.shop.MiniShop_471752;
import com.vpu.mp.db.shop.tables.records.GoodsUserSummaryRecord;

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
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class GoodsUserSummary extends TableImpl<GoodsUserSummaryRecord> {

    private static final long serialVersionUID = 754471044;

    /**
     * The reference instance of <code>mini_shop_471752.b2c_goods_user_summary</code>
     */
    public static final GoodsUserSummary GOODS_USER_SUMMARY = new GoodsUserSummary();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<GoodsUserSummaryRecord> getRecordType() {
        return GoodsUserSummaryRecord.class;
    }

    /**
     * The column <code>mini_shop_471752.b2c_goods_user_summary.id</code>.
     */
    public final TableField<GoodsUserSummaryRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_goods_user_summary.ref_date</code>. 2018-09-04
     */
    public final TableField<GoodsUserSummaryRecord, Date> REF_DATE = createField("ref_date", org.jooq.impl.SQLDataType.DATE, this, "2018-09-04");

    /**
     * The column <code>mini_shop_471752.b2c_goods_user_summary.type</code>. 1,7,30
     */
    public final TableField<GoodsUserSummaryRecord, Byte> TYPE = createField("type", org.jooq.impl.SQLDataType.TINYINT, this, "1,7,30");

    /**
     * The column <code>mini_shop_471752.b2c_goods_user_summary.goods_id_number</code>. 在售商品数
     */
    public final TableField<GoodsUserSummaryRecord, Integer> GOODS_ID_NUMBER = createField("goods_id_number", org.jooq.impl.SQLDataType.INTEGER, this, "在售商品数");

    /**
     * The column <code>mini_shop_471752.b2c_goods_user_summary.prd_id_number</code>. 在售规格数
     */
    public final TableField<GoodsUserSummaryRecord, Integer> PRD_ID_NUMBER = createField("prd_id_number", org.jooq.impl.SQLDataType.INTEGER, this, "在售规格数");

    /**
     * The column <code>mini_shop_471752.b2c_goods_user_summary.goods_id_visit</code>. 访问商品数
     */
    public final TableField<GoodsUserSummaryRecord, Integer> GOODS_ID_VISIT = createField("goods_id_visit", org.jooq.impl.SQLDataType.INTEGER, this, "访问商品数");

    /**
     * The column <code>mini_shop_471752.b2c_goods_user_summary.goods_user_visit</code>. UV
     */
    public final TableField<GoodsUserSummaryRecord, Integer> GOODS_USER_VISIT = createField("goods_user_visit", org.jooq.impl.SQLDataType.INTEGER, this, "UV");

    /**
     * The column <code>mini_shop_471752.b2c_goods_user_summary.goods_visit</code>. goods pv
     */
    public final TableField<GoodsUserSummaryRecord, Integer> GOODS_VISIT = createField("goods_visit", org.jooq.impl.SQLDataType.INTEGER, this, "goods pv");

    /**
     * The column <code>mini_shop_471752.b2c_goods_user_summary.cart_user_number</code>. 加购人数
     */
    public final TableField<GoodsUserSummaryRecord, Integer> CART_USER_NUMBER = createField("cart_user_number", org.jooq.impl.SQLDataType.INTEGER, this, "加购人数");

    /**
     * The column <code>mini_shop_471752.b2c_goods_user_summary.cart_goods_number</code>. 加购件数
     */
    public final TableField<GoodsUserSummaryRecord, Integer> CART_GOODS_NUMBER = createField("cart_goods_number", org.jooq.impl.SQLDataType.INTEGER, this, "加购件数");

    /**
     * The column <code>mini_shop_471752.b2c_goods_user_summary.paid_goods_number</code>. 付款人数
     */
    public final TableField<GoodsUserSummaryRecord, Integer> PAID_GOODS_NUMBER = createField("paid_goods_number", org.jooq.impl.SQLDataType.INTEGER, this, "付款人数");

    /**
     * The column <code>mini_shop_471752.b2c_goods_user_summary.paid_user_number</code>. 付费用户数
     */
    public final TableField<GoodsUserSummaryRecord, Integer> PAID_USER_NUMBER = createField("paid_user_number", org.jooq.impl.SQLDataType.INTEGER, this, "付费用户数");

    /**
     * The column <code>mini_shop_471752.b2c_goods_user_summary.add_time</code>.
     */
    public final TableField<GoodsUserSummaryRecord, Timestamp> ADD_TIME = createField("add_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * Create a <code>mini_shop_471752.b2c_goods_user_summary</code> table reference
     */
    public GoodsUserSummary() {
        this(DSL.name("b2c_goods_user_summary"), null);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_goods_user_summary</code> table reference
     */
    public GoodsUserSummary(String alias) {
        this(DSL.name(alias), GOODS_USER_SUMMARY);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_goods_user_summary</code> table reference
     */
    public GoodsUserSummary(Name alias) {
        this(alias, GOODS_USER_SUMMARY);
    }

    private GoodsUserSummary(Name alias, Table<GoodsUserSummaryRecord> aliased) {
        this(alias, aliased, null);
    }

    private GoodsUserSummary(Name alias, Table<GoodsUserSummaryRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> GoodsUserSummary(Table<O> child, ForeignKey<O, GoodsUserSummaryRecord> key) {
        super(child, key, GOODS_USER_SUMMARY);
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
        return Arrays.<Index>asList(Indexes.GOODS_USER_SUMMARY_PRIMARY, Indexes.GOODS_USER_SUMMARY_REF_TYPE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<GoodsUserSummaryRecord, Integer> getIdentity() {
        return Keys.IDENTITY_GOODS_USER_SUMMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<GoodsUserSummaryRecord> getPrimaryKey() {
        return Keys.KEY_B2C_GOODS_USER_SUMMARY_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<GoodsUserSummaryRecord>> getKeys() {
        return Arrays.<UniqueKey<GoodsUserSummaryRecord>>asList(Keys.KEY_B2C_GOODS_USER_SUMMARY_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsUserSummary as(String alias) {
        return new GoodsUserSummary(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsUserSummary as(Name alias) {
        return new GoodsUserSummary(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public GoodsUserSummary rename(String name) {
        return new GoodsUserSummary(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public GoodsUserSummary rename(Name name) {
        return new GoodsUserSummary(name, null);
    }
}
