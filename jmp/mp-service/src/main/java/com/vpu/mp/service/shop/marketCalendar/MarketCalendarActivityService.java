package com.vpu.mp.service.shop.marketCalendar;

import static com.vpu.mp.db.shop.tables.MarketCalendarActivity.MARKET_CALENDAR_ACTIVITY;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.Utils;
import org.jooq.Result;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.main.tables.records.MarketCalendarRecord;
import com.vpu.mp.db.shop.tables.records.MarketCalendarActivityRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.overview.marketcalendar.CalendarAct;
import com.vpu.mp.service.pojo.shop.overview.marketcalendar.CalendarAction;
import com.vpu.mp.service.pojo.shop.overview.marketcalendar.MarketCalendarActivityVo;
import com.vpu.mp.service.pojo.shop.overview.marketcalendar.MarketcalendarParam;

@Service
public class MarketCalendarActivityService extends ShopBaseService {

	/**
	 * 新增
	 * 
	 * @param param
	 * @param calendarId
	 */
	public void addCalendarAct(MarketcalendarParam param, Integer calendarId) {
		List<CalendarAct> calendarActList = param.getCalendarAct();
		for (CalendarAct calendarAct : calendarActList) {
			MarketCalendarActivityRecord record = db().newRecord(MARKET_CALENDAR_ACTIVITY, calendarAct);
			record.setCalendarId(calendarId == null ? 0 : calendarId);
			int insert = record.insert();
			logger().info("更新日历活动id：{}；营销活动：{}；结果：{}", calendarId, calendarAct.getActivityId(), insert);
		}
	}

	/**
	 * 更新
	 * 
	 * @param param
	 * @param calendarId
	 */
	public void editCalendarAct(MarketcalendarParam param, Integer calendarId) {
		List<CalendarAct> calendarActList = param.getCalendarAct();
		for (CalendarAct calendarAct : calendarActList) {
			Integer id = calendarAct.getCalActId();
			if (id > 0) {
				logger().info("编辑的id：{}",id);
				MarketCalendarActivityRecord activityRecord = db().selectFrom(MARKET_CALENDAR_ACTIVITY).where(
						MARKET_CALENDAR_ACTIVITY.ID.eq(id).and(MARKET_CALENDAR_ACTIVITY.CALENDAR_ID.eq(calendarId)))
						.fetchAny();
				Integer sysCalActId = activityRecord.getSysCalActId();
				if (sysCalActId > 0) {
					logger().info("为推荐活动calendar_activity的id:{}", sysCalActId);
					activityRecord.setActivityId(calendarAct.getActivityId());
					Byte isSync = activityRecord.getIsSync();
					if (isSync == 0) {
						// 没同步到system
						logger().info("没有同步到system");
						com.vpu.mp.db.main.tables.records.MarketCalendarActivityRecord sysCalActInfo = saas.shop.calendarService.calendarActivityService
								.getInfoById(sysCalActId);
						if (sysCalActInfo != null) {
							String shopIds = sysCalActInfo.getShopIds();
							if (!StringUtils.isEmpty(shopIds)) {
								shopIds = "," + getShopId();
							} else {
								shopIds = String.valueOf(getShopId());
							}
							sysCalActInfo.setShopUseNum(sysCalActInfo.getShopUseNum() + 1);
							int update = sysCalActInfo.update();
							logger().info("更新主库calendar_activity的活动id：{}；结果：{}", sysCalActId, update);
							if (update > 0) {
								activityRecord.setIsSync(CalendarAction.ONE);
							}
						}
					}

					int update = activityRecord.update();
					logger().info("更新推荐的日历活动id：{}；calendarId:{},营销活动：{}；结果：{}", id, calendarId, update);
				}else {
					logger().info("更新");
					activityRecord.setDelFlag(calendarAct.getDelFlag());
					activityRecord.setActivityId(calendarAct.getActivityId());
					activityRecord.setActivityType(calendarAct.getActivityType());
					int update = activityRecord.update();
					logger().info("更新id为：{}的数据，结果：{}",id,update);
				}

			} else {
				// 新建的
				logger().info("新建的");
				MarketCalendarActivityRecord record = db().newRecord(MARKET_CALENDAR_ACTIVITY, calendarAct);
				record.setCalendarId(calendarId == null ? 0 : calendarId);
				int insert = record.insert();
				logger().info("插入日历活动id：{}；营销活动：{}；结果：{}", calendarId, calendarAct.getActivityId(), insert);
			}
		}
	}

	public List<MarketCalendarActivityVo> calendarActList(Integer calendarId) {
		return db().selectFrom(MARKET_CALENDAR_ACTIVITY)
				.where(MARKET_CALENDAR_ACTIVITY.DEL_FLAG.eq(DelFlag.NORMAL_VALUE)
						.and(MARKET_CALENDAR_ACTIVITY.CALENDAR_ID.eq(calendarId)))
				.fetchInto(MarketCalendarActivityVo.class);
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	public boolean delInfo(Integer id) {
		int execute = db().update(MARKET_CALENDAR_ACTIVITY)
				.set(MARKET_CALENDAR_ACTIVITY.DEL_FLAG, DelFlag.DISABLE_VALUE).where(MARKET_CALENDAR_ACTIVITY.ID.eq(id))
				.execute();
		return execute == 1;
	}
}
