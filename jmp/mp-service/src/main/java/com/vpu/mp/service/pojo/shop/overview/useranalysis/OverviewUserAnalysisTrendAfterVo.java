package com.vpu.mp.service.pojo.shop.overview.useranalysis;


import lombok.Data;

/**
 * 客户概况及趋势
 * @author liangchen
 * @date 2019年7月18日
 */
@Data
public class OverviewUserAnalysisTrendAfterVo {
	
	private Integer loginDataTotal;
	private Integer userDataTotal;
	private Integer orderUserDataTotal;

}
