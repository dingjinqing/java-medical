package com.vpu.mp.service.pojo.shop.overview.useranalysis;

import java.util.Date;

import lombok.Data;

/**
 * 客户复购趋势
 * @author liangchen
 * @date 2019年7月22日
 */
@Data
public class OverviewUserAnalysisRebuyParam {

	private Integer weekNum;
	private Date startTime;
	private Date endTime;
	private Date thirdStartTime;
	private Date thirdEndTime;
	private Date secondStartTime;
	private Date secondEndTime;
	private Date firstStartTime;
	private Date firstEndTime;
	
}
