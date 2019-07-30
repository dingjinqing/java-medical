package com.vpu.mp.service.pojo.shop.distribution;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DistributorLevelVo {
	public Integer    id;
	public Byte       levelId;
	public String     levelName;
	public Byte       levelUpRoute;
	public Integer    inviteNumber;
	public BigDecimal totalDistributionMoney;
	public BigDecimal totalBuyMoney;
	public String     levelUserIds;
	public Byte       levelStatus;
}
