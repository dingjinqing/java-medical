package com.vpu.mp.service.pojo.shop.distribution;

import lombok.Data;

@Data
public class DistributorLevelVo {
	public Integer    id;
	public Byte       levelId;
	public String     levelName;
	public Byte       levelUpRoute;
	public Integer    inviteNumber;
	public String totalDistributionMoney;
	public String totalBuyMoney;
	public String     levelUserIds;
	public Byte       levelStatus;
}
