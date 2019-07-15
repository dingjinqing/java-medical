package com.vpu.mp.service.shop.goods;

import static com.vpu.mp.db.shop.Tables.BRAND_CLASSIFY;

import java.util.List;

import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.DelFlag;
import com.vpu.mp.service.pojo.shop.goods.brand.BrandVo;

/**
 * 品牌
 * @author 常乐
 * 2019年7月15日
 */
public class BrandService extends BaseService{

	/**
	 * 品牌分类列表
	 * @return
	 */
	public List<BrandVo> getBrandClassifyList() {
		List<BrandVo> list = db().select(BRAND_CLASSIFY.CLASSIFY_ID,BRAND_CLASSIFY.CLASSIFY_NAME)
				.from(BRAND_CLASSIFY)
				.where(BRAND_CLASSIFY.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
				.fetch().into(BrandVo.class);
		return list;
	}
}
