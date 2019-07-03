/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables;


import com.vpu.mp.db.shop.Indexes;
import com.vpu.mp.db.shop.MiniShop_471752;
import com.vpu.mp.db.shop.tables.records.MpSummaryTrendRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
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
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class MpSummaryTrend extends TableImpl<MpSummaryTrendRecord> {

    private static final long serialVersionUID = 1094808001;

    /**
     * The reference instance of <code>mini_shop_471752.b2c_mp_summary_trend</code>
     */
    public static final MpSummaryTrend MP_SUMMARY_TREND = new MpSummaryTrend();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<MpSummaryTrendRecord> getRecordType() {
        return MpSummaryTrendRecord.class;
    }

    /**
     * The column <code>mini_shop_471752.b2c_mp_summary_trend.ref_date</code>. 日期
     */
    public final TableField<MpSummaryTrendRecord, String> REF_DATE = createField("ref_date", org.jooq.impl.SQLDataType.CHAR(8).nullable(false), this, "日期");

    /**
     * The column <code>mini_shop_471752.b2c_mp_summary_trend.visit_total</code>. 总访问量
     */
    public final TableField<MpSummaryTrendRecord, Integer> VISIT_TOTAL = createField("visit_total", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "总访问量");

    /**
     * The column <code>mini_shop_471752.b2c_mp_summary_trend.share_pv</code>. 转发次数
     */
    public final TableField<MpSummaryTrendRecord, Integer> SHARE_PV = createField("share_pv", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "转发次数");

    /**
     * The column <code>mini_shop_471752.b2c_mp_summary_trend.share_uv</code>. 转发人数
     */
    public final TableField<MpSummaryTrendRecord, Integer> SHARE_UV = createField("share_uv", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "转发人数");

    /**
     * The column <code>mini_shop_471752.b2c_mp_summary_trend.add_time</code>. 添加时间
     */
    public final TableField<MpSummaryTrendRecord, Timestamp> ADD_TIME = createField("add_time", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "添加时间");

    /**
     * Create a <code>mini_shop_471752.b2c_mp_summary_trend</code> table reference
     */
    public MpSummaryTrend() {
        this(DSL.name("b2c_mp_summary_trend"), null);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_mp_summary_trend</code> table reference
     */
    public MpSummaryTrend(String alias) {
        this(DSL.name(alias), MP_SUMMARY_TREND);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_mp_summary_trend</code> table reference
     */
    public MpSummaryTrend(Name alias) {
        this(alias, MP_SUMMARY_TREND);
    }

    private MpSummaryTrend(Name alias, Table<MpSummaryTrendRecord> aliased) {
        this(alias, aliased, null);
    }

    private MpSummaryTrend(Name alias, Table<MpSummaryTrendRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> MpSummaryTrend(Table<O> child, ForeignKey<O, MpSummaryTrendRecord> key) {
        super(child, key, MP_SUMMARY_TREND);
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
        return Arrays.<Index>asList(Indexes.MP_SUMMARY_TREND_REF_DATE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MpSummaryTrend as(String alias) {
        return new MpSummaryTrend(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MpSummaryTrend as(Name alias) {
        return new MpSummaryTrend(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public MpSummaryTrend rename(String name) {
        return new MpSummaryTrend(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public MpSummaryTrend rename(Name name) {
        return new MpSummaryTrend(name, null);
    }
}
