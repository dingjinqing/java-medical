package com.vpu.mp.service.shop.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.pojo.shop.config.BottomNavigatorConfig;

/**
 * 
 * @author 新国
 *
 */
public class BottomNavigatorConfigService extends BaseShopConfigService {

	/**
	 * 导航键值
	 */
	final public static String K_BOTTOM = "bottom";

	/**
	 * 获取底部导航配置
	 * @return
	 */
	public List<BottomNavigatorConfig> getBottomNavigatorConfig() {
		return this.getJsonObject(K_BOTTOM, new TypeReference<List<BottomNavigatorConfig>>() {});
	}

	/**
	 * 设置底部导航配置
	 * @param config
	 * @return
	 */
	public int setBottomNavigatorConfig(List<BottomNavigatorConfig> config) {
		return this.setJsonObject(K_BOTTOM, config);
	}
}
