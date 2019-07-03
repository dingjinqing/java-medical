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
public class UserRebatePrice implements Serializable {

    private static final long serialVersionUID = -656428384;

    private Integer    id;
    private Integer    userId;
    private Integer    goodsId;
    private Integer    productId;
    private BigDecimal advicePrice;
    private Timestamp  addTime;
    private Timestamp  expireTime;
    private Timestamp  updateTime;

    public UserRebatePrice() {}

    public UserRebatePrice(UserRebatePrice value) {
        this.id = value.id;
        this.userId = value.userId;
        this.goodsId = value.goodsId;
        this.productId = value.productId;
        this.advicePrice = value.advicePrice;
        this.addTime = value.addTime;
        this.expireTime = value.expireTime;
        this.updateTime = value.updateTime;
    }

    public UserRebatePrice(
        Integer    id,
        Integer    userId,
        Integer    goodsId,
        Integer    productId,
        BigDecimal advicePrice,
        Timestamp  addTime,
        Timestamp  expireTime,
        Timestamp  updateTime
    ) {
        this.id = id;
        this.userId = userId;
        this.goodsId = goodsId;
        this.productId = productId;
        this.advicePrice = advicePrice;
        this.addTime = addTime;
        this.expireTime = expireTime;
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

    public BigDecimal getAdvicePrice() {
        return this.advicePrice;
    }

    public void setAdvicePrice(BigDecimal advicePrice) {
        this.advicePrice = advicePrice;
    }

    public Timestamp getAddTime() {
        return this.addTime;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }

    public Timestamp getExpireTime() {
        return this.expireTime;
    }

    public void setExpireTime(Timestamp expireTime) {
        this.expireTime = expireTime;
    }

    public Timestamp getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("UserRebatePrice (");

        sb.append(id);
        sb.append(", ").append(userId);
        sb.append(", ").append(goodsId);
        sb.append(", ").append(productId);
        sb.append(", ").append(advicePrice);
        sb.append(", ").append(addTime);
        sb.append(", ").append(expireTime);
        sb.append(", ").append(updateTime);

        sb.append(")");
        return sb.toString();
    }
}
