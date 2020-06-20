package com.vpu.mp.service.pojo.shop.distribution;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DistributorLevelVo {
	private Integer    id;
	private Byte       levelId;
	private String     levelName;
	private Byte       levelUpRoute;
	private Integer    inviteNumber;
	private BigDecimal totalDistributionMoney;
	private BigDecimal totalBuyMoney;
	private String     levelUserIds;
	private Byte       levelStatus;
}
