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

    private static final long serialVersionUID = -642863719;

    private Integer   userId;
    private Integer   tagId;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Byte      isDelete;

    public UserTag() {}

    public UserTag(UserTag value) {
        this.userId = value.userId;
        this.tagId = value.tagId;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
        this.isDelete = value.isDelete;
    }

    public UserTag(
        Integer   userId,
        Integer   tagId,
        Timestamp createTime,
        Timestamp updateTime,
        Byte      isDelete
    ) {
        this.userId = userId;
        this.tagId = tagId;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.isDelete = isDelete;
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

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Byte getIsDelete() {
        return this.isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("UserTag (");

        sb.append(userId);
        sb.append(", ").append(tagId);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);
        sb.append(", ").append(isDelete);

        sb.append(")");
        return sb.toString();
    }
}
