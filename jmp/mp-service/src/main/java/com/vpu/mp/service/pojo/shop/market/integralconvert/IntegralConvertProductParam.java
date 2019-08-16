package com.vpu.mp.service.pojo.shop.market.integralconvert;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 添加积分活动
 * @author liangchen
 * @date 2019年8月15日
 */
@Data
public class IntegralConvertProductParam {
	
	/* 商品兑换金额 */
	private BigDecimal money;
	
	/* 商品兑换积分 */
	private Integer score;
	
	/* 兑换商品库存 */
	private Short stock;
	
	
}
