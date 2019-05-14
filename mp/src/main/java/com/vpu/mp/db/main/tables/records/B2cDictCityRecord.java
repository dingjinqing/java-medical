/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.main.tables.records;


import com.vpu.mp.db.main.tables.B2cDictCity;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;


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
public class B2cDictCityRecord extends UpdatableRecordImpl<B2cDictCityRecord> implements Record7<Integer, String, Integer, String, String, String, Timestamp> {

    private static final long serialVersionUID = 1625011010;

    /**
     * Setter for <code>mini_main.b2c_dict_city.city_id</code>.
     */
    public void setCityId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_main.b2c_dict_city.city_id</code>.
     */
    public Integer getCityId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_main.b2c_dict_city.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_main.b2c_dict_city.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>mini_main.b2c_dict_city.province_id</code>.
     */
    public void setProvinceId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_main.b2c_dict_city.province_id</code>.
     */
    public Integer getProvinceId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>mini_main.b2c_dict_city.postcode</code>.
     */
    public void setPostcode(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_main.b2c_dict_city.postcode</code>.
     */
    public String getPostcode() {
        return (String) get(3);
    }

    /**
     * Setter for <code>mini_main.b2c_dict_city.short_name</code>.
     */
    public void setShortName(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_main.b2c_dict_city.short_name</code>.
     */
    public String getShortName() {
        return (String) get(4);
    }

    /**
     * Setter for <code>mini_main.b2c_dict_city.pinyin</code>.
     */
    public void setPinyin(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_main.b2c_dict_city.pinyin</code>.
     */
    public String getPinyin() {
        return (String) get(5);
    }

    /**
     * Setter for <code>mini_main.b2c_dict_city.modified</code>.
     */
    public void setModified(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_main.b2c_dict_city.modified</code>.
     */
    public Timestamp getModified() {
        return (Timestamp) get(6);
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
    // Record7 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, String, Integer, String, String, String, Timestamp> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, String, Integer, String, String, String, Timestamp> valuesRow() {
        return (Row7) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return B2cDictCity.B2C_DICT_CITY.CITY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return B2cDictCity.B2C_DICT_CITY.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return B2cDictCity.B2C_DICT_CITY.PROVINCE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return B2cDictCity.B2C_DICT_CITY.POSTCODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return B2cDictCity.B2C_DICT_CITY.SHORT_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return B2cDictCity.B2C_DICT_CITY.PINYIN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return B2cDictCity.B2C_DICT_CITY.MODIFIED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getCityId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component3() {
        return getProvinceId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getPostcode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getShortName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getPinyin();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component7() {
        return getModified();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getCityId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getProvinceId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getPostcode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getShortName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getPinyin();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value7() {
        return getModified();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cDictCityRecord value1(Integer value) {
        setCityId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cDictCityRecord value2(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cDictCityRecord value3(Integer value) {
        setProvinceId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cDictCityRecord value4(String value) {
        setPostcode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cDictCityRecord value5(String value) {
        setShortName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cDictCityRecord value6(String value) {
        setPinyin(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cDictCityRecord value7(Timestamp value) {
        setModified(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cDictCityRecord values(Integer value1, String value2, Integer value3, String value4, String value5, String value6, Timestamp value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached B2cDictCityRecord
     */
    public B2cDictCityRecord() {
        super(B2cDictCity.B2C_DICT_CITY);
    }

    /**
     * Create a detached, initialised B2cDictCityRecord
     */
    public B2cDictCityRecord(Integer cityId, String name, Integer provinceId, String postcode, String shortName, String pinyin, Timestamp modified) {
        super(B2cDictCity.B2C_DICT_CITY);

        set(0, cityId);
        set(1, name);
        set(2, provinceId);
        set(3, postcode);
        set(4, shortName);
        set(5, pinyin);
        set(6, modified);
    }
}
