package com.vpu.mp.service.pojo.shop.distribution;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class DistributionStrategyVo {
	    private Integer   id;
	    private String    strategyName;
	    private Byte      strategyLevel;
	    private Timestamp startTime;
	    private Timestamp endTime;
	    private Double    fanliRatio;
	    private Byte      status;
	    private Byte      delFlag;
	    private Timestamp createTime;
}
