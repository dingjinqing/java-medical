package com.vpu.mp.service.pojo.shop.market.integralconvert;

import lombok.Data;

/**
 * 积分兑换分页查询列表
 * @author liangchen
 * @date 2019年8月14日
 */
@Data
public class IntegralConvertSwitchParam {
	
	/* 是否停用 0：停用  1：启用 */
	public static final int BLOCK = 0;
	public static final int NOT_BLOCK = 1;
	
	/* 活动id */
	private Integer id;

	
}
