package com.vpu.mp.service.pojo.shop.market.integralconvert;

import lombok.Data;

/**
 * 添加积分活动
 * @author liangchen
 * @date 2019年8月15日
 */
@Data
public class IntegralConvertGoodsParam {
	
	/** 商品id */
	private Integer goodsId;
	/** 0：未删除 1：删除 */
	public static final int NOT_DELETE = 0; 
	public static final int DELETE = 1; 
}
