package com.vpu.mp.service.pojo.wxapp.card.param;

import lombok.Data;

/**
 * 兑换商品加购入参
 * @author 黄壮壮
 *
 */
@Data
public class CardAddExchangeGoodsParam {
	/**
	 * 用户ID
	 */
	private Integer userId;
	
	/**
	 * 商品ID
	 */
	private Integer goodsId;
	
	/**
	 * 规格Id
	 */
	private Integer productId;
	
	/**
	 * 规格数量
	 */
	private Integer prdNumber;
	
	/**
	 * 兑换商品数量
	 */
	private Integer changeGoodsNumber;
	
	/**
	 * 会员卡卡号
	 */
	private String cardNo;
	
}
