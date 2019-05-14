/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables;


import com.vpu.mp.db.shop.MiniShop_471752;
import com.vpu.mp.db.shop.tables.records.B2cFanliGoodsStatisticsRecord;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
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
public class B2cFanliGoodsStatistics extends TableImpl<B2cFanliGoodsStatisticsRecord> {

    private static final long serialVersionUID = 1757740404;

    /**
     * The reference instance of <code>mini_shop_471752.b2c_fanli_goods_statistics</code>
     */
    public static final B2cFanliGoodsStatistics B2C_FANLI_GOODS_STATISTICS = new B2cFanliGoodsStatistics();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<B2cFanliGoodsStatisticsRecord> getRecordType() {
        return B2cFanliGoodsStatisticsRecord.class;
    }

    /**
     * The column <code>mini_shop_471752.b2c_fanli_goods_statistics.prd_id</code>.
     */
    public final TableField<B2cFanliGoodsStatisticsRecord, Integer> PRD_ID = createField("prd_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_fanli_goods_statistics.prd_sn</code>. 规格编码
     */
    public final TableField<B2cFanliGoodsStatisticsRecord, String> PRD_SN = createField("prd_sn", org.jooq.impl.SQLDataType.VARCHAR(30), this, "规格编码");

    /**
     * The column <code>mini_shop_471752.b2c_fanli_goods_statistics.goods_id</code>.
     */
    public final TableField<B2cFanliGoodsStatisticsRecord, Integer> GOODS_ID = createField("goods_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>mini_shop_471752.b2c_fanli_goods_statistics.cat_id</code>. 分类ID
     */
    public final TableField<B2cFanliGoodsStatisticsRecord, Integer> CAT_ID = createField("cat_id", org.jooq.impl.SQLDataType.INTEGER, this, "分类ID");

    /**
     * The column <code>mini_shop_471752.b2c_fanli_goods_statistics.sale_number</code>. 销量
     */
    public final TableField<B2cFanliGoodsStatisticsRecord, Integer> SALE_NUMBER = createField("sale_number", org.jooq.impl.SQLDataType.INTEGER, this, "销量");

    /**
     * The column <code>mini_shop_471752.b2c_fanli_goods_statistics.prd_total_fanli</code>. 商品返利总金额
     */
    public final TableField<B2cFanliGoodsStatisticsRecord, BigDecimal> PRD_TOTAL_FANLI = createField("prd_total_fanli", org.jooq.impl.SQLDataType.DECIMAL(10, 2).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "商品返利总金额");

    /**
     * The column <code>mini_shop_471752.b2c_fanli_goods_statistics.add_time</code>.
     */
    public final TableField<B2cFanliGoodsStatisticsRecord, Timestamp> ADD_TIME = createField("add_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_fanli_goods_statistics.update_time</code>.
     */
    public final TableField<B2cFanliGoodsStatisticsRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0000-00-00 00:00:00", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>mini_shop_471752.b2c_fanli_goods_statistics</code> table reference
     */
    public B2cFanliGoodsStatistics() {
        this(DSL.name("b2c_fanli_goods_statistics"), null);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_fanli_goods_statistics</code> table reference
     */
    public B2cFanliGoodsStatistics(String alias) {
        this(DSL.name(alias), B2C_FANLI_GOODS_STATISTICS);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_fanli_goods_statistics</code> table reference
     */
    public B2cFanliGoodsStatistics(Name alias) {
        this(alias, B2C_FANLI_GOODS_STATISTICS);
    }

    private B2cFanliGoodsStatistics(Name alias, Table<B2cFanliGoodsStatisticsRecord> aliased) {
        this(alias, aliased, null);
    }

    private B2cFanliGoodsStatistics(Name alias, Table<B2cFanliGoodsStatisticsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> B2cFanliGoodsStatistics(Table<O> child, ForeignKey<O, B2cFanliGoodsStatisticsRecord> key) {
        super(child, key, B2C_FANLI_GOODS_STATISTICS);
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
    public B2cFanliGoodsStatistics as(String alias) {
        return new B2cFanliGoodsStatistics(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cFanliGoodsStatistics as(Name alias) {
        return new B2cFanliGoodsStatistics(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cFanliGoodsStatistics rename(String name) {
        return new B2cFanliGoodsStatistics(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cFanliGoodsStatistics rename(Name name) {
        return new B2cFanliGoodsStatistics(name, null);
    }
}
