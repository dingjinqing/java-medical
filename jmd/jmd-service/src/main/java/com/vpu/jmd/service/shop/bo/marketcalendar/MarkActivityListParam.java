package com.vpu.jmd.service.shop.bo.marketcalendar;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 查询可用的营销活动
 *
 * @author zhaojianqiang 2020年4月23日下午3:31:47
 */
@Data
public class MarkActivityListParam extends MarketParam {
	@NotNull
	@NotEmpty
	private String activityType;
}
