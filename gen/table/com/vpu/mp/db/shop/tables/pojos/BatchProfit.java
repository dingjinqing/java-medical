/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.pojos;


import java.io.Serializable;
import java.math.BigDecimal;
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
public class BatchProfit implements Serializable {

    private static final long serialVersionUID = -429618187;

    private Integer    id;
    private Integer    brandId;
    private Integer    sortId;
    private Integer    actId;
    private BigDecimal profitPer;
    private String     fileName;
    private Timestamp  createTime;
    private Timestamp  updateTime;

    public BatchProfit() {}

    public BatchProfit(BatchProfit value) {
        this.id = value.id;
        this.brandId = value.brandId;
        this.sortId = value.sortId;
        this.actId = value.actId;
        this.profitPer = value.profitPer;
        this.fileName = value.fileName;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
    }

    public BatchProfit(
        Integer    id,
        Integer    brandId,
        Integer    sortId,
        Integer    actId,
        BigDecimal profitPer,
        String     fileName,
        Timestamp  createTime,
        Timestamp  updateTime
    ) {
        this.id = id;
        this.brandId = brandId;
        this.sortId = sortId;
        this.actId = actId;
        this.profitPer = profitPer;
        this.fileName = fileName;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBrandId() {
        return this.brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getSortId() {
        return this.sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public Integer getActId() {
        return this.actId;
    }

    public void setActId(Integer actId) {
        this.actId = actId;
    }

    public BigDecimal getProfitPer() {
        return this.profitPer;
    }

    public void setProfitPer(BigDecimal profitPer) {
        this.profitPer = profitPer;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("BatchProfit (");

        sb.append(id);
        sb.append(", ").append(brandId);
        sb.append(", ").append(sortId);
        sb.append(", ").append(actId);
        sb.append(", ").append(profitPer);
        sb.append(", ").append(fileName);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);

        sb.append(")");
        return sb.toString();
    }
}
