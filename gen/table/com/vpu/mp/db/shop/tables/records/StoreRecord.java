/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.Store;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Record1;
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
public class StoreRecord extends UpdatableRecordImpl<StoreRecord> {

    private static final long serialVersionUID = 504486665;

    /**
     * Setter for <code>mini_shop_471752.b2c_store.store_id</code>.
     */
    public void setStoreId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store.store_id</code>.
     */
    public Integer getStoreId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store.store_name</code>. 门店名称
     */
    public void setStoreName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store.store_name</code>. 门店名称
     */
    public String getStoreName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store.manager</code>. 负责人
     */
    public void setManager(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store.manager</code>. 负责人
     */
    public String getManager() {
        return (String) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store.mobile</code>. 联系电话
     */
    public void setMobile(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store.mobile</code>. 联系电话
     */
    public String getMobile() {
        return (String) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store.store_imgs</code>. 图片
     */
    public void setStoreImgs(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store.store_imgs</code>. 图片
     */
    public String getStoreImgs() {
        return (String) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store.business_state</code>. 营业状态1:营业，0:关店
     */
    public void setBusinessState(Byte value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store.business_state</code>. 营业状态1:营业，0:关店
     */
    public Byte getBusinessState() {
        return (Byte) get(5);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store.business_type</code>. 营业时间1：每天，0：工作日
     */
    public void setBusinessType(Byte value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store.business_type</code>. 营业时间1：每天，0：工作日
     */
    public Byte getBusinessType() {
        return (Byte) get(6);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store.opening_time</code>. 开门时间
     */
    public void setOpeningTime(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store.opening_time</code>. 开门时间
     */
    public String getOpeningTime() {
        return (String) get(7);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store.close_time</code>. 打烊时间
     */
    public void setCloseTime(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store.close_time</code>. 打烊时间
     */
    public String getCloseTime() {
        return (String) get(8);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store.province_code</code>. 省
     */
    public void setProvinceCode(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store.province_code</code>. 省
     */
    public String getProvinceCode() {
        return (String) get(9);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store.city_code</code>. 市
     */
    public void setCityCode(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store.city_code</code>. 市
     */
    public String getCityCode() {
        return (String) get(10);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store.district_code</code>. 区
     */
    public void setDistrictCode(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store.district_code</code>. 区
     */
    public String getDistrictCode() {
        return (String) get(11);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store.latitude</code>. 纬度
     */
    public void setLatitude(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store.latitude</code>. 纬度
     */
    public String getLatitude() {
        return (String) get(12);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store.longitude</code>. 经度
     */
    public void setLongitude(String value) {
        set(13, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store.longitude</code>. 经度
     */
    public String getLongitude() {
        return (String) get(13);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store.address</code>. 详细地址
     */
    public void setAddress(String value) {
        set(14, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store.address</code>. 详细地址
     */
    public String getAddress() {
        return (String) get(14);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store.group</code>. 分组
     */
    public void setGroup(Integer value) {
        set(15, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store.group</code>. 分组
     */
    public Integer getGroup() {
        return (Integer) get(15);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store.service</code>. 服务
     */
    public void setService(String value) {
        set(16, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store.service</code>. 服务
     */
    public String getService() {
        return (String) get(16);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store.content</code>. 介绍
     */
    public void setContent(String value) {
        set(17, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store.content</code>. 介绍
     */
    public String getContent() {
        return (String) get(17);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store.pos_shop_id</code>. pos店铺ID
     */
    public void setPosShopId(Integer value) {
        set(18, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store.pos_shop_id</code>. pos店铺ID
     */
    public Integer getPosShopId() {
        return (Integer) get(18);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store.add_time</code>. 添加时间
     */
    public void setAddTime(Timestamp value) {
        set(19, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store.add_time</code>. 添加时间
     */
    public Timestamp getAddTime() {
        return (Timestamp) get(19);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store.up_time</code>. 更新时间
     */
    public void setUpTime(Timestamp value) {
        set(20, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store.up_time</code>. 更新时间
     */
    public Timestamp getUpTime() {
        return (Timestamp) get(20);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store.auto_pick</code>. 设定自提
     */
    public void setAutoPick(Short value) {
        set(21, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store.auto_pick</code>. 设定自提
     */
    public Short getAutoPick() {
        return (Short) get(21);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store.is_delete</code>. 1为删除状态
     */
    public void setIsDelete(Byte value) {
        set(22, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store.is_delete</code>. 1为删除状态
     */
    public Byte getIsDelete() {
        return (Byte) get(22);
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
     * Create a detached StoreRecord
     */
    public StoreRecord() {
        super(Store.STORE);
    }

    /**
     * Create a detached, initialised StoreRecord
     */
    public StoreRecord(Integer storeId, String storeName, String manager, String mobile, String storeImgs, Byte businessState, Byte businessType, String openingTime, String closeTime, String provinceCode, String cityCode, String districtCode, String latitude, String longitude, String address, Integer group, String service, String content, Integer posShopId, Timestamp addTime, Timestamp upTime, Short autoPick, Byte isDelete) {
        super(Store.STORE);

        set(0, storeId);
        set(1, storeName);
        set(2, manager);
        set(3, mobile);
        set(4, storeImgs);
        set(5, businessState);
        set(6, businessType);
        set(7, openingTime);
        set(8, closeTime);
        set(9, provinceCode);
        set(10, cityCode);
        set(11, districtCode);
        set(12, latitude);
        set(13, longitude);
        set(14, address);
        set(15, group);
        set(16, service);
        set(17, content);
        set(18, posShopId);
        set(19, addTime);
        set(20, upTime);
        set(21, autoPick);
        set(22, isDelete);
    }
}
