package com.vpu.mp.service.pojo.shop.distribution;

import lombok.Data;

@Data
public class DistributorLevelParam {
	private Integer id;
	private Byte levelId;
	private String levelName;
	private Integer levelUpRoute;
	private Integer inviteNumber;
	private String totalDistributionMoney;
	private String totalBuyMoney;
	private String levelUserIds;
	private Integer users;
	private Integer levelStatus;
}
