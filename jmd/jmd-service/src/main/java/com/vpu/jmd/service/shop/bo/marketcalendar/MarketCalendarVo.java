package com.vpu.jmd.service.shop.bo.marketcalendar;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
public class MarketCalendarVo {
	private Integer id;
	private String eventName;
	private Date eventTime;
	private String eventDesc;
	private Byte pubFlag;
	private Byte delFlag;
	private Byte source;
	private Integer sourceId;
	private Timestamp createTime;
	private Timestamp updateTime;
	// 1未开始，2进行中，3失效，4已结束 CalendarAction
	private Byte eventStatus;
	// 还有多久开始
	private Integer downTime = 0;
}
