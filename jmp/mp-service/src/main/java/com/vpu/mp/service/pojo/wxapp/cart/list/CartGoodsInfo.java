package com.vpu.mp.service.pojo.wxapp.cart.list;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 购物车商品信息
 * @author 孔德成
 * @date 2019/11/5 16:47
 */
@Setter
@Getter
public class CartGoodsInfo {

    //**********基础信息***************
    /**
     * 是否选中
     */
    private Byte isChecked;
    /**
     * 商品id
     */
    private Integer goodsId;
    /**
     * 商品图片
     */
    private String goodsImg;
    /**
     * 规格id
     */
    private Integer productId;
    /**
     * 图片
     */
    private String prdImg;
    /**
     * 购物车数量
     */
    private Integer prdNumber;
    /**
     * 商品规格
     */
    private String goodsSpecs;
    /**
     * 商品最大购买数量
     */
    private Integer limitBuyNum;
    /**
     * 商品最小购买数量
     */
    private Integer limitMaxNum;
    /**
     * 商品规格价格现价
     */
    private BigDecimal prdPrice;
    /**
     * 添加到购物车时的价格
     */
    private BigDecimal oldPrice;
    //*******活动**********

}
