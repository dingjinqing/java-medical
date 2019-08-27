package com.vpu.mp.service.shop.market.channel;

import static com.vpu.mp.db.shop.Tables.CHANNEL_STATISTICAL;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jooq.SelectConditionStep;
import org.jooq.SelectWhereStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vpu.mp.db.shop.tables.records.ChannelRecord;
import com.vpu.mp.db.shop.tables.records.ChannelStatisticalRecord;
import com.vpu.mp.service.foundation.database.DslPlus;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.market.channel.ChannelConstant;
import com.vpu.mp.service.pojo.shop.market.channel.ChannelStatisticalAllVo;
import com.vpu.mp.service.pojo.shop.market.channel.ChannelStatisticalDayInfo;
import com.vpu.mp.service.pojo.shop.market.channel.ChannelStatisticalParam;
import com.vpu.mp.service.pojo.shop.market.channel.PerChannelDayInfo;
/**
 * @author huangronggang
 * @date 2019年8月15日
 */
@Service
public class ChannelStatisticalService extends ShopBaseService {
	
	@Autowired  ChannelService channelService;
	
	public final static int YESTERDAY_OFFSET=-1;
	public final static String SEPARATOR=",";
	public final static String I18N_RESOURCE ="messages";
	
	public final static int WEEK_INDEX=1;
	public final static int MONTH_INDEX=2;
	public final static int DAY_INDEX=0;
	public ChannelStatisticalRecord selectYesterDayRecord(Integer channelId, Integer pageId, Integer goodsId) {
		if (pageId == null && goodsId == null) {
			return null;
		}
		SelectConditionStep<ChannelStatisticalRecord> step = db().selectFrom(CHANNEL_STATISTICAL)
				.where(DslPlus.findInSet(channelId.toString(), CHANNEL_STATISTICAL.CHANNEL_ID))
				.and(CHANNEL_STATISTICAL.REF_DATE.eq(yesterDate()));
		if (pageId != null && pageId != 0) {
			step.and(CHANNEL_STATISTICAL.PAGE_ID.eq(pageId));
		} else {
			step.and(CHANNEL_STATISTICAL.GOODS_ID.eq(goodsId));
		}
		return step.fetchAny();
	}


	public ChannelStatisticalAllVo channelStatistical(ChannelStatisticalParam param) {
		
		ChannelStatisticalAllVo result = new ChannelStatisticalAllVo();
		
//		待查询的时间范围
		List<Date> dateRange = dateRange(param.getStartDate(), param.getEndDate());
		
		ChannelRecord channelRecord = channelService.selectChannelRecord(param.getChannelId());
//		//获取某一个渠道页面的全部渠道ID列表：先拿着这个渠道ID查询渠道ID绑定的页面或商品，然后拿页面ID或商品ID去查所有绑定本商品ID或页面ID的渠道ID
		List<Integer> channelIdList = selectStatisticalChannelIdList(channelRecord, yesterDate());
		Map<Integer, String> channelNameMap = channelService.selectChannelName(channelIdList);
		
		for (Date date : dateRange) {
			ChannelStatisticalRecord statisticalRecord = selectRecord(channelRecord.getSourceType(), date,
					channelRecord.getPageId(	), channelRecord.getGoodsId());
			ChannelStatisticalDayInfo dayInfo = new ChannelStatisticalDayInfo();
			result.getAllInfo().put(date, dayInfo);
			if(statisticalRecord == null) {
				continue;
			}
			Map<Integer, Integer> channelAllPvMap = convertJson2Map(statisticalRecord.getChannelAllPv());
			Map<Integer, Integer> channelAllUvMap = convertJson2Map(statisticalRecord.getChannelAllUv());
			Map<Integer, Integer> channelPvNewMap = convertJson2Map(statisticalRecord.getChannelNewPv());
			Map<Integer, Integer> channelUvNewMap = convertJson2Map(statisticalRecord.getChannelNewUv());
			Map<Integer, Integer> channelPvOldMap = convertJson2Map(statisticalRecord.getChannelOldPv());
			Map<Integer, Integer> channelUvOldMap = convertJson2Map(statisticalRecord.getChannelOldUv());

			for (Integer channelId : channelIdList) {
				PerChannelDayInfo perChannelInfo = new PerChannelDayInfo();
				perChannelInfo.setPvAll(channelAllPvMap.get(channelId));
				perChannelInfo.setUvAll(channelAllUvMap.get(channelId));
				perChannelInfo.setPvNew(channelPvNewMap.get(channelId));
				perChannelInfo.setUvNew(channelUvNewMap.get(channelId));
				perChannelInfo.setPvOld(channelPvOldMap.get(channelId));
				perChannelInfo.setUvOld(channelUvOldMap.get(channelId));
				dayInfo.getDayInfoMap().put(channelNameMap.get(channelId), perChannelInfo);
			}
//			全部访问量，包括有渠道和无渠道
			PerChannelDayInfo allInfo = new PerChannelDayInfo();
			allInfo.setPvAll(valueOfIndex(statisticalRecord.getAllPv(), DAY_INDEX, SEPARATOR));
			allInfo.setUvAll(valueOfIndex(statisticalRecord.getAllUv(), DAY_INDEX, SEPARATOR));
			allInfo.setPvNew(valueOfIndex(statisticalRecord.getNewPv(), DAY_INDEX, SEPARATOR));
			allInfo.setUvNew(valueOfIndex(statisticalRecord.getNewUv(), DAY_INDEX, SEPARATOR));
			allInfo.setPvOld(valueOfIndex(statisticalRecord.getOldPv(), DAY_INDEX, SEPARATOR));
			allInfo.setUvOld(valueOfIndex(statisticalRecord.getOldUv(), DAY_INDEX, SEPARATOR));
			dayInfo.setAllInfo(allInfo);
//			计算并设置全部渠道访问量，和无渠道访问量
			dayInfo.setTotalChannelInfo();
			dayInfo.setNoChannelInfo();

		}
		result.setChannelName(channelService.selectAllPageInfo());
		return result;
	}
	
	private List<Integer> selectStatisticalChannelIdList(ChannelRecord channelRecord,Date date){
		if(channelRecord == null) {
			return Collections.emptyList();
		}
		ChannelStatisticalRecord statisticalRecord = selectRecord(channelRecord.getSourceType(), date, channelRecord.getPageId(), channelRecord.getGoodsId());
		if(statisticalRecord == null) {
			return Collections.emptyList();
		}
		List<Integer> channelIdList = Util.valueOf( statisticalRecord.getChannelId().split(SEPARATOR));
		return channelIdList;
	}
	
	/**
	 * @param step
	 * @param sourceType
	 * @param pageId
	 * @param goodsId
	 */
	private void buildByChannelType(SelectWhereStep<?> select, Byte sourceType, Integer pageId,
			Integer goodsId) {
		if (ChannelConstant.SOURCETYPE_CUSTOMIZE.equals(sourceType)) {
			select.where(CHANNEL_STATISTICAL.PAGE_ID.eq(pageId));
		} else {
			select.where(CHANNEL_STATISTICAL.GOODS_ID.eq(goodsId));
		}
	}
	
	public ChannelStatisticalRecord selectRecord(Byte channelSourceType,Date date,Integer pageId,Integer goodsId) {
		SelectWhereStep<ChannelStatisticalRecord> select = db().selectFrom(CHANNEL_STATISTICAL);
		buildByChannelType(select, channelSourceType, pageId, goodsId);
		ChannelStatisticalRecord statisticalRecord = select.where(CHANNEL_STATISTICAL.REF_DATE.eq(date))
				.fetchAny();
		return statisticalRecord;
	}

	private List<Date> dateRange(java.util.Date start,java.util.Date end){
		if(start.after(end)) {
			return Collections.emptyList();
		}
		List<Date> range = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		while(true) {
			if(!end.before(start)) {
				range.add(new Date(start.getTime()));
				calendar.setTime(start);
				calendar.add(Calendar.DATE, 1);
				start = new Date(calendar.getTimeInMillis());
			}else {
				break;
			}
		}
		return range;
	}
	private Date dateOffsetDay(Date date,int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, day);
		return new Date(calendar.getTimeInMillis());
	}
	private Date nowDate() {
		return new Date(System.currentTimeMillis());
	}

	private Date yesterDate() {
		return dateOffsetDay(nowDate(), YESTERDAY_OFFSET);
	}
	
	private int valueOfIndex(String valueString,int index,String separator) {
		if(valueString == null) {
			return 0;
		}
		String[] split = valueString.split(separator);
		if(split.length<=index) {
			return 0;
		}
		String string = split[index];
		return Integer.parseInt(string);
	}
	
	private Map<Integer,Integer> convertJson2Map( String json) {
		if (json == null) {
			return Collections.emptyMap();
		}
		Map<String, Integer> pvMap = Util.parseJson(json, new TypeReference<Map<String, Integer>>() {
		});
		if (pvMap == null) {
			return Collections.emptyMap();
		}
		Map<Integer,Integer> resultMap = new HashMap<>();
		for(String s:pvMap.keySet()) {
			resultMap.put(Integer.parseInt(s), pvMap.get(s));
		}
		return resultMap;
	}
}

