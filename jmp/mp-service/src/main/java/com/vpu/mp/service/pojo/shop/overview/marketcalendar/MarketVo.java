package com.vpu.mp.service.pojo.shop.overview.marketcalendar;

import java.sql.Timestamp;

import lombok.Data;

/**
 * 所有营销活动的信息
 * 
 * @author zhaojianqiang 2020年4月21日下午4:26:39
 */
@Data
public class MarketVo {
	private Integer id;
	private String actName;
	private Timestamp startTime;
	private Timestamp endTime;
	/** 为1表示永久有效 */
	private Byte isPermanent = 0;

	private Byte actStatus = 3;
	/** 活动类型 */
	private String activityType;
}
