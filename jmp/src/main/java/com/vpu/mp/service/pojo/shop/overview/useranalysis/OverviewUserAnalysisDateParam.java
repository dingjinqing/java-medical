package com.vpu.mp.service.pojo.shop.overview.useranalysis;

import java.util.Date;

import lombok.Data;

/**
 * @author liangchen
 * @date 2019年7月18日
 */
@Data
public class OverviewUserAnalysisDateParam {
	
	private String lastNum;
	private Date startTime;
	private Date endTime;
}
