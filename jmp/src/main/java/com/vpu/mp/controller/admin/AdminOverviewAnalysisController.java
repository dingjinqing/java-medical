package com.vpu.mp.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.tools.ant.taskdefs.TempFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.pojo.shop.overview.analysis.OverviewAnalysisDateParam;
import com.vpu.mp.service.pojo.shop.overview.analysis.OverviewAnalysisSelectParam;
import com.vpu.mp.service.pojo.shop.overview.analysis.OverviewAnalysisSelectVo;
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
	
	/**
	 *折线图综合查询
	 *@Param param
	 *@return
	 */
	@PostMapping("/api/admin/overview/analysis/select")
	public JsonResult getSelect(@RequestBody OverviewAnalysisSelectParam param) {
		String startTime;
		String endTime;
		//**  获得今日时间（字符串格式精确到日）*/
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		Date now = new Date();
		String dateNowStr = simpleDateFormat.format(now);
		
		String tempStartTime = param.getStartTime();
		startTime = "07".equals(tempStartTime) ? getDate(tempStartTime) : tempStartTime;
		startTime = "30".equals(tempStartTime) ? getDate(tempStartTime) : tempStartTime;
		param.setStartTime(startTime);
		System.out.println(startTime);
		
		String tempEndTime = param.getEndTime();
		endTime = StringUtils.isEmpty(tempEndTime) ? dateNowStr : tempEndTime;
		param.setEndTime(endTime);		
		
		List<OverviewAnalysisSelectVo> oASP = shop().overview.overviewAnalysisService.getSelect(param);
		
		return success(oASP);
	}
	
	public String getDate(String days) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");                
	    Calendar c = Calendar.getInstance();           
	    c.add(Calendar.DATE, - Integer.valueOf(days));           
	    Date time = c.getTime();         
	    String preDay = sdf.format(time);
	    System.out.println(preDay);
	    return preDay;
	}
	
}
