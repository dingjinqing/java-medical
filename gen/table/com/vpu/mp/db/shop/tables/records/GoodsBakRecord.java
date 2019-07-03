/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.GoodsBak;

import java.math.BigDecimal;
import java.sql.Date;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record11;
import org.jooq.Row11;
import org.jooq.impl.UpdatableRecordImpl;


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
public class GoodsBakRecord extends UpdatableRecordImpl<GoodsBakRecord> implements Record11<Integer, Date, Integer, Integer, Integer, Integer, BigDecimal, BigDecimal, Integer, Byte, Byte> {

    private static final long serialVersionUID = 1658111430;

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_bak.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_bak.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_bak.bak_date</code>. 备份日期：例2018-09-05
     */
    public void setBakDate(Date value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_bak.bak_date</code>. 备份日期：例2018-09-05
     */
    public Date getBakDate() {
        return (Date) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_bak.goods_id</code>.
     */
    public void setGoodsId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_bak.goods_id</code>.
     */
    public Integer getGoodsId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_bak.shop_id</code>. 店铺ID
     */
    public void setShopId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_bak.shop_id</code>. 店铺ID
     */
    public Integer getShopId() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_bak.cat_id</code>.
     */
    public void setCatId(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_bak.cat_id</code>.
     */
    public Integer getCatId() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_bak.sort_id</code>.
     */
    public void setSortId(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_bak.sort_id</code>.
     */
    public Integer getSortId() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_bak.market_price</code>.
     */
    public void setMarketPrice(BigDecimal value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_bak.market_price</code>.
     */
    public BigDecimal getMarketPrice() {
        return (BigDecimal) get(6);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_bak.shop_price</code>.
     */
    public void setShopPrice(BigDecimal value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_bak.shop_price</code>.
     */
    public BigDecimal getShopPrice() {
        return (BigDecimal) get(7);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_bak.goods_number</code>. 库存
     */
    public void setGoodsNumber(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_bak.goods_number</code>. 库存
     */
    public Integer getGoodsNumber() {
        return (Integer) get(8);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_bak.is_on_sale</code>. 是否在售，1在售，0下架
     */
    public void setIsOnSale(Byte value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_bak.is_on_sale</code>. 是否在售，1在售，0下架
     */
    public Byte getIsOnSale() {
        return (Byte) get(9);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_bak.goods_type</code>. 商品类型，0普通商品，1拼团商品，2分销，3砍价商品 4积分商品 5秒杀商品
     */
    public void setGoodsType(Byte value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_bak.goods_type</code>. 商品类型，0普通商品，1拼团商品，2分销，3砍价商品 4积分商品 5秒杀商品
     */
    public Byte getGoodsType() {
        return (Byte) get(10);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record11 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row11<Integer, Date, Integer, Integer, Integer, Integer, BigDecimal, BigDecimal, Integer, Byte, Byte> fieldsRow() {
        return (Row11) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row11<Integer, Date, Integer, Integer, Integer, Integer, BigDecimal, BigDecimal, Integer, Byte, Byte> valuesRow() {
        return (Row11) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return GoodsBak.GOODS_BAK.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Date> field2() {
        return GoodsBak.GOODS_BAK.BAK_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return GoodsBak.GOODS_BAK.GOODS_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return GoodsBak.GOODS_BAK.SHOP_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return GoodsBak.GOODS_BAK.CAT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return GoodsBak.GOODS_BAK.SORT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field7() {
        return GoodsBak.GOODS_BAK.MARKET_PRICE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field8() {
        return GoodsBak.GOODS_BAK.SHOP_PRICE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field9() {
        return GoodsBak.GOODS_BAK.GOODS_NUMBER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field10() {
        return GoodsBak.GOODS_BAK.IS_ON_SALE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field11() {
        return GoodsBak.GOODS_BAK.GOODS_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date component2() {
        return getBakDate();
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
        return getShopId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component5() {
        return getCatId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component6() {
        return getSortId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component7() {
        return getMarketPrice();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component8() {
        return getShopPrice();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component9() {
        return getGoodsNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component10() {
        return getIsOnSale();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component11() {
        return getGoodsType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date value2() {
        return getBakDate();
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
        return getShopId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getCatId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getSortId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value7() {
        return getMarketPrice();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value8() {
        return getShopPrice();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value9() {
        return getGoodsNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value10() {
        return getIsOnSale();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value11() {
        return getGoodsType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsBakRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsBakRecord value2(Date value) {
        setBakDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsBakRecord value3(Integer value) {
        setGoodsId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsBakRecord value4(Integer value) {
        setShopId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsBakRecord value5(Integer value) {
        setCatId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsBakRecord value6(Integer value) {
        setSortId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsBakRecord value7(BigDecimal value) {
        setMarketPrice(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsBakRecord value8(BigDecimal value) {
        setShopPrice(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsBakRecord value9(Integer value) {
        setGoodsNumber(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsBakRecord value10(Byte value) {
        setIsOnSale(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsBakRecord value11(Byte value) {
        setGoodsType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsBakRecord values(Integer value1, Date value2, Integer value3, Integer value4, Integer value5, Integer value6, BigDecimal value7, BigDecimal value8, Integer value9, Byte value10, Byte value11) {
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
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached GoodsBakRecord
     */
    public GoodsBakRecord() {
        super(GoodsBak.GOODS_BAK);
    }

    /**
     * Create a detached, initialised GoodsBakRecord
     */
    public GoodsBakRecord(Integer id, Date bakDate, Integer goodsId, Integer shopId, Integer catId, Integer sortId, BigDecimal marketPrice, BigDecimal shopPrice, Integer goodsNumber, Byte isOnSale, Byte goodsType) {
        super(GoodsBak.GOODS_BAK);

        set(0, id);
        set(1, bakDate);
        set(2, goodsId);
        set(3, shopId);
        set(4, catId);
        set(5, sortId);
        set(6, marketPrice);
        set(7, shopPrice);
        set(8, goodsNumber);
        set(9, isOnSale);
        set(10, goodsType);
    }
}
