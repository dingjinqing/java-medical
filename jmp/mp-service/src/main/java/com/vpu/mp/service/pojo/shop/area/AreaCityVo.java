package com.vpu.mp.service.pojo.shop.area;

import java.util.List;

import lombok.Data;

/**
 *	区域列表弹窗
 * @author liangchen
 * @date 2019年8月27日
 */
@Data
public class AreaCityVo {
	/** 市代码 */
	private Integer cityId;
	/** 市名称 */
	private String cityName;
	/** 区县代码及名称 */
	public List<AreaDistrictVo> areaDistrict;
}
