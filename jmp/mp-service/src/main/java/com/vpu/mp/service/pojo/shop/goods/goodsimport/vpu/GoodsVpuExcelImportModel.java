package com.vpu.mp.service.pojo.shop.goods.goodsimport.vpu;

import com.vpu.mp.service.foundation.excel.annotation.ExcelColumn;
import com.vpu.mp.service.foundation.excel.annotation.ExcelIgnore;
import com.vpu.mp.service.foundation.excel.annotation.ExcelSheet;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 导入的excel对应的对象装换类
 * 目前没有导入商品条码的概念
 * @author 李晓冰
 * @date 2020年03月18日
 */
@ExcelSheet
@Data
public class GoodsVpuExcelImportModel {
    /**商品id*/
    @ExcelIgnore
    private Integer goodsId;

    /**一级分类名称*/
    @ExcelColumn(columnIndex = 0, columnName = "vpu.import.goods.first.sort")
    private String firstSortName;

    /**二级分类名称*/
    @ExcelColumn(columnIndex = 1, columnName = "vpu.import.goods.second.sort")
    private String secondSortName;
    /**货品编号*/
    @ExcelColumn(columnIndex = 2, columnName = "vpu.import.goods.sn")
    private String goodsSn;
    /**商品名称*/
    @ExcelColumn(columnIndex = 3, columnName = "vpu.import.goods.name")
    private String goodsName;
    /**对应sku，null表示默认sku*/
    @ExcelColumn(columnIndex = 4, columnName = "vpu.import.goods.prd.desc")
    private String prdDesc;
    /**sku对应的规格组合id编号*/
    @ExcelIgnore
    private String prdSpecs;
    /**商品广告词*/
    @ExcelColumn(columnIndex = 5, columnName = "vpu.import.goods.ad")
    private String goodsAd;
    /**商家编码*/
    @ExcelColumn(columnIndex = 6, columnName = "vpu.import.goods.prd.sn")
    private String prdSn;
    /**商品库存*/
    @ExcelColumn(columnIndex = 7, columnName = "vpu.import.goods.stock")
    private Integer stock;
    /**市场价格*/
    @ExcelColumn(columnIndex = 8, columnName = "vpu.import.goods.market.price")
    private BigDecimal marketPrice;
    /**商品店铺价格-零售价*/
    @ExcelColumn(columnIndex = 9, columnName = "vpu.import.goods.shop.price")
    private BigDecimal shopPrice;
    /**是否上架1是0否*/
    @ExcelColumn(columnIndex = 10, columnName = "vpu.import.goods.is.on.sale")
    private Byte isOnSale;
    /**最小限购数 0 表示不限制，用户不填的话更新操作需要手动设置为0*/
    @ExcelColumn(columnIndex = 11, columnName = "vpu.import.goods.limit.buy.num")
    private Integer limitBuyNum;
    /**商品重量，不填默认是0*/
    @ExcelColumn(columnIndex = 12, columnName = "vpu.import.first.goods.weight")
    private Integer goodsWeight;
    /**商品计价单位*/
    @ExcelColumn(columnIndex = 13, columnName = "vpu.import.goods.unit")
    private String unit;
    /**商品图片使用;分隔*/
    @ExcelColumn(columnIndex = 14, columnName = "vpu.import.goods.imgs")
    private String goodsImgsStr;
    /**商品描述信息，需要通过正则进行信息的处理过滤*/
    @ExcelColumn(columnIndex = 15, columnName = "vpu.import.goods.desc")
    private String goodsDesc;
    /**默认发货地*/
    @ExcelColumn(columnIndex = 16, columnName = "vpu.import.goods.deliver.place")
    private String deliverPlace;
}
