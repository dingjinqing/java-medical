package com.vpu.mp.service.shop.market.friendpromote;

import java.sql.Timestamp;

import org.jooq.Record7;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import com.mysql.cj.util.StringUtils;
import com.vpu.mp.db.shop.tables.FriendPromoteActivity;
import com.vpu.mp.db.shop.tables.FriendPromoteLaunch;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.friendpromote.FriendPromoteListParam;
import com.vpu.mp.service.pojo.shop.market.friendpromote.FriendPromoteListVo;

/**
 * 好友助力
 * 
 * @author liangchen
 * @date 2019年8月7日
 */
@Service
public class FriendPromoteService extends ShopBaseService {
	private static FriendPromoteActivity fpa = FriendPromoteActivity.FRIEND_PROMOTE_ACTIVITY.as("fpa");
	private static FriendPromoteLaunch fpl = FriendPromoteLaunch.FRIEND_PROMOTE_LAUNCH.as("fpl");

	/**
	 * 好友助力活动列表
	 *
	 * @param param
	 * @return pageResult
	 */
	public PageResult<FriendPromoteListVo> friendPromoteList(FriendPromoteListParam param) {
		/* 查询好友助力活动 */
			Timestamp nowTime = new Timestamp(System.currentTimeMillis());
			SelectConditionStep<Record7<Integer, String, Timestamp, Timestamp, Byte, String, Byte>> sql = db()
					.select(fpa.ID.as("actId"),fpa.ACT_NAME, fpa.START_TIME, fpa.END_TIME, fpa.REWARD_TYPE, 
							fpa.REWARD_CONTENT,fpa.IS_BLOCK).from(fpa)
					.where(fpa.DEL_FLAG.eq((byte) 0));
			/* 查询条件：活动名称 */
			if (!StringUtils.isNullOrEmpty(param.getActName())) {
				sql = sql.and(fpa.ACT_NAME.like(this.likeValue(param.getActName())));
			}
			/* 查询条件：开始时间 */
			if (param.getStartTime() != null) {
				sql = sql.and(fpa.START_TIME.greaterOrEqual(param.getStartTime()));
			}
			/* 查询条件：结束时间 */
			if (param.getEndTime() != null) {
				sql = sql.and(fpa.END_TIME.lessOrEqual(param.getEndTime()));
			}
			/* 查询条件：奖励类型 */
			if (param.getRewardType() != FriendPromoteListParam.REWARDTYPE_DEFAULT_VALUE) {
				sql = sql.and(fpa.REWARD_TYPE.eq((byte) param.getRewardType().intValue()));
			}
			/* 活动状态0已停用 */
			if (0 == param.getActState()) {
				sql = sql.and(fpa.IS_BLOCK.eq((byte) 1));
			}
			/* 活动状态1进行中 */
			if (1 == param.getActState()) {
				sql = sql.and(fpa.IS_BLOCK.eq((byte) 0)).and(fpa.START_TIME.lessOrEqual(nowTime))
						.and(fpa.END_TIME.greaterOrEqual(nowTime));
			}
			/* 活动状态2未开始 */
			if (2 == param.getActState()) {
				sql = sql.and(fpa.IS_BLOCK.eq((byte) 0)).and(fpa.START_TIME.greaterOrEqual(nowTime));
			}
			/* 活动状态3已结束 */
			if (3 == param.getActState()) {
				sql = sql.and(fpa.IS_BLOCK.eq((byte) 0)).and(fpa.END_TIME.lessOrEqual(nowTime));
			}
			/* 整合分页信息 */
			PageResult<FriendPromoteListVo> pageResultVo = getPageResult(sql, param.getCurrentPage(),
					param.getPageRows(), FriendPromoteListVo.class);
			/* 获取领取数量和活动状态  */
			for (FriendPromoteListVo vo : pageResultVo.getDataList()) {
				/* 领取数量recNum */
				int recNum = db().select(DSL.count(fpl.PROMOTE_STATUS).as("recNum"))
				.from(fpl)
				.where(fpl.PROMOTE_ID.eq(vo.getActId()))
				.and(fpl.PROMOTE_STATUS.eq((byte)2))
				.fetchOptionalInto(Integer.class)
				.orElse(0);
				vo.setRecNum(recNum);
				/* 活动状态actState */
				if (1 == vo.getIsBlock() ) {
					vo.setActState(0);
				}
				if (0 == vo.getIsBlock() && nowTime.after(vo.getStartTime()) && nowTime.before(vo.getEndTime())) {
					vo.setActState(1);
				}
				if (0 == vo.getIsBlock() && nowTime.before(vo.getStartTime()) ) {
					vo.setActState(2);
				}
				if (0 == vo.getIsBlock() && nowTime.after(vo.getEndTime())) {
					vo.setActState(3);
				}
			}
			return pageResultVo;
	}
}
