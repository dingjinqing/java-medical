package com.vpu.mp.service.pojo.shop.distribution;

import lombok.Data;

@Data
public class DistributorLevelParam {
	public Integer id;
	public Byte levelId;
	public String levelName;
	public Integer levelUpRoute;
	public Integer inviteNumber;
	public String totalDistributionMoney;
	public String totalBuyMoney;
	public String levelUserIds;
}
