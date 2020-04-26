package com.vpu.mp.service.saas.marketCalendar;

import static com.vpu.mp.db.main.tables.MarketCalendarActivity.MARKET_CALENDAR_ACTIVITY;

import java.util.List;

import org.jooq.Result;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.main.tables.records.MarketCalendarActivityRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.pojo.saas.marketCalendar.MarketCalendarParam;
import com.vpu.mp.service.pojo.saas.marketCalendar.SysCalendarAct;
import com.vpu.mp.service.pojo.saas.marketCalendar.SysCalendarActVo;

/**
 * system用的详情
 * 
 * @author zhaojianqiang 2020年4月24日下午3:31:44
 */
@Service
public class MarketSysCalendarActivityService extends MainBaseService {
	/**
	 * 新增
	 * 
	 * @param param
	 * @param calendarId
	 */
	public void addCalendarAct(MarketCalendarParam param, Integer calendarId) {
		List<SysCalendarAct> calendarActList = param.getCalendarAct();
		for (SysCalendarAct sysCalendarAct : calendarActList) {
			MarketCalendarActivityRecord record = db().newRecord(MARKET_CALENDAR_ACTIVITY, sysCalendarAct);
			record.setCalendarId(calendarId == null ? 0 : calendarId);
			int insert = record.insert();
			logger().info("system新增日历活动id：{}；营销活动：{}；结果：{}", calendarId, sysCalendarAct.getActivityType(), insert);
		}
	}

	public void editCalendarAct(MarketCalendarParam param, Integer calendarId) {
		List<SysCalendarAct> calendarActList = param.getCalendarAct();
		for (SysCalendarAct sysCalendarAct : calendarActList) {
			MarketCalendarActivityRecord record = db().newRecord(MARKET_CALENDAR_ACTIVITY, sysCalendarAct);
			record.setCalendarId(calendarId == null ? 0 : calendarId);
			int update = record.update();
			logger().info("system更新日历活动id：{}；营销活动：{}；结果：{}", calendarId, sysCalendarAct.getActivityType(), update);
		}
	}

	public MarketCalendarActivityRecord getInfoById(Integer id) {
		return db().selectFrom(MARKET_CALENDAR_ACTIVITY).where(MARKET_CALENDAR_ACTIVITY.ID.eq(id)).fetchAny();
	}

	public Result<MarketCalendarActivityRecord> getInfoByCalendarId(Integer calendarId) {
		return db().selectFrom(MARKET_CALENDAR_ACTIVITY).where(MARKET_CALENDAR_ACTIVITY.CALENDAR_ID.eq(calendarId))
				.fetch();
	}

	public List<SysCalendarActVo> calendarActList(Integer calendarId) {
		List<SysCalendarActVo> list = db().selectFrom(MARKET_CALENDAR_ACTIVITY).where(MARKET_CALENDAR_ACTIVITY.DEL_FLAG
				.eq(DelFlag.NORMAL_VALUE).and(MARKET_CALENDAR_ACTIVITY.CALENDAR_ID.eq(calendarId)))
				.fetchInto(SysCalendarActVo.class);
		for (SysCalendarActVo item : list) {
			String shopIds = item.getShopIds();
			String[] split = shopIds.split(",");
			item.setShopNum(split.length);
		}
		return list;
	}

}
