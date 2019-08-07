/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables;


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

import com.vpu.mp.db.shop.Indexes;
import com.vpu.mp.db.shop.Keys;
import com.vpu.mp.db.shop.MiniShop_471752;
import com.vpu.mp.db.shop.tables.records.UserFanliStatisticsRecord;


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
public class UserFanliStatistics extends TableImpl<UserFanliStatisticsRecord> {

    private static final long serialVersionUID = 573270407;

    /**
     * The reference instance of <code>jmini_shop_471752.b2c_user_fanli_statistics</code>
     */
    public static final UserFanliStatistics USER_FANLI_STATISTICS = new UserFanliStatistics();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserFanliStatisticsRecord> getRecordType() {
        return UserFanliStatisticsRecord.class;
    }

    /**
     * The column <code>jmini_shop_471752.b2c_user_fanli_statistics.id</code>.
     */
    public final TableField<UserFanliStatisticsRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>jmini_shop_471752.b2c_user_fanli_statistics.user_id</code>.
     */
    public final TableField<UserFanliStatisticsRecord, Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>jmini_shop_471752.b2c_user_fanli_statistics.fanli_user_id</code>. 邀请人id
     */
    public final TableField<UserFanliStatisticsRecord, Integer> FANLI_USER_ID = createField("fanli_user_id", org.jooq.impl.SQLDataType.INTEGER, this, "邀请人id");

    /**
     * The column <code>jmini_shop_471752.b2c_user_fanli_statistics.order_number</code>. 累积订单数量
     */
    public final TableField<UserFanliStatisticsRecord, Integer> ORDER_NUMBER = createField("order_number", org.jooq.impl.SQLDataType.INTEGER, this, "累积订单数量");

    /**
     * The column <code>jmini_shop_471752.b2c_user_fanli_statistics.total_can_fanli_money</code>. 累计返利订单可计算返利总金额
     */
    public final TableField<UserFanliStatisticsRecord, BigDecimal> TOTAL_CAN_FANLI_MONEY = createField("total_can_fanli_money", org.jooq.impl.SQLDataType.DECIMAL(10, 2).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "累计返利订单可计算返利总金额");

    /**
     * The column <code>jmini_shop_471752.b2c_user_fanli_statistics.total_fanli_money</code>. 用户累计返利佣金
     */
    public final TableField<UserFanliStatisticsRecord, BigDecimal> TOTAL_FANLI_MONEY = createField("total_fanli_money", org.jooq.impl.SQLDataType.DECIMAL(10, 2).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "用户累计返利佣金");

    /**
     * The column <code>jmini_shop_471752.b2c_user_fanli_statistics.rebate_level</code>. 返利等级 0自购；1直接；2间接
     */
    public final TableField<UserFanliStatisticsRecord, Byte> REBATE_LEVEL = createField("rebate_level", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.TINYINT)), this, "返利等级 0自购；1直接；2间接");

    /**
     * The column <code>jmini_shop_471752.b2c_user_fanli_statistics.create_time</code>.
     */
    public final TableField<UserFanliStatisticsRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>jmini_shop_471752.b2c_user_fanli_statistics.update_time</code>. 最后修改时间
     */
    public final TableField<UserFanliStatisticsRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "最后修改时间");

    /**
     * Create a <code>jmini_shop_471752.b2c_user_fanli_statistics</code> table reference
     */
    public UserFanliStatistics() {
        this(DSL.name("b2c_user_fanli_statistics"), null);
    }

    /**
     * Create an aliased <code>jmini_shop_471752.b2c_user_fanli_statistics</code> table reference
     */
    public UserFanliStatistics(String alias) {
        this(DSL.name(alias), USER_FANLI_STATISTICS);
    }

    /**
     * Create an aliased <code>jmini_shop_471752.b2c_user_fanli_statistics</code> table reference
     */
    public UserFanliStatistics(Name alias) {
        this(alias, USER_FANLI_STATISTICS);
    }

    private UserFanliStatistics(Name alias, Table<UserFanliStatisticsRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserFanliStatistics(Name alias, Table<UserFanliStatisticsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> UserFanliStatistics(Table<O> child, ForeignKey<O, UserFanliStatisticsRecord> key) {
        super(child, key, USER_FANLI_STATISTICS);
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
        return Arrays.<Index>asList(Indexes.USER_FANLI_STATISTICS_PRIMARY, Indexes.USER_FANLI_STATISTICS_USER_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<UserFanliStatisticsRecord, Integer> getIdentity() {
        return Keys.IDENTITY_USER_FANLI_STATISTICS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UserFanliStatisticsRecord> getPrimaryKey() {
        return Keys.KEY_B2C_USER_FANLI_STATISTICS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UserFanliStatisticsRecord>> getKeys() {
        return Arrays.<UniqueKey<UserFanliStatisticsRecord>>asList(Keys.KEY_B2C_USER_FANLI_STATISTICS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserFanliStatistics as(String alias) {
        return new UserFanliStatistics(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserFanliStatistics as(Name alias) {
        return new UserFanliStatistics(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UserFanliStatistics rename(String name) {
        return new UserFanliStatistics(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public UserFanliStatistics rename(Name name) {
        return new UserFanliStatistics(name, null);
    }
}
