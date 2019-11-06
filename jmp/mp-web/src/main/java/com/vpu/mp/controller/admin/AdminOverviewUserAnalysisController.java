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
	public JsonResult getTrend(@RequestBody OverviewUserAnalysisDateParam param) {
		//通过引用传递 得到段时间对应的开始日期和结束日期
	    getDateParam(param);
	    //得到客户概况和变化趋势
		OverviewUserAnalysisTrendTotalVo result = shop().overview.overviewUserAnalysisService.getTrend(param);
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
	public JsonResult getActive(@RequestBody OverviewUserAnalysisDateParam param) {
        //通过引用传递 得到段时间对应的开始日期和结束日期
        getDateParam(param);
        //得到用户活跃情况
	    OverviewUserAnalysisActiveTotalVo result = shop().overview.overviewUserAnalysisService
				.getActive(param);
        //返回计算结果
		return success(result);

	}

	/**
	 * 会员统计
	 * 
	 * @Param param
	 * @return
	 */
	@PostMapping("/vip")
	public JsonResult getVip(@RequestBody OverviewUserAnalysisDateParam param) {
        //通过引用传递 得到段时间对应的开始日期和结束日期
        getDateParam(param);
        //得到会员统计情况
		OverviewUserAnalysisVipVo result = shop().overview.overviewUserAnalysisService
				.getVip(param);

		return success(result);

	}

	/**
	 * 成交用户分析
	 * 
	 * @Param param
	 * @return
	 */
	@PostMapping("/order")
	public JsonResult getOrder(@RequestBody OverviewUserAnalysisDateParam param) {
        //通过引用传递 得到段时间对应的开始日期和结束日期
        getDateParam(param);
        //得到成交用户情况
		List<OverviewUserAnalysisOrderVo> overviewUserAnalysisOrderVos = shop().overview.overviewUserAnalysisService
				.getOrder(param);

		return success((Object)overviewUserAnalysisOrderVos);

	}

	/**
	 * 客户复购趋势
	 * 
	 * @Param param
	 * @return
	 */
	@PostMapping("/rebuy")
	public JsonResult getRebuyTrend(@RequestBody OverviewUserAnalysisRebuyParam param) {
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
		List<OverviewUserAnalysisRebuyVo> overviewUserAnalysisRebuyVos = shop().overview.overviewUserAnalysisService
				.getRebuyTrend(param);

		return success(overviewUserAnalysisRebuyVos);

	}

    /**
     * 通用方法-得到N天前的日期
     * @param days N天前
     * @return 对应日期(String型)
     */
    private String getDate(String days) {
        //设置日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();
        //Calendar.DATE=5 代表对日期操作，减去N，得到N天前
        c.add(Calendar.DATE, -Integer.parseInt(days));
        //转换成Date型
        Date time = c.getTime();
        //转换并返回指定时间格式的String型时间
        return sdf.format(time);
    }

    /**
     * 通用方法-得到段时间对应的开始日期和结束日期
     *
     * @param param 时间段 1:最近一天，7:最近一周，30:最近一月
     */
    private void getDateParam(OverviewUserAnalysisDateParam param){
        try {
            //得到昨天日期(date型)
            Date yesterday = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
            //默认查询最近一天数据
            String lastNum = "1";
            //N天前为开始日期
            String tempStartTime = param.getLastNum();
            //判断N天前为几天前，最后得到一个String类型的yyyyMMdd格式的日期
            lastNum = "1".equals(tempStartTime) ? getDate("1") : lastNum;
            lastNum = "7".equals(tempStartTime) ? getDate("7") : lastNum;
            lastNum = "30".equals(tempStartTime) ? getDate("30") : lastNum;
            //将String日期转为yyyyMMdd格式的Date型日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startTime = sdf.parse(lastNum);
            //将设置好的Date型日期放入param，进行db操作
            param.setStartTime(startTime);
            param.setEndTime(yesterday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
	private static void backSeven(Calendar calendar) {
	    int day = calendar.get(Calendar.DATE);
	    calendar.set(Calendar.DATE, day - 7);
	  }
}
