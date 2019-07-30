package com.vpu.mp.service.pojo.shop.overview.analysis;

import lombok.Data;

/**
 * @author liangchen
 * @date  2019年7月26日
 */
@Data
public class OverviewAnalysisDayAgoVo {

	private Integer sessionCnt;
	private Integer visitPv;
	private Integer visitUv;
	private Integer visitUvNew;
	private Integer sharePv;
	private Integer shareUv;
	
}
