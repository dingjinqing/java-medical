package com.vpu.mp.service.shop.marketCalendar;

import static com.vpu.mp.db.shop.tables.MarketCalendar.MARKET_CALENDAR;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import org.jooq.Record4;
import org.jooq.SelectJoinStep;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.MarketCalendarRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.saas.shop.version.VersionName;
import com.vpu.mp.service.pojo.shop.overview.marketcalendar.CalendarAction;
import com.vpu.mp.service.pojo.shop.overview.marketcalendar.MarketCalendarActivityVo;
import com.vpu.mp.service.pojo.shop.overview.marketcalendar.MarketCalendarVo;
import com.vpu.mp.service.pojo.shop.overview.marketcalendar.MarketcalendarParam;
import com.vpu.mp.service.shop.ShopApplication;

@Service
public class MarketCalendarService extends ShopBaseService {
	@Autowired
	public MarketCalendarActivityService calendarActivityService;

	/**
	 * 创建日历活动
	 * 
	 * @param param
	 * @return
	 */
	public boolean addCalendar(MarketcalendarParam param) {
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
	public boolean edit(MarketcalendarParam param) {
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
		calendarActivityService.editCalendarAct(param, calendarId);
		return update == 1 ? true : false;
	}

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public boolean del(Integer id) {
		int execute = db().update(MARKET_CALENDAR).set(MARKET_CALENDAR.DEL_FLAG, DelFlag.DISABLE_VALUE).where(MARKET_CALENDAR.ID.eq(id)).execute();
		return execute > 0 ? true : false;
	}
	
	
	public void getListByYear(String year) {
		List<MarketCalendarVo> calendarList = db().selectFrom(MARKET_CALENDAR)
				.where(MARKET_CALENDAR.DEL_FLAG.eq(DelFlag.NORMAL_VALUE)
						.and(DSL.left(MARKET_CALENDAR.EVENT_TIME.cast(String.class), 4).eq(year)))
				.fetchInto(MarketCalendarVo.class);
		for (MarketCalendarVo item : calendarList) {
			
		}
		
	}
	
	public MarketCalendarVo eventStatus(MarketCalendarVo param) {
		//1未开始，2进行中，3失效，4已结束
		int hasAct=0;
		Date nowDate = Date.valueOf(LocalDate.now());
		if(param.getEventTime().before(nowDate)) {
			param.setStatus(CalendarAction.ONE);
			return param;
		}
		List<MarketCalendarActivityVo> calendarActList = calendarActivityService.calendarActList(param.getId());
		for (MarketCalendarActivityVo item : calendarActList) {
			if(item.getActivityId()>0) {
				hasAct++;
				String activityType = item.getActivityType();
				Integer activityId = item.getActivityId();
			}
		}
		
		return param;
	}
	
	public void getActInfo(String actType,Integer actId,String type) {
		ShopApplication shopApp = saas.getShopApp(getShopId());
		//VsName
		switch (actType) {
		case VersionName.SUB_4_PIN_GROUP:
			shopApp.groupBuy.getActInfo(actId);
			break;
		case VersionName.SUB_4_BARGAIN:
			shopApp.bargain.getActInfo(actId);
			break;
		case VersionName.SUB_4_GROUP_DRAW:
			shopApp.groupDraw.getActInfo(actId);
			break;
		case VersionName.SUB_4_PIN_INTEGRATION:
			shopApp.groupIntegration.getActInfo(actId);
			break;
		case VersionName.SUB_4_LOTTERY:
			shopApp.lottery.getActInfo(actId);
			break;
		case VersionName.SUB_4_PROMOTE:
			shopApp.friendPromoteService.getActInfo(actId);
			break;
		default:
			break;
		}
	}
	
	
	

}
