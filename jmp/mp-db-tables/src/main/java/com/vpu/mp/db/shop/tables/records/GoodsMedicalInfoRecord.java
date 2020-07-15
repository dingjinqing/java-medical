/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.GoodsMedicalInfo;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


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
public class GoodsMedicalInfoRecord extends UpdatableRecordImpl<GoodsMedicalInfoRecord> {

    private static final long serialVersionUID = -1204333948;

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
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.insurance_flag</code>. 医保类型 1:甲 2:乙 3:丙 4:科研
     */
    public void setInsuranceFlag(Byte value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.insurance_flag</code>. 医保类型 1:甲 2:乙 3:丙 4:科研
     */
    public Byte getInsuranceFlag() {
        return (Byte) get(7);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.insurance_code</code>. 医保编码
     */
    public void setInsuranceCode(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.insurance_code</code>. 医保编码
     */
    public String getInsuranceCode() {
        return (String) get(8);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.insurance_database_name</code>. 医保库内名称
     */
    public void setInsuranceDatabaseName(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.insurance_database_name</code>. 医保库内名称
     */
    public String getInsuranceDatabaseName() {
        return (String) get(9);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.goods_basic_unit</code>. 商品基本单位
     */
    public void setGoodsBasicUnit(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.goods_basic_unit</code>. 商品基本单位
     */
    public String getGoodsBasicUnit() {
        return (String) get(10);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.goods_package_unit</code>. 商品包装单位
     */
    public void setGoodsPackageUnit(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.goods_package_unit</code>. 商品包装单位
     */
    public String getGoodsPackageUnit() {
        return (String) get(11);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.goods_unit_convert_factor</code>. 整包转换系数
     */
    public void setGoodsUnitConvertFactor(Double value) {
        set(12, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.goods_unit_convert_factor</code>. 整包转换系数
     */
    public Double getGoodsUnitConvertFactor() {
        return (Double) get(12);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.goods_equivalent_quantity</code>. 等效量
     */
    public void setGoodsEquivalentQuantity(Double value) {
        set(13, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.goods_equivalent_quantity</code>. 等效量
     */
    public Double getGoodsEquivalentQuantity() {
        return (Double) get(13);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.goods_equivalent_unit</code>. 等效单位
     */
    public void setGoodsEquivalentUnit(String value) {
        set(14, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.goods_equivalent_unit</code>. 等效单位
     */
    public String getGoodsEquivalentUnit() {
        return (String) get(14);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.goods_composition</code>. 药品成分
     */
    public void setGoodsComposition(String value) {
        set(15, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.goods_composition</code>. 药品成分
     */
    public String getGoodsComposition() {
        return (String) get(15);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.goods_characters</code>. 药品性状
     */
    public void setGoodsCharacters(String value) {
        set(16, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.goods_characters</code>. 药品性状
     */
    public String getGoodsCharacters() {
        return (String) get(16);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.goods_function</code>. 功能主治
     */
    public void setGoodsFunction(String value) {
        set(17, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.goods_function</code>. 功能主治
     */
    public String getGoodsFunction() {
        return (String) get(17);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.goods_use_method</code>. 用法用量
     */
    public void setGoodsUseMethod(String value) {
        set(18, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.goods_use_method</code>. 用法用量
     */
    public String getGoodsUseMethod() {
        return (String) get(18);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.goods_adverse_reaction</code>. 不良反应
     */
    public void setGoodsAdverseReaction(String value) {
        set(19, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.goods_adverse_reaction</code>. 不良反应
     */
    public String getGoodsAdverseReaction() {
        return (String) get(19);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.goods_taboos</code>. 药品禁忌
     */
    public void setGoodsTaboos(String value) {
        set(20, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.goods_taboos</code>. 药品禁忌
     */
    public String getGoodsTaboos() {
        return (String) get(20);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.goods_notice_event</code>. 注意事项
     */
    public void setGoodsNoticeEvent(String value) {
        set(21, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.goods_notice_event</code>. 注意事项
     */
    public String getGoodsNoticeEvent() {
        return (String) get(21);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.goods_interaction</code>. 相互作用
     */
    public void setGoodsInteraction(String value) {
        set(22, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.goods_interaction</code>. 相互作用
     */
    public String getGoodsInteraction() {
        return (String) get(22);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.goods_store_method</code>. 贮藏方法
     */
    public void setGoodsStoreMethod(String value) {
        set(23, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.goods_store_method</code>. 贮藏方法
     */
    public String getGoodsStoreMethod() {
        return (String) get(23);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.goods_package_method</code>. 药品包装
     */
    public void setGoodsPackageMethod(String value) {
        set(24, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.goods_package_method</code>. 药品包装
     */
    public String getGoodsPackageMethod() {
        return (String) get(24);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.goods_valid_time</code>. 有效期
     */
    public void setGoodsValidTime(String value) {
        set(25, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.goods_valid_time</code>. 有效期
     */
    public String getGoodsValidTime() {
        return (String) get(25);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.goods_approval_number</code>. 批准文号
     */
    public void setGoodsApprovalNumber(String value) {
        set(26, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.goods_approval_number</code>. 批准文号
     */
    public String getGoodsApprovalNumber() {
        return (String) get(26);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.goods_production_enterprise</code>. 生产企业
     */
    public void setGoodsProductionEnterprise(String value) {
        set(27, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.goods_production_enterprise</code>. 生产企业
     */
    public String getGoodsProductionEnterprise() {
        return (String) get(27);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.goods_limit_duty</code>. 药品最低聘任职务（限制对应医师开方）
     */
    public void setGoodsLimitDuty(Byte value) {
        set(28, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.goods_limit_duty</code>. 药品最低聘任职务（限制对应医师开方）
     */
    public Byte getGoodsLimitDuty() {
        return (Byte) get(28);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.goods_limit_antibacterial</code>. 抗菌限制
     */
    public void setGoodsLimitAntibacterial(Byte value) {
        set(29, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.goods_limit_antibacterial</code>. 抗菌限制
     */
    public Byte getGoodsLimitAntibacterial() {
        return (Byte) get(29);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.is_delete</code>.
     */
    public void setIsDelete(Byte value) {
        set(30, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.is_delete</code>.
     */
    public Byte getIsDelete() {
        return (Byte) get(30);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.create_time</code>.
     */
    public void setCreateTime(Timestamp value) {
        set(31, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.create_time</code>.
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(31);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_medical_info.update_time</code>. 最后修改时间
     */
    public void setUpdateTime(Timestamp value) {
        set(32, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_medical_info.update_time</code>. 最后修改时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(32);
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
    public GoodsMedicalInfoRecord(Integer id, Integer goodsId, String goodsCode, String goodsCommonName, String goodsAliasName, String goodsQualityRatio, Byte isRx, Byte insuranceFlag, String insuranceCode, String insuranceDatabaseName, String goodsBasicUnit, String goodsPackageUnit, Double goodsUnitConvertFactor, Double goodsEquivalentQuantity, String goodsEquivalentUnit, String goodsComposition, String goodsCharacters, String goodsFunction, String goodsUseMethod, String goodsAdverseReaction, String goodsTaboos, String goodsNoticeEvent, String goodsInteraction, String goodsStoreMethod, String goodsPackageMethod, String goodsValidTime, String goodsApprovalNumber, String goodsProductionEnterprise, Byte goodsLimitDuty, Byte goodsLimitAntibacterial, Byte isDelete, Timestamp createTime, Timestamp updateTime) {
        super(GoodsMedicalInfo.GOODS_MEDICAL_INFO);

        set(0, id);
        set(1, goodsId);
        set(2, goodsCode);
        set(3, goodsCommonName);
        set(4, goodsAliasName);
        set(5, goodsQualityRatio);
        set(6, isRx);
        set(7, insuranceFlag);
        set(8, insuranceCode);
        set(9, insuranceDatabaseName);
        set(10, goodsBasicUnit);
        set(11, goodsPackageUnit);
        set(12, goodsUnitConvertFactor);
        set(13, goodsEquivalentQuantity);
        set(14, goodsEquivalentUnit);
        set(15, goodsComposition);
        set(16, goodsCharacters);
        set(17, goodsFunction);
        set(18, goodsUseMethod);
        set(19, goodsAdverseReaction);
        set(20, goodsTaboos);
        set(21, goodsNoticeEvent);
        set(22, goodsInteraction);
        set(23, goodsStoreMethod);
        set(24, goodsPackageMethod);
        set(25, goodsValidTime);
        set(26, goodsApprovalNumber);
        set(27, goodsProductionEnterprise);
        set(28, goodsLimitDuty);
        set(29, goodsLimitAntibacterial);
        set(30, isDelete);
        set(31, createTime);
        set(32, updateTime);
    }
}
