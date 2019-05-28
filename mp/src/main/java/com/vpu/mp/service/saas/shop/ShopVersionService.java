package com.vpu.mp.service.saas.shop;

import static com.vpu.mp.db.main.tables.ShopVersion.SHOP_VERSION;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.db.main.tables.records.ShopVersionRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.foundation.Util;

/**
 * 
 * @author 新国
 *
 */
public class ShopVersionService extends BaseService {

	final public static class VersionMainConfig {
		@JsonProperty(value = "sub_0")
		public List<String> sub0 = new ArrayList<String>();

		@JsonProperty(value = "sub_1")
		public List<String> sub1 = new ArrayList<String>();

		@JsonProperty(value = "sub_2")
		public List<String> sub2 = new ArrayList<String>();

		@JsonProperty(value = "sub_3")
		public List<String> sub3 = new ArrayList<String>();

		@JsonProperty(value = "sub_4")
		public List<String> sub4 = new ArrayList<String>();

		@JsonProperty(value = "sub_5")
		public List<String> sub5 = new ArrayList<String>();
	}

	final public static class VersionNumberConfig {
		@JsonProperty(value = "picture_num")
		public Integer pictureNum = -1;

		@JsonProperty(value = "video_num")
		public Integer videoNum = -1;

		@JsonProperty(value = "goods_num")
		public Integer goodsNum = -1;

		@JsonProperty(value = "store_num")
		public Integer storeNum = -1;

		@JsonProperty(value = "decorate_num")
		public Integer decorateNum = -1;

		@JsonProperty(value = "form_num")
		public Integer formNum = -1;

		@JsonProperty(value = "picture_num_plus")
		public Integer pictureNumPlus = 0;

		@JsonProperty(value = "video_num_plus")
		public Integer videoNumPlus = 0;

		@JsonProperty(value = "goods_num_plus")
		public Integer goodsNumPlus = 0;

		@JsonProperty(value = "store_num_plus")
		public Integer storeNumPlus = 0;

		@JsonProperty(value = "decorate_num_plus")
		public Integer decorateNumPlus = 0;

		@JsonProperty(value = "form_num_plus")
		public Integer formNumPlus = 0;

	}

	final public static class VersionConfig {
		@JsonProperty(value = "main_config")
		public VersionMainConfig mainConfig = new VersionMainConfig();

		@JsonProperty(value = "num_config")
		public VersionNumberConfig numConfig = new VersionNumberConfig();
	};

	final public static class VersionListQueryParam {
		public Integer page;
		public String versionName;
	};

	public PageResult getPageList(VersionListQueryParam param) {
		SelectWhereStep<Record> select = db().select().from(SHOP_VERSION);
		if (!StringUtils.isEmpty(param.versionName)) {
			select.where(SHOP_VERSION.VERSION_NAME.like(this.likeValue(param.versionName)));
		}
		select.orderBy(SHOP_VERSION.ID.desc());
		return this.getPageResult(select, param.page);
	}

	public Result<ShopVersionRecord> getAllVersion() {
		return db().selectFrom(SHOP_VERSION)
				.where(SHOP_VERSION.FLAG.eq((byte) 0))
				.orderBy(SHOP_VERSION.LEVEL.desc())
				.fetch();
	}

	public Map<String, String> getVersionMap() {
		Map<String, String> result = new HashMap<String, String>(10);
		for (ShopVersionRecord record : getAllVersion()) {
			result.put(record.getLevel(), record.getVersionName());
		}
		return result;
	}

	public ShopVersionRecord getVersionByLevel(String level) {
		return db().selectFrom(SHOP_VERSION).where(SHOP_VERSION.LEVEL.eq(level)).fetchOne();
	}

	/**
	 * 得到店铺对应的版本配置
	 * 
	 * @param shopId
	 * @return
	 */
	public VersionConfig mergeVersion(Integer shopId) {
		ShopRecord shop = saas().shop.getShopById(shopId);
		if (shop == null || StringUtils.isEmpty(shop.getShopType())) {
			return null;
		}

		VersionConfig versionConfig = Util.parseJSON(shop.getVersionConfig(), VersionConfig.class);
		if (versionConfig == null) {
			versionConfig = new VersionConfig();
		}

		ShopVersionRecord versionRecord = getVersionByLevel(shop.getShopType());
		if (versionRecord == null) {
			return null;
		}
		VersionConfig levelVersionConfig = Util.parseJSON(versionRecord.getContent(), VersionConfig.class);
		if (levelVersionConfig == null) {
			return null;
		}
		Util.mergeList(versionConfig.mainConfig.sub0,levelVersionConfig.mainConfig.sub0);
		Util.mergeList(versionConfig.mainConfig.sub1,levelVersionConfig.mainConfig.sub1);
		Util.mergeList(versionConfig.mainConfig.sub2,levelVersionConfig.mainConfig.sub2);
		Util.mergeList(versionConfig.mainConfig.sub3,levelVersionConfig.mainConfig.sub3);
		Util.mergeList(versionConfig.mainConfig.sub4,levelVersionConfig.mainConfig.sub4);
		Util.mergeList(versionConfig.mainConfig.sub5,levelVersionConfig.mainConfig.sub5); 

		VersionNumberConfig num1 = versionConfig.numConfig;
		VersionNumberConfig num2 = levelVersionConfig.numConfig;
		num1.decorateNum = (num1.decorateNum == -1 || num2.decorateNum == -1) ? -1
				: num2.decorateNum + num2.decorateNumPlus;
		num1.formNum = (num1.formNum == -1 || num2.formNum == -1) ? -1 : num2.formNum + num2.formNumPlus;
		num1.goodsNum = (num1.goodsNum == -1 || num2.goodsNum == -1) ? -1 : num2.goodsNum + num2.goodsNumPlus;
		num1.pictureNum = (num1.pictureNum == -1 || num2.pictureNum == -1) ? -1 : num2.pictureNum + num2.pictureNumPlus;
		num1.storeNum = (num1.storeNum == -1 || num2.storeNum == -1) ? -1 : num2.storeNum + num2.storeNumPlus;
		num1.videoNum = (num1.videoNum == -1 || num2.videoNum == -1) ? -1 : num2.videoNum + num2.videoNumPlus;

		return versionConfig;
	}

	public void getVersionConfig() {
		String json = Util.loadResource("test.json");
		VersionConfig config = new VersionConfig();
		config = Util.parseJSON(json, VersionConfig.class);
		System.out.println(config);
	}

	/**
	 * 获取所有未删除的level
	 * 
	 * @return
	 */
	public List<String> getAllLevel() {
		Byte normalFlag = 0;
		return db().select().from(SHOP_VERSION).where(SHOP_VERSION.FLAG.eq(normalFlag)).fetch(SHOP_VERSION.LEVEL);
	}

	/**
	 * 删除版本
	 * 
	 * @param id
	 * @return
	 */
	public int delVersion(Integer id) {
		Byte delFlag = 1;
		return db().update(SHOP_VERSION)
				.set(SHOP_VERSION.FLAG, delFlag)
				.set(SHOP_VERSION.LEVEL, DSL.concat("del_" + id, SHOP_VERSION.LEVEL))
				.where(SHOP_VERSION.ID.eq(id)).execute();
	}

	/**
	 * 功能判断
	 * @param modName
	 * @param subName
	 * @param shopId
	 * @return
	 */
	public Map<String, Object> mainJudgment(String modName, String subName,Integer shopId) {
		ShopRecord shop = saas().shop.getShopById(shopId);
		VersionConfig config = this.mergeVersion(shopId);
		VersionMainConfig m = config.mainConfig;
		String[] subNames = { "sub_0", "sub_1", "sub_2", "sub_3", "sub_4", "sub_5" };
		Object[] subObjects = { m.sub0, m.sub1, m.sub2, m.sub3, m.sub4, m.sub5 };
		int index  = Arrays.asList(subNames).indexOf(subName) ;
		Integer content = -1;
		if( index != -1 && Arrays.asList(subObjects[index]).indexOf(modName)  != -1) {
			content = 1;			
		}
		Map<String,Object> result = new HashMap<String,Object>(0);
		Map<String,Object> message = new HashMap<String,Object>(0);
		result.put("content", content);
		result.put("version_name", getVersionByLevel(shop.getShopType()).getVersionName());
		result.put("version_id", shop.getShopType());
		result.put("expire_time", saas().shop.renew.getShopRenewExpireTime(shopId));
		message.put("message", result);
		return message;
	}

}
