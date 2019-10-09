package com.vpu.mp.service.shop.store.store;

import static com.vpu.mp.db.shop.tables.Goods.GOODS;
import static com.vpu.mp.db.shop.tables.GoodsSpecProduct.GOODS_SPEC_PRODUCT;
import static com.vpu.mp.db.shop.tables.StoreGoods.STORE_GOODS;

import java.util.List;

import com.vpu.mp.service.pojo.shop.store.goods.StoreGoodsUpdateParam;
import org.jooq.Record;
import org.jooq.SelectWhereStep;
import org.jooq.tools.StringUtils;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.store.goods.StoreGoodsListQueryParam;
import com.vpu.mp.service.pojo.shop.store.goods.StoreGoodsListQueryVo;

import org.springframework.stereotype.Service;

/**
 * @author 王兵兵
 *
 * 2019年7月12日
 */
@Service

public class StoreGoodsService extends ShopBaseService{

    private static final Byte ON_SALE = 1;
    private static final Byte OFF_SALE = 0;
	

	/**
	 * 门店商品列表分页查询
	 * @param param
	 * @return StorePageListVo
	 */
	public PageResult<StoreGoodsListQueryVo> getPageList(StoreGoodsListQueryParam param) {
		SelectWhereStep<? extends Record> select = db().
				select(
				GOODS.GOODS_IMG,GOODS.GOODS_NAME,STORE_GOODS.IS_SYNC,GOODS_SPEC_PRODUCT.PRD_PRICE,GOODS_SPEC_PRODUCT.PRD_NUMBER,
				GOODS_SPEC_PRODUCT.PRD_DESC,STORE_GOODS.PRODUCT_PRICE,STORE_GOODS.PRODUCT_NUMBER,STORE_GOODS.PRD_ID,STORE_GOODS.PRD_SN,GOODS_SPEC_PRODUCT.PRD_CODES
				).
				from(STORE_GOODS).
				leftJoin(GOODS_SPEC_PRODUCT).on(STORE_GOODS.PRD_ID.eq(GOODS_SPEC_PRODUCT.PRD_ID)).
				leftJoin(GOODS).on(GOODS.GOODS_ID.eq(GOODS_SPEC_PRODUCT.GOODS_ID));
		select = this.buildOptions(select, param);
		select.where(STORE_GOODS.STORE_ID.eq(param.getStoreId())).orderBy(STORE_GOODS.CREATE_TIME);
		return getPageResult(select,param.getCurrentPage(),param.getPageRows(),StoreGoodsListQueryVo.class);
	}
	
	/**
	 * 条件查询
	 * @param select
	 * @param param
	 * @return
	 */
	public SelectWhereStep<? extends Record> buildOptions(SelectWhereStep<? extends  Record> select, StoreGoodsListQueryParam param) {
		if (param == null) {
			return select;
		}
		if (param.getIsOnSale() != null) {
			select.where(STORE_GOODS.IS_ON_SALE.eq(param.getIsOnSale()));
		}
		if (param.getIsSync() != null) {
			select.where(STORE_GOODS.IS_SYNC.eq(param.getIsSync()));
		}
		if (param.getCatId() != null && param.getCatId() > 0) {
			List<Integer> allCatId = saas().sysCate.findChildrenByParentId(param.getCatId());
			select.where(GOODS.CAT_ID.in(allCatId));
		}
		if (!StringUtils.isEmpty(param.getKeywords())) {
			select.where(GOODS.GOODS_NAME.contains(param.getKeywords()).or(GOODS_SPEC_PRODUCT.PRD_CODES.eq(param.getKeywords())));
		}
		return select;
	}

    /**
     * 门店商品-上架
     * @param param
     */
    public void storeGoodsPutOnSale(StoreGoodsUpdateParam param) {
        db().update(STORE_GOODS).set(STORE_GOODS.IS_ON_SALE,ON_SALE).where(STORE_GOODS.PRD_ID.in(param.getPrdId()).and(STORE_GOODS.STORE_ID.eq(param.getStoreId()))).execute();
    }

    /**
     * 门店商品-下架
     * @param param
     */
    public void storeGoodsPutOffSale(StoreGoodsUpdateParam param) {
        db().update(STORE_GOODS).set(STORE_GOODS.IS_ON_SALE,OFF_SALE).where(STORE_GOODS.PRD_ID.in(param.getPrdId()).and(STORE_GOODS.STORE_ID.eq(param.getStoreId()))).execute();
    }

}
