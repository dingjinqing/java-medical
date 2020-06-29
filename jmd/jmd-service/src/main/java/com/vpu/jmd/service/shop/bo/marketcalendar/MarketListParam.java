package com.vpu.jmd.service.shop.bo.marketcalendar;

import lombok.Data;

import java.util.List;

/**
 *
 * @author zhaojianqiang
 * 2020年4月23日下午2:48:36
 */
@Data
public class MarketListParam {
	private List<Integer> activityIdList;
	private Integer calendarId;

}
