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
public class Sort implements Serializable {

    private static final long serialVersionUID = -1244214921;

    private Integer   sortId;
    private String    sortName;
    private Integer   parentId;
    private Short     level;
    private Byte      hasChild;
    private Timestamp createTime;
    private Timestamp updateTime;
    private String    sortImg;
    private String    imgLink;
    private Short     first;
    private Byte      type;
    private String    sortDesc;

    public Sort() {}

    public Sort(Sort value) {
        this.sortId = value.sortId;
        this.sortName = value.sortName;
        this.parentId = value.parentId;
        this.level = value.level;
        this.hasChild = value.hasChild;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
        this.sortImg = value.sortImg;
        this.imgLink = value.imgLink;
        this.first = value.first;
        this.type = value.type;
        this.sortDesc = value.sortDesc;
    }

    public Sort(
        Integer   sortId,
        String    sortName,
        Integer   parentId,
        Short     level,
        Byte      hasChild,
        Timestamp createTime,
        Timestamp updateTime,
        String    sortImg,
        String    imgLink,
        Short     first,
        Byte      type,
        String    sortDesc
    ) {
        this.sortId = sortId;
        this.sortName = sortName;
        this.parentId = parentId;
        this.level = level;
        this.hasChild = hasChild;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.sortImg = sortImg;
        this.imgLink = imgLink;
        this.first = first;
        this.type = type;
        this.sortDesc = sortDesc;
    }

    public Integer getSortId() {
        return this.sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public String getSortName() {
        return this.sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public Integer getParentId() {
        return this.parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Short getLevel() {
        return this.level;
    }

    public void setLevel(Short level) {
        this.level = level;
    }

    public Byte getHasChild() {
        return this.hasChild;
    }

    public void setHasChild(Byte hasChild) {
        this.hasChild = hasChild;
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

    public String getSortImg() {
        return this.sortImg;
    }

    public void setSortImg(String sortImg) {
        this.sortImg = sortImg;
    }

    public String getImgLink() {
        return this.imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public Short getFirst() {
        return this.first;
    }

    public void setFirst(Short first) {
        this.first = first;
    }

    public Byte getType() {
        return this.type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getSortDesc() {
        return this.sortDesc;
    }

    public void setSortDesc(String sortDesc) {
        this.sortDesc = sortDesc;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Sort (");

        sb.append(sortId);
        sb.append(", ").append(sortName);
        sb.append(", ").append(parentId);
        sb.append(", ").append(level);
        sb.append(", ").append(hasChild);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);
        sb.append(", ").append(sortImg);
        sb.append(", ").append(imgLink);
        sb.append(", ").append(first);
        sb.append(", ").append(type);
        sb.append(", ").append(sortDesc);

        sb.append(")");
        return sb.toString();
    }
}
