package com.vpu.mp.service.pojo.shop.overview.analysis;

import lombok.Data;

/**
 * @author liangchen
 * @date  2019年7月15日
 */
@Data
public class OverviewAnalysisYesterdayVo {

	private Integer sessionCnt;
	private Integer visitPv;
	private Integer visitUv;
	private Integer visitUvNew;
	private Integer sharePv;
	private Integer shareUv;
	
	private double sessionCntDayRate;
	private double sessionCntWeekRate;
	private double sessionCntMonthRate;
	private double visitPvDayRate;
	private double visitPvWeekRate;
	private double visitPvMonthRate;
	private double visitUvDayRate;
	private double visitUvWeekRate;
	private double visitUvMonthRate;
	private double visitUvNewDayRate;
	private double visitUvNewWeekRate;
	private double visitUvNewMonthRate;
	private double sharePvDayRate;
	private double sharePvWeekRate;
	private double sharePvMonthRate;
	private double shareUvDayRate;
	private double shareUvWeekRate;
	private double shareUvMonthRate;
	
}
