package com.vpu.mp.service.pojo.shop.market.bargain;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.vpu.mp.service.pojo.shop.config.ShopShareConfig;

import lombok.Data;

/**
 * @author 王兵兵
 *
 * 2019年7月25日
 */
@Data
public class BargainUpdateParam {

	/**
	 * 活动的主键 
	 */
	@NotNull
	@Min(1)
	private int id;
	
	/**
	 *  状态：1可用，0停用
	 */
	private byte status;
	
	private String bargainName;
	
	private ShopShareConfig shareConfig;
}
