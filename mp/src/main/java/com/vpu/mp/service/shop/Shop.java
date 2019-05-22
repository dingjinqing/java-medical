package com.vpu.mp.service.shop;

import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.shop.goods.CGoods;
/**
 * 
 * @author 新国
 *
 */
public class Shop {

	public CGoods goods;

	protected Integer shopId = 0;

	public Shop(Integer shopId) {
		this.shopId = shopId;
		Util.initComponents(this);
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
}
