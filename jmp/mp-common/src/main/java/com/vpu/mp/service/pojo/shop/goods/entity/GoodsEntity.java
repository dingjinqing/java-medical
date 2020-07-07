package com.vpu.mp.service.pojo.shop.goods.entity;

import com.vpu.mp.service.pojo.shop.goods.MedicalGoodsConstant;
import com.vpu.mp.service.pojo.shop.sku.entity.GoodsSpecProductEntity;
import com.vpu.mp.service.pojo.shop.sku.entity.SpecEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * @author 李晓冰
 * @date 2020年07月02日
 */
@Data
public class GoodsEntity {
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
    private Byte isMedical;
    private Byte delFlag;
    private Timestamp createTime;
    private Timestamp updateTime;

    /**
     * 药品信息
     */
    private GoodsMedicalInfoEntity goodsMedicalInfo;

    /**
     * 规格组集合
     */
    private List<SpecEntity> specEntities;
    /**
     * 规格信息
     */
    private List<GoodsSpecProductEntity> goodsSpecProducts;

    private List<Integer> labelIds;
    /**
     * 幅图图片相对地址
     */
    private List<String> imgPaths;

    @SuppressWarnings("all")
    public void calculateGoodsPriceWeight() {

        // 当存在商品规格时，统计商品总数和最低商品价格
        BigDecimal smallestShopPrice = BigDecimal.valueOf(Double.MAX_VALUE);
        BigDecimal largestMarketPrice = BigDecimal.valueOf(Double.MIN_VALUE);
        BigDecimal smallestCostPrice = BigDecimal.valueOf(Double.MAX_VALUE);
        BigDecimal smallestGoodsWeight = BigDecimal.valueOf(Double.MAX_VALUE);

        Integer goodsNumberSum = 0;
        for (GoodsSpecProductEntity specProduct : goodsSpecProducts) {
            goodsNumberSum += specProduct.getPrdNumber();
            if (smallestShopPrice.compareTo(specProduct.getPrdPrice()) > 0) {
                smallestShopPrice = specProduct.getPrdPrice();
            }
            if (specProduct.getPrdMarketPrice() != null && largestMarketPrice.compareTo(specProduct.getPrdMarketPrice()) < 0) {
                largestMarketPrice = specProduct.getPrdMarketPrice();
            }
            if (specProduct.getPrdCostPrice() != null && smallestCostPrice.compareTo(specProduct.getPrdCostPrice()) > 0) {
                smallestCostPrice = specProduct.getPrdCostPrice();
            }
            if (specProduct.getPrdWeight() != null && smallestGoodsWeight.compareTo(specProduct.getPrdWeight()) > 0) {
                smallestGoodsWeight = specProduct.getPrdWeight();
            }
        }

        goodsNumber = goodsNumberSum;
        shopPrice = smallestShopPrice;
        marketPrice = largestMarketPrice;
        costPrice = smallestCostPrice;
        goodsWeight = smallestGoodsWeight;
    }

    /**
     * 根据规格组（规格名值id）处理sku prdSpecs内容
     */
    public void calculateSkuPrdSpecsBySpecs() {
        if (!MedicalGoodsConstant.DEFAULT_SKU.equals(isDefaultProduct)) {
            Map<String, SpecEntity> specNameMap = SpecEntity.mapNameToSpec(specEntities);
            for (GoodsSpecProductEntity goodsSpecProductEntity : goodsSpecProducts) {
                goodsSpecProductEntity.calculatePrdSpecs(specNameMap);
            }
        } else {
            if (goodsSpecProducts != null) {
                goodsSpecProducts.get(0).setPrdSpecs("");
            }
        }
    }
}
