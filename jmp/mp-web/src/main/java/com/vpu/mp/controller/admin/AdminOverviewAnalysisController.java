package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.overview.analysis.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 概况统计控制器
 * 
 * @author liangchen
 * @date  2019年7月15日
 */

@RestController
@RequestMapping("/api/admin/overview/analysis")
public class AdminOverviewAnalysisController extends AdminBaseController{
	
	/**
	 * 昨日概况统计
	 * @return 基础信息和变化率
	 */
	@GetMapping("/yesterday")
	public JsonResult yesterdayAnalysis() {
		
		List<YesterdayStatisticsVo> yesterdayStatisticsVos = shop().overview.overviewAnalysisService.yesterdayAnalysis();
		
		return success(yesterdayStatisticsVos);
	}
	
	/**
	 *折线图综合查询
	 *@Param param
	 *@return
	 */
	@PostMapping("/select")
	public JsonResult getSelect(@RequestBody VisitTrendParam param) {

		VisitTrendVo visitTrendVo = shop().overview.overviewAnalysisService.getVisitTrend(param);
		
		return success(visitTrendVo);
	}
	
	public String getDate(String days) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");                
	    Calendar c = Calendar.getInstance();           
	    c.add(Calendar.DATE, - Integer.valueOf(days));           
	    Date time = c.getTime();         
	    String preDay = sdf.format(time);
	    System.out.println("preDay"+preDay);
	    return preDay;
	}
	
	/**
	 *页面访问数量查询
	 *@Param param
	 *@return
	 */
	@PostMapping("/pagelist")
	public JsonResult getPageInfo(@RequestBody OverviewAnalysisPageParam param) {
		String startTime="7";
		String endTime;
		//**  获得今日时间（字符串格式精确到日）*/
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		Date now = new Date();
		String dateNowStr = simpleDateFormat.format(now);
		
		String tempStartTime = param.getStartTime();
		startTime = tempStartTime!=null ? tempStartTime: getDate(startTime);
		startTime = "7".equals(tempStartTime) ? getDate(startTime) : startTime;
		startTime = "30".equals(tempStartTime) ? getDate(startTime) : startTime;
		
		param.setStartTime(startTime);
		System.out.println(startTime);
		
		String tempEndTime = param.getEndTime();
		endTime = StringUtils.isEmpty(tempEndTime) ? dateNowStr : tempEndTime;
		param.setEndTime(endTime);		
		
		OverviewAnalysisPageVo overviewAnalysisPageVos = shop().overview.overviewAnalysisService.getPageInfo(param);
		
		return i18nSuccess(overviewAnalysisPageVos);
	}
}
