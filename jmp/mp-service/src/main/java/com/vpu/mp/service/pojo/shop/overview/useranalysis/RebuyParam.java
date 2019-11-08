package com.vpu.mp.service.pojo.shop.overview.useranalysis;

import lombok.Data;

import java.util.Date;

/**
 * 客户复购趋势
 * @author liangchen
 * @date 2019年7月22日
 */
@Data
public class RebuyParam {

	private Integer weekNum;
	private Date startTime;
	private Date endTime;
	private Date thirdStartTime;
	private Date thirdEndTime;
	private Date secondStartTime;
	private Date secondEndTime;
	private Date firstStartTime;
	private Date firstEndTime;
	
}
