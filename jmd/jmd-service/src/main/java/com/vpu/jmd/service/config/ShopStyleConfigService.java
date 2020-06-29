package com.vpu.jmd.service.config;

import com.vpu.mp.service.foundation.jedis.JedisKeyConstant;
import com.vpu.mp.service.foundation.jedis.JedisManager;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.ShopStyleConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 新国
 */
@Service

public class ShopStyleConfigService extends BaseShopConfigService {
    @Autowired
    JedisManager jedisManager;

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
		int res = this.setJsonObject(K_SHOP_STYLE, config);
        if (res > 0) {
            //缓存
            jedisManager.set(JedisKeyConstant.CONFIG_SHOP_STYLE + getShopId(), Util.toJson(config), ShopCommonConfigCacheService.MAX_TIME_OUT);
        }
        return res;
    }
}
