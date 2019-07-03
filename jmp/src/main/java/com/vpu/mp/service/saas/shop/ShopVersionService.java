package com.vpu.mp.service.saas.shop;

import static com.vpu.mp.db.main.tables.ShopVersion.SHOP_VERSION;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;

import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.db.main.tables.records.ShopVersionRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.pojo.saas.shop.ShopVersionPojo;
import com.vpu.mp.service.pojo.saas.shop.VersionListQueryParam;
import com.vpu.mp.service.pojo.saas.shop.version.VersionConfig;
import com.vpu.mp.service.pojo.saas.shop.version.VersionMainConfig;
import com.vpu.mp.service.pojo.saas.shop.version.VersionNumberConfig;

/**
 * 
 * @author 新国
 *
 */
public class ShopVersionService extends BaseService {

	public PageResult<ShopVersionPojo> getPageList(VersionListQueryParam param) {
		SelectWhereStep<Record> select = db().select().from(SHOP_VERSION);
		if (!StringUtils.isEmpty(param.versionName)) {
			select.where(SHOP_VERSION.VERSION_NAME.like(this.likeValue(param.versionName)));
		}
		select.orderBy(SHOP_VERSION.ID.desc());
		return this.getPageResult(select, param.page,ShopVersionPojo.class);
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
		return db().selectFrom(SHOP_VERSION).where(SHOP_VERSION.LEVEL.eq(level)).fetchAny();
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
		String json = shop.getVersionConfig();
		VersionConfig versionConfig = StringUtils.isBlank(json) ? null : Util.parseJSON(json, VersionConfig.class);
		if (versionConfig == null) {
			versionConfig = new VersionConfig();
		}

		ShopVersionRecord versionRecord = getVersionByLevel(shop.getShopType());
		if (versionRecord == null) {
			return null;
		}

		json = versionRecord.getContent();
		VersionConfig levelVersionConfig = StringUtils.isBlank(json) ? null : Util.parseJSON(json, VersionConfig.class);
		if (levelVersionConfig == null) {
			return null;
		}
		Util.mergeList(versionConfig.mainConfig.sub0, levelVersionConfig.mainConfig.sub0);
		Util.mergeList(versionConfig.mainConfig.sub1, levelVersionConfig.mainConfig.sub1);
		Util.mergeList(versionConfig.mainConfig.sub2, levelVersionConfig.mainConfig.sub2);
		Util.mergeList(versionConfig.mainConfig.sub3, levelVersionConfig.mainConfig.sub3);
		Util.mergeList(versionConfig.mainConfig.sub4, levelVersionConfig.mainConfig.sub4);
		Util.mergeList(versionConfig.mainConfig.sub5, levelVersionConfig.mainConfig.sub5);

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
	 * 
	 * @param modName
	 * @param subName
	 * @param shopId
	 * @return
	 */
	public Map<String, Object> mainJudgment(String modName, String subName, Integer shopId) {
		ShopRecord shop = saas().shop.getShopById(shopId);
		VersionConfig config = this.mergeVersion(shopId);
		VersionMainConfig m = config.mainConfig;
		String[] subNames = { "sub_0", "sub_1", "sub_2", "sub_3", "sub_4", "sub_5" };
		Object[] subObjects = { m.sub0, m.sub1, m.sub2, m.sub3, m.sub4, m.sub5 };
		int index = Arrays.asList(subNames).indexOf(subName);
		Integer content = -1;
		if (index != -1 && Arrays.asList(subObjects[index]).indexOf(modName) != -1) {
			content = 1;
		}
		Map<String, Object> result = new HashMap<String, Object>(0);
		Map<String, Object> message = new HashMap<String, Object>(0);
		result.put("content", content);
		result.put("version_name", getVersionByLevel(shop.getShopType()).getVersionName());
		result.put("version_id", shop.getShopType());
		result.put("expire_time", saas().shop.renew.getShopRenewExpireTime(shopId));
		message.put("message", result);
		return message;
	}

	/**
	 * 所有版本数量控制展示
	 * 
	 * @param modName
	 * @param shopId
	 * @return
	 */
	public Map<String, Object> versionNumShow(String modName, Integer shopId) {
		ShopRecord shop = saas().shop.getShopById(shopId);
		VersionConfig config = this.mergeVersion(shopId);

		ShopVersionRecord version = this.getVersionByLevel(shop.getShopType());

		Map<String, Object> shopMap = new HashMap<String, Object>(10);
		shopMap.put("version_name", version.getVersionName());
		shopMap.put("num", getConfigNumber(config, modName));

		List<Map<String, Object>> allVersions = this.getAllVersion().intoMaps();
		for (Map<String, Object> map : allVersions) {
			VersionConfig cfg = this.getVersionConfig((String) map.get("level"));
			map.put("num", getConfigNumber(cfg, modName));
		}

		Map<String, Object> result = new HashMap<String, Object>(2);
		result.put("self", shopMap);
		result.put("all", allVersions);

		return result;
	}

	/**
	 * 根据等级获取版本配置
	 * 
	 * @param level
	 * @return
	 */
	protected VersionConfig getVersionConfig(String level) {
		ShopVersionRecord version = this.getVersionByLevel(level);
		return Util.parseJSON(version.getContent(), VersionConfig.class);
	}

	/**
	 * 得到版本配置获取对应模块的数量
	 * 
	 * @param config
	 * @param modName
	 * @return
	 */
	protected Integer getConfigNumber(VersionConfig config, String modName) {
		VersionNumberConfig m = config.numConfig;
		String[] modNames = { "picture_num", "video_num", "goods_num", "store_num", "decorate_num", "form_num",
				"picture_num_plus", "video_num_plus", "goods_num_plus", "store_num_plus", "decorate_num_plus",
				"form_num_plus",
		};
		Object[] modObjects = { m.pictureNum, m.videoNum, m.goodsNum, m.storeNum, m.decorateNum, m.formNum,
				m.pictureNumPlus, m.videoNumPlus, m.goodsNumPlus, m.storeNumPlus, m.decorateNumPlus, m.formNumPlus };
		int index = Arrays.asList(modNames).indexOf(modName);
		Integer number = -1;
		if (index != -1) {
			number = (Integer) modObjects[index];
		}
		return number;
	}

}
