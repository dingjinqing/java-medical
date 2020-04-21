package com.vpu.mp.service.shop.marketCalendar;

import static com.vpu.mp.db.shop.tables.MarketCalendarActivity.MARKET_CALENDAR_ACTIVITY;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.MarketCalendarActivityRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.overview.marketcalendar.CalendarAct;
import com.vpu.mp.service.pojo.shop.overview.marketcalendar.MarketCalendarActivityVo;
import com.vpu.mp.service.pojo.shop.overview.marketcalendar.MarketcalendarParam;

@Service
public class MarketCalendarActivityService extends ShopBaseService {

	/**
	 * 新增
	 * @param param
	 * @param calendarId
	 */
	public void addCalendarAct(MarketcalendarParam param, Integer calendarId) {
		List<CalendarAct> calendarActList = param.getCalendarAct();
		for (CalendarAct calendarAct : calendarActList) {
			MarketCalendarActivityRecord record = db().newRecord(MARKET_CALENDAR_ACTIVITY, calendarAct);
			record.setCalendarId(calendarId == null ? 0 : calendarId);
			int insert = record.insert();
			logger().info("更新日历活动id：{}；营销活动：{}；结果：{}",calendarId,calendarAct.getActivityId(),insert);
		}
	}

	/**
	 * 更新
	 * @param param
	 * @param calendarId
	 */
	public void editCalendarAct(MarketcalendarParam param, Integer calendarId) {
		List<CalendarAct> calendarActList = param.getCalendarAct();
		for (CalendarAct calendarAct : calendarActList) {
			MarketCalendarActivityRecord record = db().newRecord(MARKET_CALENDAR_ACTIVITY, calendarAct);
			record.setCalendarId(calendarId == null ? 0 : calendarId);
			int insert = record.update();
			logger().info("更新日历活动id：{}；营销活动：{}；结果：{}",calendarId,calendarAct.getActivityId(),insert);
		}
	}
	
	
	public void calendarActList(Integer calendarId) {
		List<MarketCalendarActivityVo> fetchInto = db().selectFrom(MARKET_CALENDAR_ACTIVITY).where(MARKET_CALENDAR_ACTIVITY.DEL_FLAG.eq(DelFlag.NORMAL_VALUE)).fetchInto(MarketCalendarActivityVo.class);
		
	}
}
