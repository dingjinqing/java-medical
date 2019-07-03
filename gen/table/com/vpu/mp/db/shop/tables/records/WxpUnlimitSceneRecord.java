/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.WxpUnlimitScene;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
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
public class WxpUnlimitSceneRecord extends UpdatableRecordImpl<WxpUnlimitSceneRecord> implements Record2<Integer, String> {

    private static final long serialVersionUID = -1874534045;

    /**
     * Setter for <code>mini_shop_471752.b2c_wxp_unlimit_scene.scene_id</code>.
     */
    public void setSceneId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_wxp_unlimit_scene.scene_id</code>.
     */
    public Integer getSceneId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_wxp_unlimit_scene.scene_value</code>.
     */
    public void setSceneValue(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_wxp_unlimit_scene.scene_value</code>.
     */
    public String getSceneValue() {
        return (String) get(1);
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
    // Record2 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<Integer, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<Integer, String> valuesRow() {
        return (Row2) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return WxpUnlimitScene.WXP_UNLIMIT_SCENE.SCENE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return WxpUnlimitScene.WXP_UNLIMIT_SCENE.SCENE_VALUE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getSceneId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getSceneValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getSceneId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getSceneValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WxpUnlimitSceneRecord value1(Integer value) {
        setSceneId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WxpUnlimitSceneRecord value2(String value) {
        setSceneValue(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WxpUnlimitSceneRecord values(Integer value1, String value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached WxpUnlimitSceneRecord
     */
    public WxpUnlimitSceneRecord() {
        super(WxpUnlimitScene.WXP_UNLIMIT_SCENE);
    }

    /**
     * Create a detached, initialised WxpUnlimitSceneRecord
     */
    public WxpUnlimitSceneRecord(Integer sceneId, String sceneValue) {
        super(WxpUnlimitScene.WXP_UNLIMIT_SCENE);

        set(0, sceneId);
        set(1, sceneValue);
    }
}
