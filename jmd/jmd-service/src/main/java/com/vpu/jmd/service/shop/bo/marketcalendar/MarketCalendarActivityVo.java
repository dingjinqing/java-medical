package com.vpu.jmd.service.shop.bo.marketcalendar;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class MarketCalendarActivityVo {
	private Integer id;
	private Integer calendarId;
	private Integer sysCalActId;
	private String activityType;
	private Integer activityId;
	private Byte recommendType;
	private String recommendLink;
	private Byte delFlag;
	private Timestamp createTime;
	private Timestamp updateTime;
	private Byte isSync;
	private String recommendTitle;
}
