package com.vpu.mp.service.shop.overview;

import com.vpu.mp.service.foundation.BaseService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @author liangchen
 * @date  2019年7月15日
 */
@Service
@Scope("prototype")
public class OverviewService extends BaseService{
	public OverviewAnalysisService overviewAnalysisService;
	public OverviewUserAnalysisService overviewUserAnalysisService;
}
