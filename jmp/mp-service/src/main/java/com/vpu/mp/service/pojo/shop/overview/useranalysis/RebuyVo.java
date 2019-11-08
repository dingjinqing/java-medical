package com.vpu.mp.service.pojo.shop.overview.useranalysis;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * 客户复购趋势
 * @author liangchen
 * @date 2019年7月22日
 */
@Data
@AllArgsConstructor
public class RebuyVo {
	
	private Date startTime;
	private Date endTime;
	private Double rebuyRate;
	
}
