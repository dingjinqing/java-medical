/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.service.pojo.shop.decoration;


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
public class PageClassificationPojo implements Serializable {

    private static final long serialVersionUID = 1174538240;

    private Integer   id;
    private Integer   shopId;
    private String    name;
    private Timestamp createTime;

    public PageClassificationPojo() {}

    public PageClassificationPojo(PageClassificationPojo value) {
        this.id = value.id;
        this.shopId = value.shopId;
        this.name = value.name;
        this.createTime = value.createTime;
    }

    public PageClassificationPojo(
        Integer   id,
        Integer   shopId,
        String    name,
        Timestamp createTime
    ) {
        this.id = id;
        this.shopId = shopId;
        this.name = name;
        this.createTime = createTime;
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("PageClassificationPojo (");

        sb.append(id);
        sb.append(", ").append(shopId);
        sb.append(", ").append(name);
        sb.append(", ").append(createTime);

        sb.append(")");
        return sb.toString();
    }
}
