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
public class UserTag implements Serializable {

    private static final long serialVersionUID = 828432499;

    private Integer   userId;
    private Integer   tagId;
    private Timestamp addTime;

    public UserTag() {}

    public UserTag(UserTag value) {
        this.userId = value.userId;
        this.tagId = value.tagId;
        this.addTime = value.addTime;
    }

    public UserTag(
        Integer   userId,
        Integer   tagId,
        Timestamp addTime
    ) {
        this.userId = userId;
        this.tagId = tagId;
        this.addTime = addTime;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTagId() {
        return this.tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public Timestamp getAddTime() {
        return this.addTime;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("UserTag (");

        sb.append(userId);
        sb.append(", ").append(tagId);
        sb.append(", ").append(addTime);

        sb.append(")");
        return sb.toString();
    }
}
