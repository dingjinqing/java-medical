package com.vpu.jmd.service.shop.bo.marketcalendar;

import lombok.Data;

import javax.validation.constraints.NotNull;

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

	/** 编辑时候传的id */
	private Integer calActId = 0;
	/** 编辑时候传的 是否已删除：0否，1是 */
	private Byte delFlag = 0;

}
