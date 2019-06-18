package com.vpu.mp.service.shop.version;

import java.util.Map;

import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.saas.shop.ShopVersionService.VersionConfig;
import com.vpu.mp.service.shop.decoration.MpDecorationService;
import com.vpu.mp.service.shop.image.ImageService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author lixinguo
 *
 */
public class VersionService extends BaseService {

	protected ImageService image;
	protected MpDecorationService mpDecoration;

	String mainConfig = "main_config";
	String numConfig = "num_config";
	String pictureNum = "picture_num";
	String decorateNum = "decorate_num";
	String videoNum = "video_num";
	String goodsNum = "goods_num";
	String storeNum = "store_num";
	String formNum = "form_num";

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class VersionQueryParam {
		public String configName;
		public String modName;
		public String subName;
	};

	/**
	 * 得到店铺等级对应的数量和权限
	 * 
	 * @param configName
	 * @param modName
	 * @param subName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getVersionDetail(VersionQueryParam param) {
		if (param.configName == null || param.modName == null) {
			return null;
		}

		VersionConfig config = saas().shop.version.mergeVersion(this.getShopId());
		if (config == null) {
			return null;
		}
		if (param.configName.equals(mainConfig)) {
			if (param.subName == null) {
				return null;
			}
			return saas().shop.version.mainJudgment(param.modName, param.subName, this.getShopId());
		} else if (param.configName.equals(numConfig)) {
			Map<String, Object> versionNumberMap = saas().shop.version.versionNumShow(param.modName, this.getShopId());
			Map<String, Object> self = (Map<String, Object>) versionNumberMap.get("self");

			if (param.modName.equals(pictureNum)) {
				float size = image.getAllSize() / 1024 / 1024;
				self.put("use", String.format("%.2f", size));
			} else if (param.modName.equals(decorateNum)) {
				self.put("use", String.format("%d", mpDecoration.getPageCount()));
			} else if (param.modName.equals(videoNum)) {
				// TODO: need implements
				// self.put("use", String.format("%d", mpDecoration.getPageCount()));
			} else if (param.modName.equals(goodsNum)) {
				// self.put("use", String.format("%d", mpDecoration.getPageCount()));
			} else if (param.modName.equals(storeNum)) {
				// self.put("use", String.format("%d", mpDecoration.getPageCount()));
			} else if (param.modName.equals(formNum)) {
				// self.put("use", String.format("%d", mpDecoration.getPageCount()));
			}
			return versionNumberMap;

		}
		return null;

	}

	public Map<String, Object> getPictureNumConfig() {
		return this.getVersionDetail(new VersionQueryParam(numConfig, pictureNum, null));
	}

	public Map<String, Object> getDecorateNumConfig() {
		return this.getVersionDetail(new VersionQueryParam(numConfig, decorateNum, null));
	}

	public Map<String, Object> getVideoNumConfig() {
		return this.getVersionDetail(new VersionQueryParam(numConfig, videoNum, null));
	}

	public Map<String, Object> getGoodsNumConfig() {
		return this.getVersionDetail(new VersionQueryParam(numConfig, goodsNum, null));
	}

	public Map<String, Object> getStoreNumConfig() {
		return this.getVersionDetail(new VersionQueryParam(numConfig, storeNum, null));
	}

	public Map<String, Object> getFormNumConfig() {
		return this.getVersionDetail(new VersionQueryParam(numConfig, formNum, null));
	}

}
