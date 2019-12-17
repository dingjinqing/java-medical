package com.vpu.mp.service.shop.collection;

import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.UserCollectionRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.wxapp.collection.AddAndCancelCollectionParam;
import com.vpu.mp.service.pojo.wxapp.collection.CancleCollectParam;
import com.vpu.mp.service.pojo.wxapp.collection.CollectListParam;
import com.vpu.mp.service.pojo.wxapp.collection.CollectListVo;
import com.vpu.mp.service.shop.goods.mp.GoodsMpService;
import com.vpu.mp.service.shop.image.ImageService;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.vpu.mp.db.shop.Tables.GOODS;
import static com.vpu.mp.db.shop.Tables.USER_COLLECTION;

/**
 * 商品收藏service
 * @author 常乐
 * 2019年10月16日
 */
@Service
public class CollectService extends ShopBaseService{
	@Autowired
    ImageService imageService;

	@Autowired
    GoodsMpService goodsMpService;
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

    /**
     * 添加用户商品收藏信息
     * @param param 待添加商品信息
     * @param userId 用户id
     * @param userName 用户名称
     */
	public void addCollection(AddAndCancelCollectionParam param, Integer userId, String userName){
        GoodsRecord goodsInfo = goodsMpService.getGoodsCollectionInfoDao(param.getGoodsId());
        if (goodsInfo == null||isCollectedDao(userId, param.getGoodsId())) {
            return;
        }

        transaction(()->{
            goodsMpService.incOrDecGoodsCollectionNumDao(param.getGoodsId(),true);
            addCollectionDao(param.getGoodsId(),goodsInfo.getShopPrice(),userId,userName);
        });
    }

    /**
     * 取消商品收藏
     * @param param 商品信息
     * @param userId 用户id
     */
    public void cancelCollection(AddAndCancelCollectionParam param, Integer userId) {
        transaction(()->{
            int i = deleteCollectionDao(param.getGoodsId(), userId);
            if (i > 0) {
                GoodsRecord goodsInfo = goodsMpService.getGoodsCollectionInfoDao(param.getGoodsId());
                if (goodsInfo.getGoodsCollectNum() > 0) {
                    goodsMpService.incOrDecGoodsCollectionNumDao(param.getGoodsId(),false);
                }
            }
        });
    }

    /**
     * 判断用户是否已经收藏了该商品
     * @param userId 用户id
     * @param goodsId 商品id
     * @return true 已收藏 false 未收藏
     */
    private boolean isCollectedDao (Integer userId, Integer goodsId) {
        int count = db().fetchCount(USER_COLLECTION, USER_COLLECTION.USER_ID.eq(userId).and(USER_COLLECTION.GOODS_ID.eq(goodsId)));
        if (count == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 插入用户收藏商品信息
     * @param goodsId 商品id
     * @param shopPrice 商品收藏时候的价格
     * @param userId 用户id
     */
    private void addCollectionDao(Integer goodsId,BigDecimal shopPrice,Integer userId,String userName) {
        UserCollectionRecord userCollectionRecord = db().newRecord(USER_COLLECTION);
        userCollectionRecord.setGoodsId(goodsId);
        userCollectionRecord.setCollectPrice(shopPrice);
        userCollectionRecord.setUserId(userId);
        userCollectionRecord.setUsername(userName);
        userCollectionRecord.insert();
    }

    /**
     * 删除用户收藏商品信息
     * @param goodsId 商品id
     * @param userId 用户id
     * @return 受影响行数
     */
    private int deleteCollectionDao(Integer goodsId, Integer userId) {
       return db().delete(USER_COLLECTION).where(USER_COLLECTION.GOODS_ID.eq(goodsId).and(USER_COLLECTION.USER_ID.eq(userId))).execute();
    }
}
