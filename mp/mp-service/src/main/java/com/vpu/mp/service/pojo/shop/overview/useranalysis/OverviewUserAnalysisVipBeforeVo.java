package com.vpu.mp.service.pojo.shop.overview.useranalysis;

import lombok.Data;

/**
 * 会员统计
 * @author liangchen
 * @date 2019年7月18日
 */
@Data
public class OverviewUserAnalysisVipBeforeVo {

	private Integer userData;
	private Integer regUserData;
	private Integer upgradeUserData;
	private Integer chargeUserData;
	
}
