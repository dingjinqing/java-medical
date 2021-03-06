/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.main.tables;


import com.vpu.mp.db.main.Indexes;
import com.vpu.mp.db.main.Keys;
import com.vpu.mp.db.main.MiniMain;
import com.vpu.mp.db.main.tables.records.ReturnOrderBakRecord;

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
 * 退回订单表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ReturnOrderBak extends TableImpl<ReturnOrderBakRecord> {

    private static final long serialVersionUID = 1417915170;

    /**
     * The reference instance of <code>mini_main.b2c_return_order_bak</code>
     */
    public static final ReturnOrderBak RETURN_ORDER_BAK = new ReturnOrderBak();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ReturnOrderBakRecord> getRecordType() {
        return ReturnOrderBakRecord.class;
    }

    /**
     * The column <code>mini_main.b2c_return_order_bak.ret_id</code>.
     */
    public final TableField<ReturnOrderBakRecord, Integer> RET_ID = createField("ret_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_main.b2c_return_order_bak.order_id</code>.
     */
    public final TableField<ReturnOrderBakRecord, Integer> ORDER_ID = createField("order_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>mini_main.b2c_return_order_bak.order_sn</code>.
     */
    public final TableField<ReturnOrderBakRecord, String> ORDER_SN = createField("order_sn", org.jooq.impl.SQLDataType.VARCHAR(30).nullable(false).defaultValue(DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>mini_main.b2c_return_order_bak.return_order_sn</code>. 退款单号
     */
    public final TableField<ReturnOrderBakRecord, String> RETURN_ORDER_SN = createField("return_order_sn", org.jooq.impl.SQLDataType.VARCHAR(30).defaultValue(DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "退款单号");

    /**
     * The column <code>mini_main.b2c_return_order_bak.shop_id</code>. 店铺id
     */
    public final TableField<ReturnOrderBakRecord, Integer> SHOP_ID = createField("shop_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "店铺id");

    /**
     * The column <code>mini_main.b2c_return_order_bak.user_id</code>.
     */
    public final TableField<ReturnOrderBakRecord, Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>mini_main.b2c_return_order_bak.goods_id</code>.
     */
    public final TableField<ReturnOrderBakRecord, Integer> GOODS_ID = createField("goods_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>mini_main.b2c_return_order_bak.refund_status</code>. 1是审核中，2是通过审核，3退货没通过审核，4买家提交物流 或 仅退款申请，5：退款退货成功，6是拒绝退款退货,7 撤销退款、退货
     */
    public final TableField<ReturnOrderBakRecord, Byte> REFUND_STATUS = createField("refund_status", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(DSL.inline("1", org.jooq.impl.SQLDataType.TINYINT)), this, "1是审核中，2是通过审核，3退货没通过审核，4买家提交物流 或 仅退款申请，5：退款退货成功，6是拒绝退款退货,7 撤销退款、退货");

    /**
     * The column <code>mini_main.b2c_return_order_bak.money</code>. 退款商品金额
     */
    public final TableField<ReturnOrderBakRecord, BigDecimal> MONEY = createField("money", org.jooq.impl.SQLDataType.DECIMAL(10, 2).nullable(false).defaultValue(DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "退款商品金额");

    /**
     * The column <code>mini_main.b2c_return_order_bak.shipping_fee</code>. 退运费金额
     */
    public final TableField<ReturnOrderBakRecord, BigDecimal> SHIPPING_FEE = createField("shipping_fee", org.jooq.impl.SQLDataType.DECIMAL(10, 2).nullable(false).defaultValue(DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "退运费金额");

    /**
     * The column <code>mini_main.b2c_return_order_bak.return_type</code>. 退款类型,0:只退款，1:退货又退款
     */
    public final TableField<ReturnOrderBakRecord, Byte> RETURN_TYPE = createField("return_type", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "退款类型,0:只退款，1:退货又退款");

    /**
     * The column <code>mini_main.b2c_return_order_bak.reason_type</code>. 退款/退货原因类型，0：协商一致退款，1：未按约定时间发货，2：缺货，3：拍错/多拍/不想要，4：其他
     */
    public final TableField<ReturnOrderBakRecord, Byte> REASON_TYPE = createField("reason_type", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "退款/退货原因类型，0：协商一致退款，1：未按约定时间发货，2：缺货，3：拍错/多拍/不想要，4：其他");

    /**
     * The column <code>mini_main.b2c_return_order_bak.reason_desc</code>. 退款/退货描述
     */
    public final TableField<ReturnOrderBakRecord, String> REASON_DESC = createField("reason_desc", org.jooq.impl.SQLDataType.CLOB, this, "退款/退货描述");

    /**
     * The column <code>mini_main.b2c_return_order_bak.shipping_type</code>. 快递类型
     */
    public final TableField<ReturnOrderBakRecord, String> SHIPPING_TYPE = createField("shipping_type", org.jooq.impl.SQLDataType.VARCHAR(191).nullable(false).defaultValue(DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "快递类型");

    /**
     * The column <code>mini_main.b2c_return_order_bak.shipping_no</code>. 快递单号
     */
    public final TableField<ReturnOrderBakRecord, String> SHIPPING_NO = createField("shipping_no", org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false).defaultValue(DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "快递单号");

    /**
     * The column <code>mini_main.b2c_return_order_bak.goods_images</code>. 商品图片
     */
    public final TableField<ReturnOrderBakRecord, String> GOODS_IMAGES = createField("goods_images", org.jooq.impl.SQLDataType.CLOB, this, "商品图片");

    /**
     * The column <code>mini_main.b2c_return_order_bak.voucher_images</code>. 凭证图片
     */
    public final TableField<ReturnOrderBakRecord, String> VOUCHER_IMAGES = createField("voucher_images", org.jooq.impl.SQLDataType.CLOB, this, "凭证图片");

    /**
     * The column <code>mini_main.b2c_return_order_bak.phone</code>. 电话号码
     */
    public final TableField<ReturnOrderBakRecord, String> PHONE = createField("phone", org.jooq.impl.SQLDataType.VARCHAR(12).nullable(false).defaultValue(DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "电话号码");

    /**
     * The column <code>mini_main.b2c_return_order_bak.apply_time</code>. 退货且退货提交审核时间，对应refund_status=1
     */
    public final TableField<ReturnOrderBakRecord, Timestamp> APPLY_TIME = createField("apply_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "退货且退货提交审核时间，对应refund_status=1");

    /**
     * The column <code>mini_main.b2c_return_order_bak.apply_pass_time</code>. 审核通过时间，对应refund_status=2
     */
    public final TableField<ReturnOrderBakRecord, Timestamp> APPLY_PASS_TIME = createField("apply_pass_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "审核通过时间，对应refund_status=2");

    /**
     * The column <code>mini_main.b2c_return_order_bak.apply_not_pass_time</code>. 审核未通过时间，对应refund_status=3
     */
    public final TableField<ReturnOrderBakRecord, Timestamp> APPLY_NOT_PASS_TIME = createField("apply_not_pass_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "审核未通过时间，对应refund_status=3");

    /**
     * The column <code>mini_main.b2c_return_order_bak.shipping_or_refund_time</code>. 只退款时为退款申请时间，退货又退款时为提交物流信息时间，对应refund_status=4
     */
    public final TableField<ReturnOrderBakRecord, Timestamp> SHIPPING_OR_REFUND_TIME = createField("shipping_or_refund_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "只退款时为退款申请时间，退货又退款时为提交物流信息时间，对应refund_status=4");

    /**
     * The column <code>mini_main.b2c_return_order_bak.refund_success_time</code>. 退款成功时间，对应refund_status=5
     */
    public final TableField<ReturnOrderBakRecord, Timestamp> REFUND_SUCCESS_TIME = createField("refund_success_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "退款成功时间，对应refund_status=5");

    /**
     * The column <code>mini_main.b2c_return_order_bak.refund_refuse_time</code>. 退款拒绝时间，对应refund_status=6
     */
    public final TableField<ReturnOrderBakRecord, Timestamp> REFUND_REFUSE_TIME = createField("refund_refuse_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "退款拒绝时间，对应refund_status=6");

    /**
     * The column <code>mini_main.b2c_return_order_bak.refund_cancel_time</code>. 退款撤销时间，对应refund_status=7
     */
    public final TableField<ReturnOrderBakRecord, Timestamp> REFUND_CANCEL_TIME = createField("refund_cancel_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "退款撤销时间，对应refund_status=7");

    /**
     * The column <code>mini_main.b2c_return_order_bak.apply_not_pass_reason</code>. 审核未通过原因
     */
    public final TableField<ReturnOrderBakRecord, String> APPLY_NOT_PASS_REASON = createField("apply_not_pass_reason", org.jooq.impl.SQLDataType.VARCHAR(1000), this, "审核未通过原因");

    /**
     * The column <code>mini_main.b2c_return_order_bak.refund_refuse_reason</code>. 退款拒绝原因
     */
    public final TableField<ReturnOrderBakRecord, String> REFUND_REFUSE_REASON = createField("refund_refuse_reason", org.jooq.impl.SQLDataType.VARCHAR(1000), this, "退款拒绝原因");

    /**
     * The column <code>mini_main.b2c_return_order_bak.return_address</code>. 退货地址
     */
    public final TableField<ReturnOrderBakRecord, String> RETURN_ADDRESS = createField("return_address", org.jooq.impl.SQLDataType.VARCHAR(1000).nullable(false).defaultValue(DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "退货地址");

    /**
     * The column <code>mini_main.b2c_return_order_bak.merchant_telephone</code>. 商家电话
     */
    public final TableField<ReturnOrderBakRecord, String> MERCHANT_TELEPHONE = createField("merchant_telephone", org.jooq.impl.SQLDataType.VARCHAR(12).nullable(false).defaultValue(DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "商家电话");

    /**
     * The column <code>mini_main.b2c_return_order_bak.consignee</code>. 收货人
     */
    public final TableField<ReturnOrderBakRecord, String> CONSIGNEE = createField("consignee", org.jooq.impl.SQLDataType.VARCHAR(32).nullable(false).defaultValue(DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "收货人");

    /**
     * The column <code>mini_main.b2c_return_order_bak.zip_code</code>. 邮编
     */
    public final TableField<ReturnOrderBakRecord, String> ZIP_CODE = createField("zip_code", org.jooq.impl.SQLDataType.VARCHAR(10).nullable(false).defaultValue(DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "邮编");

    /**
     * The column <code>mini_main.b2c_return_order_bak.currency</code>. 币种
     */
    public final TableField<ReturnOrderBakRecord, String> CURRENCY = createField("currency", org.jooq.impl.SQLDataType.VARCHAR(10).nullable(false).defaultValue(DSL.inline("CNY", org.jooq.impl.SQLDataType.VARCHAR)), this, "币种");

    /**
     * The column <code>mini_main.b2c_return_order_bak.create_time</code>.
     */
    public final TableField<ReturnOrderBakRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>mini_main.b2c_return_order_bak.update_time</code>. 最后修改时间
     */
    public final TableField<ReturnOrderBakRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "最后修改时间");

    /**
     * The column <code>mini_main.b2c_return_order_bak.is_auto_return</code>. 0否；1是
     */
    public final TableField<ReturnOrderBakRecord, Byte> IS_AUTO_RETURN = createField("is_auto_return", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "0否；1是");

    /**
     * The column <code>mini_main.b2c_return_order_bak.return_source</code>. 售后发起来源：0商家手动发起，1用户主动申请，2订单异常系统自动发起
     */
    public final TableField<ReturnOrderBakRecord, Byte> RETURN_SOURCE = createField("return_source", org.jooq.impl.SQLDataType.TINYINT.defaultValue(DSL.inline("1", org.jooq.impl.SQLDataType.TINYINT)), this, "售后发起来源：0商家手动发起，1用户主动申请，2订单异常系统自动发起");

    /**
     * The column <code>mini_main.b2c_return_order_bak.return_source_type</code>. 售后发起来源类型：0改价失败自动售后，1微信支付失败，2活动自动售后
     */
    public final TableField<ReturnOrderBakRecord, Byte> RETURN_SOURCE_TYPE = createField("return_source_type", org.jooq.impl.SQLDataType.TINYINT.defaultValue(DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "售后发起来源类型：0改价失败自动售后，1微信支付失败，2活动自动售后");

    /**
     * Create a <code>mini_main.b2c_return_order_bak</code> table reference
     */
    public ReturnOrderBak() {
        this(DSL.name("b2c_return_order_bak"), null);
    }

    /**
     * Create an aliased <code>mini_main.b2c_return_order_bak</code> table reference
     */
    public ReturnOrderBak(String alias) {
        this(DSL.name(alias), RETURN_ORDER_BAK);
    }

    /**
     * Create an aliased <code>mini_main.b2c_return_order_bak</code> table reference
     */
    public ReturnOrderBak(Name alias) {
        this(alias, RETURN_ORDER_BAK);
    }

    private ReturnOrderBak(Name alias, Table<ReturnOrderBakRecord> aliased) {
        this(alias, aliased, null);
    }

    private ReturnOrderBak(Name alias, Table<ReturnOrderBakRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("退回订单表"));
    }

    public <O extends Record> ReturnOrderBak(Table<O> child, ForeignKey<O, ReturnOrderBakRecord> key) {
        super(child, key, RETURN_ORDER_BAK);
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
        return Arrays.<Index>asList(Indexes.RETURN_ORDER_BAK_ORDER_SN, Indexes.RETURN_ORDER_BAK_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<ReturnOrderBakRecord, Integer> getIdentity() {
        return Keys.IDENTITY_RETURN_ORDER_BAK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ReturnOrderBakRecord> getPrimaryKey() {
        return Keys.KEY_B2C_RETURN_ORDER_BAK_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ReturnOrderBakRecord>> getKeys() {
        return Arrays.<UniqueKey<ReturnOrderBakRecord>>asList(Keys.KEY_B2C_RETURN_ORDER_BAK_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReturnOrderBak as(String alias) {
        return new ReturnOrderBak(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReturnOrderBak as(Name alias) {
        return new ReturnOrderBak(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ReturnOrderBak rename(String name) {
        return new ReturnOrderBak(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ReturnOrderBak rename(Name name) {
        return new ReturnOrderBak(name, null);
    }
}
