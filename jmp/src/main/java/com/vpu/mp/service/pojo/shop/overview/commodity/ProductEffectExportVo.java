package com.vpu.mp.service.pojo.shop.overview.commodity;

import com.vpu.mp.service.foundation.excel.annotation.ExcelColumn;
import com.vpu.mp.service.foundation.excel.annotation.ExcelColumnNotNull;
import com.vpu.mp.service.foundation.excel.annotation.ExcelIgnore;
import com.vpu.mp.service.foundation.excel.annotation.ExcelSheet;
import lombok.Data;

/**
 * @Author:liufei
 * @Date:2019/7/29
 * @Description:
 */
@Data
@ExcelSheet
public class ProductEffectExportVo{
    /** 商品信息，导出excel中格式为：商品名称 + 空格 + 商品价格 */
    @ExcelColumn(columnIndex = 0,columnName = "goods_info")
    @ExcelColumnNotNull
    private String goodsInfo;
    /** 商品id */
    @ExcelIgnore
    private int goodsId;
    /** 商品名称 */
    @ExcelIgnore
    private String goodsName;
    /** 商品图片 */
    @ExcelIgnore
    private String goodsImg;
    /** 商品价格 */
    @ExcelIgnore
    private double shopPrice;
    /**  新成交客户数  */
    @ExcelColumn(columnIndex = 5,columnName = "newUserNumber")
    @ExcelColumnNotNull
    private  int newUserNumber;
    /**  老成交客户数  */
    @ExcelColumn(columnIndex = 7,columnName = "oldUserNumber")
    @ExcelColumnNotNull
    private  int oldUserNumber;
    /**  浏览量  */
    @ExcelColumn(columnIndex = 2,columnName = "pv")
    @ExcelColumnNotNull
    private  int pv;
    /**  访客数  */
    @ExcelColumn(columnIndex = 1,columnName = "uv")
    @ExcelColumnNotNull
    private  int uv;
    /**  加购人数  */
    @ExcelColumn(columnIndex = 3,columnName = "cartUv")
    @ExcelColumnNotNull
    private  int cartUv;
    /**  付款人数  */
    @ExcelColumn(columnIndex = 4,columnName = "paidUv")
    @ExcelColumnNotNull
    private  int paidUv;
    /**  付款商品件数  */
    @ExcelColumn(columnIndex = 9,columnName = "paidGoodsNumber")
    @ExcelColumnNotNull
    private  int paidGoodsNumber;
    /**  新成交客户数占比  */
    @ExcelColumn(columnIndex = 6,columnName = "newUserPercentage")
    @ExcelColumnNotNull
    private double newUserPercentage;
    /**  老成交客户数占比  */
    @ExcelColumn(columnIndex = 8,columnName = "oldUserPercentage")
    @ExcelColumnNotNull
    private double oldUserPercentage;
    /**  商品转化率  */
    @ExcelColumn(columnIndex = 10,columnName = "uv2paidGoods")
    @ExcelColumnNotNull
    private double uv2paidGoods;
    /**  推荐人数  */

    /**  收藏人数  */
}
