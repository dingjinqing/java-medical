package com.vpu.mp.service.shop.overview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.service.ShopBaseService;

/**
 * @author liangchen
 * @date  2019年7月15日
 */
@Service

public class OverviewService extends ShopBaseService{
	@Autowired public OverviewAnalysisService overviewAnalysisService;
	@Autowired public OverviewUserAnalysisService overviewUserAnalysisService;
}
