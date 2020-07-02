/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.PrescriptionItem;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record13;
import org.jooq.Row13;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 处方项目明细表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PrescriptionItemRecord extends UpdatableRecordImpl<PrescriptionItemRecord> implements Record13<Integer, String, String, Integer, String, String, Integer, String, Byte, String, Byte, Timestamp, Timestamp> {

    private static final long serialVersionUID = 612730029;

    /**
     * Setter for <code>mini_shop_471752.b2c_prescription_item.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_prescription_item.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_prescription_item.prescription_no</code>. 处方号外键
     */
    public void setPrescriptionNo(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_prescription_item.prescription_no</code>. 处方号外键
     */
    public String getPrescriptionNo() {
        return (String) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_prescription_item.prescription_detail_no</code>. 处方项目明细号码（可根据此字段反查批次号）
     */
    public void setPrescriptionDetailNo(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_prescription_item.prescription_detail_no</code>. 处方项目明细号码（可根据此字段反查批次号）
     */
    public String getPrescriptionDetailNo() {
        return (String) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_prescription_item.goods_id</code>. 商品id
     */
    public void setGoodsId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_prescription_item.goods_id</code>. 商品id
     */
    public Integer getGoodsId() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_prescription_item.goods_common_name</code>. 通用名
     */
    public void setGoodsCommonName(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_prescription_item.goods_common_name</code>. 通用名
     */
    public String getGoodsCommonName() {
        return (String) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_prescription_item.goods_quality_ratio</code>. 规格系数，通用名和规格系数确定一个药品
     */
    public void setGoodsQualityRatio(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_prescription_item.goods_quality_ratio</code>. 规格系数，通用名和规格系数确定一个药品
     */
    public String getGoodsQualityRatio() {
        return (String) get(5);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_prescription_item.goods_num</code>. 使用药品数量
     */
    public void setGoodsNum(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_prescription_item.goods_num</code>. 使用药品数量
     */
    public Integer getGoodsNum() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_prescription_item.goods_use_memo</code>. 药品使用方式说明
     */
    public void setGoodsUseMemo(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_prescription_item.goods_use_memo</code>. 药品使用方式说明
     */
    public String getGoodsUseMemo() {
        return (String) get(7);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_prescription_item.status</code>. 处方审核状态 0待审核 1审核通过 2审核未通过
     */
    public void setStatus(Byte value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_prescription_item.status</code>. 处方审核状态 0待审核 1审核通过 2审核未通过
     */
    public Byte getStatus() {
        return (Byte) get(8);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_prescription_item.status_memo</code>. 处方审核医师评价
     */
    public void setStatusMemo(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_prescription_item.status_memo</code>. 处方审核医师评价
     */
    public String getStatusMemo() {
        return (String) get(9);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_prescription_item.is_delete</code>.
     */
    public void setIsDelete(Byte value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_prescription_item.is_delete</code>.
     */
    public Byte getIsDelete() {
        return (Byte) get(10);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_prescription_item.create_time</code>.
     */
    public void setCreateTime(Timestamp value) {
        set(11, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_prescription_item.create_time</code>.
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(11);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_prescription_item.update_time</code>. 最后修改时间
     */
    public void setUpdateTime(Timestamp value) {
        set(12, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_prescription_item.update_time</code>. 最后修改时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(12);
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
    // Record13 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row13<Integer, String, String, Integer, String, String, Integer, String, Byte, String, Byte, Timestamp, Timestamp> fieldsRow() {
        return (Row13) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row13<Integer, String, String, Integer, String, String, Integer, String, Byte, String, Byte, Timestamp, Timestamp> valuesRow() {
        return (Row13) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return PrescriptionItem.PRESCRIPTION_ITEM.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return PrescriptionItem.PRESCRIPTION_ITEM.PRESCRIPTION_NO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return PrescriptionItem.PRESCRIPTION_ITEM.PRESCRIPTION_DETAIL_NO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return PrescriptionItem.PRESCRIPTION_ITEM.GOODS_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return PrescriptionItem.PRESCRIPTION_ITEM.GOODS_COMMON_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return PrescriptionItem.PRESCRIPTION_ITEM.GOODS_QUALITY_RATIO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return PrescriptionItem.PRESCRIPTION_ITEM.GOODS_NUM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return PrescriptionItem.PRESCRIPTION_ITEM.GOODS_USE_MEMO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field9() {
        return PrescriptionItem.PRESCRIPTION_ITEM.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field10() {
        return PrescriptionItem.PRESCRIPTION_ITEM.STATUS_MEMO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field11() {
        return PrescriptionItem.PRESCRIPTION_ITEM.IS_DELETE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field12() {
        return PrescriptionItem.PRESCRIPTION_ITEM.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field13() {
        return PrescriptionItem.PRESCRIPTION_ITEM.UPDATE_TIME;
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
    public String component2() {
        return getPrescriptionNo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getPrescriptionDetailNo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component4() {
        return getGoodsId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getGoodsCommonName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getGoodsQualityRatio();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component7() {
        return getGoodsNum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component8() {
        return getGoodsUseMemo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component9() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component10() {
        return getStatusMemo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component11() {
        return getIsDelete();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component12() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component13() {
        return getUpdateTime();
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
    public String value2() {
        return getPrescriptionNo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getPrescriptionDetailNo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getGoodsId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getGoodsCommonName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getGoodsQualityRatio();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getGoodsNum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getGoodsUseMemo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value9() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value10() {
        return getStatusMemo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value11() {
        return getIsDelete();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value12() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value13() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PrescriptionItemRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PrescriptionItemRecord value2(String value) {
        setPrescriptionNo(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PrescriptionItemRecord value3(String value) {
        setPrescriptionDetailNo(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PrescriptionItemRecord value4(Integer value) {
        setGoodsId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PrescriptionItemRecord value5(String value) {
        setGoodsCommonName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PrescriptionItemRecord value6(String value) {
        setGoodsQualityRatio(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PrescriptionItemRecord value7(Integer value) {
        setGoodsNum(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PrescriptionItemRecord value8(String value) {
        setGoodsUseMemo(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PrescriptionItemRecord value9(Byte value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PrescriptionItemRecord value10(String value) {
        setStatusMemo(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PrescriptionItemRecord value11(Byte value) {
        setIsDelete(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PrescriptionItemRecord value12(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PrescriptionItemRecord value13(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PrescriptionItemRecord values(Integer value1, String value2, String value3, Integer value4, String value5, String value6, Integer value7, String value8, Byte value9, String value10, Byte value11, Timestamp value12, Timestamp value13) {
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
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached PrescriptionItemRecord
     */
    public PrescriptionItemRecord() {
        super(PrescriptionItem.PRESCRIPTION_ITEM);
    }

    /**
     * Create a detached, initialised PrescriptionItemRecord
     */
    public PrescriptionItemRecord(Integer id, String prescriptionNo, String prescriptionDetailNo, Integer goodsId, String goodsCommonName, String goodsQualityRatio, Integer goodsNum, String goodsUseMemo, Byte status, String statusMemo, Byte isDelete, Timestamp createTime, Timestamp updateTime) {
        super(PrescriptionItem.PRESCRIPTION_ITEM);

        set(0, id);
        set(1, prescriptionNo);
        set(2, prescriptionDetailNo);
        set(3, goodsId);
        set(4, goodsCommonName);
        set(5, goodsQualityRatio);
        set(6, goodsNum);
        set(7, goodsUseMemo);
        set(8, status);
        set(9, statusMemo);
        set(10, isDelete);
        set(11, createTime);
        set(12, updateTime);
    }
}
