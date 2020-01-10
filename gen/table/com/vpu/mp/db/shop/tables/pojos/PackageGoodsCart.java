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
public class PackageGoodsCart implements Serializable {

    private static final long serialVersionUID = -1159330347;

    private Integer   id;
    private Integer   userId;
    private Integer   packageId;
    private Byte      groupId;
    private Integer   goodsId;
    private Integer   productId;
    private Integer   goodsNumber;
    private Timestamp createTime;
    private Timestamp updateTime;

    public PackageGoodsCart() {}

    public PackageGoodsCart(PackageGoodsCart value) {
        this.id = value.id;
        this.userId = value.userId;
        this.packageId = value.packageId;
        this.groupId = value.groupId;
        this.goodsId = value.goodsId;
        this.productId = value.productId;
        this.goodsNumber = value.goodsNumber;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
    }

    public PackageGoodsCart(
        Integer   id,
        Integer   userId,
        Integer   packageId,
        Byte      groupId,
        Integer   goodsId,
        Integer   productId,
        Integer   goodsNumber,
        Timestamp createTime,
        Timestamp updateTime
    ) {
        this.id = id;
        this.userId = userId;
        this.packageId = packageId;
        this.groupId = groupId;
        this.goodsId = goodsId;
        this.productId = productId;
        this.goodsNumber = goodsNumber;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPackageId() {
        return this.packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public Byte getGroupId() {
        return this.groupId;
    }

    public void setGroupId(Byte groupId) {
        this.groupId = groupId;
    }

    public Integer getGoodsId() {
        return this.goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getProductId() {
        return this.productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getGoodsNumber() {
        return this.goodsNumber;
    }

    public void setGoodsNumber(Integer goodsNumber) {
        this.goodsNumber = goodsNumber;
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
        StringBuilder sb = new StringBuilder("PackageGoodsCart (");

        sb.append(id);
        sb.append(", ").append(userId);
        sb.append(", ").append(packageId);
        sb.append(", ").append(groupId);
        sb.append(", ").append(goodsId);
        sb.append(", ").append(productId);
        sb.append(", ").append(goodsNumber);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);

        sb.append(")");
        return sb.toString();
    }
}
