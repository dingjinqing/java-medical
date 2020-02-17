package com.vpu.mp.service.pojo.shop.overview.commodity;

import com.vpu.mp.service.foundation.excel.annotation.ExcelColumn;
import com.vpu.mp.service.foundation.excel.annotation.ExcelColumnNotNull;
import com.vpu.mp.service.foundation.excel.annotation.ExcelIgnore;
import com.vpu.mp.service.foundation.excel.annotation.ExcelSheet;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author:liufei
 * @Date:2019/7/29
 * @Description:
 */
@Data
@ExcelSheet
public class ProductEffectExportVo{
    /** 商品信息，导出excel中格式为：商品名称 + 空格 + 商品价格 */
    @ExcelColumn(columnIndex = 0,columnName = "overview.commodity.effect.goods_info")
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
    /**
     * 商品标签名
     */
    @ExcelColumn(columnIndex = 1, columnName = "overview.commodity.effect.goods_label")
    @ExcelColumnNotNull
    private String name;
    /**
     * 商品品牌名
     */
    @ExcelColumn(columnIndex = 2, columnName = "overview.commodity.effect.goods_brand")
    @ExcelColumnNotNull
    private String brandName;
    /**
     * 商品商家分类名
     */
    @ExcelColumn(columnIndex = 3, columnName = "overview.commodity.effect.goods_sort")
    @ExcelColumnNotNull
    private String sortName;
    /**
     * 访客数
     */
    @ExcelColumn(columnIndex = 4, columnName = "overview.commodity.effect.uv")
    @ExcelColumnNotNull
    private int uv;
    /**
     * 浏览量
     */
    @ExcelColumn(columnIndex = 5, columnName = "overview.commodity.effect.pv")
    @ExcelColumnNotNull
    private int pv;
    /**
     * 加购人数
     */
    @ExcelColumn(columnIndex = 6, columnName = "overview.commodity.effect.cart_uv")
    @ExcelColumnNotNull
    private int cartUv;
    /**
     * 付款人数
     */
    @ExcelColumn(columnIndex = 7, columnName = "overview.commodity.effect.paid_uv")
    @ExcelColumnNotNull
    private int paidUv;
    /**
     * 新成交客户数
     */
    @ExcelColumn(columnIndex = 8,columnName = "overview.commodity.effect.new_user_num")
    @ExcelColumnNotNull
    private int newUserNumber;
    /**
     * 新成交客户数占比
     */
    @ExcelColumn(columnIndex = 9, columnName = "overview.commodity.effect.new_user_percentage")
    @ExcelColumnNotNull
    private double newUserPercentage;
    /**  老成交客户数  */
    @ExcelColumn(columnIndex = 10,columnName = "overview.commodity.effect.old_user_num")
    @ExcelColumnNotNull
    private int oldUserNumber;
    /**
     * 老成交客户数占比
     */
    @ExcelColumn(columnIndex = 11, columnName = "overview.commodity.effect.old_user_percentage")
    @ExcelColumnNotNull
    private double oldUserPercentage;
    /**  付款商品件数  */
    @ExcelColumn(columnIndex = 12,columnName = "overview.commodity.effect.paid_goods_num")
    @ExcelColumnNotNull
    private int paidGoodsNumber;
    /**  商品转化率  */
    @ExcelColumn(columnIndex = 13,columnName = "overview.commodity.effect.uv2paid")
    @ExcelColumnNotNull
    private double uv2paidGoods;
    /**
     * 销售额
     */
    @ExcelColumn(columnIndex = 14, columnName = "overview.commodity.effect.goods_sales")
    @ExcelColumnNotNull
    private BigDecimal goodsSales;
    /**
     * 推荐人数
     */
    @ExcelColumn(columnIndex = 15, columnName = "overview.commodity.effect.recommend_user_num")
    @ExcelColumnNotNull
    private int recommendUserNum;
    /**
     * 收藏人数
     */
    @ExcelColumn(columnIndex = 16,columnName = "overview.commodity.effect.collect_use_num")
    @ExcelColumnNotNull
    private int collectUseNum;
}
