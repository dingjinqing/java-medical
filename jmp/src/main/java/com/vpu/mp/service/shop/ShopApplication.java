package com.vpu.mp.service.shop;

import com.vpu.mp.service.foundation.ServiceContainer;
import com.vpu.mp.service.shop.config.ConfigService;
import com.vpu.mp.service.shop.decoration.MpDecorationService;
import com.vpu.mp.service.shop.decoration.PageClassificationService;
import com.vpu.mp.service.shop.goods.GoodsRecommendService;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.image.ImageCategoryService;
import com.vpu.mp.service.shop.image.ImageService;
import com.vpu.mp.service.shop.order.OrderService;
import com.vpu.mp.service.shop.store.store.StoreService;
import com.vpu.mp.service.shop.version.VersionService;

/**
 * 
 * @author 新国
 *
 */
public class ShopApplication extends ServiceContainer {

	public GoodsService goods;
	public ImageService image;
	public ImageCategoryService imageCatgory;
	public MpDecorationService mpDecoration;
	public OrderService order;
	public PageClassificationService pageClassification;
	public VersionService version;
	public ConfigService config;;
	public StoreService store;
	public GoodsRecommendService goodsRecommend;

	public ShopApplication(Integer shopId) {
		this.shopId = shopId;
	}

}
