package com.vpu.mp.service.pojo.saas.shop;

/**
 * 
 * @author zhaojianqiang
 *
 */
public class ShopConst {

	/**
	 * 店铺等级
	 */
	public interface shopType {
		String v1 = "v1";
		String v2 = "v2";
		String v3 = "v3";
		String v4 = "v4";
	}

	/**
	 * 
	 * 1:体验版 2:付费版
	 */
	public interface shopTypes {
		String TRIAL_VERSION = "1";
		String PAID_VERSION = "2";
	}
}
