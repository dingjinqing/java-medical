package com.vpu.mp.controller.admin;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.pojo.shop.overview.analysis.OverviewAnalysisDateParam;
import com.vpu.mp.service.pojo.shop.overview.analysis.OverviewAnalysisYesterdayVo;

/**
 * 概况统计控制器
 * 
 * @author liangchen
 * @date  2019年7月15日
 */

@RestController
public class AdminOverviewAnalysisController extends AdminBaseController{
	
	/**
	 * 昨日概况统计
	 * @param param
	 * @return JsonResult
	 */
	@PostMapping("/api/admin/overview/analysis/yesterday")
	public JsonResult yesterdayAnalysis(@RequestBody OverviewAnalysisDateParam param) {
		
		List<OverviewAnalysisYesterdayVo> overviewAnalysisYesterdayVos = shop().overview.overviewAnalysisService.yesterdayAnalysis(param);
		
		return success(overviewAnalysisYesterdayVos);
	}
}
