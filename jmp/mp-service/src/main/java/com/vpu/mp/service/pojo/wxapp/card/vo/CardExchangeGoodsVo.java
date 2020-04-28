package com.vpu.mp.service.pojo.wxapp.card.vo;

import java.util.Map;

import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListVo;

import lombok.Data;

/**
 * 会员卡兑换商品信息
 * @author 黄壮壮
 */
@Data
public class CardExchangeGoodsVo {
	/**
	 * 兑换商品列表
	 */
	private PageResult<GoodsPageListVo> goodsPageResult;
	
	/**
	 * 会员卡相关字段
	 */
	private Map<String, Object> cardInfo;
	
	/**
	 * 已选择兑换商品的数量
	 */
	private Integer totalNumber;
}
