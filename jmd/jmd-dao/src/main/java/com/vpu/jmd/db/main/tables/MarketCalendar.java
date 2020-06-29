/*
 * This file is generated by jOOQ.
 */
package com.vpu.jmd.db.main.tables;


import com.vpu.mp.db.main.Indexes;
import com.vpu.mp.db.main.Keys;
import com.vpu.mp.db.main.MiniMain;
import com.vpu.mp.db.main.tables.records.MarketCalendarRecord;

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
public class MarketCalendar extends TableImpl<MarketCalendarRecord> {

    private static final long serialVersionUID = -1579753299;

    /**
     * The reference instance of <code>mini_main.b2c_market_calendar</code>
     */
    public static final MarketCalendar MARKET_CALENDAR = new MarketCalendar();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<MarketCalendarRecord> getRecordType() {
        return MarketCalendarRecord.class;
    }

    /**
     * The column <code>mini_main.b2c_market_calendar.id</code>.
     */
    public final TableField<MarketCalendarRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_main.b2c_market_calendar.event_name</code>. 事件名称
     */
    public final TableField<MarketCalendarRecord, String> EVENT_NAME = createField("event_name", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "事件名称");

    /**
     * The column <code>mini_main.b2c_market_calendar.event_time</code>. 事件时间
     */
    public final TableField<MarketCalendarRecord, Date> EVENT_TIME = createField("event_time", org.jooq.impl.SQLDataType.DATE, this, "事件时间");

    /**
     * The column <code>mini_main.b2c_market_calendar.event_desc</code>. 事件说明
     */
    public final TableField<MarketCalendarRecord, String> EVENT_DESC = createField("event_desc", org.jooq.impl.SQLDataType.CLOB, this, "事件说明");

    /**
     * The column <code>mini_main.b2c_market_calendar.pub_flag</code>. 发布状态：0未发布，1已发布
     */
    public final TableField<MarketCalendarRecord, Byte> PUB_FLAG = createField("pub_flag", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "发布状态：0未发布，1已发布");

    /**
     * The column <code>mini_main.b2c_market_calendar.del_flag</code>. 是否已删除：0否，1是
     */
    public final TableField<MarketCalendarRecord, Byte> DEL_FLAG = createField("del_flag", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否已删除：0否，1是");

    /**
     * The column <code>mini_main.b2c_market_calendar.create_time</code>. 创建时间
     */
    public final TableField<MarketCalendarRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>mini_main.b2c_market_calendar.update_time</code>. 更新时间
     */
    public final TableField<MarketCalendarRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "更新时间");

    /**
     * Create a <code>mini_main.b2c_market_calendar</code> table reference
     */
    public MarketCalendar() {
        this(DSL.name("b2c_market_calendar"), null);
    }

    /**
     * Create an aliased <code>mini_main.b2c_market_calendar</code> table reference
     */
    public MarketCalendar(String alias) {
        this(DSL.name(alias), MARKET_CALENDAR);
    }

    /**
     * Create an aliased <code>mini_main.b2c_market_calendar</code> table reference
     */
    public MarketCalendar(Name alias) {
        this(alias, MARKET_CALENDAR);
    }

    private MarketCalendar(Name alias, Table<MarketCalendarRecord> aliased) {
        this(alias, aliased, null);
    }

    private MarketCalendar(Name alias, Table<MarketCalendarRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> MarketCalendar(Table<O> child, ForeignKey<O, MarketCalendarRecord> key) {
        super(child, key, MARKET_CALENDAR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return MiniMain.MINI_MAIN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.MARKET_CALENDAR_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<MarketCalendarRecord, Integer> getIdentity() {
        return Keys.IDENTITY_MARKET_CALENDAR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<MarketCalendarRecord> getPrimaryKey() {
        return Keys.KEY_B2C_MARKET_CALENDAR_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<MarketCalendarRecord>> getKeys() {
        return Arrays.<UniqueKey<MarketCalendarRecord>>asList(Keys.KEY_B2C_MARKET_CALENDAR_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MarketCalendar as(String alias) {
        return new MarketCalendar(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MarketCalendar as(Name alias) {
        return new MarketCalendar(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public MarketCalendar rename(String name) {
        return new MarketCalendar(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public MarketCalendar rename(Name name) {
        return new MarketCalendar(name, null);
    }
}
