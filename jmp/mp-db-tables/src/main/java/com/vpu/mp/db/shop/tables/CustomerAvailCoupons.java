/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables;


import com.vpu.mp.db.shop.Indexes;
import com.vpu.mp.db.shop.Keys;
import com.vpu.mp.db.shop.MiniShop_471752;
import com.vpu.mp.db.shop.tables.records.CustomerAvailCouponsRecord;

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
public class CustomerAvailCoupons extends TableImpl<CustomerAvailCouponsRecord> {

    private static final long serialVersionUID = -1950548090;

    /**
     * The reference instance of <code>mini_shop_471752.b2c_customer_avail_coupons</code>
     */
    public static final CustomerAvailCoupons CUSTOMER_AVAIL_COUPONS = new CustomerAvailCoupons();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CustomerAvailCouponsRecord> getRecordType() {
        return CustomerAvailCouponsRecord.class;
    }

    /**
     * The column <code>mini_shop_471752.b2c_customer_avail_coupons.id</code>.
     */
    public final TableField<CustomerAvailCouponsRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_customer_avail_coupons.coupon_sn</code>.
     */
    public final TableField<CustomerAvailCouponsRecord, String> COUPON_SN = createField("coupon_sn", org.jooq.impl.SQLDataType.VARCHAR(30).nullable(false).defaultValue(DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_customer_avail_coupons.user_id</code>.
     */
    public final TableField<CustomerAvailCouponsRecord, Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_customer_avail_coupons.act_type</code>. user_id不为空时1:经销商等级打折,为空时1:首次下单优惠，2减价，3打折
     */
    public final TableField<CustomerAvailCouponsRecord, Integer> ACT_TYPE = createField("act_type", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "user_id不为空时1:经销商等级打折,为空时1:首次下单优惠，2减价，3打折");

    /**
     * The column <code>mini_shop_471752.b2c_customer_avail_coupons.act_id</code>.
     */
    public final TableField<CustomerAvailCouponsRecord, Integer> ACT_ID = createField("act_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_customer_avail_coupons.start_time</code>.
     */
    public final TableField<CustomerAvailCouponsRecord, Timestamp> START_TIME = createField("start_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_customer_avail_coupons.end_time</code>.
     */
    public final TableField<CustomerAvailCouponsRecord, Timestamp> END_TIME = createField("end_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(DSL.inline("0000-00-00 00:00:00", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_customer_avail_coupons.type</code>. 0为减价，1为打折
     */
    public final TableField<CustomerAvailCouponsRecord, Byte> TYPE = createField("type", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "0为减价，1为打折");

    /**
     * The column <code>mini_shop_471752.b2c_customer_avail_coupons.amount</code>. 打折或减价量
     */
    public final TableField<CustomerAvailCouponsRecord, BigDecimal> AMOUNT = createField("amount", org.jooq.impl.SQLDataType.DECIMAL(10, 2).nullable(false).defaultValue(DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "打折或减价量");

    /**
     * The column <code>mini_shop_471752.b2c_customer_avail_coupons.act_desc</code>.
     */
    public final TableField<CustomerAvailCouponsRecord, String> ACT_DESC = createField("act_desc", org.jooq.impl.SQLDataType.VARCHAR(128).nullable(false).defaultValue(DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_customer_avail_coupons.limit_order_amount</code>. 满多少可用
     */
    public final TableField<CustomerAvailCouponsRecord, BigDecimal> LIMIT_ORDER_AMOUNT = createField("limit_order_amount", org.jooq.impl.SQLDataType.DECIMAL(10, 2).nullable(false).defaultValue(DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "满多少可用");

    /**
     * The column <code>mini_shop_471752.b2c_customer_avail_coupons.is_used</code>. 0 未使用 1 已使用 
     */
    public final TableField<CustomerAvailCouponsRecord, Byte> IS_USED = createField("is_used", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "0 未使用 1 已使用 ");

    /**
     * The column <code>mini_shop_471752.b2c_customer_avail_coupons.used_time</code>.
     */
    public final TableField<CustomerAvailCouponsRecord, Timestamp> USED_TIME = createField("used_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(DSL.inline("0000-00-00 00:00:00", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_customer_avail_coupons.access_mode</code>. 获取方式，0：发放，1：领取
     */
    public final TableField<CustomerAvailCouponsRecord, Byte> ACCESS_MODE = createField("access_mode", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "获取方式，0：发放，1：领取");

    /**
     * The column <code>mini_shop_471752.b2c_customer_avail_coupons.access_id</code>. 发放活动id
     */
    public final TableField<CustomerAvailCouponsRecord, Integer> ACCESS_ID = createField("access_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "发放活动id");

    /**
     * The column <code>mini_shop_471752.b2c_customer_avail_coupons.notify_time</code>. 通知时间
     */
    public final TableField<CustomerAvailCouponsRecord, Timestamp> NOTIFY_TIME = createField("notify_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(DSL.inline("0000-00-00 00:00:00", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "通知时间");

    /**
     * The column <code>mini_shop_471752.b2c_customer_avail_coupons.order_sn</code>. 优惠订单编号
     */
    public final TableField<CustomerAvailCouponsRecord, String> ORDER_SN = createField("order_sn", org.jooq.impl.SQLDataType.VARCHAR(20).nullable(false).defaultValue(DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "优惠订单编号");

    /**
     * The column <code>mini_shop_471752.b2c_customer_avail_coupons.del_flag</code>. 是否删除,1：删除
     */
    public final TableField<CustomerAvailCouponsRecord, Byte> DEL_FLAG = createField("del_flag", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否删除,1：删除");

    /**
     * The column <code>mini_shop_471752.b2c_customer_avail_coupons.get_source</code>. //1表单送券2支付送券3活动送券4积分兑换5直接领取6分裂优惠券7crm领券8幸运大抽奖9定向发券
     */
    public final TableField<CustomerAvailCouponsRecord, Byte> GET_SOURCE = createField("get_source", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "//1表单送券2支付送券3活动送券4积分兑换5直接领取6分裂优惠券7crm领券8幸运大抽奖9定向发券");

    /**
     * The column <code>mini_shop_471752.b2c_customer_avail_coupons.create_time</code>.
     */
    public final TableField<CustomerAvailCouponsRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_customer_avail_coupons.update_time</code>. 最后修改时间
     */
    public final TableField<CustomerAvailCouponsRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "最后修改时间");

    /**
     * Create a <code>mini_shop_471752.b2c_customer_avail_coupons</code> table reference
     */
    public CustomerAvailCoupons() {
        this(DSL.name("b2c_customer_avail_coupons"), null);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_customer_avail_coupons</code> table reference
     */
    public CustomerAvailCoupons(String alias) {
        this(DSL.name(alias), CUSTOMER_AVAIL_COUPONS);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_customer_avail_coupons</code> table reference
     */
    public CustomerAvailCoupons(Name alias) {
        this(alias, CUSTOMER_AVAIL_COUPONS);
    }

    private CustomerAvailCoupons(Name alias, Table<CustomerAvailCouponsRecord> aliased) {
        this(alias, aliased, null);
    }

    private CustomerAvailCoupons(Name alias, Table<CustomerAvailCouponsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> CustomerAvailCoupons(Table<O> child, ForeignKey<O, CustomerAvailCouponsRecord> key) {
        super(child, key, CUSTOMER_AVAIL_COUPONS);
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
        return Arrays.<Index>asList(Indexes.CUSTOMER_AVAIL_COUPONS_COUPON_SN, Indexes.CUSTOMER_AVAIL_COUPONS_PRIMARY, Indexes.CUSTOMER_AVAIL_COUPONS_USER_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<CustomerAvailCouponsRecord, Integer> getIdentity() {
        return Keys.IDENTITY_CUSTOMER_AVAIL_COUPONS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<CustomerAvailCouponsRecord> getPrimaryKey() {
        return Keys.KEY_B2C_CUSTOMER_AVAIL_COUPONS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<CustomerAvailCouponsRecord>> getKeys() {
        return Arrays.<UniqueKey<CustomerAvailCouponsRecord>>asList(Keys.KEY_B2C_CUSTOMER_AVAIL_COUPONS_PRIMARY, Keys.KEY_B2C_CUSTOMER_AVAIL_COUPONS_COUPON_SN);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CustomerAvailCoupons as(String alias) {
        return new CustomerAvailCoupons(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CustomerAvailCoupons as(Name alias) {
        return new CustomerAvailCoupons(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public CustomerAvailCoupons rename(String name) {
        return new CustomerAvailCoupons(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public CustomerAvailCoupons rename(Name name) {
        return new CustomerAvailCoupons(name, null);
    }
}
