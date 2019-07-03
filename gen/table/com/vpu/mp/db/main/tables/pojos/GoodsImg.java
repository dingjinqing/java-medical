/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.main.tables.pojos;


import java.io.Serializable;

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
public class GoodsImg implements Serializable {

    private static final long serialVersionUID = 569021048;

    private Integer imgId;
    private Integer goodsId;
    private String  imgUrl;
    private String  imgDesc;

    public GoodsImg() {}

    public GoodsImg(GoodsImg value) {
        this.imgId = value.imgId;
        this.goodsId = value.goodsId;
        this.imgUrl = value.imgUrl;
        this.imgDesc = value.imgDesc;
    }

    public GoodsImg(
        Integer imgId,
        Integer goodsId,
        String  imgUrl,
        String  imgDesc
    ) {
        this.imgId = imgId;
        this.goodsId = goodsId;
        this.imgUrl = imgUrl;
        this.imgDesc = imgDesc;
    }

    public Integer getImgId() {
        return this.imgId;
    }

    public void setImgId(Integer imgId) {
        this.imgId = imgId;
    }

    public Integer getGoodsId() {
        return this.goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getImgUrl() {
        return this.imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgDesc() {
        return this.imgDesc;
    }

    public void setImgDesc(String imgDesc) {
        this.imgDesc = imgDesc;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("GoodsImg (");

        sb.append(imgId);
        sb.append(", ").append(goodsId);
        sb.append(", ").append(imgUrl);
        sb.append(", ").append(imgDesc);

        sb.append(")");
        return sb.toString();
    }
}
