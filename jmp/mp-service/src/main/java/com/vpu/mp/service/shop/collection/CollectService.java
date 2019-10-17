package com.vpu.mp.service.shop.collection;

import static com.vpu.mp.db.shop.Tables.GOODS;
import static com.vpu.mp.db.shop.Tables.USER_COLLECTION;

import java.math.BigDecimal;

import org.jooq.Record;
import org.jooq.Record3;
import org.jooq.SelectConditionStep;
import org.jooq.SelectSelectStep;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.wxapp.collection.CollectListParam;
import com.vpu.mp.service.pojo.wxapp.collection.CollectListVo;

/**
 * 商品收藏service
 * @author 常乐
 * 2019年10月16日
 */
@Service
public class CollectService extends ShopBaseService{
	/**
	 * 商品收藏列表
	 * @param param
	 * @param userId
	 * @return
	 */
	public PageResult<CollectListVo> collectList(CollectListParam param , Integer userId) {
		 SelectConditionStep<? extends Record> sql = db().select(USER_COLLECTION.USER_ID,GOODS.GOODS_NAME,GOODS.GOODS_IMG,
				 GOODS.SHOP_PRICE,GOODS.GOODS_TYPE,USER_COLLECTION.COLLECT_PRICE,USER_COLLECTION.USERNAME,USER_COLLECTION.CREATE_TIME)
				.from(USER_COLLECTION
				.leftJoin(GOODS).on(USER_COLLECTION.GOODS_ID.eq(GOODS.GOODS_ID)))
				.where(USER_COLLECTION.USER_ID.eq(userId).and(GOODS.DEL_FLAG.eq((byte)0)));
		PageResult<CollectListVo> list = getPageResult(sql, param.getCurrentPage(), param.getPageRows(), CollectListVo.class);
		
		//TODO:判断是否为拼团商品
		
		return list;
	}
}
