package com.vpu.mp.service.pojo.shop.overview.useranalysis;

import lombok.Data;

/**
 * 用户活跃
 * @author liangchen
 * @date 2019年7月18日
 */
@Data
public class OverviewUserAnalysisActiveTotalVo {

	private Integer loginDataTotal;
	private Integer couponDataTotal;
	private Integer cartDataTotal;
	private Integer orderUserDataTotal;
	private Integer userDataTotal;
	
}
