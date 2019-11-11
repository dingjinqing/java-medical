package com.vpu.mp.service.shop.goods.mp;

import static com.vpu.mp.db.shop.tables.Goods.GOODS;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.goods.goods.shopMoment.BrandInfo;
import com.vpu.mp.service.pojo.shop.goods.goods.shopMoment.ListProduct;
import com.vpu.mp.service.pojo.shop.goods.goods.shopMoment.Product;
import com.vpu.mp.service.pojo.shop.goods.goods.shopMoment.SkuAttrList;
import com.vpu.mp.service.pojo.shop.goods.goods.shopMoment.SkuList;
import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpecProduct;
import com.vpu.mp.service.saas.categroy.SysCateService;
import com.vpu.mp.service.saas.shop.ShopService;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.image.ImageService;

/**
 * 好物圈相关
 * 
 * @author zhaojianqiang
 *
 *         2019年11月11日 下午1:57:11
 */
@Service
public class GoodsRecommend extends ShopBaseService {
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private SysCateService syCateService;
	@Autowired
	private ShopService shopService;
	@Autowired
	private ImageService imageService;

	/**
	 * 好物推荐商品
	 * 
	 * @param goodsId
	 * @return
	 */
	public List<ListProduct> shippingRecommendGoods(List<Integer> goodsId) {
		ShopRecord shop = shopService.getShopById(getShopId());
		Result<GoodsRecord> goodsList = db().selectFrom(GOODS).where(GOODS.GOODS_ID.in(goodsId)).fetch();
		List<ListProduct> vo = new ArrayList<ListProduct>();
		for (GoodsRecord goods : goodsList) {
			ListProduct pListProduct = new ListProduct();
			Product productVo = new Product();
			// getAllProductListByGoodsId
			List<GoodsSpecProduct> productList = goodsService.goodsSpecProductService
					.selectByGoodsId(goods.getGoodsId());
			List<SkuList> liSkuLists = new ArrayList<SkuList>();
			for (GoodsSpecProduct product : productList) {
				SkuList skuList = new SkuList();
				String prdDesc = product.getPrdDesc();
				List<SkuAttrList> skuAttrList = new ArrayList<SkuAttrList>();
				if (!StringUtils.isEmpty(prdDesc)) {
					skuAttrList = goodsService.goodsSpecProductService.getSkuAttrList(prdDesc);
				} else {
					skuAttrList.add(new SkuAttrList(goods.getGoodsName(), goods.getGoodsName()));
				}
				skuList.setSkuId(String.valueOf(product.getPrdId()));
				skuList.setPrice(product.getPrdPrice().multiply(new BigDecimal(100)));
				skuList.setOriginalPrice(product.getPrdPrice().multiply(new BigDecimal(100)));
				skuList.setStatus(goods.getIsOnSale() == (byte) 1 ? (byte) 1 : (byte) 2);
				skuList.setSkuAttrList(skuAttrList);
				liSkuLists.add(skuList);
			}
			List<String> list = syCateService.getCategories(goods.getCatId());
			if (list == null || list.size() == 0) {
				list = new ArrayList<String>();
				list.add("未知");
			}
			productVo.setItemCode(String.valueOf(goods.getGoodsId()));
			productVo.setTitle(goods.getGoodsName());
			productVo.setDesc(goods.getGoodsName());
			productVo.setCategoryList(list);
			productVo.setImageList(goodsService.getGoodsImageList(goods.getGoodsId()));
			productVo.setSrcMiniProgramPath("/pages/item/item?goods_id=" + goods.getGoodsId());
			productVo.setSkuList(liSkuLists);
			String logo = shop.getLogo();
			productVo.setBrandInfo(new BrandInfo(shop.getShopName(), StringUtils.isEmpty(logo)?"":imageService.imageUrl(shop.getLogo())));
			pListProduct.setProduct(productVo);
			pListProduct.setGoodsId(goods.getGoodsId());
			vo.add(pListProduct);
		}
		return vo;
	}

}
