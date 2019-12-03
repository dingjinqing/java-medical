package com.vpu.mp.service.shop.config;

import com.vpu.mp.service.pojo.shop.market.collect.CollectGiftParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *	收藏有礼开关配置
 * @author liangchen
 * @date 2019年8月20日
 */
@Service
@Slf4j
public class CollectGiftConfigService extends BaseShopConfigService{
	/**	收藏有礼K值 */
	private static final String K_COLLECT_GIFT = "collect_gift";
	
	/**
	 * 	返回收藏有礼配置信息
	 *	返回开关配置状态，默认为关
	 * @return param 收藏有礼配置信息(object)
	 */
	public CollectGiftParam collectGiftConfig() {
		CollectGiftParam param = this.getJsonObject(K_COLLECT_GIFT,CollectGiftParam.class);
		if (param == null) {
			param = new CollectGiftParam();
			this.setJsonObject(K_COLLECT_GIFT, param);
		}
		return param;
	}
	
	/**
	 *	开关控制
	 */
	public void updateStatus() {
		//	查看当前状态
		CollectGiftParam param = this.getJsonObject(K_COLLECT_GIFT,CollectGiftParam.class);
		log.info("收藏有礼开关配置对应value值："+param);
		log.info("收藏有礼开关配置对应开关状态on_off："+param.getOnOff());
		int nowStatus = param.getOnOff();
		//	根据当前状态选择开或关
		if (0==nowStatus) {
			param.setOnOff(1);
			this.setJsonObject(K_COLLECT_GIFT,param);
		} else {
			param.setOnOff(0);
			this.setJsonObject(K_COLLECT_GIFT, param);
		}
	}
	
	/**
	 *	修改收藏有礼配置信息
	 * @param param 收藏有礼配置
	 */
	
	public void updateCollectGiftConfig(CollectGiftParam param) {
		this.setJsonObject(K_COLLECT_GIFT, param);
	}
}
