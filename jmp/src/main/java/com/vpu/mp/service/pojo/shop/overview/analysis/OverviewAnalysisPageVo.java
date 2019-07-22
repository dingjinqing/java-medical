package com.vpu.mp.service.pojo.shop.overview.analysis;

import com.vpu.mp.controller.I18N;

import lombok.Data;

/**
 * 
 * @author liangchen
 * @date  2019年7月16日
 */
@Data
public class OverviewAnalysisPageVo {
	
	private String pagePath;
	 @I18N(propertiesFileName = "page")
	private String pageName;
	private Integer pageVisitPv;
	private double rate;
	
}
