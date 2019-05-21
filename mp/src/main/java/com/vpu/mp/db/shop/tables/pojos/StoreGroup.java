/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.pojos;


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
public class StoreGroup implements Serializable {

    private static final long serialVersionUID = 379854970;

    private Integer   storeId;
    private Integer   groupId;
    private Timestamp addTime;

    public StoreGroup() {}

    public StoreGroup(StoreGroup value) {
        this.storeId = value.storeId;
        this.groupId = value.groupId;
        this.addTime = value.addTime;
    }

    public StoreGroup(
        Integer   storeId,
        Integer   groupId,
        Timestamp addTime
    ) {
        this.storeId = storeId;
        this.groupId = groupId;
        this.addTime = addTime;
    }

    public Integer getStoreId() {
        return this.storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getGroupId() {
        return this.groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Timestamp getAddTime() {
        return this.addTime;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("StoreGroup (");

        sb.append(storeId);
        sb.append(", ").append(groupId);
        sb.append(", ").append(addTime);

        sb.append(")");
        return sb.toString();
    }
}
