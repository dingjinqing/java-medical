package com.vpu.mp.service.index;

import static com.vpu.mp.db.main.tables.ShopFreeExperience.SHOP_FREE_EXPERIENCE;

import org.springframework.stereotype.Component;

import com.vpu.mp.db.main.tables.records.ShopFreeExperienceRecord;
import com.vpu.mp.service.foundation.BaseService;

/**
 * 
 * @author 黄壮壮
 * 2019-06-25 
 * 首页处理逻辑
 */
@Component
public class IndexService extends BaseService {

	/**
	 * 查询手机号是否注册成功
	 * @param mobile
	 * @return boolean
	 */
	public boolean verifyIsExist(String mobile) {
		ShopFreeExperienceRecord record = dm.db()
		.selectFrom(SHOP_FREE_EXPERIENCE)
		.where(SHOP_FREE_EXPERIENCE.MOBILE.eq(mobile))
		.fetchAny();
		
		return !(record==null);
	}

	/**
	 * 将用户信息持久化
	 * @param username
	 * @param mobile
	 */
	public int insertUserInfo(ShopFreeExperienceRecord shopFreeExperienceRecord) {
		ShopFreeExperienceRecord shop = shopFreeExperienceRecord;
		int i = dm.db()
			.insertInto(SHOP_FREE_EXPERIENCE,SHOP_FREE_EXPERIENCE.CONTACT, SHOP_FREE_EXPERIENCE.MOBILE,SHOP_FREE_EXPERIENCE.SOURCE)
			.values(shop.getContact(), shop.getMobile(),shop.getSource())
			.execute();
		return i;
	}
}
