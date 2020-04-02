package com.vpu.mp.service.shop.market.live;

import static com.vpu.mp.db.shop.tables.LiveGoods.LIVE_GOODS;
import static com.vpu.mp.db.shop.tables.Sort.SORT;
import static com.vpu.mp.db.shop.tables.Goods.GOODS;

import java.math.BigDecimal;
import java.util.List;

import org.jooq.Record1;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.GoodsBrandRecord;
import com.vpu.mp.db.shop.tables.records.SortRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.goods.recommend.GoodsLabelsVo;
import com.vpu.mp.service.pojo.shop.market.live.LiveRomeGoodListVo;
import com.vpu.mp.service.shop.goods.GoodsBrandService;
import com.vpu.mp.service.shop.goods.mp.GoodsMpService;

/**
 * 直播商品
 * 
 * @author zhaojianqiang
 * @time 下午4:16:31
 */
@Service
public class LiveGoodsService extends ShopBaseService {

	@Autowired
	private GoodsBrandService goodsBrandService;

	@Autowired
	private GoodsMpService goodsMp;

	private static final Byte ONE = 1;

	/**
	 * 获得直播间商品列表
	 * 
	 * @param id
	 * @return
	 */
	public List<LiveRomeGoodListVo> getRoomGoodsList(Integer id) {
		List<LiveRomeGoodListVo> list = db().select(LIVE_GOODS.fields())
				.select(GOODS.GOODS_NAME, GOODS.GOODS_SN, GOODS.SHOP_PRICE, GOODS.GOODS_NUMBER, GOODS.SORT_ID,
						GOODS.BRAND_ID, GOODS.GOODS_IMG)
				.from(LIVE_GOODS).leftJoin(GOODS).on(LIVE_GOODS.GOODS_ID.eq(GOODS.GOODS_ID))
				.where(LIVE_GOODS.LIVE_ID.eq(id)).fetchInto(LiveRomeGoodListVo.class);
		for (LiveRomeGoodListVo item : list) {
			item.setGoodsImg(imageUrl(item.getGoodsImg()));
		}
		return list;
	}

	/**
	 * 商品列表
	 * 
	 * @param id
	 * @return
	 */
	public List<LiveRomeGoodListVo> packageGoodsList(Integer id) {
		List<LiveRomeGoodListVo> goodsList = getRoomGoodsList(id);
		for (LiveRomeGoodListVo goods : goodsList) {
			if (goods.getGoodsId() == null) {
				continue;
			}
			if (goods.getSortId() != null) {
				SortRecord sortRecord = db().selectFrom(SORT).where(SORT.SORT_ID.eq(goods.getSortId())).fetchAny();
				goods.setSortName(sortRecord.getSortName());
			}
			if (goods.getBrandId() != null) {
				GoodsBrandRecord brand = goodsBrandService.getBrandById(goods.getBrandId());
				goods.setBrandName(brand.getBrandName());
			}
			List<GoodsLabelsVo> goodsTag = goodsMp.mpGoodsRecommendService.getGoodsLabelsByGoods(goods.getGoodsId(), 5,
					ONE);
			goods.setGoodsTag(goodsTag);
		}
		return goodsList;
	}

	/**
	 * 获取商品列表数量
	 * 
	 * @param id
	 * @return
	 */
	public int getPackageGoodsListNum(Integer id) {
		List<LiveRomeGoodListVo> goodsList = getRoomGoodsList(id);
		return goodsList.size();
	}

	/**
	 * 获得加购总数
	 * 
	 * @param roomId
	 * @param goodsId
	 * @return
	 */
	public Integer getAddCartNum(Integer roomId, Integer goodsId) {
		SelectConditionStep<Record1<BigDecimal>> where = db().select(DSL.sum(LIVE_GOODS.ADD_CART_NUM)).from(LIVE_GOODS)
				.where(LIVE_GOODS.ROOM_ID.eq(roomId));
		if (goodsId != null) {
			where.and(LIVE_GOODS.GOODS_ID.eq(goodsId));
		}
		return where.fetchAnyInto(Integer.class);
	}
}
