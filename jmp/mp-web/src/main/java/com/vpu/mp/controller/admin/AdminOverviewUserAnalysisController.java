package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.overview.useranalysis.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 用户统计控制器
 * 
 * @author liangchen
 * @date 2019年7月18日
 */
@RestController
@RequestMapping("/api/admin/overview/user/analysis")
public class AdminOverviewUserAnalysisController extends AdminBaseController {

	/**
	 * 客户概况及趋势
	 * 
	 * @param  param 查看最近N天的数据(默认N=1) 1：一天，7：一周，30：一个月
	 * @return 相关数量及趋势
	 */
	@PostMapping("/trend")
	public JsonResult getTrend(@RequestBody DateParam param) {
	    //得到客户概况和变化趋势
		TrendVo result = shop().overview.overviewUserAnalysisService.getTrend(param);
        //返回计算结果
		return success(result);

	}

	/**
	 * 用户活跃
	 * 
	 * @param param 查看最近N天的数据(默认N=1) 1：一天，7：一周，30：一个月
	 * @return 不同类型用户数据
	 */
	@PostMapping("/active")
	public JsonResult getActive(@RequestBody DateParam param) {
        //得到用户活跃情况
	    ActiveTotalVo result = shop().overview.overviewUserAnalysisService
				.getActive(param);
        //返回计算结果
		return success(result);

	}

	/**
	 * 会员统计
	 * 
	 * @Param param 查看最近N天的数据(默认N=1) 1：一天，7：一周，30：一个月
	 * @return 会员数变化率
	 */
	@PostMapping("/vip")
	public JsonResult getVip(@RequestBody DateParam param) {
        //得到会员统计情况
		VipVo result = shop().overview.overviewUserAnalysisService
				.getVip(param);

		return success(result);

	}

	/**
	 * 成交用户分析
	 * 
	 * @Param param 查看最近N天的数据(默认N=1) 1：一天，7：一周，30：一个月
	 * @return 成交用户变化率占比等
	 */
	@PostMapping("/order")
	public JsonResult getOrder(@RequestBody DateParam param) {
        //得到成交用户情况
		OrderVo result = shop().overview.overviewUserAnalysisService
				.getOrder(param);
		return success(result);
	}

	/**
	 * 客户复购趋势
	 * 
	 * @Param param
	 * @return
	 */
	@PostMapping("/rebuy")
	public JsonResult getRebuyTrend(@RequestBody RebuyParam param) {
		try {
        Calendar calendar = Calendar.getInstance();
        /** 设置星期一为一周开始的第一天 */
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        /** 获得当前的时间戳 */
        calendar.setTimeInMillis(System.currentTimeMillis());
        /** 获得当前的年 */
        int weekYear = calendar.get(Calendar.YEAR);
        /** 获得当前日期属于今年的第几周 */
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        int weekNum = param.getWeekNum().equals(null)? weekOfYear : param.getWeekNum();
        System.out.println("第几周："+weekNum);
        
        /**  获得指定年的第几周的开始日期 */
        calendar.setWeekDate(weekYear, weekNum, 2);
        /**  创建日期的时间该周的第一天 */
        long start = calendar.getTime().getTime();
        /**  获得指定年的第几周的结束日期 */
        calendar.setWeekDate(weekYear, weekNum, 1);
        long end = calendar.getTime().getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String startTimeString = simpleDateFormat.format(start);
        String endTimeString = simpleDateFormat.format(end);
        Date startTime = simpleDateFormat.parse(startTimeString);
        Date endTime = simpleDateFormat.parse(endTimeString);
        param.setStartTime(startTime);
        param.setEndTime(endTime);
        
        calendar.setTime(startTime);
        backSeven(calendar);
        String thirdStartTimeString = simpleDateFormat.format(calendar.getTime());
        calendar.setTime(endTime);
        backSeven(calendar);
        String thirdEndTimeString = simpleDateFormat.format(calendar.getTime());
        Date thirdStartTime = simpleDateFormat.parse(thirdStartTimeString);
        Date thirdEndTime = simpleDateFormat.parse(thirdEndTimeString);
        param.setThirdStartTime(thirdStartTime);
        param.setThirdEndTime(thirdEndTime);
        
        calendar.setTime(thirdStartTime);
        backSeven(calendar);
        String secondStartTimeString = simpleDateFormat.format(calendar.getTime());
        calendar.setTime(thirdEndTime);
        backSeven(calendar);
        String secondEndTimeString = simpleDateFormat.format(calendar.getTime());
        Date secondStartTime = simpleDateFormat.parse(secondStartTimeString);
        Date secondEndTime = simpleDateFormat.parse(secondEndTimeString);
        param.setSecondStartTime(secondStartTime);
        param.setSecondEndTime(secondEndTime);
        
        calendar.setTime(secondStartTime);
        backSeven(calendar);
        String firstStartTimeString = simpleDateFormat.format(calendar.getTime());
        calendar.setTime(secondEndTime);
        backSeven(calendar);
        String firstEndTimeString = simpleDateFormat.format(calendar.getTime());
        Date firstStartTime = simpleDateFormat.parse(firstStartTimeString);
        Date firstEndTime = simpleDateFormat.parse(firstEndTimeString);
        param.setFirstStartTime(firstStartTime);
        param.setFirstEndTime(firstEndTime);
        
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<RebuyVo> rebuyVos = shop().overview.overviewUserAnalysisService
				.getRebuyTrend(param);

		return success(rebuyVos);

	}

	private static void backSeven(Calendar calendar) {
	    int day = calendar.get(Calendar.DATE);
	    calendar.set(Calendar.DATE, day - 7);
	  }
}
