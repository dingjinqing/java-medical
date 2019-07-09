package com.vpu.mp.service.shop.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	@SuppressWarnings("unchecked")
	public List<BottomNavigatorConfig> getBottomNavigatorConfig() {
		List<BottomNavigatorConfig> result = new ArrayList<BottomNavigatorConfig>(1);
		List<Map<String,Object>> list = this.getJsonObject(K_BOTTOM, ArrayList.class);
		for(Map<String,Object> m: list) {
			BottomNavigatorConfig cfg = new BottomNavigatorConfig();
			cfg.setBtn(Util.getInteger(m.get("btn")));
			cfg.setHover(m.get("hover").toString());
			cfg.setNormal(m.get("normal").toString());
			cfg.setPage(m.get("page").toString());
			cfg.setText(m.get("text").toString());
			result.add(cfg);
		}
		return result;
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
