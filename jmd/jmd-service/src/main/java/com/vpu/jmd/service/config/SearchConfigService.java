package com.vpu.jmd.service.config;

import com.vpu.mp.service.pojo.shop.config.SearchConfig;
import org.springframework.stereotype.Service;

/**
 *
 * @author 新国
 *
 */
@Service

public class SearchConfigService extends BaseShopConfigService {

	final public static String K_SEARCH_CONFIG = "search_config";

	/**
	 * 获取搜索配置
	 *
	 * @return
	 */
	public SearchConfig getSearchConfig() {
		return this.getJsonObject(K_SEARCH_CONFIG, SearchConfig.class);
	}

	/**
	 * 设置搜索配置
	 *
	 * @param  config
	 * @return
	 */
	public int setSearchConfig(SearchConfig config) {
		return this.setJsonObject(K_SEARCH_CONFIG, config);
	}
}
