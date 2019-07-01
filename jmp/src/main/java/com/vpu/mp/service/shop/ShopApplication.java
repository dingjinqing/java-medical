package com.vpu.mp.service.shop;

import com.vpu.mp.service.foundation.ServiceFactory;
import com.vpu.mp.service.shop.decoration.MpDecorationService;
import com.vpu.mp.service.shop.decoration.PageClassificationService;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.image.ImageService;
import com.vpu.mp.service.shop.order.OrderService;
import com.vpu.mp.service.shop.version.VersionService;

/**
 * 
 * @author 新国
 *
 */
public class ShopApplication {

	public GoodsService goods;
	public ImageService image;
	public MpDecorationService mpDecoration;
	public OrderService order;
	public PageClassificationService pageClassification;
	public VersionService version;

	protected Integer shopId = 0;

	public ShopApplication(Integer shopId) {
		this.shopId = shopId;
		ServiceFactory.initServices(this);
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	
}
