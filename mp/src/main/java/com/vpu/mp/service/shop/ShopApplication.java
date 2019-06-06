package com.vpu.mp.service.shop;

import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.shop.decoration.MpDecorationService;
import com.vpu.mp.service.shop.decoration.PageClassificationService;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.image.ImageService;

/**
 * 
 * @author 新国
 *
 */
public class ShopApplication {

	public GoodsService goods;
	public ImageService image;
	public MpDecorationService mpDecoration;
	public PageClassificationService pageClassification;

	protected Integer shopId = 0;

	public ShopApplication(Integer shopId) {
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
