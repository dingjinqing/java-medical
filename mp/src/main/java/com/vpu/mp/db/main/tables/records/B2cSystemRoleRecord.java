/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.main.tables.records;


import com.vpu.mp.db.main.tables.B2cSystemRole;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
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
public class B2cSystemRoleRecord extends UpdatableRecordImpl<B2cSystemRoleRecord> implements Record5<Integer, Integer, String, String, Timestamp> {

    private static final long serialVersionUID = -1142597965;

    /**
     * Setter for <code>mini_main.b2c_system_role.role_id</code>.
     */
    public void setRoleId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_main.b2c_system_role.role_id</code>.
     */
    public Integer getRoleId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_main.b2c_system_role.system_user_id</code>. 平台账号ID
     */
    public void setSystemUserId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_main.b2c_system_role.system_user_id</code>. 平台账号ID
     */
    public Integer getSystemUserId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_main.b2c_system_role.role_name</code>. 角色名称
     */
    public void setRoleName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_main.b2c_system_role.role_name</code>. 角色名称
     */
    public String getRoleName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>mini_main.b2c_system_role.privilege_list</code>. 权限列表，json数组存储
     */
    public void setPrivilegeList(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_main.b2c_system_role.privilege_list</code>. 权限列表，json数组存储
     */
    public String getPrivilegeList() {
        return (String) get(3);
    }

    /**
     * Setter for <code>mini_main.b2c_system_role.create_time</code>.
     */
    public void setCreateTime(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_main.b2c_system_role.create_time</code>.
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(4);
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
    // Record5 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Integer, Integer, String, String, Timestamp> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Integer, Integer, String, String, Timestamp> valuesRow() {
        return (Row5) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return B2cSystemRole.B2C_SYSTEM_ROLE.ROLE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return B2cSystemRole.B2C_SYSTEM_ROLE.SYSTEM_USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return B2cSystemRole.B2C_SYSTEM_ROLE.ROLE_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return B2cSystemRole.B2C_SYSTEM_ROLE.PRIVILEGE_LIST;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return B2cSystemRole.B2C_SYSTEM_ROLE.CREATE_TIME;
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
        return getSystemUserId();
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
    public Timestamp component5() {
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
        return getSystemUserId();
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
    public Timestamp value5() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cSystemRoleRecord value1(Integer value) {
        setRoleId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cSystemRoleRecord value2(Integer value) {
        setSystemUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cSystemRoleRecord value3(String value) {
        setRoleName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cSystemRoleRecord value4(String value) {
        setPrivilegeList(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cSystemRoleRecord value5(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cSystemRoleRecord values(Integer value1, Integer value2, String value3, String value4, Timestamp value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached B2cSystemRoleRecord
     */
    public B2cSystemRoleRecord() {
        super(B2cSystemRole.B2C_SYSTEM_ROLE);
    }

    /**
     * Create a detached, initialised B2cSystemRoleRecord
     */
    public B2cSystemRoleRecord(Integer roleId, Integer systemUserId, String roleName, String privilegeList, Timestamp createTime) {
        super(B2cSystemRole.B2C_SYSTEM_ROLE);

        set(0, roleId);
        set(1, systemUserId);
        set(2, roleName);
        set(3, privilegeList);
        set(4, createTime);
    }
}
