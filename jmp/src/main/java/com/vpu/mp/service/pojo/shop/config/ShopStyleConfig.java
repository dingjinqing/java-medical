package com.vpu.mp.service.pojo.shop.config;

import lombok.Data;

/**
 * 
 * @author 新国
 * @description 店铺风格样例 {"shopStyleId":"5","shopStyleValue":"#feb609,#333333"} 
 */
@Data
public class ShopStyleConfig {
	
	/**
	 * 店铺风格ID
	 */
	public Integer shopStyleId;
	
	/**
	 * 店铺风格值
	 */
	public String shopStyleValue;
}
