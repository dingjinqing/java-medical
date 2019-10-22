package com.vpu.mp.service.pojo.shop.goods.goods;

import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.excel.annotation.ExcelColumn;
import com.vpu.mp.service.foundation.excel.annotation.ExcelIgnore;
import com.vpu.mp.service.foundation.excel.annotation.ExcelSheet;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author: 王兵兵
 * @create: 2019-10-21 15:24
 **/
@ExcelSheet
@Data
public class GoodsExportVo {

    /**
     * 规格添加时间
     */
    @ExcelColumn(columnName = JsonResultMessage.GOODS_EXPORT_COLUMN_CREATE_TIME,columnIndex = 0)
    private Timestamp createTime;
    /**
     * 平台分类
     */
    @ExcelColumn(columnName = JsonResultMessage.GOODS_EXPORT_COLUMN_CAT_NAME,columnIndex = 1)
    private String catName;
    /**
     * 商家一级分类名
     */
    @ExcelColumn(columnName = JsonResultMessage.GOODS_EXPORT_COLUMN_SORT_NAME_PARENT,columnIndex = 2)
    private String sortNameParent;
    /**
     * 商家二级分类名
     */
    @ExcelColumn(columnName = JsonResultMessage.GOODS_EXPORT_COLUMN_SORT_NAME_CHILD,columnIndex = 3)
    private String sortNameChild;
    /**
     * 品牌
     */
    @ExcelColumn(columnName = JsonResultMessage.GOODS_EXPORT_COLUMN_BRAND_NAME,columnIndex = 4)
    private String brandName;
    /**
     * 货品编号
     */
    @ExcelColumn(columnName = JsonResultMessage.GOODS_EXPORT_COLUMN_GOODS_SN,columnIndex = 5)
    private String goodsSn;
    /**
     * 货品名称
     */
    @ExcelColumn(columnName = JsonResultMessage.GOODS_EXPORT_COLUMN_GOODS_NAME,columnIndex = 6)
    private String goodsName;
    /**
     * 规格名称
     */
    @ExcelColumn(columnName = JsonResultMessage.GOODS_EXPORT_COLUMN_PRD_DESC,columnIndex = 7)
    private String prdDesc;
    /**
     * 商品广告词
     */
    @ExcelColumn(columnName = JsonResultMessage.GOODS_EXPORT_COLUMN_GOODS_AD,columnIndex = 8)
    private String goodsAd;
    /**
     * 规格编码
     */
    @ExcelColumn(columnName = JsonResultMessage.GOODS_EXPORT_COLUMN_PRD_SN,columnIndex = 9)
    private String prdSn;
    /**
     * 库存（规格的库存）
     */
    @ExcelColumn(columnName = JsonResultMessage.GOODS_EXPORT_COLUMN_PRD_NUMBER,columnIndex = 10)
    private Integer prdNumber;
    /**
     * 成本价
     */
    @ExcelColumn(columnName = JsonResultMessage.GOODS_EXPORT_COLUMN_PRD_COST_PRICE,columnIndex = 11)
    private BigDecimal prdCostPrice;
    /**
     * 市场价
     */
    @ExcelColumn(columnName = JsonResultMessage.GOODS_EXPORT_COLUMN_MARKET_PRICE,columnIndex = 12)
    private BigDecimal marketPrice;
    /**
     * 零售价
     */
    @ExcelColumn(columnName = JsonResultMessage.GOODS_EXPORT_COLUMN_SHOP_PRICE,columnIndex = 13)
    private BigDecimal shopPrice;
    /**
     * 上架状态
     */
    @ExcelColumn(columnName = JsonResultMessage.GOODS_EXPORT_COLUMN_IS_ON_SALE,columnIndex = 14)
    private Byte isOnSale;
    /**
     * 最小限购数量
     */
    @ExcelColumn(columnName = JsonResultMessage.GOODS_EXPORT_COLUMN_LIMIT_BUY_NUMBER,columnIndex = 15)
    private Integer limitBuyNum;
    /**
     * 重量
     */
    @ExcelColumn(columnName = JsonResultMessage.GOODS_EXPORT_COLUMN_GOODS_WEIGHT,columnIndex = 16)
    private BigDecimal goodsWeight;
    /**
     * 单位量词
     */
    @ExcelColumn(columnName = JsonResultMessage.GOODS_EXPORT_COLUMN_UNIT,columnIndex = 17)
    private String unit;
    /**
     * 商品主图
     */
    @ExcelColumn(columnName = JsonResultMessage.GOODS_EXPORT_COLUMN_GOODS_IMG,columnIndex = 18)
    private String goodsImg;

    @ExcelIgnore
    private Integer goodsId;
    @ExcelIgnore
    private Integer brandId;
    @ExcelIgnore
    private Integer catId;
    @ExcelIgnore
    private Integer sortId;
}
