/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.main.tables.records;


import com.vpu.mp.db.main.tables.ArticleCategory;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
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
public class ArticleCategoryRecord extends UpdatableRecordImpl<ArticleCategoryRecord> implements Record3<Integer, String, Byte> {

    private static final long serialVersionUID = 232879596;

    /**
     * Setter for <code>mini_main.b2c_article_category.category_id</code>.
     */
    public void setCategoryId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_main.b2c_article_category.category_id</code>.
     */
    public Integer getCategoryId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_main.b2c_article_category.category_name</code>.
     */
    public void setCategoryName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_main.b2c_article_category.category_name</code>.
     */
    public String getCategoryName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>mini_main.b2c_article_category.use_footer_nav</code>. 是否用于底部导航
     */
    public void setUseFooterNav(Byte value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_main.b2c_article_category.use_footer_nav</code>. 是否用于底部导航
     */
    public Byte getUseFooterNav() {
        return (Byte) get(2);
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
    // Record3 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Integer, String, Byte> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Integer, String, Byte> valuesRow() {
        return (Row3) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return ArticleCategory.ARTICLE_CATEGORY.CATEGORY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return ArticleCategory.ARTICLE_CATEGORY.CATEGORY_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field3() {
        return ArticleCategory.ARTICLE_CATEGORY.USE_FOOTER_NAV;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getCategoryId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getCategoryName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component3() {
        return getUseFooterNav();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getCategoryId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getCategoryName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value3() {
        return getUseFooterNav();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArticleCategoryRecord value1(Integer value) {
        setCategoryId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArticleCategoryRecord value2(String value) {
        setCategoryName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArticleCategoryRecord value3(Byte value) {
        setUseFooterNav(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArticleCategoryRecord values(Integer value1, String value2, Byte value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ArticleCategoryRecord
     */
    public ArticleCategoryRecord() {
        super(ArticleCategory.ARTICLE_CATEGORY);
    }

    /**
     * Create a detached, initialised ArticleCategoryRecord
     */
    public ArticleCategoryRecord(Integer categoryId, String categoryName, Byte useFooterNav) {
        super(ArticleCategory.ARTICLE_CATEGORY);

        set(0, categoryId);
        set(1, categoryName);
        set(2, useFooterNav);
    }
}
