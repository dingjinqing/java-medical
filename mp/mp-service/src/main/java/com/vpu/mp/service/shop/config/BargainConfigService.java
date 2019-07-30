package com.vpu.mp.service.shop.config;

import org.springframework.stereotype.Service;

/**
 * @author 王兵兵
 *
 * 2019年7月25日
 */
@Service
public class BargainConfigService extends BaseShopConfigService{

	/**
	 * 砍价活动，每个被邀请的用户，单日可帮助砍价的次数，空或0时不限制
	 */
	final public static String K_DAILY_CUT_TIMES= "daily_cut_times";
	
	/**
	 * 取单日可帮助砍价的次数
	 * @return
	 */
	public Integer getDailyCutTimes() {
		return this.get(K_DAILY_CUT_TIMES, Integer.class, 0);
	}
	
	/**
	 * 设置取单日可帮助砍价的次数
	 * @return
	 */
	public int setDailyCutTimes(int value) {
		assert(value >= 0);
		return this.set(K_DAILY_CUT_TIMES,  value, Integer.class);
	}
}
