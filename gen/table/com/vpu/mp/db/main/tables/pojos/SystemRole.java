/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.main.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


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
public class SystemRole implements Serializable {

    private static final long serialVersionUID = 1323178950;

    private Integer   roleId;
    private Integer   systemUserId;
    private String    roleName;
    private String    privilegeList;
    private Timestamp createTime;

    public SystemRole() {}

    public SystemRole(SystemRole value) {
        this.roleId = value.roleId;
        this.systemUserId = value.systemUserId;
        this.roleName = value.roleName;
        this.privilegeList = value.privilegeList;
        this.createTime = value.createTime;
    }

    public SystemRole(
        Integer   roleId,
        Integer   systemUserId,
        String    roleName,
        String    privilegeList,
        Timestamp createTime
    ) {
        this.roleId = roleId;
        this.systemUserId = systemUserId;
        this.roleName = roleName;
        this.privilegeList = privilegeList;
        this.createTime = createTime;
    }

    public Integer getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getSystemUserId() {
        return this.systemUserId;
    }

    public void setSystemUserId(Integer systemUserId) {
        this.systemUserId = systemUserId;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getPrivilegeList() {
        return this.privilegeList;
    }

    public void setPrivilegeList(String privilegeList) {
        this.privilegeList = privilegeList;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("SystemRole (");

        sb.append(roleId);
        sb.append(", ").append(systemUserId);
        sb.append(", ").append(roleName);
        sb.append(", ").append(privilegeList);
        sb.append(", ").append(createTime);

        sb.append(")");
        return sb.toString();
    }
}
