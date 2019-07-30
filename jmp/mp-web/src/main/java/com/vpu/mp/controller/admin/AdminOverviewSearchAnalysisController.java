package com.vpu.mp.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.overview.searchanalysis.OverviewSearchAnalysisHistoryParam;
import com.vpu.mp.service.pojo.shop.overview.searchanalysis.OverviewSearchAnalysisHistoryVo;
import com.vpu.mp.service.pojo.shop.overview.searchanalysis.OverviewSearchAnalysisHotVo;

/**
 * 搜索统计控制器
 * 
 * @author liangchen
 * @date  2019年7月15日
 */

@RestController
public class AdminOverviewSearchAnalysisController extends AdminBaseController{
	/**
	 *搜索历史统计
	 *@Param param
	 *@return
	 */
	@PostMapping("/api/admin/overview/searchanalysis/history")
	public JsonResult getSearchHistory(@RequestBody OverviewSearchAnalysisHistoryParam param) {
		String startTime="7";
		String endTime;
		//**  获得今日时间（字符串格式精确到日）*/
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		String dateNowStr = simpleDateFormat.format(now);
		
		String tempStartTime = param.getStartTime();
		startTime = tempStartTime!=null ? tempStartTime: getDate(startTime);
		startTime = "7".equals(tempStartTime) ? getDate(startTime) : startTime;
		startTime = "30".equals(tempStartTime) ? getDate(startTime) : startTime;
			
		String startTail = " 00:00:00";
		String startTimeTotal = startTime+startTail; 
		param.setStartTime(startTimeTotal);
		
		String tempEndTime = param.getEndTime();
		endTime = StringUtils.isEmpty(tempEndTime) ? dateNowStr : tempEndTime;
		String endTail = " 23:59:59";
		String endTimeTotal = endTime+endTail; 
		param.setEndTime(endTimeTotal);		
		List<OverviewSearchAnalysisHistoryVo> overviewSearchAnalysisHistoryVos = shop().overview.overviewSearchAnalysisService.getSearchHistory(param);
		
		return success(overviewSearchAnalysisHistoryVos);
		
	}
	
	
	/**
	 *计算X天前对应日期的公共方法
	 *@param days
	 *@return
	 */
	public String getDate(String days) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");                
	    Calendar c = Calendar.getInstance();           
	    c.add(Calendar.DATE, - Integer.valueOf(days));           
	    Date time = c.getTime();         
	    String preDay = sdf.format(time);
	    System.out.println("preDay"+preDay);
	    return preDay;
	}
	/**
	 *搜索热词统计
	 *@Param param
	 *@return
	 */
	@PostMapping("/api/admin/overview/searchanalysis/hot")
	public JsonResult getHotSearchHistory(@RequestBody OverviewSearchAnalysisHistoryParam param) {
		String startTime="7";
		String endTime;
		//**  获得今日时间（字符串格式精确到日）*/
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		String dateNowStr = simpleDateFormat.format(now);
		
		String tempStartTime = param.getStartTime();
		startTime = tempStartTime!=null ? tempStartTime: getDate(startTime);
		startTime = "7".equals(tempStartTime) ? getDate(startTime) : startTime;
		startTime = "30".equals(tempStartTime) ? getDate(startTime) : startTime;
		
		String startTail = " 00:00:00";
		String startTimeTotal = startTime+startTail; 
		param.setStartTime(startTimeTotal);
		
		String tempEndTime = param.getEndTime();
		endTime = StringUtils.isEmpty(tempEndTime) ? dateNowStr : tempEndTime;
		String endTail = " 23:59:59";
		String endTimeTotal = endTime+endTail; 
		param.setEndTime(endTimeTotal);		
		List<OverviewSearchAnalysisHotVo> overviewSearchAnalysisHotVos = shop().overview.overviewSearchAnalysisService.getHotSearchHistory(param);
		
		return success(overviewSearchAnalysisHotVos);
		
	}
	
}
