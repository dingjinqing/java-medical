package com.vpu.mp.service.shop.collection;

import static com.vpu.mp.db.shop.Tables.GOODS;
import static com.vpu.mp.db.shop.Tables.USER_COLLECTION;

import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.wxapp.collection.CancleCollectParam;
import com.vpu.mp.service.pojo.wxapp.collection.CollectListParam;
import com.vpu.mp.service.pojo.wxapp.collection.CollectListVo;
import com.vpu.mp.service.shop.image.ImageService;

/**
 * 商品收藏service
 * @author 常乐
 * 2019年10月16日
 */
@Service
public class CollectService extends ShopBaseService{
	@Autowired
    ImageService imageService;
	/**
	 * 商品收藏列表
	 * @param param
	 * @param userId
	 * @return
	 */
	public PageResult<CollectListVo> collectList(CollectListParam param , Integer userId) {
		 SelectConditionStep<? extends Record> sql = db().select(USER_COLLECTION.ID,USER_COLLECTION.USER_ID,GOODS.GOODS_NAME,GOODS.GOODS_IMG,
				 GOODS.SHOP_PRICE,GOODS.GOODS_TYPE,USER_COLLECTION.COLLECT_PRICE,USER_COLLECTION.USERNAME,USER_COLLECTION.CREATE_TIME)
				.from(USER_COLLECTION
				.leftJoin(GOODS).on(USER_COLLECTION.GOODS_ID.eq(GOODS.GOODS_ID)))
				.where(USER_COLLECTION.USER_ID.eq(userId).and(GOODS.DEL_FLAG.eq((byte)0)));
		PageResult<CollectListVo> lists = getPageResult(sql, param.getCurrentPage(), param.getPageRows(), CollectListVo.class);
		
		//TODO:判断是否为拼团商品
		
		//处理图片路径为全路径
		for(CollectListVo list : lists.dataList) {
			if(!org.apache.commons.lang3.StringUtils.isBlank(list.getGoodsImg())) {
				list.setGoodsImg(imageService.imageUrl(list.getGoodsImg()));
			}
		}
		return lists;
	}
	
	/**
	 * 取消收藏
	 * @param param
	 * @return
	 */
	public int cancalCollect(CancleCollectParam param) {
		System.out.println(1234);
		int res = db().delete(USER_COLLECTION).where(USER_COLLECTION.ID.eq(param.getId())).execute();
		return res;
	}
}
