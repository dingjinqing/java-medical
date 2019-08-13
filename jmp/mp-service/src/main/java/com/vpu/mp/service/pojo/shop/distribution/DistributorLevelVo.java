package com.vpu.mp.service.pojo.shop.distribution;

import lombok.Data;

@Data
public class DistributorLevelVo {
	private Integer    id;
	private Byte       levelId;
	private String     levelName;
	private Byte       levelUpRoute;
	private Integer    inviteNumber;
	private String totalDistributionMoney;
	private String totalBuyMoney;
	private String     levelUserIds;
	private Byte       levelStatus;
}
