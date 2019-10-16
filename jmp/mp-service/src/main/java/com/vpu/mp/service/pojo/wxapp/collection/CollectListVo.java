package com.vpu.mp.service.pojo.wxapp.collection;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 商品收藏出参类
 * @author 常乐
 * 2019年10月16日
 */
@Data
public class CollectListVo {
	/**
	 * 用户ID
	 */
	private Integer userId;
	/**
	 * 商品名称
	 */
	private String goodsName;
	/**
	 * 商品价格
	 */
	private BigDecimal shopPrice;
	/**
	 * 商品图片
	 */
	private String goodsImg;
	
}
