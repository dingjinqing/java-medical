/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.main.tables.pojos;


import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.types.UByte;
import org.jooq.types.UInteger;


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
public class Goods implements Serializable {

    private static final long serialVersionUID = 196258202;

    private Long       id;
    private Integer    goodsId;
    private Integer    shopId;
    private Integer    catId;
    private String     goodsSn;
    private String     goodsName;
    private Integer    brandId;
    private String     goodsAd;
    private Integer    goodsNumber;
    private BigDecimal goodsWeight;
    private BigDecimal marketPrice;
    private BigDecimal shopPrice;
    private BigDecimal costPrice;
    private String     goodsDesc;
    private String     goodsImg;
    private UByte      isOnSale;
    private UByte      isDelete;
    private UByte      goodsType;
    private UInteger   deliverTemplateId;
    private UInteger   goodsSaleNum;
    private UInteger   goodsCollectNum;
    private Timestamp  addTime;
    private Timestamp  updateTime;
    private Byte       state;
    private String     reason;
    private Integer    subAccountId;
    private Timestamp  saleTime;
    private Integer    limitBuyNum;
    private Integer    limitMaxNum;
    private String     unit;
    private Integer    addSaleNum;
    private Byte       saleType;
    private UInteger   sortId;
    private String     goodsVideo;
    private String     goodsVideoImg;
    private Integer    goodsVideoSize;
    private Integer    goodsVideoId;
    private Integer    goodsPageId;
    private Byte       isPageUp;
    private Byte       isCardExclusive;
    private UInteger   baseSale;
    private Byte       source;
    private Byte       isControlPrice;
    private Byte       canRebate;

    public Goods() {}

    public Goods(Goods value) {
        this.id = value.id;
        this.goodsId = value.goodsId;
        this.shopId = value.shopId;
        this.catId = value.catId;
        this.goodsSn = value.goodsSn;
        this.goodsName = value.goodsName;
        this.brandId = value.brandId;
        this.goodsAd = value.goodsAd;
        this.goodsNumber = value.goodsNumber;
        this.goodsWeight = value.goodsWeight;
        this.marketPrice = value.marketPrice;
        this.shopPrice = value.shopPrice;
        this.costPrice = value.costPrice;
        this.goodsDesc = value.goodsDesc;
        this.goodsImg = value.goodsImg;
        this.isOnSale = value.isOnSale;
        this.isDelete = value.isDelete;
        this.goodsType = value.goodsType;
        this.deliverTemplateId = value.deliverTemplateId;
        this.goodsSaleNum = value.goodsSaleNum;
        this.goodsCollectNum = value.goodsCollectNum;
        this.addTime = value.addTime;
        this.updateTime = value.updateTime;
        this.state = value.state;
        this.reason = value.reason;
        this.subAccountId = value.subAccountId;
        this.saleTime = value.saleTime;
        this.limitBuyNum = value.limitBuyNum;
        this.limitMaxNum = value.limitMaxNum;
        this.unit = value.unit;
        this.addSaleNum = value.addSaleNum;
        this.saleType = value.saleType;
        this.sortId = value.sortId;
        this.goodsVideo = value.goodsVideo;
        this.goodsVideoImg = value.goodsVideoImg;
        this.goodsVideoSize = value.goodsVideoSize;
        this.goodsVideoId = value.goodsVideoId;
        this.goodsPageId = value.goodsPageId;
        this.isPageUp = value.isPageUp;
        this.isCardExclusive = value.isCardExclusive;
        this.baseSale = value.baseSale;
        this.source = value.source;
        this.isControlPrice = value.isControlPrice;
        this.canRebate = value.canRebate;
    }

    public Goods(
        Long       id,
        Integer    goodsId,
        Integer    shopId,
        Integer    catId,
        String     goodsSn,
        String     goodsName,
        Integer    brandId,
        String     goodsAd,
        Integer    goodsNumber,
        BigDecimal goodsWeight,
        BigDecimal marketPrice,
        BigDecimal shopPrice,
        BigDecimal costPrice,
        String     goodsDesc,
        String     goodsImg,
        UByte      isOnSale,
        UByte      isDelete,
        UByte      goodsType,
        UInteger   deliverTemplateId,
        UInteger   goodsSaleNum,
        UInteger   goodsCollectNum,
        Timestamp  addTime,
        Timestamp  updateTime,
        Byte       state,
        String     reason,
        Integer    subAccountId,
        Timestamp  saleTime,
        Integer    limitBuyNum,
        Integer    limitMaxNum,
        String     unit,
        Integer    addSaleNum,
        Byte       saleType,
        UInteger   sortId,
        String     goodsVideo,
        String     goodsVideoImg,
        Integer    goodsVideoSize,
        Integer    goodsVideoId,
        Integer    goodsPageId,
        Byte       isPageUp,
        Byte       isCardExclusive,
        UInteger   baseSale,
        Byte       source,
        Byte       isControlPrice,
        Byte       canRebate
    ) {
        this.id = id;
        this.goodsId = goodsId;
        this.shopId = shopId;
        this.catId = catId;
        this.goodsSn = goodsSn;
        this.goodsName = goodsName;
        this.brandId = brandId;
        this.goodsAd = goodsAd;
        this.goodsNumber = goodsNumber;
        this.goodsWeight = goodsWeight;
        this.marketPrice = marketPrice;
        this.shopPrice = shopPrice;
        this.costPrice = costPrice;
        this.goodsDesc = goodsDesc;
        this.goodsImg = goodsImg;
        this.isOnSale = isOnSale;
        this.isDelete = isDelete;
        this.goodsType = goodsType;
        this.deliverTemplateId = deliverTemplateId;
        this.goodsSaleNum = goodsSaleNum;
        this.goodsCollectNum = goodsCollectNum;
        this.addTime = addTime;
        this.updateTime = updateTime;
        this.state = state;
        this.reason = reason;
        this.subAccountId = subAccountId;
        this.saleTime = saleTime;
        this.limitBuyNum = limitBuyNum;
        this.limitMaxNum = limitMaxNum;
        this.unit = unit;
        this.addSaleNum = addSaleNum;
        this.saleType = saleType;
        this.sortId = sortId;
        this.goodsVideo = goodsVideo;
        this.goodsVideoImg = goodsVideoImg;
        this.goodsVideoSize = goodsVideoSize;
        this.goodsVideoId = goodsVideoId;
        this.goodsPageId = goodsPageId;
        this.isPageUp = isPageUp;
        this.isCardExclusive = isCardExclusive;
        this.baseSale = baseSale;
        this.source = source;
        this.isControlPrice = isControlPrice;
        this.canRebate = canRebate;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGoodsId() {
        return this.goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getShopId() {
        return this.shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getCatId() {
        return this.catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public String getGoodsSn() {
        return this.goodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }

    public String getGoodsName() {
        return this.goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getBrandId() {
        return this.brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getGoodsAd() {
        return this.goodsAd;
    }

    public void setGoodsAd(String goodsAd) {
        this.goodsAd = goodsAd;
    }

    public Integer getGoodsNumber() {
        return this.goodsNumber;
    }

    public void setGoodsNumber(Integer goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public BigDecimal getGoodsWeight() {
        return this.goodsWeight;
    }

    public void setGoodsWeight(BigDecimal goodsWeight) {
        this.goodsWeight = goodsWeight;
    }

    public BigDecimal getMarketPrice() {
        return this.marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public BigDecimal getShopPrice() {
        return this.shopPrice;
    }

    public void setShopPrice(BigDecimal shopPrice) {
        this.shopPrice = shopPrice;
    }

    public BigDecimal getCostPrice() {
        return this.costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public String getGoodsDesc() {
        return this.goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public String getGoodsImg() {
        return this.goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public UByte getIsOnSale() {
        return this.isOnSale;
    }

    public void setIsOnSale(UByte isOnSale) {
        this.isOnSale = isOnSale;
    }

    public UByte getIsDelete() {
        return this.isDelete;
    }

    public void setIsDelete(UByte isDelete) {
        this.isDelete = isDelete;
    }

    public UByte getGoodsType() {
        return this.goodsType;
    }

    public void setGoodsType(UByte goodsType) {
        this.goodsType = goodsType;
    }

    public UInteger getDeliverTemplateId() {
        return this.deliverTemplateId;
    }

    public void setDeliverTemplateId(UInteger deliverTemplateId) {
        this.deliverTemplateId = deliverTemplateId;
    }

    public UInteger getGoodsSaleNum() {
        return this.goodsSaleNum;
    }

    public void setGoodsSaleNum(UInteger goodsSaleNum) {
        this.goodsSaleNum = goodsSaleNum;
    }

    public UInteger getGoodsCollectNum() {
        return this.goodsCollectNum;
    }

    public void setGoodsCollectNum(UInteger goodsCollectNum) {
        this.goodsCollectNum = goodsCollectNum;
    }

    public Timestamp getAddTime() {
        return this.addTime;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }

    public Timestamp getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Byte getState() {
        return this.state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getSubAccountId() {
        return this.subAccountId;
    }

    public void setSubAccountId(Integer subAccountId) {
        this.subAccountId = subAccountId;
    }

    public Timestamp getSaleTime() {
        return this.saleTime;
    }

    public void setSaleTime(Timestamp saleTime) {
        this.saleTime = saleTime;
    }

    public Integer getLimitBuyNum() {
        return this.limitBuyNum;
    }

    public void setLimitBuyNum(Integer limitBuyNum) {
        this.limitBuyNum = limitBuyNum;
    }

    public Integer getLimitMaxNum() {
        return this.limitMaxNum;
    }

    public void setLimitMaxNum(Integer limitMaxNum) {
        this.limitMaxNum = limitMaxNum;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getAddSaleNum() {
        return this.addSaleNum;
    }

    public void setAddSaleNum(Integer addSaleNum) {
        this.addSaleNum = addSaleNum;
    }

    public Byte getSaleType() {
        return this.saleType;
    }

    public void setSaleType(Byte saleType) {
        this.saleType = saleType;
    }

    public UInteger getSortId() {
        return this.sortId;
    }

    public void setSortId(UInteger sortId) {
        this.sortId = sortId;
    }

    public String getGoodsVideo() {
        return this.goodsVideo;
    }

    public void setGoodsVideo(String goodsVideo) {
        this.goodsVideo = goodsVideo;
    }

    public String getGoodsVideoImg() {
        return this.goodsVideoImg;
    }

    public void setGoodsVideoImg(String goodsVideoImg) {
        this.goodsVideoImg = goodsVideoImg;
    }

    public Integer getGoodsVideoSize() {
        return this.goodsVideoSize;
    }

    public void setGoodsVideoSize(Integer goodsVideoSize) {
        this.goodsVideoSize = goodsVideoSize;
    }

    public Integer getGoodsVideoId() {
        return this.goodsVideoId;
    }

    public void setGoodsVideoId(Integer goodsVideoId) {
        this.goodsVideoId = goodsVideoId;
    }

    public Integer getGoodsPageId() {
        return this.goodsPageId;
    }

    public void setGoodsPageId(Integer goodsPageId) {
        this.goodsPageId = goodsPageId;
    }

    public Byte getIsPageUp() {
        return this.isPageUp;
    }

    public void setIsPageUp(Byte isPageUp) {
        this.isPageUp = isPageUp;
    }

    public Byte getIsCardExclusive() {
        return this.isCardExclusive;
    }

    public void setIsCardExclusive(Byte isCardExclusive) {
        this.isCardExclusive = isCardExclusive;
    }

    public UInteger getBaseSale() {
        return this.baseSale;
    }

    public void setBaseSale(UInteger baseSale) {
        this.baseSale = baseSale;
    }

    public Byte getSource() {
        return this.source;
    }

    public void setSource(Byte source) {
        this.source = source;
    }

    public Byte getIsControlPrice() {
        return this.isControlPrice;
    }

    public void setIsControlPrice(Byte isControlPrice) {
        this.isControlPrice = isControlPrice;
    }

    public Byte getCanRebate() {
        return this.canRebate;
    }

    public void setCanRebate(Byte canRebate) {
        this.canRebate = canRebate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Goods (");

        sb.append(id);
        sb.append(", ").append(goodsId);
        sb.append(", ").append(shopId);
        sb.append(", ").append(catId);
        sb.append(", ").append(goodsSn);
        sb.append(", ").append(goodsName);
        sb.append(", ").append(brandId);
        sb.append(", ").append(goodsAd);
        sb.append(", ").append(goodsNumber);
        sb.append(", ").append(goodsWeight);
        sb.append(", ").append(marketPrice);
        sb.append(", ").append(shopPrice);
        sb.append(", ").append(costPrice);
        sb.append(", ").append(goodsDesc);
        sb.append(", ").append(goodsImg);
        sb.append(", ").append(isOnSale);
        sb.append(", ").append(isDelete);
        sb.append(", ").append(goodsType);
        sb.append(", ").append(deliverTemplateId);
        sb.append(", ").append(goodsSaleNum);
        sb.append(", ").append(goodsCollectNum);
        sb.append(", ").append(addTime);
        sb.append(", ").append(updateTime);
        sb.append(", ").append(state);
        sb.append(", ").append(reason);
        sb.append(", ").append(subAccountId);
        sb.append(", ").append(saleTime);
        sb.append(", ").append(limitBuyNum);
        sb.append(", ").append(limitMaxNum);
        sb.append(", ").append(unit);
        sb.append(", ").append(addSaleNum);
        sb.append(", ").append(saleType);
        sb.append(", ").append(sortId);
        sb.append(", ").append(goodsVideo);
        sb.append(", ").append(goodsVideoImg);
        sb.append(", ").append(goodsVideoSize);
        sb.append(", ").append(goodsVideoId);
        sb.append(", ").append(goodsPageId);
        sb.append(", ").append(isPageUp);
        sb.append(", ").append(isCardExclusive);
        sb.append(", ").append(baseSale);
        sb.append(", ").append(source);
        sb.append(", ").append(isControlPrice);
        sb.append(", ").append(canRebate);

        sb.append(")");
        return sb.toString();
    }
}
