package com.vpu.mp.service.pojo.shop.goods.spec;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 货品SKU
 * 
 * @author 李晓冰
 * @date 2019年07月05日
 */
@Data
public class GoodsSpecProduct {
	private Integer prdId;

	private Integer goodsId;
    private BigDecimal prdPrice;
    private BigDecimal prdMarketPrice;
    private BigDecimal prdCostPrice;
    private Integer prdNumber;

    /**
     * 	规格编码
     */
	private String prdSn;
	private String prdSpecs;
	private String prdDesc;

	private BigDecimal lowShopPrice;
	private String prdImg;
	private String prdImgPath;
}
