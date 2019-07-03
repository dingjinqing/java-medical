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
public class GoodsImportDetail implements Serializable {

    private static final long serialVersionUID = -2038327012;

    private Integer   id;
    private Integer   batchId;
    private String    goodsSn;
    private String    prdSn;
    private String    goodsName;
    private String    prdDesc;
    private String    errorMsg;
    private Timestamp addTime;
    private Byte      isSuccess;

    public GoodsImportDetail() {}

    public GoodsImportDetail(GoodsImportDetail value) {
        this.id = value.id;
        this.batchId = value.batchId;
        this.goodsSn = value.goodsSn;
        this.prdSn = value.prdSn;
        this.goodsName = value.goodsName;
        this.prdDesc = value.prdDesc;
        this.errorMsg = value.errorMsg;
        this.addTime = value.addTime;
        this.isSuccess = value.isSuccess;
    }

    public GoodsImportDetail(
        Integer   id,
        Integer   batchId,
        String    goodsSn,
        String    prdSn,
        String    goodsName,
        String    prdDesc,
        String    errorMsg,
        Timestamp addTime,
        Byte      isSuccess
    ) {
        this.id = id;
        this.batchId = batchId;
        this.goodsSn = goodsSn;
        this.prdSn = prdSn;
        this.goodsName = goodsName;
        this.prdDesc = prdDesc;
        this.errorMsg = errorMsg;
        this.addTime = addTime;
        this.isSuccess = isSuccess;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBatchId() {
        return this.batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public String getGoodsSn() {
        return this.goodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }

    public String getPrdSn() {
        return this.prdSn;
    }

    public void setPrdSn(String prdSn) {
        this.prdSn = prdSn;
    }

    public String getGoodsName() {
        return this.goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getPrdDesc() {
        return this.prdDesc;
    }

    public void setPrdDesc(String prdDesc) {
        this.prdDesc = prdDesc;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Timestamp getAddTime() {
        return this.addTime;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }

    public Byte getIsSuccess() {
        return this.isSuccess;
    }

    public void setIsSuccess(Byte isSuccess) {
        this.isSuccess = isSuccess;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("GoodsImportDetail (");

        sb.append(id);
        sb.append(", ").append(batchId);
        sb.append(", ").append(goodsSn);
        sb.append(", ").append(prdSn);
        sb.append(", ").append(goodsName);
        sb.append(", ").append(prdDesc);
        sb.append(", ").append(errorMsg);
        sb.append(", ").append(addTime);
        sb.append(", ").append(isSuccess);

        sb.append(")");
        return sb.toString();
    }
}
