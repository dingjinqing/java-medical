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
public class Attribute implements Serializable {

    private static final long serialVersionUID = 1945000909;

    private Integer   attrId;
    private String    name;
    private String    attributeInfo;
    private Timestamp createTime;
    private Byte      isDel;

    public Attribute() {}

    public Attribute(Attribute value) {
        this.attrId = value.attrId;
        this.name = value.name;
        this.attributeInfo = value.attributeInfo;
        this.createTime = value.createTime;
        this.isDel = value.isDel;
    }

    public Attribute(
        Integer   attrId,
        String    name,
        String    attributeInfo,
        Timestamp createTime,
        Byte      isDel
    ) {
        this.attrId = attrId;
        this.name = name;
        this.attributeInfo = attributeInfo;
        this.createTime = createTime;
        this.isDel = isDel;
    }

    public Integer getAttrId() {
        return this.attrId;
    }

    public void setAttrId(Integer attrId) {
        this.attrId = attrId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttributeInfo() {
        return this.attributeInfo;
    }

    public void setAttributeInfo(String attributeInfo) {
        this.attributeInfo = attributeInfo;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Byte getIsDel() {
        return this.isDel;
    }

    public void setIsDel(Byte isDel) {
        this.isDel = isDel;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Attribute (");

        sb.append(attrId);
        sb.append(", ").append(name);
        sb.append(", ").append(attributeInfo);
        sb.append(", ").append(createTime);
        sb.append(", ").append(isDel);

        sb.append(")");
        return sb.toString();
    }
}
