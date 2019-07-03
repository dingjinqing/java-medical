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
public class WxShoppingRecommend implements Serializable {

    private static final long serialVersionUID = 1191060661;

    private Integer   id;
    private Integer   userId;
    private Integer   goodsId;
    private Integer   orderSn;
    private Integer   clickNum;
    private Timestamp addTime;

    public WxShoppingRecommend() {}

    public WxShoppingRecommend(WxShoppingRecommend value) {
        this.id = value.id;
        this.userId = value.userId;
        this.goodsId = value.goodsId;
        this.orderSn = value.orderSn;
        this.clickNum = value.clickNum;
        this.addTime = value.addTime;
    }

    public WxShoppingRecommend(
        Integer   id,
        Integer   userId,
        Integer   goodsId,
        Integer   orderSn,
        Integer   clickNum,
        Timestamp addTime
    ) {
        this.id = id;
        this.userId = userId;
        this.goodsId = goodsId;
        this.orderSn = orderSn;
        this.clickNum = clickNum;
        this.addTime = addTime;
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

    public Integer getOrderSn() {
        return this.orderSn;
    }

    public void setOrderSn(Integer orderSn) {
        this.orderSn = orderSn;
    }

    public Integer getClickNum() {
        return this.clickNum;
    }

    public void setClickNum(Integer clickNum) {
        this.clickNum = clickNum;
    }

    public Timestamp getAddTime() {
        return this.addTime;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("WxShoppingRecommend (");

        sb.append(id);
        sb.append(", ").append(userId);
        sb.append(", ").append(goodsId);
        sb.append(", ").append(orderSn);
        sb.append(", ").append(clickNum);
        sb.append(", ").append(addTime);

        sb.append(")");
        return sb.toString();
    }
}
