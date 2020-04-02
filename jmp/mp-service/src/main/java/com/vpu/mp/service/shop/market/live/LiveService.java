package com.vpu.mp.service.shop.market.live;

import static com.vpu.mp.db.shop.tables.LiveBroadcast.LIVE_BROADCAST;
import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;

import java.util.List;

import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.vpu.mp.db.shop.tables.records.LiveBroadcastRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.live.LiveListParam;
import com.vpu.mp.service.pojo.shop.market.live.LiveListVo;
import com.vpu.mp.service.wechat.api.WxMaLiveService;
import com.vpu.mp.service.wechat.bean.open.WxMaLiveInfoResult;

import me.chanjar.weixin.common.error.WxErrorException;


/**
 * 直播
 * 
 * @author zhaojianqiang
 * @time 下午3:42:02
 */
@Service
public class LiveService extends ShopBaseService {

	
	@Autowired
	public LiveGoodsService liveGoods;
	
	/**
	 * 直播列表页
	 * @param param
	 * @return
	 */
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
	
	
	/**
	 * 授权后的列表
	 * @param param
	 * @return
	 */
	public PageResult<LiveListVo> getList(LiveListParam param) {
		PageResult<LiveListVo> pageList = getPageList(param);
		List<LiveListVo> dataList = pageList.getDataList();
		for (LiveListVo liveListVo : dataList) {
			liveListVo.setGoodsListNum(liveGoods.getPackageGoodsListNum(liveListVo.getId()));
			liveListVo.getLiveStatus();
			liveListVo.setAddCartNum(liveGoods.getAddCartNum(liveListVo.getRoomId(), null));
			liveListVo.setOrderNum(getOrderNum(liveListVo.getRoomId()));
		}
		return pageList;
	}
	
	/**
	 * 获得订单数
	 * @param roomId
	 * @return
	 */
	public Integer getOrderNum(Integer roomId) {
		return db().select(DSL.count()).from(ORDER_INFO).where(ORDER_INFO.ACTIVITY_ID.eq(roomId))
				.fetchAnyInto(Integer.class);
	}
	
	
	/**
	 * 【获取直播房间列表】接口，仅供后台调用
	 * @param appId
	 * @param start
	 * @param limit
	 * @return
	 */
	public WxMaLiveInfoResult getliveinfo(String appId,Integer start,Integer limit) {
		WxMaLiveService service = open.getMaExtService();
		WxMaLiveInfoResult liveInfo=null;
		try {
			 liveInfo = service.getLiveInfo(appId, start, limit);
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
		logger().info("小程序：{}，直播列表为：{}",appId,liveInfo.toString());
		return liveInfo;
	}

}
