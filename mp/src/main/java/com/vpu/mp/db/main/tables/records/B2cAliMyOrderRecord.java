/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.main.tables.records;


import com.vpu.mp.db.main.tables.B2cAliMyOrder;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record14;
import org.jooq.Row14;
import org.jooq.impl.UpdatableRecordImpl;
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
public class B2cAliMyOrderRecord extends UpdatableRecordImpl<B2cAliMyOrderRecord> implements Record14<UInteger, String, Timestamp, String, String, String, String, String, UInteger, BigDecimal, Timestamp, String, String, Integer> {

    private static final long serialVersionUID = 2001390271;

    /**
     * Setter for <code>mini_main.b2c_ali_my_order.rec_id</code>.
     */
    public void setRecId(UInteger value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_my_order.rec_id</code>.
     */
    public UInteger getRecId() {
        return (UInteger) get(0);
    }

    /**
     * Setter for <code>mini_main.b2c_ali_my_order.commodity_order_id</code>. 订单编号
     */
    public void setCommodityOrderId(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_my_order.commodity_order_id</code>. 订单编号
     */
    public String getCommodityOrderId() {
        return (String) get(1);
    }

    /**
     * Setter for <code>mini_main.b2c_ali_my_order.order_time</code>. 订购时间
     */
    public void setOrderTime(Timestamp value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_my_order.order_time</code>. 订购时间
     */
    public Timestamp getOrderTime() {
        return (Timestamp) get(2);
    }

    /**
     * Setter for <code>mini_main.b2c_ali_my_order.title</code>. 服务名称
     */
    public void setTitle(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_my_order.title</code>. 服务名称
     */
    public String getTitle() {
        return (String) get(3);
    }

    /**
     * Setter for <code>mini_main.b2c_ali_my_order.name</code>. 商户名称
     */
    public void setName(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_my_order.name</code>. 商户名称
     */
    public String getName() {
        return (String) get(4);
    }

    /**
     * Setter for <code>mini_main.b2c_ali_my_order.merchant_pid</code>. 商户ID	
     */
    public void setMerchantPid(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_my_order.merchant_pid</code>. 商户ID	
     */
    public String getMerchantPid() {
        return (String) get(5);
    }

    /**
     * Setter for <code>mini_main.b2c_ali_my_order.contactor</code>. 联系人	
     */
    public void setContactor(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_my_order.contactor</code>. 联系人	
     */
    public String getContactor() {
        return (String) get(6);
    }

    /**
     * Setter for <code>mini_main.b2c_ali_my_order.phone</code>. 联系电话
     */
    public void setPhone(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_my_order.phone</code>. 联系电话
     */
    public String getPhone() {
        return (String) get(7);
    }

    /**
     * Setter for <code>mini_main.b2c_ali_my_order.order_item_num</code>. 门店数量	
     */
    public void setOrderItemNum(UInteger value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_my_order.order_item_num</code>. 门店数量	
     */
    public UInteger getOrderItemNum() {
        return (UInteger) get(8);
    }

    /**
     * Setter for <code>mini_main.b2c_ali_my_order.total_price</code>. 总价格	
     */
    public void setTotalPrice(BigDecimal value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_my_order.total_price</code>. 总价格	
     */
    public BigDecimal getTotalPrice() {
        return (BigDecimal) get(9);
    }

    /**
     * Setter for <code>mini_main.b2c_ali_my_order.create_time</code>.
     */
    public void setCreateTime(Timestamp value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_my_order.create_time</code>.
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(10);
    }

    /**
     * Setter for <code>mini_main.b2c_ali_my_order.order_ticket</code>. 订单授权码，用于事务创建接口中的入参
     */
    public void setOrderTicket(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_my_order.order_ticket</code>. 订单授权码，用于事务创建接口中的入参
     */
    public String getOrderTicket() {
        return (String) get(11);
    }

    /**
     * Setter for <code>mini_main.b2c_ali_my_order.service_code</code>. 服务码，用于商户和ISV匹配订单
     */
    public void setServiceCode(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_my_order.service_code</code>. 服务码，用于商户和ISV匹配订单
     */
    public String getServiceCode() {
        return (String) get(12);
    }

    /**
     * Setter for <code>mini_main.b2c_ali_my_order.link_shop_id</code>. 关联店铺Id
     */
    public void setLinkShopId(Integer value) {
        set(13, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_my_order.link_shop_id</code>. 关联店铺Id
     */
    public Integer getLinkShopId() {
        return (Integer) get(13);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<UInteger> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record14 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row14<UInteger, String, Timestamp, String, String, String, String, String, UInteger, BigDecimal, Timestamp, String, String, Integer> fieldsRow() {
        return (Row14) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row14<UInteger, String, Timestamp, String, String, String, String, String, UInteger, BigDecimal, Timestamp, String, String, Integer> valuesRow() {
        return (Row14) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UInteger> field1() {
        return B2cAliMyOrder.B2C_ALI_MY_ORDER.REC_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return B2cAliMyOrder.B2C_ALI_MY_ORDER.COMMODITY_ORDER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field3() {
        return B2cAliMyOrder.B2C_ALI_MY_ORDER.ORDER_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return B2cAliMyOrder.B2C_ALI_MY_ORDER.TITLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return B2cAliMyOrder.B2C_ALI_MY_ORDER.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return B2cAliMyOrder.B2C_ALI_MY_ORDER.MERCHANT_PID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return B2cAliMyOrder.B2C_ALI_MY_ORDER.CONTACTOR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return B2cAliMyOrder.B2C_ALI_MY_ORDER.PHONE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UInteger> field9() {
        return B2cAliMyOrder.B2C_ALI_MY_ORDER.ORDER_ITEM_NUM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field10() {
        return B2cAliMyOrder.B2C_ALI_MY_ORDER.TOTAL_PRICE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field11() {
        return B2cAliMyOrder.B2C_ALI_MY_ORDER.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field12() {
        return B2cAliMyOrder.B2C_ALI_MY_ORDER.ORDER_TICKET;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field13() {
        return B2cAliMyOrder.B2C_ALI_MY_ORDER.SERVICE_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field14() {
        return B2cAliMyOrder.B2C_ALI_MY_ORDER.LINK_SHOP_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger component1() {
        return getRecId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getCommodityOrderId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component3() {
        return getOrderTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getTitle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getMerchantPid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getContactor();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component8() {
        return getPhone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger component9() {
        return getOrderItemNum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component10() {
        return getTotalPrice();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component11() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component12() {
        return getOrderTicket();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component13() {
        return getServiceCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component14() {
        return getLinkShopId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger value1() {
        return getRecId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getCommodityOrderId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value3() {
        return getOrderTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getTitle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getMerchantPid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getContactor();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getPhone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger value9() {
        return getOrderItemNum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value10() {
        return getTotalPrice();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value11() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value12() {
        return getOrderTicket();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value13() {
        return getServiceCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value14() {
        return getLinkShopId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cAliMyOrderRecord value1(UInteger value) {
        setRecId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cAliMyOrderRecord value2(String value) {
        setCommodityOrderId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cAliMyOrderRecord value3(Timestamp value) {
        setOrderTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cAliMyOrderRecord value4(String value) {
        setTitle(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cAliMyOrderRecord value5(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cAliMyOrderRecord value6(String value) {
        setMerchantPid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cAliMyOrderRecord value7(String value) {
        setContactor(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cAliMyOrderRecord value8(String value) {
        setPhone(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cAliMyOrderRecord value9(UInteger value) {
        setOrderItemNum(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cAliMyOrderRecord value10(BigDecimal value) {
        setTotalPrice(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cAliMyOrderRecord value11(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cAliMyOrderRecord value12(String value) {
        setOrderTicket(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cAliMyOrderRecord value13(String value) {
        setServiceCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cAliMyOrderRecord value14(Integer value) {
        setLinkShopId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cAliMyOrderRecord values(UInteger value1, String value2, Timestamp value3, String value4, String value5, String value6, String value7, String value8, UInteger value9, BigDecimal value10, Timestamp value11, String value12, String value13, Integer value14) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        value12(value12);
        value13(value13);
        value14(value14);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached B2cAliMyOrderRecord
     */
    public B2cAliMyOrderRecord() {
        super(B2cAliMyOrder.B2C_ALI_MY_ORDER);
    }

    /**
     * Create a detached, initialised B2cAliMyOrderRecord
     */
    public B2cAliMyOrderRecord(UInteger recId, String commodityOrderId, Timestamp orderTime, String title, String name, String merchantPid, String contactor, String phone, UInteger orderItemNum, BigDecimal totalPrice, Timestamp createTime, String orderTicket, String serviceCode, Integer linkShopId) {
        super(B2cAliMyOrder.B2C_ALI_MY_ORDER);

        set(0, recId);
        set(1, commodityOrderId);
        set(2, orderTime);
        set(3, title);
        set(4, name);
        set(5, merchantPid);
        set(6, contactor);
        set(7, phone);
        set(8, orderItemNum);
        set(9, totalPrice);
        set(10, createTime);
        set(11, orderTicket);
        set(12, serviceCode);
        set(13, linkShopId);
    }
}
