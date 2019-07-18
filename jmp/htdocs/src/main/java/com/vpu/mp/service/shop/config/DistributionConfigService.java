package com.vpu.mp.service.shop.config;

import com.vpu.mp.service.pojo.shop.config.distribution.DistributionParam;
import com.vpu.mp.service.shop.ShopApplication;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 分销配置
 * @author 常乐
 * 2019年7月17日
 */
@Service
@Scope("prototype")
public class DistributionConfigService extends BaseShopConfigService{
	/**
	 * 分销开关
	 */
	final public static String K_FANLI = "fanli";
	
	
	/**
	 * 获取返利配置
	 * 
	 * @return
	 */
	public DistributionParam getDistributionCfg() {
		return this.getJsonObject(K_FANLI, DistributionParam.class);
	}

	/**
	 * 设置返利配置
	 * 
	 * @param  config
	 * @return
	 */
	public int setDistributionCfg(DistributionParam config) {
		return this.setJsonObject(K_FANLI, config);
	}
}
