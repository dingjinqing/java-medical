package com.vpu.mp.controller.admin;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.overview.marketcalendar.CalendarAction;
import com.vpu.mp.service.pojo.shop.overview.marketcalendar.MarketListData;
import com.vpu.mp.service.pojo.shop.overview.marketcalendar.MarketcalendarParam;

/**
 * 营销日历相关
 * 
 * @author zhaojianqiang 2020年4月20日下午2:47:07
 */
@RestController
public class AdminMarketcalendarController extends AdminBaseController {

	/**
	 * 新建和编辑
	 * 
	 * @param param
	 * @return
	 */
	@PostMapping(value = "/api/admin/calendar/market/up")
	public JsonResult marketCalendarUp(@RequestBody MarketcalendarParam param) {
		String act = param.getAct();
		if (act.equals(CalendarAction.ADD)) {
			logger().info("新建");
			boolean addCalendar = shop().calendarService.addCalendar(param);
			if (addCalendar) {
				return success();
			}
		}
		if (act.equals(CalendarAction.EDIT)) {
			logger().info("更新");
			if (param.getCalendarId() == null) {
				// id不能为0
				return fail();
			}
			shop().calendarService.edit(param);
		}
		return fail();
	}

	/**
	 * 删除
	 * 
	 * @param calendarId
	 * @return
	 */
	@GetMapping(value = "/api/admin/calendar/market/del/{calendarId}")
	public JsonResult markCalendarDel(@PathVariable Integer calendarId) {
		boolean del = shop().calendarService.del(calendarId);
		if (del) {
			return success();
		}
		return fail();
	}
	
	/**
	 * 营销日历列表
	 * @param year
	 * @return
	 */
	@GetMapping(value = "/api/admin/calendar/list/{year}")
	public JsonResult calendarList(@PathVariable String year) {
		return success(shop().calendarService.getListByYear(year));
	}

}
