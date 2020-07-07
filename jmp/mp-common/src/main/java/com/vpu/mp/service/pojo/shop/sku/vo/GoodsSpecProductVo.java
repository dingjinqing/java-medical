package com.vpu.mp.service.pojo.shop.sku.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 李晓冰
 * @date 2020年07月07日
 */
@Data
public class GoodsSpecProductVo {
    private Integer prdId;
    private Integer goodsId;
    private String prdSn;
    private BigDecimal prdPrice;
    private BigDecimal prdMarketPrice;
    private BigDecimal prdCostPrice;
    private Integer prdNumber;
    private String prdCodes;
    private String prdSpecs;
    private String prdDesc;
    private String prdImg;
    private BigDecimal prdWeight;
}
