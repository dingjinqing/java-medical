package com.vpu.mp.service.pojo.shop.overview.commodity;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author:liufei
 * @Date:2019/7/22
 * @Description:
 */
@Getter
@Setter
public class ProductEffectParam extends ProductOverviewParam {
	/** 排序字段 */
	public String orderByField;
	/** 排序类型 */
	public String orderByType;
	public int currentPage;
	public int pageRows;
}
