package com.vpu.mp.service.saas.marketCalendar;

import static com.vpu.mp.db.main.tables.MarketCalendar.MARKET_CALENDAR;

import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jooq.Field;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.main.tables.records.MarketCalendarRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.saas.marketCalendar.MarketCalendarParam;
import com.vpu.mp.service.pojo.shop.overview.marketcalendar.CalendarAction;
import com.vpu.mp.service.pojo.shop.overview.marketcalendar.MarketCalendarVo;
import com.vpu.mp.service.pojo.shop.overview.marketcalendar.MarketListData;
import com.vpu.mp.service.pojo.shop.overview.marketcalendar.MarketListDataVo;

/**
 * system营销日历
 * 
 * @author zhaojianqiang 2020年4月24日下午2:34:17
 */
@Service
public class MarketSysCalendarService extends MainBaseService {
	@Autowired
	public MarketSysCalendarActivityService calendarActivityService;

	/**
	 * 营销日历列表
	 * 
	 * @param year
	 * @return
	 */
	public MarketListDataVo getListByYear(String year) {
		if (StringUtils.isEmpty(year)) {
			year = String.valueOf(LocalDate.now().getYear());
		}
		List<MarketListData> list = new LinkedList<MarketListData>();
		List<MarketCalendarVo> calendarList = db().selectFrom(MARKET_CALENDAR)
				.where(MARKET_CALENDAR.DEL_FLAG.eq(DelFlag.NORMAL_VALUE)
						.and(DSL.left(MARKET_CALENDAR.EVENT_TIME.cast(String.class), 4).eq(year)))
				.orderBy(MARKET_CALENDAR.EVENT_TIME.asc()).fetchInto(MarketCalendarVo.class);
		Date nowDate = DateUtil.yyyyMmDdDate(LocalDate.now());
		for (int i = 1; i < 13; i++) {
			MarketListData data = new MarketListData();
			if (i < 10) {
				data.setMonth("0" + i);
			} else {
				data.setMonth(String.valueOf(i));
			}
			List<MarketCalendarVo> dataList = new LinkedList<MarketCalendarVo>();
			data.setData(dataList);
			list.add(data);
		}
		for (MarketCalendarVo item : calendarList) {
			item = eventStatus(item);
			if (item.getEventStatus().equals(CalendarAction.ONE)) {
				int days = (int) ((item.getEventTime().getTime() - nowDate.getTime()) / (1000 * 60 * 60 * 24L));
				item.setDownTime(days);
			}
			Date eventTime = item.getEventTime();
			LocalDate localDate = eventTime.toLocalDate();
			int month = localDate.getMonth().getValue();
			MarketListData marketListData = list.get(month - 1);
			List<MarketCalendarVo> data = marketListData.getData();
			data.add(item);
		}
		return new MarketListDataVo(list, nowDate, getYearList());

	}

	public MarketCalendarVo eventStatus(MarketCalendarVo param) {
		logger().info("开始判断状态");
		// 1未开始，2进行中，3失效，4已结束
		Date nowDate = Date.valueOf(LocalDate.now());
		if (param.getEventTime().after(nowDate)) {
			param.setEventStatus(CalendarAction.ONE);
		} else {
			param.setEventStatus(CalendarAction.TWO);
		}
		return param;
	}

	public List<String> getYearList() {
		Field<String> field = DSL.left(MARKET_CALENDAR.EVENT_TIME.cast(String.class), 4).as("year");
		return db().select(field).from(MARKET_CALENDAR).where(MARKET_CALENDAR.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
				.groupBy(field).orderBy(field.desc()).fetchInto(String.class);
	}

	/**
	 * 创建日历活动
	 * 
	 * @param param
	 * @return
	 */
	public boolean addCalendar(MarketCalendarParam param) {
		MarketCalendarRecord record = db().newRecord(MARKET_CALENDAR, param);
		int insert = record.insert();
		logger().info("创建营销活动：{}，结果：{}", param.getEventName(), insert);
		if (insert < 0) {
			return false;
		}
		calendarActivityService.addCalendarAct(param, record.getId());
		return insert == 1 ? true : false;
	}

	/**
	 * 编辑日历活动
	 * 
	 * @param param
	 * @return
	 */
	public boolean edit(MarketCalendarParam param) {
		Integer calendarId = param.getCalendarId();
		if (calendarId == null || calendarId.equals(0)) {
			logger().info("活动名称：{}，id为0或不存在，不能更新", param.getEventName());
			return false;
		}
		MarketCalendarRecord record = db().newRecord(MARKET_CALENDAR, param);
		record.setId(calendarId);
		int update = record.update();
		if (update < 0) {
			return false;
		}
		logger().info("更新营销活动id:{},名称：{}，结果：{}", calendarId, param.getEventName(), update);
		//calendarActivityService.editCalendarAct(param, calendarId);
		return update == 1 ? true : false;
	}
	
	
	public MarketCalendarRecord getInfoById(Integer id) {
		return db().selectFrom(MARKET_CALENDAR).where(MARKET_CALENDAR.ID.eq(id)).fetchAny();
	}
}
