package com.vpu.mp.service.shop.goods;

import com.vpu.mp.service.foundation.BaseService;

/**
 *	 商品品牌
 *
 * @author 李晓冰
 * @date 2019年6月25日
 */
public class GoodsService extends BaseService {
    public GoodsBrandService goodsBrand;
    public GoodsSortService goodsSort;
    public GoodsSpecProductService goodsSpecProductService;
    
	public GoodsLabelService goodsLabel;
	public GoodsLabelCoupleService goodsLabelCouple;

    private GoodsSpecService goodsSpecService=new GoodsSpecService();
}

