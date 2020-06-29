package com.vpu.jmd.service.shop;


import com.vpu.jmd.table.main.tables.records.ShopTaokeRecord;
import com.vpu.jmd.service.base.MainBaseService;

import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.vpu.jmd.table.main.tables.ShopTaoke.SHOP_TAOKE;


/**
 * 淘客
 *
 * @author zhaojianqiang
 * @date 2020年5月29日下午1:43:46
 */
@Service
public class ShopTaokeService extends MainBaseService {
	private static final byte ZERO = 0;
	private static final byte ONE = 1;
	private static final byte TWO = 2;
	private static final byte THREE = 3;

	/**
	 * 创建
	 *
	 * @param shopReq
	 */
	public void insertToke(ShopReq shopReq) {
		Integer shopId = shopReq.getShopId();
		if (Objects.equals(ONE, shopReq.getJdGoods())) {
			logger().info("店铺：{}，设置了京东", shopId);
			ShopTaokeRecord record = db().newRecord(SHOP_TAOKE);
			record.setShopId(shopId);
			record.setAppType(ONE);
			record.setAppKey(shopReq.getJdAppKey());
			record.setAppSecret(shopReq.getJdAppSecret());
			record.setTkShopId(shopReq.getJdShopId());
			int insert = record.insert();
			logger().info("店铺：{}，设置了京东，插入结果{}", shopId, insert);
		}
		if (Objects.equals(ONE, shopReq.getTbGoods())) {
			logger().info("店铺：{}，设置了淘宝", shopId);
			ShopTaokeRecord record = db().newRecord(SHOP_TAOKE);
			record.setShopId(shopId);
			record.setAppType(TWO);
			record.setAppKey(shopReq.getTbAppKey());
			record.setAppSecret(shopReq.getTbAppSecret());
			record.setTkShopId(shopReq.getTbShopId());
			int insert = record.insert();
			logger().info("店铺：{}，设置了淘宝，插入结果{}", shopId, insert);
		}
		if (Objects.equals(ONE, shopReq.getJtGoods())) {
			logger().info("店铺：{}，设置了聚塔", shopId);
			ShopTaokeRecord record = db().newRecord(SHOP_TAOKE);
			record.setShopId(shopId);
			record.setAppType(THREE);
			record.setAppKey(shopReq.getJtAppKey());
			record.setAppSecret("");
			int insert = record.insert();
			logger().info("店铺：{}，设置了聚塔，插入结果{}", shopId, insert);
		}
	}

	/**
	 * 更新
	 *
	 * @param shopReq
	 */
	public void updateToke(ShopReq shopReq) {
		Integer shopId = shopReq.getShopId();
		SelectConditionStep<ShopTaokeRecord> select = db().selectFrom(SHOP_TAOKE).where(SHOP_TAOKE.SHOP_ID.eq(shopId));
		if (Objects.equals(ONE, shopReq.getJdGoods())) {
			logger().info("店铺：{}，设置了京东", shopId);
			ShopTaokeRecord record = makeRecord(select, ONE, shopId);
			record.setAppKey(shopReq.getJdAppKey());
			record.setAppSecret(shopReq.getJdAppSecret());
			record.setTkShopId(shopReq.getJdShopId());
			int insert = record.store();
			logger().info("店铺：{}，设置了京东，更新结果{}", shopId, insert);
		}
		if (Objects.equals(ONE, shopReq.getTbGoods())) {
			logger().info("店铺：{}，设置了淘宝", shopId);
			ShopTaokeRecord record = makeRecord(select, TWO, shopId);
			record.setAppKey(shopReq.getTbAppKey());
			record.setAppSecret(shopReq.getTbAppSecret());
			record.setTkShopId(shopReq.getTbShopId());
			int insert = record.store();
			logger().info("店铺：{}，设置了淘宝，更新结果{}", shopId, insert);
		}
		if (Objects.equals(ONE, shopReq.getJtGoods())) {
			logger().info("店铺：{}，设置了聚塔", shopId);
			ShopTaokeRecord record = makeRecord(select, THREE, shopId);
			record.setAppKey(shopReq.getJtAppKey());
			record.setAppSecret("");
			int insert = record.store();
			logger().info("店铺：{}，设置了聚塔，更新结果{}", shopId, insert);
		}
	}

	/**
	 * 创建需要的record
	 *
	 * @param select
	 * @param type
	 * @param shopId
	 * @return
	 */
	private ShopTaokeRecord makeRecord(SelectConditionStep<ShopTaokeRecord> select, Byte type, Integer shopId) {
		ShopTaokeRecord record = select.and(SHOP_TAOKE.APP_TYPE.eq(type)).fetchAny();
		if (null == record) {
			record = db().newRecord(SHOP_TAOKE);
			record.setShopId(shopId);
			record.setAppType(type);
		}
		return record;
	}

	/**
	 * 返回店铺信息
	 * @param pojo
	 * @return
	 */
	public ShopPojo findToke(ShopPojo pojo) {
		Integer shopId = pojo.getShopId();
		Result<ShopTaokeRecord> result = db().selectFrom(SHOP_TAOKE)
				.where(SHOP_TAOKE.SHOP_ID.eq(shopId)).fetch();
		for (ShopTaokeRecord record : result) {
			Byte appType = record.getAppType();
			if(Objects.equals(ONE, appType)) {
				pojo.setJdGoods(ONE);
				pojo.setJdAppKey(record.getAppKey());
				pojo.setJdAppSecret(record.getAppSecret());
				pojo.setJdShopId(record.getTkShopId());
				pojo.setJdRebateRatio(record.getTkRebateRatio());
			}
			if(Objects.equals(TWO, appType)) {
				pojo.setTbGoods(TWO);
				pojo.setTbAppKey(record.getAppKey());
				pojo.setTbAppSecret(record.getAppSecret());
				pojo.setTbShopId(record.getTkShopId());
			}
			if(Objects.equals(THREE, appType)) {
				pojo.setJtGoods(THREE);
				pojo.setJtAppKey(record.getAppKey());
			}
		}
		return pojo;
	}

}
