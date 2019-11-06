package com.vpu.mp.service.pojo.shop.overview.useranalysis;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author liangchen
 * @date 2019年7月18日
 */
@Data
public class OverviewUserAnalysisDateParam {
	/** 时间段 1:最近一天，7:最近一周，30:最近一月 */
	@NotNull
	private String lastNum;
	/** 当前时间段的开始时间 */
	private Date startTime;
	/** 当前时间段的结束时间 */
	private Date endTime;
}
