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
public class GoodsLabel implements Serializable {

    private static final long serialVersionUID = 304922414;

    private Integer   id;
    private String    name;
    private Byte      goodsDetail;
    private Byte      goodsList;
    private Byte      isAll;
    private Short     level;
    private Timestamp delTime;
    private Integer   delFlag;
    private Short     listPattern;
    private Byte      goodsSelect;
    private Timestamp createTime;
    private Timestamp updateTime;

    public GoodsLabel() {}

    public GoodsLabel(GoodsLabel value) {
        this.id = value.id;
        this.name = value.name;
        this.goodsDetail = value.goodsDetail;
        this.goodsList = value.goodsList;
        this.isAll = value.isAll;
        this.level = value.level;
        this.delTime = value.delTime;
        this.delFlag = value.delFlag;
        this.listPattern = value.listPattern;
        this.goodsSelect = value.goodsSelect;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
    }

    public GoodsLabel(
        Integer   id,
        String    name,
        Byte      goodsDetail,
        Byte      goodsList,
        Byte      isAll,
        Short     level,
        Timestamp delTime,
        Integer   delFlag,
        Short     listPattern,
        Byte      goodsSelect,
        Timestamp createTime,
        Timestamp updateTime
    ) {
        this.id = id;
        this.name = name;
        this.goodsDetail = goodsDetail;
        this.goodsList = goodsList;
        this.isAll = isAll;
        this.level = level;
        this.delTime = delTime;
        this.delFlag = delFlag;
        this.listPattern = listPattern;
        this.goodsSelect = goodsSelect;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getGoodsDetail() {
        return this.goodsDetail;
    }

    public void setGoodsDetail(Byte goodsDetail) {
        this.goodsDetail = goodsDetail;
    }

    public Byte getGoodsList() {
        return this.goodsList;
    }

    public void setGoodsList(Byte goodsList) {
        this.goodsList = goodsList;
    }

    public Byte getIsAll() {
        return this.isAll;
    }

    public void setIsAll(Byte isAll) {
        this.isAll = isAll;
    }

    public Short getLevel() {
        return this.level;
    }

    public void setLevel(Short level) {
        this.level = level;
    }

    public Timestamp getDelTime() {
        return this.delTime;
    }

    public void setDelTime(Timestamp delTime) {
        this.delTime = delTime;
    }

    public Integer getDelFlag() {
        return this.delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Short getListPattern() {
        return this.listPattern;
    }

    public void setListPattern(Short listPattern) {
        this.listPattern = listPattern;
    }

    public Byte getGoodsSelect() {
        return this.goodsSelect;
    }

    public void setGoodsSelect(Byte goodsSelect) {
        this.goodsSelect = goodsSelect;
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
        StringBuilder sb = new StringBuilder("GoodsLabel (");

        sb.append(id);
        sb.append(", ").append(name);
        sb.append(", ").append(goodsDetail);
        sb.append(", ").append(goodsList);
        sb.append(", ").append(isAll);
        sb.append(", ").append(level);
        sb.append(", ").append(delTime);
        sb.append(", ").append(delFlag);
        sb.append(", ").append(listPattern);
        sb.append(", ").append(goodsSelect);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);

        sb.append(")");
        return sb.toString();
    }
}
