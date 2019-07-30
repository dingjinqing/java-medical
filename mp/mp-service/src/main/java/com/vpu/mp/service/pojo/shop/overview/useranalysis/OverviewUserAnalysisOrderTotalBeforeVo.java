package com.vpu.mp.service.pojo.shop.overview.useranalysis;

import lombok.Data;

/**
 * 成交用户分析
 * @author liangchen
 * @date 2019年7月19日
 */
@Data
public class OverviewUserAnalysisOrderTotalBeforeVo {
	
	private Integer loginDataTotal;	
	private Integer userDataTotal;
	private Integer orderUserDataTotal;
	private Integer oldOrderUserDataTotal;
	private Integer newOrderUserDataTotal;
	
	private Integer totalPaidMoneyTotal;
	private Double oldPaidMoneyTotal;
	private Double newPaidMoneyTotal;
	
}
