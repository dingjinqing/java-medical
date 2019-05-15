package com.vpu.mp.service.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vpu.mp.service.shop.goods.Goods;

@Component
@Scope("prototype")
public class Shop {
	
	@Autowired
	public Goods goods;
	
	protected Integer shopId = 0;

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	
	public void initComponents() {
		goods.setShopId(shopId);
	}
}
