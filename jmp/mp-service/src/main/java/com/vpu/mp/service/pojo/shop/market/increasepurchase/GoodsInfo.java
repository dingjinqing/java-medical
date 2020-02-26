package com.vpu.mp.service.pojo.shop.market.increasepurchase;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author liufei
 * @date 2019/8/14
 * @description
 */
@Data
public class GoodsInfo {
    /** 商品名称 */
    private String goodsName;
    /**
     * 商品图片
     */
    private String goodsImg;
    /** 商品原价 */
    private BigDecimal shopPrice;
    /** 商品库存 */
    private Integer goodsNumber;
}
