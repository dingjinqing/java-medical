package com.vpu.mp.service.pojo.shop.market.channel;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huangronggang
 * @date 2019年8月16日
 */
@Data
@NoArgsConstructor
public class ChannelStatisticalAllVo {
	/** key为日期，value 为当日访问量 */
	private Map<Date,ChannelStatisticalDayInfo> allInfo = new HashMap<>();
	/** 渠道页面 */
	private List<ChannelPageInfo> channelName;
}


