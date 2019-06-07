package com.vpu.mp.service.shop.version;

import java.util.Map;

import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.saas.shop.ShopVersionService.VersionConfig;
import com.vpu.mp.service.shop.decoration.MpDecorationService;
import com.vpu.mp.service.shop.image.ImageService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class VersionService extends BaseService {

	protected ImageService image;
	protected MpDecorationService mpDecoration;

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class VersionQueryParam{
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
		if(param.configName == null || param.modName == null) {
			return null;
		}
		
		VersionConfig config = saas().shop.version.mergeVersion(this.getShopId());
		if(config == null) {
			return null;
		}
		if (param.configName.equals("main_config")) {
			if(param.subName == null) {
				return null;
			}
			return saas().shop.version.mainJudgment(param.modName, param.subName, this.getShopId());
		} else if (param.configName.equals("num_config")) {
			Map<String, Object> versionNumberMap = saas().shop.version.versionNumShow(param.modName, this.getShopId());
			Map<String, Object> self = (Map<String, Object>) versionNumberMap.get("self");

			if (param.modName.equals("picture_num")) {
				float size = image.getAllSize() / 1024 / 1024;
				self.put("use", String.format("%.2f", size));
			} else if (param.modName.equals("decorate_num")) {
				self.put("use", String.format("%d", mpDecoration.getPageCount()));
			} else if (param.modName.equals("video_num")) {
				// TODO: need implements
				//self.put("use", String.format("%d", mpDecoration.getPageCount()));
			} else if (param.modName.equals("goods_num")) {
				//self.put("use", String.format("%d", mpDecoration.getPageCount()));
			} else if (param.modName.equals("store_num")) {
				//self.put("use", String.format("%d", mpDecoration.getPageCount()));
			} else if (param.modName.equals("form_num")) {
				//self.put("use", String.format("%d", mpDecoration.getPageCount()));
			}
			return versionNumberMap;

		}
		return null;

	}
	
	public Map<String, Object> getPictureNumConfig() {
		return this.getVersionDetail(new VersionQueryParam("num_config","picture_num",null));
	}
	
	public Map<String, Object> getDecorateNumConfig() {
		return this.getVersionDetail(new VersionQueryParam("num_config","decorate_num",null));
	}
	
	public Map<String, Object> getVideoNumConfig() {
		return this.getVersionDetail(new VersionQueryParam("num_config","video_num",null));
	}
	
	public Map<String, Object> getGoodsNumConfig() {
		return this.getVersionDetail(new VersionQueryParam("num_config","goods_num",null));
	}
	
	public Map<String, Object> getStoreNumConfig() {
		return this.getVersionDetail(new VersionQueryParam("num_config","store_num",null));
	}
	
	public Map<String, Object> getFormNumConfig() {
		return this.getVersionDetail(new VersionQueryParam("num_config","form_num",null));
	}

}
