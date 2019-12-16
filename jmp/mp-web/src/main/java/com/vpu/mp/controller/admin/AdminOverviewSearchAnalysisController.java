package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.overview.searchanalysis.SearchHistoryParam;
import com.vpu.mp.service.pojo.shop.overview.searchanalysis.SearchHistoryVo;
import com.vpu.mp.service.pojo.shop.overview.searchanalysis.SearchHotWordsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 搜索统计控制器
 * 
 * @author liangchen
 * @date  2019年7月15日
 */

@RestController
@RequestMapping("/api/admin/overview/search/analysis")
public class AdminOverviewSearchAnalysisController extends AdminBaseController{
	/**
	 *搜索历史统计
	 *@Param param
	 *@return
	 */
	@PostMapping("/history")
	public JsonResult getSearchHistory(@RequestBody SearchHistoryParam param) {
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
		List<SearchHistoryVo> searchHistoryVos = shop().overview.searchAnalysisService.getSearchHistory(param);
		
		return success(searchHistoryVos);
		
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
	@PostMapping("/hot")
	public JsonResult getHotSearchHistory(@RequestBody SearchHistoryParam param) {
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
		List<SearchHotWordsVo> searchHotWordsVos = shop().overview.searchAnalysisService.getHotSearchHistory(param);
		
		return success(searchHotWordsVos);
		
	}
	
}
