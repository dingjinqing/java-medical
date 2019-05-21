/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.main.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;

import org.jooq.types.UInteger;


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
public class SpecVals implements Serializable {

    private static final long serialVersionUID = 1436307774;

    private UInteger specvalid;
    private Integer  specId;
    private String   specvalname;
    private Byte     delFlag;
    private Integer  shopId;

    public SpecVals() {}

    public SpecVals(SpecVals value) {
        this.specvalid = value.specvalid;
        this.specId = value.specId;
        this.specvalname = value.specvalname;
        this.delFlag = value.delFlag;
        this.shopId = value.shopId;
    }

    public SpecVals(
        UInteger specvalid,
        Integer  specId,
        String   specvalname,
        Byte     delFlag,
        Integer  shopId
    ) {
        this.specvalid = specvalid;
        this.specId = specId;
        this.specvalname = specvalname;
        this.delFlag = delFlag;
        this.shopId = shopId;
    }

    public UInteger getSpecvalid() {
        return this.specvalid;
    }

    public void setSpecvalid(UInteger specvalid) {
        this.specvalid = specvalid;
    }

    public Integer getSpecId() {
        return this.specId;
    }

    public void setSpecId(Integer specId) {
        this.specId = specId;
    }

    public String getSpecvalname() {
        return this.specvalname;
    }

    public void setSpecvalname(String specvalname) {
        this.specvalname = specvalname;
    }

    public Byte getDelFlag() {
        return this.delFlag;
    }

    public void setDelFlag(Byte delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getShopId() {
        return this.shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("SpecVals (");

        sb.append(specvalid);
        sb.append(", ").append(specId);
        sb.append(", ").append(specvalname);
        sb.append(", ").append(delFlag);
        sb.append(", ").append(shopId);

        sb.append(")");
        return sb.toString();
    }
}
