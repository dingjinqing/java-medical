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
	/**
	 * 商品名称
	 */
	private String goodsName;
	/**
	 * 商品价格
	 */
	private BigDecimal shopPrice;
	/**
	 * 所属分类
	 */
	private String catName;
	
	private short catId;
	/**
	 * 商品总销量
	 */
	private Integer goodsSaleNum;
	/**
	 * 已返利总数量
	 */
	private Integer saleNumber;
	/**
	 * 已返利总佣金
	 */
	private BigDecimal prdTotalFanli;
}
