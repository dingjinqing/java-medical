package com.vpu.mp.service.pojo.shop.market.channel;


import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huangronggang
 * @date 2019年8月16日
 * 渠道页面数据统计 查询参数
 */
@Data
@NoArgsConstructor
public class ChannelStatisticalParam {
	/** 时间范围类型 2: 最近7天 ，3：最近30天，4，自定义时间 */
	private Byte timeType;
	/** 开始时间 */
	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date startDate;
	/** 截至时间 */
	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date endDate;
	/** 查询页面ID */
	@NotNull
	private Integer channelId;
	
}

