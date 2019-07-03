/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables;


import com.vpu.mp.db.shop.Indexes;
import com.vpu.mp.db.shop.Keys;
import com.vpu.mp.db.shop.MiniShop_471752;
import com.vpu.mp.db.shop.tables.records.UserSummaryTrendRecord;

import java.math.BigDecimal;
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
public class UserSummaryTrend extends TableImpl<UserSummaryTrendRecord> {

    private static final long serialVersionUID = 477036513;

    /**
     * The reference instance of <code>mini_shop_471752.b2c_user_summary_trend</code>
     */
    public static final UserSummaryTrend USER_SUMMARY_TREND = new UserSummaryTrend();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserSummaryTrendRecord> getRecordType() {
        return UserSummaryTrendRecord.class;
    }

    /**
     * The column <code>mini_shop_471752.b2c_user_summary_trend.id</code>.
     */
    public final TableField<UserSummaryTrendRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_user_summary_trend.ref_date</code>. 2018-09-04
     */
    public final TableField<UserSummaryTrendRecord, Date> REF_DATE = createField("ref_date", org.jooq.impl.SQLDataType.DATE.nullable(false), this, "2018-09-04");

    /**
     * The column <code>mini_shop_471752.b2c_user_summary_trend.type</code>. 1,7,30
     */
    public final TableField<UserSummaryTrendRecord, Byte> TYPE = createField("type", org.jooq.impl.SQLDataType.TINYINT.nullable(false), this, "1,7,30");

    /**
     * The column <code>mini_shop_471752.b2c_user_summary_trend.login_data</code>. 访问会员数据
     */
    public final TableField<UserSummaryTrendRecord, Integer> LOGIN_DATA = createField("login_data", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "访问会员数据");

    /**
     * The column <code>mini_shop_471752.b2c_user_summary_trend.user_data</code>. 累积会员数
     */
    public final TableField<UserSummaryTrendRecord, Integer> USER_DATA = createField("user_data", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "累积会员数");

    /**
     * The column <code>mini_shop_471752.b2c_user_summary_trend.coupon_data</code>. 领券会员数
     */
    public final TableField<UserSummaryTrendRecord, Integer> COUPON_DATA = createField("coupon_data", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "领券会员数");

    /**
     * The column <code>mini_shop_471752.b2c_user_summary_trend.cart_data</code>. 加购会员数
     */
    public final TableField<UserSummaryTrendRecord, Integer> CART_DATA = createField("cart_data", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "加购会员数");

    /**
     * The column <code>mini_shop_471752.b2c_user_summary_trend.reg_user_data</code>. 注册数
     */
    public final TableField<UserSummaryTrendRecord, Integer> REG_USER_DATA = createField("reg_user_data", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "注册数");

    /**
     * The column <code>mini_shop_471752.b2c_user_summary_trend.upgrade_user_data</code>. 升级会员数
     */
    public final TableField<UserSummaryTrendRecord, Integer> UPGRADE_USER_DATA = createField("upgrade_user_data", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "升级会员数");

    /**
     * The column <code>mini_shop_471752.b2c_user_summary_trend.charge_user_data</code>. 储值会员数
     */
    public final TableField<UserSummaryTrendRecord, Integer> CHARGE_USER_DATA = createField("charge_user_data", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "储值会员数");

    /**
     * The column <code>mini_shop_471752.b2c_user_summary_trend.order_user_data</code>. 成交客户数
     */
    public final TableField<UserSummaryTrendRecord, Integer> ORDER_USER_DATA = createField("order_user_data", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "成交客户数");

    /**
     * The column <code>mini_shop_471752.b2c_user_summary_trend.new_order_user_data</code>. 成交新客户数
     */
    public final TableField<UserSummaryTrendRecord, Integer> NEW_ORDER_USER_DATA = createField("new_order_user_data", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "成交新客户数");

    /**
     * The column <code>mini_shop_471752.b2c_user_summary_trend.old_order_user_data</code>. 成交老客户数
     */
    public final TableField<UserSummaryTrendRecord, Integer> OLD_ORDER_USER_DATA = createField("old_order_user_data", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "成交老客户数");

    /**
     * The column <code>mini_shop_471752.b2c_user_summary_trend.total_paid_money</code>. 总成交金额
     */
    public final TableField<UserSummaryTrendRecord, Integer> TOTAL_PAID_MONEY = createField("total_paid_money", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "总成交金额");

    /**
     * The column <code>mini_shop_471752.b2c_user_summary_trend.new_paid_money</code>. 成交新客户支付金额
     */
    public final TableField<UserSummaryTrendRecord, BigDecimal> NEW_PAID_MONEY = createField("new_paid_money", org.jooq.impl.SQLDataType.DECIMAL(10, 4), this, "成交新客户支付金额");

    /**
     * The column <code>mini_shop_471752.b2c_user_summary_trend.old_paid_money</code>. 成交老客户支付金额
     */
    public final TableField<UserSummaryTrendRecord, Long> OLD_PAID_MONEY = createField("old_paid_money", org.jooq.impl.SQLDataType.BIGINT, this, "成交老客户支付金额");

    /**
     * The column <code>mini_shop_471752.b2c_user_summary_trend.pay_goods_number</code>. 付款件数
     */
    public final TableField<UserSummaryTrendRecord, Integer> PAY_GOODS_NUMBER = createField("pay_goods_number", org.jooq.impl.SQLDataType.INTEGER, this, "付款件数");

    /**
     * The column <code>mini_shop_471752.b2c_user_summary_trend.new_pay_goods_number</code>. 付款件数
     */
    public final TableField<UserSummaryTrendRecord, Integer> NEW_PAY_GOODS_NUMBER = createField("new_pay_goods_number", org.jooq.impl.SQLDataType.INTEGER, this, "付款件数");

    /**
     * The column <code>mini_shop_471752.b2c_user_summary_trend.old_pay_goods_number</code>. 付款件数
     */
    public final TableField<UserSummaryTrendRecord, Integer> OLD_PAY_GOODS_NUMBER = createField("old_pay_goods_number", org.jooq.impl.SQLDataType.INTEGER, this, "付款件数");

    /**
     * The column <code>mini_shop_471752.b2c_user_summary_trend.pay_order_num</code>. 成交订单数
     */
    public final TableField<UserSummaryTrendRecord, Integer> PAY_ORDER_NUM = createField("pay_order_num", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "成交订单数");

    /**
     * The column <code>mini_shop_471752.b2c_user_summary_trend.login_pv</code>. 登录pv
     */
    public final TableField<UserSummaryTrendRecord, Integer> LOGIN_PV = createField("login_pv", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "登录pv");

    /**
     * The column <code>mini_shop_471752.b2c_user_summary_trend.order_num</code>. 下单笔数
     */
    public final TableField<UserSummaryTrendRecord, Integer> ORDER_NUM = createField("order_num", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "下单笔数");

    /**
     * The column <code>mini_shop_471752.b2c_user_summary_trend.order_user_num</code>. 下单人数(生成订单就算)
     */
    public final TableField<UserSummaryTrendRecord, Integer> ORDER_USER_NUM = createField("order_user_num", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "下单人数(生成订单就算)");

    /**
     * The column <code>mini_shop_471752.b2c_user_summary_trend.add_time</code>. 统计时间
     */
    public final TableField<UserSummaryTrendRecord, Timestamp> ADD_TIME = createField("add_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "统计时间");

    /**
     * Create a <code>mini_shop_471752.b2c_user_summary_trend</code> table reference
     */
    public UserSummaryTrend() {
        this(DSL.name("b2c_user_summary_trend"), null);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_user_summary_trend</code> table reference
     */
    public UserSummaryTrend(String alias) {
        this(DSL.name(alias), USER_SUMMARY_TREND);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_user_summary_trend</code> table reference
     */
    public UserSummaryTrend(Name alias) {
        this(alias, USER_SUMMARY_TREND);
    }

    private UserSummaryTrend(Name alias, Table<UserSummaryTrendRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserSummaryTrend(Name alias, Table<UserSummaryTrendRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> UserSummaryTrend(Table<O> child, ForeignKey<O, UserSummaryTrendRecord> key) {
        super(child, key, USER_SUMMARY_TREND);
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
        return Arrays.<Index>asList(Indexes.USER_SUMMARY_TREND_PRIMARY, Indexes.USER_SUMMARY_TREND_REF_TYPE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<UserSummaryTrendRecord, Integer> getIdentity() {
        return Keys.IDENTITY_USER_SUMMARY_TREND;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UserSummaryTrendRecord> getPrimaryKey() {
        return Keys.KEY_B2C_USER_SUMMARY_TREND_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UserSummaryTrendRecord>> getKeys() {
        return Arrays.<UniqueKey<UserSummaryTrendRecord>>asList(Keys.KEY_B2C_USER_SUMMARY_TREND_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserSummaryTrend as(String alias) {
        return new UserSummaryTrend(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserSummaryTrend as(Name alias) {
        return new UserSummaryTrend(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UserSummaryTrend rename(String name) {
        return new UserSummaryTrend(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public UserSummaryTrend rename(Name name) {
        return new UserSummaryTrend(name, null);
    }
}
