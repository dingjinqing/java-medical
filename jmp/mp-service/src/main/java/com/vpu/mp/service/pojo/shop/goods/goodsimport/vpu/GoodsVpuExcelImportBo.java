package com.vpu.mp.service.pojo.shop.goods.goodsimport.vpu;

import com.vpu.mp.service.pojo.shop.goods.goodsimport.GoodsExcelImportBase;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 李晓冰
 * @date 2020年03月21日
 */
@Getter
@Setter
public class GoodsVpuExcelImportBo extends GoodsExcelImportBase {
    private String firstSortName;
    private String secondSortName;
    /**sku对应的规格组合id编号*/
    private String prdSpecs;
    private String goodsAd;
    private Integer stock;
    private BigDecimal marketPrice;
    private BigDecimal shopPrice;
    private Byte isOnSale;
    private Integer limitBuyNum;
    private BigDecimal goodsWeight;
    private String unit;
    /**商品图片使用;分隔*/
    private String goodsImgsStr;
    /**处理后的商品图片，默认第一个是主图，如果是远程图片则需要将其下载到又拍云上*/
    private List<String> goodsImgs;
    private String goodsDesc;
    private String deliverPlace;

    public GoodsVpuExcelImportBo(GoodsVpuExcelImportModel m){
        firstSortName = m.getFirstSortName();
        secondSortName = m.getSecondSortName();
        goodsSn = m.getGoodsSn();
        goodsName = m.getGoodsName();
        prdDesc = m.getPrdDesc();
        goodsAd = m.getGoodsAd();
        prdSn = m.getPrdSn();
        stock = m.getStock();
        marketPrice = m.getMarketPrice();
        shopPrice = m.getShopPrice();
        isOnSale = m.getIsOnSale();
        limitBuyNum = m.getLimitBuyNum();
        goodsWeight = m.getGoodsWeight();
        unit = m.getUnit();
        goodsImgsStr = m.getGoodsImgsStr();
        goodsDesc = m.getGoodsDesc();
        deliverPlace = m.getDeliverPlace();
    }
}
