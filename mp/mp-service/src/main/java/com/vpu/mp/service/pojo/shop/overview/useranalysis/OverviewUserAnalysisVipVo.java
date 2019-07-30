package com.vpu.mp.service.pojo.shop.overview.useranalysis;

import lombok.Data;

/**
 * 会员统计
 * @author liangchen
 * @date 2019年7月18日
 */
@Data
public class OverviewUserAnalysisVipVo {

	private Integer userData;
	private Integer regUserData;
	private Integer upgradeUserData;
	private Integer chargeUserData;
	
	private Double userDataRate;
	private Double regUserDataRate;
	private Double upgradeUserDataRate;
	private Double chargeUserDataRate;
	
}
