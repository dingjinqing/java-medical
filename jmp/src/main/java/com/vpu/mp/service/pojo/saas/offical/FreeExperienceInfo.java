package com.vpu.mp.service.pojo.saas.offical;

import static com.vpu.mp.db.main.tables.ShopFreeExperience.SHOP_FREE_EXPERIENCE;

import lombok.Data;

/**
 * @author 黄壮壮
 * 2019-07-01 18:03
 */

@Data
public class FreeExperienceInfo {
	public String key;
	public Integer feId;
	public String desc;
	public Integer provinceId;
}
