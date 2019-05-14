/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables;


import com.vpu.mp.db.shop.Indexes;
import com.vpu.mp.db.shop.Keys;
import com.vpu.mp.db.shop.MiniShop_471752;
import com.vpu.mp.db.shop.tables.records.B2cServiceOrderRecord;

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
public class B2cServiceOrder extends TableImpl<B2cServiceOrderRecord> {

    private static final long serialVersionUID = 1789002635;

    /**
     * The reference instance of <code>mini_shop_471752.b2c_service_order</code>
     */
    public static final B2cServiceOrder B2C_SERVICE_ORDER = new B2cServiceOrder();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<B2cServiceOrderRecord> getRecordType() {
        return B2cServiceOrderRecord.class;
    }

    /**
     * The column <code>mini_shop_471752.b2c_service_order.order_id</code>. 订单ID
     */
    public final TableField<B2cServiceOrderRecord, UInteger> ORDER_ID = createField("order_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).identity(true), this, "订单ID");

    /**
     * The column <code>mini_shop_471752.b2c_service_order.store_id</code>. 门店ID
     */
    public final TableField<B2cServiceOrderRecord, Integer> STORE_ID = createField("store_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "门店ID");

    /**
     * The column <code>mini_shop_471752.b2c_service_order.order_sn</code>. 订单编号
     */
    public final TableField<B2cServiceOrderRecord, String> ORDER_SN = createField("order_sn", org.jooq.impl.SQLDataType.VARCHAR(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "订单编号");

    /**
     * The column <code>mini_shop_471752.b2c_service_order.user_id</code>. 用户ID
     */
    public final TableField<B2cServiceOrderRecord, UInteger> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGERUNSIGNED)), this, "用户ID");

    /**
     * The column <code>mini_shop_471752.b2c_service_order.order_status</code>. 订单状态 0：待服务，1：已取消，2：已完成
     */
    public final TableField<B2cServiceOrderRecord, Byte> ORDER_STATUS = createField("order_status", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "订单状态 0：待服务，1：已取消，2：已完成");

    /**
     * The column <code>mini_shop_471752.b2c_service_order.order_status_name</code>. 订单状态名称
     */
    public final TableField<B2cServiceOrderRecord, String> ORDER_STATUS_NAME = createField("order_status_name", org.jooq.impl.SQLDataType.VARCHAR(32).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "订单状态名称");

    /**
     * The column <code>mini_shop_471752.b2c_service_order.subscriber</code>. 预约人姓名
     */
    public final TableField<B2cServiceOrderRecord, String> SUBSCRIBER = createField("subscriber", org.jooq.impl.SQLDataType.VARCHAR(60).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "预约人姓名");

    /**
     * The column <code>mini_shop_471752.b2c_service_order.mobile</code>. 手机号
     */
    public final TableField<B2cServiceOrderRecord, String> MOBILE = createField("mobile", org.jooq.impl.SQLDataType.VARCHAR(60).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "手机号");

    /**
     * The column <code>mini_shop_471752.b2c_service_order.service_id</code>. 服务ID
     */
    public final TableField<B2cServiceOrderRecord, Integer> SERVICE_ID = createField("service_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "服务ID");

    /**
     * The column <code>mini_shop_471752.b2c_service_order.technician_id</code>. 预约技师ID
     */
    public final TableField<B2cServiceOrderRecord, Integer> TECHNICIAN_ID = createField("technician_id", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "预约技师ID");

    /**
     * The column <code>mini_shop_471752.b2c_service_order.technician_name</code>. 技师名称
     */
    public final TableField<B2cServiceOrderRecord, String> TECHNICIAN_NAME = createField("technician_name", org.jooq.impl.SQLDataType.VARCHAR(20).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "技师名称");

    /**
     * The column <code>mini_shop_471752.b2c_service_order.service_date</code>. 预约日期
     */
    public final TableField<B2cServiceOrderRecord, String> SERVICE_DATE = createField("service_date", org.jooq.impl.SQLDataType.VARCHAR(18).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "预约日期");

    /**
     * The column <code>mini_shop_471752.b2c_service_order.service_period</code>. 预约时段
     */
    public final TableField<B2cServiceOrderRecord, String> SERVICE_PERIOD = createField("service_period", org.jooq.impl.SQLDataType.VARCHAR(20), this, "预约时段");

    /**
     * The column <code>mini_shop_471752.b2c_service_order.add_message</code>. 客户留言
     */
    public final TableField<B2cServiceOrderRecord, String> ADD_MESSAGE = createField("add_message", org.jooq.impl.SQLDataType.VARCHAR(191), this, "客户留言");

    /**
     * The column <code>mini_shop_471752.b2c_service_order.admin_message</code>. 商家备注
     */
    public final TableField<B2cServiceOrderRecord, String> ADMIN_MESSAGE = createField("admin_message", org.jooq.impl.SQLDataType.VARCHAR(191), this, "商家备注");

    /**
     * The column <code>mini_shop_471752.b2c_service_order.verify_code</code>. 核销自提码
     */
    public final TableField<B2cServiceOrderRecord, String> VERIFY_CODE = createField("verify_code", org.jooq.impl.SQLDataType.VARCHAR(191).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "核销自提码");

    /**
     * The column <code>mini_shop_471752.b2c_service_order.verify_admin</code>. 核销人
     */
    public final TableField<B2cServiceOrderRecord, String> VERIFY_ADMIN = createField("verify_admin", org.jooq.impl.SQLDataType.VARCHAR(30).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "核销人");

    /**
     * The column <code>mini_shop_471752.b2c_service_order.pay_code</code>. 支付代号
     */
    public final TableField<B2cServiceOrderRecord, String> PAY_CODE = createField("pay_code", org.jooq.impl.SQLDataType.VARCHAR(30).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "支付代号");

    /**
     * The column <code>mini_shop_471752.b2c_service_order.pay_name</code>. 支付名称
     */
    public final TableField<B2cServiceOrderRecord, String> PAY_NAME = createField("pay_name", org.jooq.impl.SQLDataType.VARCHAR(120).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "支付名称");

    /**
     * The column <code>mini_shop_471752.b2c_service_order.pay_sn</code>. 支付流水号
     */
    public final TableField<B2cServiceOrderRecord, String> PAY_SN = createField("pay_sn", org.jooq.impl.SQLDataType.VARCHAR(32).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "支付流水号");

    /**
     * The column <code>mini_shop_471752.b2c_service_order.money_paid</code>. 订单应付金额
     */
    public final TableField<B2cServiceOrderRecord, BigDecimal> MONEY_PAID = createField("money_paid", org.jooq.impl.SQLDataType.DECIMAL(10, 2).nullable(false).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "订单应付金额");

    /**
     * The column <code>mini_shop_471752.b2c_service_order.discount</code>. 券抵扣金额
     */
    public final TableField<B2cServiceOrderRecord, BigDecimal> DISCOUNT = createField("discount", org.jooq.impl.SQLDataType.DECIMAL(10, 2).nullable(false).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "券抵扣金额");

    /**
     * The column <code>mini_shop_471752.b2c_service_order.coupon_id</code>. 优惠券ID
     */
    public final TableField<B2cServiceOrderRecord, UInteger> COUPON_ID = createField("coupon_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGERUNSIGNED)), this, "优惠券ID");

    /**
     * The column <code>mini_shop_471752.b2c_service_order.order_amount</code>. 订单总金额
     */
    public final TableField<B2cServiceOrderRecord, BigDecimal> ORDER_AMOUNT = createField("order_amount", org.jooq.impl.SQLDataType.DECIMAL(10, 2).nullable(false).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "订单总金额");

    /**
     * The column <code>mini_shop_471752.b2c_service_order.add_time</code>. 订单提交时间
     */
    public final TableField<B2cServiceOrderRecord, Timestamp> ADD_TIME = createField("add_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "订单提交时间");

    /**
     * The column <code>mini_shop_471752.b2c_service_order.pay_time</code>. 支付时间
     */
    public final TableField<B2cServiceOrderRecord, Timestamp> PAY_TIME = createField("pay_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "支付时间");

    /**
     * The column <code>mini_shop_471752.b2c_service_order.cancelled_time</code>. 取消时间
     */
    public final TableField<B2cServiceOrderRecord, Timestamp> CANCELLED_TIME = createField("cancelled_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "取消时间");

    /**
     * The column <code>mini_shop_471752.b2c_service_order.finished_time</code>. 订单完成时间
     */
    public final TableField<B2cServiceOrderRecord, Timestamp> FINISHED_TIME = createField("finished_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "订单完成时间");

    /**
     * The column <code>mini_shop_471752.b2c_service_order.prepay_id</code>. 微信支付Id，用于发送模板消息
     */
    public final TableField<B2cServiceOrderRecord, String> PREPAY_ID = createField("prepay_id", org.jooq.impl.SQLDataType.VARCHAR(191), this, "微信支付Id，用于发送模板消息");

    /**
     * The column <code>mini_shop_471752.b2c_service_order.del_flag</code>. 删除
     */
    public final TableField<B2cServiceOrderRecord, Byte> DEL_FLAG = createField("del_flag", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "删除");

    /**
     * The column <code>mini_shop_471752.b2c_service_order.verify_type</code>. 核销方式 0是店家核销 1是用户
     */
    public final TableField<B2cServiceOrderRecord, Byte> VERIFY_TYPE = createField("verify_type", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "核销方式 0是店家核销 1是用户");

    /**
     * The column <code>mini_shop_471752.b2c_service_order.cancel_reason</code>. 取消原因
     */
    public final TableField<B2cServiceOrderRecord, String> CANCEL_REASON = createField("cancel_reason", org.jooq.impl.SQLDataType.VARCHAR(200), this, "取消原因");

    /**
     * The column <code>mini_shop_471752.b2c_service_order.type</code>. 创建类型 0用户创建 1后台
     */
    public final TableField<B2cServiceOrderRecord, Byte> TYPE = createField("type", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "创建类型 0用户创建 1后台");

    /**
     * The column <code>mini_shop_471752.b2c_service_order.verify_pay</code>. 核销支付方式 0门店买单 1会员卡 2余额
     */
    public final TableField<B2cServiceOrderRecord, Byte> VERIFY_PAY = createField("verify_pay", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "核销支付方式 0门店买单 1会员卡 2余额");

    /**
     * The column <code>mini_shop_471752.b2c_service_order.ali_trade_no</code>. 支付宝交易单号
     */
    public final TableField<B2cServiceOrderRecord, String> ALI_TRADE_NO = createField("ali_trade_no", org.jooq.impl.SQLDataType.VARCHAR(60).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "支付宝交易单号");

    /**
     * Create a <code>mini_shop_471752.b2c_service_order</code> table reference
     */
    public B2cServiceOrder() {
        this(DSL.name("b2c_service_order"), null);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_service_order</code> table reference
     */
    public B2cServiceOrder(String alias) {
        this(DSL.name(alias), B2C_SERVICE_ORDER);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_service_order</code> table reference
     */
    public B2cServiceOrder(Name alias) {
        this(alias, B2C_SERVICE_ORDER);
    }

    private B2cServiceOrder(Name alias, Table<B2cServiceOrderRecord> aliased) {
        this(alias, aliased, null);
    }

    private B2cServiceOrder(Name alias, Table<B2cServiceOrderRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> B2cServiceOrder(Table<O> child, ForeignKey<O, B2cServiceOrderRecord> key) {
        super(child, key, B2C_SERVICE_ORDER);
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
        return Arrays.<Index>asList(Indexes.B2C_SERVICE_ORDER_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<B2cServiceOrderRecord, UInteger> getIdentity() {
        return Keys.IDENTITY_B2C_SERVICE_ORDER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<B2cServiceOrderRecord> getPrimaryKey() {
        return Keys.KEY_B2C_SERVICE_ORDER_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<B2cServiceOrderRecord>> getKeys() {
        return Arrays.<UniqueKey<B2cServiceOrderRecord>>asList(Keys.KEY_B2C_SERVICE_ORDER_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cServiceOrder as(String alias) {
        return new B2cServiceOrder(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cServiceOrder as(Name alias) {
        return new B2cServiceOrder(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cServiceOrder rename(String name) {
        return new B2cServiceOrder(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cServiceOrder rename(Name name) {
        return new B2cServiceOrder(name, null);
    }
}
