package com.vpu.mp.service.pojo.shop.goods.vo;

import com.vpu.mp.service.pojo.shop.label.vo.GoodsLabelVo;
import com.vpu.mp.service.pojo.shop.sku.vo.GoodsSpecProductVo;
import com.vpu.mp.service.pojo.shop.sku.vo.SpecVo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品详细信息vo
 * @author 李晓冰
 * @date 2020年07月07日
 */
@Getter
@Setter
public class GoodsSelectVo {
    private Integer goodsId;
    private String goodsSn;
    private String goodsName;
    private Integer goodsNumber;
    private Integer brandId;
    private String brandName;
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
    private Byte isMedical;

    private GoodsMedicalInfoVo goodsMedicalInfoVo;
    /**
     * 规格组集合
     */
    private List<SpecVo> goodsSpecs;
    /**
     * 规格信息
     */
    private List<GoodsSpecProductVo> goodsSpecProducts;

    /**
     * 普通标签
     */
    private List<GoodsLabelVo> normalLabels;
    /**
     * 指定标签
     */
    private List<GoodsLabelVo> pointLabels;

    /**
     * 商品附属图片
     */
    private List<String> goodsImgs;
}
