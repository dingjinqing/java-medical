package com.vpu.mp.service.pojo.shop.goods;

import com.vpu.mp.service.pojo.shop.sku.GoodsSpecProduct;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author 李晓冰
 * @date 2020年07月02日
 */
@Data
public class Goods {
    private Integer goodsId;
    private String goodsSn;
    private String goodsName;
    private Integer goodsNumber;
    private Integer brandId;
    private Integer sortId;
    private BigDecimal marketPrice;
    private BigDecimal shopPrice;
    private BigDecimal costPrice;
    private BigDecimal goodsWeight;
    private Integer goodsSaleNum;
    private Integer deliverTemplateId;
    private Integer limitBuyNum;
    private Integer limitMaxNum;
    private Byte isCardExclusive;
    private Byte isOnSale;
    private String goodsImg;
    private String goodsAd;
    private String goodsDesc;
    private Integer goodsPageId;
    private Byte isPageUp;
    private Integer baseSale;
    private Byte source;
    private Byte canRebate;
    private Byte promotionLanguageSwitch;
    private String promotionLanguage;
    private String shareConfig;
    private Byte isDefaultProduct;
    private Byte delFlag;
    private Timestamp createTime;
    private Timestamp updateTime;
    /**规格信息*/
    private List<GoodsSpecProduct> goodsSpecProducts;
    /**药品信息*/
    private GoodsMedicalInfo goodsMedicalInfo;
}
