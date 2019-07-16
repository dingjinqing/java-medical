package com.vpu.mp.service.pojo.shop.overview.analysis;

import lombok.Data;

/**
 * @author liangchen
 * @date 2019年7月15日
 */
@Data
public class OverviewAnalysisSelectVo {
	
	private String refDate;
	private Integer totalSessionCnt;
	private Integer sessionCnt;
	private Integer visitPv;
	private Integer visitUv;
	private Integer visitUvNew;
	private Integer sharePv;
	private Integer shareUv;
	private Float stayTimeUv;
	private Float stayTimeSession;

	
	
}
