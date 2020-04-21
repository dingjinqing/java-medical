package com.vpu.mp.service.pojo.shop.overview.marketcalendar;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 营销活动
 * 
 * @author zhaojianqiang 2020年4月20日下午3:00:41
 */
@Data
public class CalendarAct {
	@NotNull
	private String activityType;
	@NotNull
	private Integer activityId = 0;

}
