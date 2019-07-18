package com.vpu.mp.service.pojo.shop.distribution;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class DistributionStrategyParam {
	    private Integer   id;
	    private String    strategyName;
	    private Byte      strategyLevel;
	    private Timestamp startTime;
	    private Timestamp endTime;
	    private Byte      selfPurchase;
	    private Byte      costProtection;
	    private Double    fanliRatio;
	    private Double    rebateRatio;
	    private Double    fanliRatio_2;
	    private Double    rebateRatio_2;
	    private Double    fanliRatio_3;
	    private Double    rebateRatio_3;
	    private Double    fanliRatio_4;
	    private Double    rebateRatio_4;
	    private Double    fanliRatio_5;
	    private Double    rebateRatio_5;
	    private Byte      recommendType;
	    private String    recommendGoodsId;
	    private String    recommendCatId;
	    private Byte      status;
	    private Byte      delFlag;
	    private Timestamp delTime;
	    private String    recommendSortId;
	    private Byte      sendCoupon;
	    private Timestamp createTime;
	    private Timestamp updateTime;
	    private Integer   nav;
	    /**
	     * 	分页信息
	     */
	    public int currentPage;
	    public int pageRows;
}
