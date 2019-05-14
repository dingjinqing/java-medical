/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.B2cShopRole;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
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
public class B2cShopRoleRecord extends UpdatableRecordImpl<B2cShopRoleRecord> implements Record6<Integer, Integer, String, String, Byte, Timestamp> {

    private static final long serialVersionUID = -2078045533;

    /**
     * Setter for <code>mini_shop_471752.b2c_shop_role.role_id</code>.
     */
    public void setRoleId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_shop_role.role_id</code>.
     */
    public Integer getRoleId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_shop_role.shop_id</code>. 店铺ID
     */
    public void setShopId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_shop_role.shop_id</code>. 店铺ID
     */
    public Integer getShopId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_shop_role.role_name</code>. 角色名称
     */
    public void setRoleName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_shop_role.role_name</code>. 角色名称
     */
    public String getRoleName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_shop_role.privilege_list</code>. 权限列表，json数组存储
     */
    public void setPrivilegeList(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_shop_role.privilege_list</code>. 权限列表，json数组存储
     */
    public String getPrivilegeList() {
        return (String) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_shop_role.official_proxy_role</code>. 是否为官方代理子帐号角色，是则不可删除
     */
    public void setOfficialProxyRole(Byte value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_shop_role.official_proxy_role</code>. 是否为官方代理子帐号角色，是则不可删除
     */
    public Byte getOfficialProxyRole() {
        return (Byte) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_shop_role.create_time</code>.
     */
    public void setCreateTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_shop_role.create_time</code>.
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(5);
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
    // Record6 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, Integer, String, String, Byte, Timestamp> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, Integer, String, String, Byte, Timestamp> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return B2cShopRole.B2C_SHOP_ROLE.ROLE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return B2cShopRole.B2C_SHOP_ROLE.SHOP_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return B2cShopRole.B2C_SHOP_ROLE.ROLE_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return B2cShopRole.B2C_SHOP_ROLE.PRIVILEGE_LIST;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field5() {
        return B2cShopRole.B2C_SHOP_ROLE.OFFICIAL_PROXY_ROLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return B2cShopRole.B2C_SHOP_ROLE.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getRoleId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component2() {
        return getShopId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getRoleName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getPrivilegeList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component5() {
        return getOfficialProxyRole();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component6() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getRoleId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getShopId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getRoleName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getPrivilegeList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value5() {
        return getOfficialProxyRole();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value6() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cShopRoleRecord value1(Integer value) {
        setRoleId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cShopRoleRecord value2(Integer value) {
        setShopId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cShopRoleRecord value3(String value) {
        setRoleName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cShopRoleRecord value4(String value) {
        setPrivilegeList(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cShopRoleRecord value5(Byte value) {
        setOfficialProxyRole(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cShopRoleRecord value6(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cShopRoleRecord values(Integer value1, Integer value2, String value3, String value4, Byte value5, Timestamp value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached B2cShopRoleRecord
     */
    public B2cShopRoleRecord() {
        super(B2cShopRole.B2C_SHOP_ROLE);
    }

    /**
     * Create a detached, initialised B2cShopRoleRecord
     */
    public B2cShopRoleRecord(Integer roleId, Integer shopId, String roleName, String privilegeList, Byte officialProxyRole, Timestamp createTime) {
        super(B2cShopRole.B2C_SHOP_ROLE);

        set(0, roleId);
        set(1, shopId);
        set(2, roleName);
        set(3, privilegeList);
        set(4, officialProxyRole);
        set(5, createTime);
    }
}
