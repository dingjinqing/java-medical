/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables;


import com.vpu.mp.db.shop.Indexes;
import com.vpu.mp.db.shop.Keys;
import com.vpu.mp.db.shop.MiniShop_471752;
import com.vpu.mp.db.shop.tables.records.B2cStatisticsSummaryRecord;

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
import org.jooq.types.UInteger;
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
public class B2cStatisticsSummary extends TableImpl<B2cStatisticsSummaryRecord> {

    private static final long serialVersionUID = -18705679;

    /**
     * The reference instance of <code>mini_shop_471752.b2c_statistics_summary</code>
     */
    public static final B2cStatisticsSummary B2C_STATISTICS_SUMMARY = new B2cStatisticsSummary();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<B2cStatisticsSummaryRecord> getRecordType() {
        return B2cStatisticsSummaryRecord.class;
    }

    /**
     * The column <code>mini_shop_471752.b2c_statistics_summary.id</code>.
     */
    public final TableField<B2cStatisticsSummaryRecord, UInteger> ID = createField("id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_statistics_summary.shop_id</code>. 店铺ID
     */
    public final TableField<B2cStatisticsSummaryRecord, Integer> SHOP_ID = createField("shop_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "店铺ID");

    /**
     * The column <code>mini_shop_471752.b2c_statistics_summary.statis_date</code>. 统计数据时间
     */
    public final TableField<B2cStatisticsSummaryRecord, Timestamp> STATIS_DATE = createField("statis_date", org.jooq.impl.SQLDataType.TIMESTAMP, this, "统计数据时间");

    /**
     * The column <code>mini_shop_471752.b2c_statistics_summary.browse_pv</code>.
     */
    public final TableField<B2cStatisticsSummaryRecord, UInteger> BROWSE_PV = createField("browse_pv", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGERUNSIGNED)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_statistics_summary.browse_uv</code>.
     */
    public final TableField<B2cStatisticsSummaryRecord, UInteger> BROWSE_UV = createField("browse_uv", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGERUNSIGNED)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_statistics_summary.total_order</code>.
     */
    public final TableField<B2cStatisticsSummaryRecord, UInteger> TOTAL_ORDER = createField("total_order", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGERUNSIGNED)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_statistics_summary.total_goods</code>.
     */
    public final TableField<B2cStatisticsSummaryRecord, UInteger> TOTAL_GOODS = createField("total_goods", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGERUNSIGNED)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_statistics_summary.total_amount</code>.
     */
    public final TableField<B2cStatisticsSummaryRecord, BigDecimal> TOTAL_AMOUNT = createField("total_amount", org.jooq.impl.SQLDataType.DECIMAL(12, 2).nullable(false).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_statistics_summary.conversion_rate</code>.
     */
    public final TableField<B2cStatisticsSummaryRecord, BigDecimal> CONVERSION_RATE = createField("conversion_rate", org.jooq.impl.SQLDataType.DECIMAL(5, 2).nullable(false).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_statistics_summary.per_cus_transaction</code>.
     */
    public final TableField<B2cStatisticsSummaryRecord, BigDecimal> PER_CUS_TRANSACTION = createField("per_cus_transaction", org.jooq.impl.SQLDataType.DECIMAL(7, 2).nullable(false).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_statistics_summary.new_order_num</code>.
     */
    public final TableField<B2cStatisticsSummaryRecord, UInteger> NEW_ORDER_NUM = createField("new_order_num", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGERUNSIGNED)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_statistics_summary.new_cod_order_num</code>.
     */
    public final TableField<B2cStatisticsSummaryRecord, UInteger> NEW_COD_ORDER_NUM = createField("new_cod_order_num", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGERUNSIGNED)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_statistics_summary.new_waitpay_order_num</code>.
     */
    public final TableField<B2cStatisticsSummaryRecord, UInteger> NEW_WAITPAY_ORDER_NUM = createField("new_waitpay_order_num", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGERUNSIGNED)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_statistics_summary.new_cancel_order_num</code>.
     */
    public final TableField<B2cStatisticsSummaryRecord, UInteger> NEW_CANCEL_ORDER_NUM = createField("new_cancel_order_num", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGERUNSIGNED)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_statistics_summary.new_close_order_num</code>.
     */
    public final TableField<B2cStatisticsSummaryRecord, UInteger> NEW_CLOSE_ORDER_NUM = createField("new_close_order_num", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGERUNSIGNED)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_statistics_summary.new_returnning_order_num</code>.
     */
    public final TableField<B2cStatisticsSummaryRecord, UInteger> NEW_RETURNNING_ORDER_NUM = createField("new_returnning_order_num", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGERUNSIGNED)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_statistics_summary.new_return_finish_order_num</code>.
     */
    public final TableField<B2cStatisticsSummaryRecord, UInteger> NEW_RETURN_FINISH_ORDER_NUM = createField("new_return_finish_order_num", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGERUNSIGNED)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_statistics_summary.total_paid_order_num</code>.
     */
    public final TableField<B2cStatisticsSummaryRecord, UInteger> TOTAL_PAID_ORDER_NUM = createField("total_paid_order_num", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGERUNSIGNED)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_statistics_summary.total_dlv_order_num</code>.
     */
    public final TableField<B2cStatisticsSummaryRecord, UInteger> TOTAL_DLV_ORDER_NUM = createField("total_dlv_order_num", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGERUNSIGNED)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_statistics_summary.total_cancelled_order_num</code>.
     */
    public final TableField<B2cStatisticsSummaryRecord, UInteger> TOTAL_CANCELLED_ORDER_NUM = createField("total_cancelled_order_num", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGERUNSIGNED)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_statistics_summary.total_closed_order_num</code>.
     */
    public final TableField<B2cStatisticsSummaryRecord, UInteger> TOTAL_CLOSED_ORDER_NUM = createField("total_closed_order_num", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGERUNSIGNED)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_statistics_summary.total_finished_order_num</code>.
     */
    public final TableField<B2cStatisticsSummaryRecord, UInteger> TOTAL_FINISHED_ORDER_NUM = createField("total_finished_order_num", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGERUNSIGNED)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_statistics_summary.total_returning_order_num</code>.
     */
    public final TableField<B2cStatisticsSummaryRecord, UInteger> TOTAL_RETURNING_ORDER_NUM = createField("total_returning_order_num", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGERUNSIGNED)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_statistics_summary.total_return_finish_order_num</code>.
     */
    public final TableField<B2cStatisticsSummaryRecord, UInteger> TOTAL_RETURN_FINISH_ORDER_NUM = createField("total_return_finish_order_num", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGERUNSIGNED)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_statistics_summary.total_goods_num</code>.
     */
    public final TableField<B2cStatisticsSummaryRecord, UShort> TOTAL_GOODS_NUM = createField("total_goods_num", org.jooq.impl.SQLDataType.SMALLINTUNSIGNED.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.SMALLINTUNSIGNED)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_statistics_summary.on_sale_goods_num</code>.
     */
    public final TableField<B2cStatisticsSummaryRecord, UShort> ON_SALE_GOODS_NUM = createField("on_sale_goods_num", org.jooq.impl.SQLDataType.SMALLINTUNSIGNED.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.SMALLINTUNSIGNED)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_statistics_summary.new_add_goods</code>.
     */
    public final TableField<B2cStatisticsSummaryRecord, UShort> NEW_ADD_GOODS = createField("new_add_goods", org.jooq.impl.SQLDataType.SMALLINTUNSIGNED.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.SMALLINTUNSIGNED)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_statistics_summary.created</code>. 创建时间
     */
    public final TableField<B2cStatisticsSummaryRecord, Timestamp> CREATED = createField("created", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * Create a <code>mini_shop_471752.b2c_statistics_summary</code> table reference
     */
    public B2cStatisticsSummary() {
        this(DSL.name("b2c_statistics_summary"), null);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_statistics_summary</code> table reference
     */
    public B2cStatisticsSummary(String alias) {
        this(DSL.name(alias), B2C_STATISTICS_SUMMARY);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_statistics_summary</code> table reference
     */
    public B2cStatisticsSummary(Name alias) {
        this(alias, B2C_STATISTICS_SUMMARY);
    }

    private B2cStatisticsSummary(Name alias, Table<B2cStatisticsSummaryRecord> aliased) {
        this(alias, aliased, null);
    }

    private B2cStatisticsSummary(Name alias, Table<B2cStatisticsSummaryRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> B2cStatisticsSummary(Table<O> child, ForeignKey<O, B2cStatisticsSummaryRecord> key) {
        super(child, key, B2C_STATISTICS_SUMMARY);
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
        return Arrays.<Index>asList(Indexes.B2C_STATISTICS_SUMMARY_CREATED, Indexes.B2C_STATISTICS_SUMMARY_PRIMARY, Indexes.B2C_STATISTICS_SUMMARY_SHOP_ID, Indexes.B2C_STATISTICS_SUMMARY_STATIS_DATE_UNIQUE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<B2cStatisticsSummaryRecord, UInteger> getIdentity() {
        return Keys.IDENTITY_B2C_STATISTICS_SUMMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<B2cStatisticsSummaryRecord> getPrimaryKey() {
        return Keys.KEY_B2C_STATISTICS_SUMMARY_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<B2cStatisticsSummaryRecord>> getKeys() {
        return Arrays.<UniqueKey<B2cStatisticsSummaryRecord>>asList(Keys.KEY_B2C_STATISTICS_SUMMARY_PRIMARY, Keys.KEY_B2C_STATISTICS_SUMMARY_STATIS_DATE_UNIQUE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cStatisticsSummary as(String alias) {
        return new B2cStatisticsSummary(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cStatisticsSummary as(Name alias) {
        return new B2cStatisticsSummary(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cStatisticsSummary rename(String name) {
        return new B2cStatisticsSummary(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cStatisticsSummary rename(Name name) {
        return new B2cStatisticsSummary(name, null);
    }
}
