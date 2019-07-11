package com.vpu.mp.service.pojo.shop.goods;

import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpec;
import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpecProduct;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author 李晓冰
 * @date 2019年07月05日
 */
@Data
public class Goods {
    private static final long serialVersionUID = 1778325631;

    private Integer goodsId;

    private String goodsName;
    private String goodsAd;
    private String goodsSn;
    /**
     * 平台分类
     */
    private Integer catId;
    /**
     * 商品主图
     */
    private String goodsImg;
    /**
     * 商品图片列表
     */
    private List<String> goodsImgs;
    /**
     * 商品标签列表
     */
    private List<Integer> goodsLabels;

    private String unit;
    private Integer sortId;

    private Integer brandId;
    private String goodsVideo;
    private String goodsVideoImg;
    private Integer goodsVideoSize;
    private Integer goodsVideoId;

    /**
     * 商品规格属性规格值信息
     */
    private List<GoodsSpec> goodsSpecs;
    /**
     * 商品规格信息
     */
    private List<GoodsSpecProduct> goodsSpecProducts;

    private Integer goodsNumber;
    private BigDecimal shopPrice;
    private BigDecimal marketPrice;

    private Integer limitBuyNum;
    private Integer limitMaxNum;
    private BigDecimal costPrice;

    private Integer deliverTemplateId;
    private BigDecimal goodsWeight;

    private Byte isCardExclusive;
    private Byte canRebate;

    private Byte isOnSale;
    private Timestamp saleTime;

    private Byte isPageUp;
    private Integer goodsPageId;
    private String goodsDesc;
}
