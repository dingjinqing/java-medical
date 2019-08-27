package com.vpu.mp.service.pojo.shop.area;

import lombok.Data;

/**
 *	区域列表弹窗
 * @author liangchen
 * @date 2019年8月27日
 */
@Data
public class AreaSelectVo {
	
	/** 省代码 */
	private Integer provinceId;
	/** 省名称 */
	private String provinceName;
	/** 市代码 */
	private Integer cityId;
	/** 市名称 */
	private String cityName;
	/** 区县代码 */
	private Integer districtId;
	/** 区县名称 */
	private String districtName;
}
