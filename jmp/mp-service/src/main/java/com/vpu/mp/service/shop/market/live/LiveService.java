package com.vpu.mp.service.shop.market.live;

import static com.vpu.mp.db.shop.tables.LiveBroadcast.LIVE_BROADCAST;

import java.util.List;

import org.jooq.SelectConditionStep;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.vpu.mp.db.shop.tables.records.LiveBroadcastRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.live.LiveListParam;
import com.vpu.mp.service.pojo.shop.market.live.LiveListVo;

/**
 * 直播
 * 
 * @author zhaojianqiang
 * @time 下午3:42:02
 */
@Service
public class LiveService extends ShopBaseService {

	public PageResult<LiveListVo> getPageList(LiveListParam param) {
		SelectConditionStep<LiveBroadcastRecord> selectFrom = db().selectFrom(LIVE_BROADCAST)
				.where(LIVE_BROADCAST.ID.gt(0));
		return optionsBuilder(param, selectFrom);
	}

	private PageResult<LiveListVo> optionsBuilder(LiveListParam param, SelectConditionStep<LiveBroadcastRecord> selectFrom) {
		if (!StringUtils.isEmpty(param.getName())) {
			selectFrom.and(LIVE_BROADCAST.NAME.like(likeValue(param.getName())));
		}
		if (!StringUtils.isEmpty(param.getAnchorName())) {
			selectFrom.and(LIVE_BROADCAST.ANCHOR_NAME.like(likeValue(param.getAnchorName())));
		}
		if (param.getLiveStatus() > 0) {
			selectFrom.and(LIVE_BROADCAST.LIVE_STATUS.eq(param.getLiveStatus()));
		}
		if (param.getBeginStartTime() != null) {
			selectFrom.and(LIVE_BROADCAST.START_TIME.ge(param.getBeginStartTime()));
		}
		if (param.getBeginEndTime() != null) {
			selectFrom.and(LIVE_BROADCAST.START_TIME.lt(param.getBeginEndTime()));
		}

		if (param.getFinishStartTime() != null) {
			selectFrom.and(LIVE_BROADCAST.END_TIME.ge(param.getFinishStartTime()));
		}
		if (param.getFinishEndTime() != null) {
			selectFrom.and(LIVE_BROADCAST.END_TIME.lt(param.getFinishEndTime()));
		}
		selectFrom.orderBy(LIVE_BROADCAST.ROOM_ID.desc());
		return this.getPageResult(selectFrom, param.getCurrentPage(), param.getPageRows(), LiveListVo.class);
	}
	
	
	public void getList(LiveListParam param) {
		PageResult<LiveListVo> pageList = getPageList(param);
		List<LiveListVo> dataList = pageList.getDataList();
		for (LiveListVo liveListVo : dataList) {
			
		}
	}

}
