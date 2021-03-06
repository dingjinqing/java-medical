/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.GoodsMedicalInfo;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record22;
import org.jooq.Row22;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.Generated;
import java.sql.Timestamp;


/**
 * 商品-药品信息表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class GoodsMedicalInfoRecord extends UpdatableRecordImpl<GoodsMedicalInfoRecord> implements Record22<Integer, Integer, String, String, String, String, Byte, Byte, Byte, String, String, String, String, Double, Double, String, String, String, String, Byte, Timestamp, Timestamp> {

    private static final long serialVersionUID = 1977892336;

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.id</code>. 商品额外信息id
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.id</code>. 商品额外信息id
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.goods_id</code>. 商品id
     */
    public void setGoodsId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.goods_id</code>. 商品id
     */
    public Integer getGoodsId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.goods_code</code>. 药品唯一编码
     */
    public void setGoodsCode(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.goods_code</code>. 药品唯一编码
     */
    public String getGoodsCode() {
        return (String) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.goods_common_name</code>. 通用名
     */
    public void setGoodsCommonName(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.goods_common_name</code>. 通用名
     */
    public String getGoodsCommonName() {
        return (String) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.goods_alias_name</code>. 别名
     */
    public void setGoodsAliasName(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.goods_alias_name</code>. 别名
     */
    public String getGoodsAliasName() {
        return (String) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.goods_quality_ratio</code>. 规格系数，通用名和规格系数确定一个药品
     */
    public void setGoodsQualityRatio(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.goods_quality_ratio</code>. 规格系数，通用名和规格系数确定一个药品
     */
    public String getGoodsQualityRatio() {
        return (String) get(5);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.is_rx</code>. 是否处方药 0否 1是 默认0
     */
    public void setIsRx(Byte value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.is_rx</code>. 是否处方药 0否 1是 默认0
     */
    public Byte getIsRx() {
        return (Byte) get(6);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.medical_type</code>. null 未知  1西药 2中成药 3中草药
     */
    public void setMedicalType(Byte value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.medical_type</code>. null 未知  1西药 2中成药 3中草药
     */
    public Byte getMedicalType() {
        return (Byte) get(7);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.insurance_flag</code>. 医保类型 1:甲 2:乙 3:丙 4:科研
     */
    public void setInsuranceFlag(Byte value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.insurance_flag</code>. 医保类型 1:甲 2:乙 3:丙 4:科研
     */
    public Byte getInsuranceFlag() {
        return (Byte) get(8);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.insurance_code</code>. 医保编码
     */
    public void setInsuranceCode(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.insurance_code</code>. 医保编码
     */
    public String getInsuranceCode() {
        return (String) get(9);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.insurance_database_name</code>. 医保库内名称
     */
    public void setInsuranceDatabaseName(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.insurance_database_name</code>. 医保库内名称
     */
    public String getInsuranceDatabaseName() {
        return (String) get(10);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.goods_basic_unit</code>. 商品基本单位
     */
    public void setGoodsBasicUnit(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.goods_basic_unit</code>. 商品基本单位
     */
    public String getGoodsBasicUnit() {
        return (String) get(11);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.goods_package_unit</code>. 商品包装单位
     */
    public void setGoodsPackageUnit(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.goods_package_unit</code>. 商品包装单位
     */
    public String getGoodsPackageUnit() {
        return (String) get(12);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.goods_unit_convert_factor</code>. 整包转换系数
     */
    public void setGoodsUnitConvertFactor(Double value) {
        set(13, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.goods_unit_convert_factor</code>. 整包转换系数
     */
    public Double getGoodsUnitConvertFactor() {
        return (Double) get(13);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.goods_equivalent_quantity</code>. 等效量
     */
    public void setGoodsEquivalentQuantity(Double value) {
        set(14, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.goods_equivalent_quantity</code>. 等效量
     */
    public Double getGoodsEquivalentQuantity() {
        return (Double) get(14);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.goods_equivalent_unit</code>. 等效单位
     */
    public void setGoodsEquivalentUnit(String value) {
        set(15, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.goods_equivalent_unit</code>. 等效单位
     */
    public String getGoodsEquivalentUnit() {
        return (String) get(15);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.goods_approval_number</code>. 批准文号
     */
    public void setGoodsApprovalNumber(String value) {
        set(16, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.goods_approval_number</code>. 批准文号
     */
    public String getGoodsApprovalNumber() {
        return (String) get(16);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.goods_production_enterprise</code>. 生产企业
     */
    public void setGoodsProductionEnterprise(String value) {
        set(17, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.goods_production_enterprise</code>. 生产企业
     */
    public String getGoodsProductionEnterprise() {
        return (String) get(17);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.goods_medical_instruction</code>.
     */
    public void setGoodsMedicalInstruction(String value) {
        set(18, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.goods_medical_instruction</code>.
     */
    public String getGoodsMedicalInstruction() {
        return (String) get(18);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.is_delete</code>.
     */
    public void setIsDelete(Byte value) {
        set(19, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.is_delete</code>.
     */
    public Byte getIsDelete() {
        return (Byte) get(19);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.create_time</code>.
     */
    public void setCreateTime(Timestamp value) {
        set(20, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.create_time</code>.
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(20);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.update_time</code>. 最后修改时间
     */
    public void setUpdateTime(Timestamp value) {
        set(21, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.update_time</code>. 最后修改时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(21);
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
    // Record22 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row22<Integer, Integer, String, String, String, String, Byte, Byte, Byte, String, String, String, String, Double, Double, String, String, String, String, Byte, Timestamp, Timestamp> fieldsRow() {
        return (Row22) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row22<Integer, Integer, String, String, String, String, Byte, Byte, Byte, String, String, String, String, Double, Double, String, String, String, String, Byte, Timestamp, Timestamp> valuesRow() {
        return (Row22) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return GoodsMedicalInfo.GOODS_MEDICAL_INFO.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return GoodsMedicalInfo.GOODS_MEDICAL_INFO.GOODS_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return GoodsMedicalInfo.GOODS_MEDICAL_INFO.GOODS_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return GoodsMedicalInfo.GOODS_MEDICAL_INFO.GOODS_COMMON_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return GoodsMedicalInfo.GOODS_MEDICAL_INFO.GOODS_ALIAS_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return GoodsMedicalInfo.GOODS_MEDICAL_INFO.GOODS_QUALITY_RATIO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field7() {
        return GoodsMedicalInfo.GOODS_MEDICAL_INFO.IS_RX;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field8() {
        return GoodsMedicalInfo.GOODS_MEDICAL_INFO.MEDICAL_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field9() {
        return GoodsMedicalInfo.GOODS_MEDICAL_INFO.INSURANCE_FLAG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field10() {
        return GoodsMedicalInfo.GOODS_MEDICAL_INFO.INSURANCE_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field11() {
        return GoodsMedicalInfo.GOODS_MEDICAL_INFO.INSURANCE_DATABASE_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field12() {
        return GoodsMedicalInfo.GOODS_MEDICAL_INFO.GOODS_BASIC_UNIT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field13() {
        return GoodsMedicalInfo.GOODS_MEDICAL_INFO.GOODS_PACKAGE_UNIT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Double> field14() {
        return GoodsMedicalInfo.GOODS_MEDICAL_INFO.GOODS_UNIT_CONVERT_FACTOR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Double> field15() {
        return GoodsMedicalInfo.GOODS_MEDICAL_INFO.GOODS_EQUIVALENT_QUANTITY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field16() {
        return GoodsMedicalInfo.GOODS_MEDICAL_INFO.GOODS_EQUIVALENT_UNIT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field17() {
        return GoodsMedicalInfo.GOODS_MEDICAL_INFO.GOODS_APPROVAL_NUMBER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field18() {
        return GoodsMedicalInfo.GOODS_MEDICAL_INFO.GOODS_PRODUCTION_ENTERPRISE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field19() {
        return GoodsMedicalInfo.GOODS_MEDICAL_INFO.GOODS_MEDICAL_INSTRUCTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field20() {
        return GoodsMedicalInfo.GOODS_MEDICAL_INFO.IS_DELETE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field21() {
        return GoodsMedicalInfo.GOODS_MEDICAL_INFO.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field22() {
        return GoodsMedicalInfo.GOODS_MEDICAL_INFO.UPDATE_TIME;
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
    public Integer component2() {
        return getGoodsId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getGoodsCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getGoodsCommonName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getGoodsAliasName();
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
    public Byte component7() {
        return getIsRx();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component8() {
        return getMedicalType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component9() {
        return getInsuranceFlag();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component10() {
        return getInsuranceCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component11() {
        return getInsuranceDatabaseName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component12() {
        return getGoodsBasicUnit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component13() {
        return getGoodsPackageUnit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double component14() {
        return getGoodsUnitConvertFactor();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double component15() {
        return getGoodsEquivalentQuantity();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component16() {
        return getGoodsEquivalentUnit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component17() {
        return getGoodsApprovalNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component18() {
        return getGoodsProductionEnterprise();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component19() {
        return getGoodsMedicalInstruction();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component20() {
        return getIsDelete();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component21() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component22() {
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
    public Integer value2() {
        return getGoodsId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getGoodsCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getGoodsCommonName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getGoodsAliasName();
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
    public Byte value7() {
        return getIsRx();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value8() {
        return getMedicalType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value9() {
        return getInsuranceFlag();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value10() {
        return getInsuranceCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value11() {
        return getInsuranceDatabaseName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value12() {
        return getGoodsBasicUnit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value13() {
        return getGoodsPackageUnit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double value14() {
        return getGoodsUnitConvertFactor();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double value15() {
        return getGoodsEquivalentQuantity();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value16() {
        return getGoodsEquivalentUnit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value17() {
        return getGoodsApprovalNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value18() {
        return getGoodsProductionEnterprise();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value19() {
        return getGoodsMedicalInstruction();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value20() {
        return getIsDelete();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value21() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value22() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsMedicalInfoRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsMedicalInfoRecord value2(Integer value) {
        setGoodsId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsMedicalInfoRecord value3(String value) {
        setGoodsCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsMedicalInfoRecord value4(String value) {
        setGoodsCommonName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsMedicalInfoRecord value5(String value) {
        setGoodsAliasName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsMedicalInfoRecord value6(String value) {
        setGoodsQualityRatio(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsMedicalInfoRecord value7(Byte value) {
        setIsRx(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsMedicalInfoRecord value8(Byte value) {
        setMedicalType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsMedicalInfoRecord value9(Byte value) {
        setInsuranceFlag(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsMedicalInfoRecord value10(String value) {
        setInsuranceCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsMedicalInfoRecord value11(String value) {
        setInsuranceDatabaseName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsMedicalInfoRecord value12(String value) {
        setGoodsBasicUnit(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsMedicalInfoRecord value13(String value) {
        setGoodsPackageUnit(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsMedicalInfoRecord value14(Double value) {
        setGoodsUnitConvertFactor(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsMedicalInfoRecord value15(Double value) {
        setGoodsEquivalentQuantity(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsMedicalInfoRecord value16(String value) {
        setGoodsEquivalentUnit(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsMedicalInfoRecord value17(String value) {
        setGoodsApprovalNumber(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsMedicalInfoRecord value18(String value) {
        setGoodsProductionEnterprise(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsMedicalInfoRecord value19(String value) {
        setGoodsMedicalInstruction(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsMedicalInfoRecord value20(Byte value) {
        setIsDelete(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsMedicalInfoRecord value21(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsMedicalInfoRecord value22(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsMedicalInfoRecord values(Integer value1, Integer value2, String value3, String value4, String value5, String value6, Byte value7, Byte value8, Byte value9, String value10, String value11, String value12, String value13, Double value14, Double value15, String value16, String value17, String value18, String value19, Byte value20, Timestamp value21, Timestamp value22) {
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
        value15(value15);
        value16(value16);
        value17(value17);
        value18(value18);
        value19(value19);
        value20(value20);
        value21(value21);
        value22(value22);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached GoodsMedicalInfoRecord
     */
    public GoodsMedicalInfoRecord() {
        super(GoodsMedicalInfo.GOODS_MEDICAL_INFO);
    }

    /**
     * Create a detached, initialised GoodsMedicalInfoRecord
     */
    public GoodsMedicalInfoRecord(Integer id, Integer goodsId, String goodsCode, String goodsCommonName, String goodsAliasName, String goodsQualityRatio, Byte isRx, Byte medicalType, Byte insuranceFlag, String insuranceCode, String insuranceDatabaseName, String goodsBasicUnit, String goodsPackageUnit, Double goodsUnitConvertFactor, Double goodsEquivalentQuantity, String goodsEquivalentUnit, String goodsApprovalNumber, String goodsProductionEnterprise, String goodsMedicalInstruction, Byte isDelete, Timestamp createTime, Timestamp updateTime) {
        super(GoodsMedicalInfo.GOODS_MEDICAL_INFO);

        set(0, id);
        set(1, goodsId);
        set(2, goodsCode);
        set(3, goodsCommonName);
        set(4, goodsAliasName);
        set(5, goodsQualityRatio);
        set(6, isRx);
        set(7, medicalType);
        set(8, insuranceFlag);
        set(9, insuranceCode);
        set(10, insuranceDatabaseName);
        set(11, goodsBasicUnit);
        set(12, goodsPackageUnit);
        set(13, goodsUnitConvertFactor);
        set(14, goodsEquivalentQuantity);
        set(15, goodsEquivalentUnit);
        set(16, goodsApprovalNumber);
        set(17, goodsProductionEnterprise);
        set(18, goodsMedicalInstruction);
        set(19, isDelete);
        set(20, createTime);
        set(21, updateTime);
    }
}
