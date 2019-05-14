/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.B2cFanliGoodsStatistics;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.TableRecordImpl;


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
public class B2cFanliGoodsStatisticsRecord extends TableRecordImpl<B2cFanliGoodsStatisticsRecord> implements Record8<Integer, String, Integer, Integer, Integer, BigDecimal, Timestamp, Timestamp> {

    private static final long serialVersionUID = 708912159;

    /**
     * Setter for <code>mini_shop_471752.b2c_fanli_goods_statistics.prd_id</code>.
     */
    public void setPrdId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_fanli_goods_statistics.prd_id</code>.
     */
    public Integer getPrdId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_fanli_goods_statistics.prd_sn</code>. 规格编码
     */
    public void setPrdSn(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_fanli_goods_statistics.prd_sn</code>. 规格编码
     */
    public String getPrdSn() {
        return (String) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_fanli_goods_statistics.goods_id</code>.
     */
    public void setGoodsId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_fanli_goods_statistics.goods_id</code>.
     */
    public Integer getGoodsId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_fanli_goods_statistics.cat_id</code>. 分类ID
     */
    public void setCatId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_fanli_goods_statistics.cat_id</code>. 分类ID
     */
    public Integer getCatId() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_fanli_goods_statistics.sale_number</code>. 销量
     */
    public void setSaleNumber(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_fanli_goods_statistics.sale_number</code>. 销量
     */
    public Integer getSaleNumber() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_fanli_goods_statistics.prd_total_fanli</code>. 商品返利总金额
     */
    public void setPrdTotalFanli(BigDecimal value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_fanli_goods_statistics.prd_total_fanli</code>. 商品返利总金额
     */
    public BigDecimal getPrdTotalFanli() {
        return (BigDecimal) get(5);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_fanli_goods_statistics.add_time</code>.
     */
    public void setAddTime(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_fanli_goods_statistics.add_time</code>.
     */
    public Timestamp getAddTime() {
        return (Timestamp) get(6);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_fanli_goods_statistics.update_time</code>.
     */
    public void setUpdateTime(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_fanli_goods_statistics.update_time</code>.
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(7);
    }

    // -------------------------------------------------------------------------
    // Record8 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Integer, String, Integer, Integer, Integer, BigDecimal, Timestamp, Timestamp> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Integer, String, Integer, Integer, Integer, BigDecimal, Timestamp, Timestamp> valuesRow() {
        return (Row8) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return B2cFanliGoodsStatistics.B2C_FANLI_GOODS_STATISTICS.PRD_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return B2cFanliGoodsStatistics.B2C_FANLI_GOODS_STATISTICS.PRD_SN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return B2cFanliGoodsStatistics.B2C_FANLI_GOODS_STATISTICS.GOODS_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return B2cFanliGoodsStatistics.B2C_FANLI_GOODS_STATISTICS.CAT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return B2cFanliGoodsStatistics.B2C_FANLI_GOODS_STATISTICS.SALE_NUMBER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field6() {
        return B2cFanliGoodsStatistics.B2C_FANLI_GOODS_STATISTICS.PRD_TOTAL_FANLI;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return B2cFanliGoodsStatistics.B2C_FANLI_GOODS_STATISTICS.ADD_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return B2cFanliGoodsStatistics.B2C_FANLI_GOODS_STATISTICS.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getPrdId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getPrdSn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component3() {
        return getGoodsId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component4() {
        return getCatId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component5() {
        return getSaleNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component6() {
        return getPrdTotalFanli();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component7() {
        return getAddTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component8() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getPrdId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getPrdSn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getGoodsId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getCatId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getSaleNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value6() {
        return getPrdTotalFanli();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value7() {
        return getAddTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value8() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cFanliGoodsStatisticsRecord value1(Integer value) {
        setPrdId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cFanliGoodsStatisticsRecord value2(String value) {
        setPrdSn(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cFanliGoodsStatisticsRecord value3(Integer value) {
        setGoodsId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cFanliGoodsStatisticsRecord value4(Integer value) {
        setCatId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cFanliGoodsStatisticsRecord value5(Integer value) {
        setSaleNumber(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cFanliGoodsStatisticsRecord value6(BigDecimal value) {
        setPrdTotalFanli(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cFanliGoodsStatisticsRecord value7(Timestamp value) {
        setAddTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cFanliGoodsStatisticsRecord value8(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cFanliGoodsStatisticsRecord values(Integer value1, String value2, Integer value3, Integer value4, Integer value5, BigDecimal value6, Timestamp value7, Timestamp value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached B2cFanliGoodsStatisticsRecord
     */
    public B2cFanliGoodsStatisticsRecord() {
        super(B2cFanliGoodsStatistics.B2C_FANLI_GOODS_STATISTICS);
    }

    /**
     * Create a detached, initialised B2cFanliGoodsStatisticsRecord
     */
    public B2cFanliGoodsStatisticsRecord(Integer prdId, String prdSn, Integer goodsId, Integer catId, Integer saleNumber, BigDecimal prdTotalFanli, Timestamp addTime, Timestamp updateTime) {
        super(B2cFanliGoodsStatistics.B2C_FANLI_GOODS_STATISTICS);

        set(0, prdId);
        set(1, prdSn);
        set(2, goodsId);
        set(3, catId);
        set(4, saleNumber);
        set(5, prdTotalFanli);
        set(6, addTime);
        set(7, updateTime);
    }
}
