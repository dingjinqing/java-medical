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
public class ChargeRenew implements Serializable {

    private static final long serialVersionUID = -2035154485;

    private Integer   id;
    private Integer   shopId;
    private Integer   applyId;
    private String    applyName;
    private Integer   sysId;
    private String    shopName;
    private Timestamp created;
    private String    applyMod;
    private Byte      applyType;
    private Byte      contact;
    private String    desc;

    public ChargeRenew() {}

    public ChargeRenew(ChargeRenew value) {
        this.id = value.id;
        this.shopId = value.shopId;
        this.applyId = value.applyId;
        this.applyName = value.applyName;
        this.sysId = value.sysId;
        this.shopName = value.shopName;
        this.created = value.created;
        this.applyMod = value.applyMod;
        this.applyType = value.applyType;
        this.contact = value.contact;
        this.desc = value.desc;
    }

    public ChargeRenew(
        Integer   id,
        Integer   shopId,
        Integer   applyId,
        String    applyName,
        Integer   sysId,
        String    shopName,
        Timestamp created,
        String    applyMod,
        Byte      applyType,
        Byte      contact,
        String    desc
    ) {
        this.id = id;
        this.shopId = shopId;
        this.applyId = applyId;
        this.applyName = applyName;
        this.sysId = sysId;
        this.shopName = shopName;
        this.created = created;
        this.applyMod = applyMod;
        this.applyType = applyType;
        this.contact = contact;
        this.desc = desc;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShopId() {
        return this.shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getApplyId() {
        return this.applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    public String getApplyName() {
        return this.applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public Integer getSysId() {
        return this.sysId;
    }

    public void setSysId(Integer sysId) {
        this.sysId = sysId;
    }

    public String getShopName() {
        return this.shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Timestamp getCreated() {
        return this.created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public String getApplyMod() {
        return this.applyMod;
    }

    public void setApplyMod(String applyMod) {
        this.applyMod = applyMod;
    }

    public Byte getApplyType() {
        return this.applyType;
    }

    public void setApplyType(Byte applyType) {
        this.applyType = applyType;
    }

    public Byte getContact() {
        return this.contact;
    }

    public void setContact(Byte contact) {
        this.contact = contact;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ChargeRenew (");

        sb.append(id);
        sb.append(", ").append(shopId);
        sb.append(", ").append(applyId);
        sb.append(", ").append(applyName);
        sb.append(", ").append(sysId);
        sb.append(", ").append(shopName);
        sb.append(", ").append(created);
        sb.append(", ").append(applyMod);
        sb.append(", ").append(applyType);
        sb.append(", ").append(contact);
        sb.append(", ").append(desc);

        sb.append(")");
        return sb.toString();
    }
}
