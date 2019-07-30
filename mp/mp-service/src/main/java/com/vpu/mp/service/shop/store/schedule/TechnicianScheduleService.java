package com.vpu.mp.service.shop.store.schedule;

import static com.vpu.mp.db.shop.Tables.SERVICE_SCHEDULE;
import static  com.vpu.mp.db.shop.Tables.SERVICE_TECHNICIAN;
import static  com.vpu.mp.db.shop.Tables.SERVICE_TECHNICIAN_SCHEDULE;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.ServiceTechnicianScheduleRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.store.schedule.SchedulePojo;
import com.vpu.mp.service.pojo.shop.store.schedule.TechnicianScheduleParam;
import com.vpu.mp.service.pojo.shop.store.schedule.TechnicianScheduleSaveParam;
import com.vpu.mp.service.pojo.shop.store.schedule.TechnicianScheduleVo;

/**
 * @author 黄荣刚
 * @date 2019年7月16日
 *
 */
@Service

public class TechnicianScheduleService extends ShopBaseService {
	
	/**
	 * 根据店铺ID查全部班次表
	 * @param storeId
	 * @return
	 */
	public List<SchedulePojo> getScheduleList(Integer storeId){
		List<SchedulePojo> list = db().selectFrom(SERVICE_SCHEDULE)
				.where(SERVICE_SCHEDULE.STORE_ID.eq(storeId))
				.and(SERVICE_SCHEDULE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
				.fetchInto(SchedulePojo.class);
		return list;
	}
	/**
	 * 插入一个新班次
	 * @param schedule
	 * @return
	 */
	public int insertSchedule(SchedulePojo schedule) {
		int result = db().insertInto(SERVICE_SCHEDULE, 
				SERVICE_SCHEDULE.SCHEDULE_NAME, SERVICE_SCHEDULE.STORE_ID,
				SERVICE_SCHEDULE.BEGCREATE_TIME, SERVICE_SCHEDULE.END_TIME,SERVICE_SCHEDULE.DEL_FLAG)
		.values(schedule.getScheduleName(), schedule.getStoreId(), 
				schedule.getBegcreateTime(), schedule.getEndTime(),DelFlag.NORMAL_VALUE)
		.execute();
		return result;
	}
	/**
	 * 删除一个班次
	 * @param scheduleId
	 * @return
	 */
	public int deleteSchedule(Byte scheduleId) {
		int result = db().update(SERVICE_SCHEDULE)
				.set(SERVICE_SCHEDULE.DEL_FLAG, DelFlag.DISABLE_VALUE)
				.where(SERVICE_SCHEDULE.SCHEDULE_ID.eq(scheduleId))
				.and(SERVICE_SCHEDULE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
				.execute();
		return result;
	}
	/**
	 * 修改班次
	 * @param schedule
	 * @return
	 */
	public int updateSchedule(SchedulePojo schedule) {
		int result = db().update(SERVICE_SCHEDULE)
				.set(SERVICE_SCHEDULE.SCHEDULE_NAME,schedule.getScheduleName())
				.set(SERVICE_SCHEDULE.BEGCREATE_TIME,schedule.getBegcreateTime())
				.set(SERVICE_SCHEDULE.END_TIME,schedule.getEndTime())
				.where(SERVICE_SCHEDULE.SCHEDULE_ID.eq(schedule.getScheduleId()))
				.and(SERVICE_SCHEDULE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
				.execute();
		return result;
	}
	/**
	 * 根据班次ID查班次
	 * @param scheduleId
	 * @return
	 */
	public SchedulePojo selectSchedule(Byte scheduleId) {
		SchedulePojo pojo = db().selectFrom(SERVICE_SCHEDULE)
				.where(SERVICE_SCHEDULE.SCHEDULE_ID.eq(scheduleId))
				.and(SERVICE_SCHEDULE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
				.fetchOneInto(SchedulePojo.class);
		return pojo;
	}
	
	public List<TechnicianScheduleVo>selectTechnicianSchedule(TechnicianScheduleParam param) {
		if(param == null) {
			return null;
		}
		List<String> days = getDays(param.getBeginTime(),param.getEndTime());
		List<TechnicianScheduleVo> technicianScheduleVo = db().select(SERVICE_TECHNICIAN_SCHEDULE.STORE_ID,SERVICE_TECHNICIAN_SCHEDULE.TECHNICIAN_ID,SERVICE_TECHNICIAN.TECHNICIAN_NAME,SERVICE_TECHNICIAN_SCHEDULE.WORK_DATE,SERVICE_TECHNICIAN_SCHEDULE.SCHEDULE_ID,SERVICE_SCHEDULE.SCHEDULE_NAME,SERVICE_SCHEDULE.BEGCREATE_TIME,SERVICE_SCHEDULE.END_TIME)
				.from(SERVICE_TECHNICIAN_SCHEDULE)
				.leftJoin(SERVICE_SCHEDULE)
				.on(SERVICE_TECHNICIAN_SCHEDULE.SCHEDULE_ID.eq(SERVICE_SCHEDULE.SCHEDULE_ID))
				.leftJoin(SERVICE_TECHNICIAN)
				.on(SERVICE_TECHNICIAN_SCHEDULE.TECHNICIAN_ID.eq(SERVICE_TECHNICIAN.ID))
				.where(SERVICE_TECHNICIAN_SCHEDULE.STORE_ID.eq(param.getStoreId()))
				.and(SERVICE_TECHNICIAN_SCHEDULE.TECHNICIAN_ID.eq(param.getTechnicianId()))
				.and(SERVICE_SCHEDULE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
				.and(SERVICE_TECHNICIAN_SCHEDULE.WORK_DATE.in(days))
				.fetchInto(TechnicianScheduleVo.class);
		return technicianScheduleVo;
	}
	
	public int saveTechnicianSchedule(TechnicianScheduleSaveParam param) {
		Map<String, Byte> scheduleMap = param.getScheduleMap();
		Integer storeId = param.getStoreId();
		Integer technicianId = param.getTechnicianId();
		Set<String> keySet = scheduleMap.keySet();
		List<ServiceTechnicianScheduleRecord> recordList = new ArrayList<ServiceTechnicianScheduleRecord>(keySet.size());
		for (String workDate : keySet) {
			ServiceTechnicianScheduleRecord record = new ServiceTechnicianScheduleRecord(null, storeId, technicianId, workDate, scheduleMap.get(workDate), null, null);
			recordList.add(record);
		}
		this.transaction(()->{
			db().deleteFrom(SERVICE_TECHNICIAN_SCHEDULE)
				.where(SERVICE_TECHNICIAN_SCHEDULE.STORE_ID.eq(storeId))
				.and(SERVICE_TECHNICIAN_SCHEDULE.TECHNICIAN_ID.eq(technicianId))
				.and(SERVICE_TECHNICIAN_SCHEDULE.WORK_DATE.in(scheduleMap.keySet()))
				.execute();
			db().batchInsert(recordList).execute();
		});
		return 0;
	}
	
	  /**
     * 获取两个日期之间的所有日期
     * 
     * @param startTime
     *            开始日期
     * @param endTime
     *            结束日期
     * @return
     */
    private static List<String> getDays(String startTime, String endTime) {

        // 返回的日期集合
        List<String> days = new ArrayList<String>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);
            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);
            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            tempEnd.add(Calendar.DATE, +1);
            while (tempStart.before(tempEnd)) {
                days.add(dateFormat.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return days;
    }
}
