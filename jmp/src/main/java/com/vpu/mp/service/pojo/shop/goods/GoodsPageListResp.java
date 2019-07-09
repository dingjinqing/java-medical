package com.vpu.mp.service.pojo.shop.goods;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 李晓冰
 * @date 2019年07月09日
 */
@Data
public class GoodsPageListResp {
    private Integer goodsId;
    private String goodsName;//商品名
    private BigDecimal shopPrice;//商品价格
    private String goodsImg;//图片
    private String goodsSn;//商品货号
    private Integer catId;//平台分类id
    private String catName;//平台分类名
    private String sortName;//商家分类名
    private String brandName;//品牌名称
    private Integer goodsNumber;//库存量
    private Integer goodsSaleNum;//销量
}
