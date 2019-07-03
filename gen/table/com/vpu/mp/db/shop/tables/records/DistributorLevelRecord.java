/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.DistributorLevel;

import java.math.BigDecimal;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
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
public class DistributorLevelRecord extends UpdatableRecordImpl<DistributorLevelRecord> implements Record9<Integer, Byte, String, Byte, Integer, BigDecimal, BigDecimal, String, Byte> {

    private static final long serialVersionUID = 434616095;

    /**
     * Setter for <code>mini_shop_471752.b2c_distributor_level.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_distributor_level.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_distributor_level.level_id</code>. 等级
     */
    public void setLevelId(Byte value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_distributor_level.level_id</code>. 等级
     */
    public Byte getLevelId() {
        return (Byte) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_distributor_level.level_name</code>. 等级名称
     */
    public void setLevelName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_distributor_level.level_name</code>. 等级名称
     */
    public String getLevelName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_distributor_level.level_up_route</code>. 升级类型：0自动，1手动
     */
    public void setLevelUpRoute(Byte value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_distributor_level.level_up_route</code>. 升级类型：0自动，1手动
     */
    public Byte getLevelUpRoute() {
        return (Byte) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_distributor_level.invite_number</code>. 邀请人数量（uo_route=0有效）
     */
    public void setInviteNumber(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_distributor_level.invite_number</code>. 邀请人数量（uo_route=0有效）
     */
    public Integer getInviteNumber() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_distributor_level.total_distribution_money</code>. 推广金额（uo_route=0有效）
     */
    public void setTotalDistributionMoney(BigDecimal value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_distributor_level.total_distribution_money</code>. 推广金额（uo_route=0有效）
     */
    public BigDecimal getTotalDistributionMoney() {
        return (BigDecimal) get(5);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_distributor_level.total_buy_money</code>. 推广和消费总额（uo_route=0有效）
     */
    public void setTotalBuyMoney(BigDecimal value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_distributor_level.total_buy_money</code>. 推广和消费总额（uo_route=0有效）
     */
    public BigDecimal getTotalBuyMoney() {
        return (BigDecimal) get(6);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_distributor_level.level_user_ids</code>. 等级用户ID（uo_route=1有效）
     */
    public void setLevelUserIds(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_distributor_level.level_user_ids</code>. 等级用户ID（uo_route=1有效）
     */
    public String getLevelUserIds() {
        return (String) get(7);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_distributor_level.level_status</code>. 状态:0停用，1启用
     */
    public void setLevelStatus(Byte value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_distributor_level.level_status</code>. 状态:0停用，1启用
     */
    public Byte getLevelStatus() {
        return (Byte) get(8);
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
    // Record9 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<Integer, Byte, String, Byte, Integer, BigDecimal, BigDecimal, String, Byte> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<Integer, Byte, String, Byte, Integer, BigDecimal, BigDecimal, String, Byte> valuesRow() {
        return (Row9) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return DistributorLevel.DISTRIBUTOR_LEVEL.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field2() {
        return DistributorLevel.DISTRIBUTOR_LEVEL.LEVEL_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return DistributorLevel.DISTRIBUTOR_LEVEL.LEVEL_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field4() {
        return DistributorLevel.DISTRIBUTOR_LEVEL.LEVEL_UP_ROUTE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return DistributorLevel.DISTRIBUTOR_LEVEL.INVITE_NUMBER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field6() {
        return DistributorLevel.DISTRIBUTOR_LEVEL.TOTAL_DISTRIBUTION_MONEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field7() {
        return DistributorLevel.DISTRIBUTOR_LEVEL.TOTAL_BUY_MONEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return DistributorLevel.DISTRIBUTOR_LEVEL.LEVEL_USER_IDS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field9() {
        return DistributorLevel.DISTRIBUTOR_LEVEL.LEVEL_STATUS;
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
    public Byte component2() {
        return getLevelId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getLevelName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component4() {
        return getLevelUpRoute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component5() {
        return getInviteNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component6() {
        return getTotalDistributionMoney();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component7() {
        return getTotalBuyMoney();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component8() {
        return getLevelUserIds();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component9() {
        return getLevelStatus();
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
    public Byte value2() {
        return getLevelId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getLevelName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value4() {
        return getLevelUpRoute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getInviteNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value6() {
        return getTotalDistributionMoney();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value7() {
        return getTotalBuyMoney();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getLevelUserIds();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value9() {
        return getLevelStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DistributorLevelRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DistributorLevelRecord value2(Byte value) {
        setLevelId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DistributorLevelRecord value3(String value) {
        setLevelName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DistributorLevelRecord value4(Byte value) {
        setLevelUpRoute(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DistributorLevelRecord value5(Integer value) {
        setInviteNumber(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DistributorLevelRecord value6(BigDecimal value) {
        setTotalDistributionMoney(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DistributorLevelRecord value7(BigDecimal value) {
        setTotalBuyMoney(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DistributorLevelRecord value8(String value) {
        setLevelUserIds(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DistributorLevelRecord value9(Byte value) {
        setLevelStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DistributorLevelRecord values(Integer value1, Byte value2, String value3, Byte value4, Integer value5, BigDecimal value6, BigDecimal value7, String value8, Byte value9) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached DistributorLevelRecord
     */
    public DistributorLevelRecord() {
        super(DistributorLevel.DISTRIBUTOR_LEVEL);
    }

    /**
     * Create a detached, initialised DistributorLevelRecord
     */
    public DistributorLevelRecord(Integer id, Byte levelId, String levelName, Byte levelUpRoute, Integer inviteNumber, BigDecimal totalDistributionMoney, BigDecimal totalBuyMoney, String levelUserIds, Byte levelStatus) {
        super(DistributorLevel.DISTRIBUTOR_LEVEL);

        set(0, id);
        set(1, levelId);
        set(2, levelName);
        set(3, levelUpRoute);
        set(4, inviteNumber);
        set(5, totalDistributionMoney);
        set(6, totalBuyMoney);
        set(7, levelUserIds);
        set(8, levelStatus);
    }
}
