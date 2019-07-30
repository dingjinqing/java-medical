package com.vpu.mp.service.shop.config;

import com.vpu.mp.service.pojo.shop.config.ShopStyleConfig;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 
 * @author 新国
 *
 */
@Service

public class ShopStyleConfigService extends BaseShopConfigService {
	
	final public static String K_SHOP_STYLE = "shop_style";
	
	/**
	 * 获取店铺风格配置
	 * 
	 * @return
	 */
	public ShopStyleConfig getShopStyleConfig() {
		return this.getJsonObject(K_SHOP_STYLE, ShopStyleConfig.class);
	}

	/**
	 * 设置店铺风格配置
	 * 
	 * @param  config
	 * @return
	 */
	public int setShopStyleConfig(ShopStyleConfig config) {
		return this.setJsonObject(K_SHOP_STYLE, config);
	}
}
