/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.B2cSearchHistory;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record7;
import org.jooq.Row7;
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
public class B2cSearchHistoryRecord extends TableRecordImpl<B2cSearchHistoryRecord> implements Record7<Integer, String, Integer, Byte, Timestamp, Timestamp, Byte> {

    private static final long serialVersionUID = 1348242873;

    /**
     * Setter for <code>mini_shop_471752.b2c_search_history.user_id</code>.
     */
    public void setUserId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_search_history.user_id</code>.
     */
    public Integer getUserId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_search_history.hot_words</code>. 热词
     */
    public void setHotWords(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_search_history.hot_words</code>. 热词
     */
    public String getHotWords() {
        return (String) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_search_history.search_count</code>. 搜索次数
     */
    public void setSearchCount(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_search_history.search_count</code>. 搜索次数
     */
    public Integer getSearchCount() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_search_history.is_hot_words</code>. 是否是热词搜索
     */
    public void setIsHotWords(Byte value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_search_history.is_hot_words</code>. 是否是热词搜索
     */
    public Byte getIsHotWords() {
        return (Byte) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_search_history.add_time</code>.
     */
    public void setAddTime(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_search_history.add_time</code>.
     */
    public Timestamp getAddTime() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_search_history.update_time</code>.
     */
    public void setUpdateTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_search_history.update_time</code>.
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(5);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_search_history.del_flag</code>.
     */
    public void setDelFlag(Byte value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_search_history.del_flag</code>.
     */
    public Byte getDelFlag() {
        return (Byte) get(6);
    }

    // -------------------------------------------------------------------------
    // Record7 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, String, Integer, Byte, Timestamp, Timestamp, Byte> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, String, Integer, Byte, Timestamp, Timestamp, Byte> valuesRow() {
        return (Row7) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return B2cSearchHistory.B2C_SEARCH_HISTORY.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return B2cSearchHistory.B2C_SEARCH_HISTORY.HOT_WORDS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return B2cSearchHistory.B2C_SEARCH_HISTORY.SEARCH_COUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field4() {
        return B2cSearchHistory.B2C_SEARCH_HISTORY.IS_HOT_WORDS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return B2cSearchHistory.B2C_SEARCH_HISTORY.ADD_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return B2cSearchHistory.B2C_SEARCH_HISTORY.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field7() {
        return B2cSearchHistory.B2C_SEARCH_HISTORY.DEL_FLAG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getHotWords();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component3() {
        return getSearchCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component4() {
        return getIsHotWords();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component5() {
        return getAddTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component6() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component7() {
        return getDelFlag();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getHotWords();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getSearchCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value4() {
        return getIsHotWords();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value5() {
        return getAddTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value6() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value7() {
        return getDelFlag();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cSearchHistoryRecord value1(Integer value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cSearchHistoryRecord value2(String value) {
        setHotWords(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cSearchHistoryRecord value3(Integer value) {
        setSearchCount(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cSearchHistoryRecord value4(Byte value) {
        setIsHotWords(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cSearchHistoryRecord value5(Timestamp value) {
        setAddTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cSearchHistoryRecord value6(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cSearchHistoryRecord value7(Byte value) {
        setDelFlag(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cSearchHistoryRecord values(Integer value1, String value2, Integer value3, Byte value4, Timestamp value5, Timestamp value6, Byte value7) {
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
     * Create a detached B2cSearchHistoryRecord
     */
    public B2cSearchHistoryRecord() {
        super(B2cSearchHistory.B2C_SEARCH_HISTORY);
    }

    /**
     * Create a detached, initialised B2cSearchHistoryRecord
     */
    public B2cSearchHistoryRecord(Integer userId, String hotWords, Integer searchCount, Byte isHotWords, Timestamp addTime, Timestamp updateTime, Byte delFlag) {
        super(B2cSearchHistory.B2C_SEARCH_HISTORY);

        set(0, userId);
        set(1, hotWords);
        set(2, searchCount);
        set(3, isHotWords);
        set(4, addTime);
        set(5, updateTime);
        set(6, delFlag);
    }
}
