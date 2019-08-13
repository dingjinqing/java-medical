package com.vpu.mp.service.pojo.shop.distribution;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 返利商品统计出参
 * @author 常乐
 * 2019年8月10日
 */
@Data
public class RebateGoodsVo {
	private String goodsName;
	private BigDecimal goodsPrice;
	private String catName;
	private Integer goodsSaleNum;
	private Integer saleNumber;
	private BigDecimal prdTotalFanli;
}
