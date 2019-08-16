package com.vpu.mp.service.shop.market.friendpromote;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.jooq.Record6;
import org.jooq.Record7;
import org.jooq.SelectConditionStep;
import org.jooq.SelectHavingStep;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import com.mysql.cj.util.StringUtils;
import com.vpu.mp.db.shop.tables.FriendPromoteActivity;
import com.vpu.mp.db.shop.tables.FriendPromoteDetail;
import com.vpu.mp.db.shop.tables.FriendPromoteLaunch;
import com.vpu.mp.db.shop.tables.User;
import com.vpu.mp.db.shop.tables.records.FriendPromoteActivityRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.FieldsUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.friendpromote.FriendPromoteAddParam;
import com.vpu.mp.service.pojo.shop.market.friendpromote.FriendPromoteLaunchParam;
import com.vpu.mp.service.pojo.shop.market.friendpromote.FriendPromoteLaunchVo;
import com.vpu.mp.service.pojo.shop.market.friendpromote.FriendPromoteListParam;
import com.vpu.mp.service.pojo.shop.market.friendpromote.FriendPromoteListVo;
import com.vpu.mp.service.pojo.shop.market.friendpromote.FriendPromoteOptionParam;
import com.vpu.mp.service.pojo.shop.market.friendpromote.FriendPromoteParticipateParam;
import com.vpu.mp.service.pojo.shop.market.friendpromote.FriendPromoteParticipateVo;
import com.vpu.mp.service.pojo.shop.market.friendpromote.FriendPromoteReceiveParam;
import com.vpu.mp.service.pojo.shop.market.friendpromote.FriendPromoteReceiveVo;
import com.vpu.mp.service.pojo.shop.market.friendpromote.FriendPromoteSelectParam;
import com.vpu.mp.service.pojo.shop.market.friendpromote.FriendPromoteSelectVo;
import com.vpu.mp.service.pojo.shop.market.friendpromote.FriendPromoteUpdateParam;

import static com.vpu.mp.db.shop.Tables.USER;
import static com.vpu.mp.db.shop.Tables.ORDER_INFO;

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
	private static FriendPromoteDetail fpd = FriendPromoteDetail.FRIEND_PROMOTE_DETAIL.as("fpd");

	/**
	 * 好友助力活动列表
	 *
	 * @param FriendPromoteListParam
	 * @return pageResult
	 */
	public PageResult<FriendPromoteListVo> friendPromoteList(FriendPromoteListParam param) {
		/* 查询好友助力活动 */
		Timestamp nowTime = new Timestamp(System.currentTimeMillis());
		SelectConditionStep<Record7<Integer, String, Timestamp, Timestamp, Byte, String, Byte>> sql = db()
				.select(fpa.ID, fpa.ACT_NAME, fpa.START_TIME, fpa.END_TIME, fpa.REWARD_TYPE, fpa.REWARD_CONTENT,
						fpa.IS_BLOCK)
				.from(fpa).where(fpa.DEL_FLAG.eq((byte) FriendPromoteListParam.NOT_DELETE));
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
		if (FriendPromoteListParam.STOPPED == param.getActState()) {
			sql = sql.and(fpa.IS_BLOCK.eq((byte) 1));
		}
		/* 活动状态1进行中 */
		if (FriendPromoteListParam.DOING == param.getActState()) {
			sql = sql.and(fpa.IS_BLOCK.eq((byte) 0)).and(fpa.START_TIME.lessOrEqual(nowTime))
					.and(fpa.END_TIME.greaterOrEqual(nowTime));
		}
		/* 活动状态2未开始 */
		if (FriendPromoteListParam.TODO == param.getActState()) {
			sql = sql.and(fpa.IS_BLOCK.eq((byte) 0)).and(fpa.START_TIME.greaterOrEqual(nowTime));
		}
		/* 活动状态3已结束 */
		if (FriendPromoteListParam.OUT_OF_DATE == param.getActState()) {
			sql = sql.and(fpa.IS_BLOCK.eq((byte) 0)).and(fpa.END_TIME.lessOrEqual(nowTime));
		}
		/* 整合分页信息 */
		PageResult<FriendPromoteListVo> pageResultVo = getPageResult(sql, param.getCurrentPage(), param.getPageRows(),
				FriendPromoteListVo.class);
		/* 获取领取数量和活动状态 */
		for (FriendPromoteListVo vo : pageResultVo.getDataList()) {
			/* 领取数量recNum */
			int recNum = db().select(DSL.count(fpl.PROMOTE_STATUS).as("recNum")).from(fpl)
					.where(fpl.PROMOTE_ID.eq(vo.getId()))
					.and(fpl.PROMOTE_STATUS.eq((byte) FriendPromoteListParam.RECIEVED)).fetchOptionalInto(Integer.class)
					.orElse(0);
			vo.setRecNum(recNum);
			/* 活动状态actState */
			if (FriendPromoteListParam.STOPPED == vo.getIsBlock()) {
				vo.setActState(FriendPromoteListParam.STOPPED);
			}
			if (FriendPromoteListParam.DOING == vo.getIsBlock() && nowTime.after(vo.getStartTime())
					&& nowTime.before(vo.getEndTime())) {
				vo.setActState(FriendPromoteListParam.DOING);
			}
			if (FriendPromoteListParam.TODO == vo.getIsBlock() && nowTime.before(vo.getStartTime())) {
				vo.setActState(FriendPromoteListParam.TODO);
			}
			if (FriendPromoteListParam.OUT_OF_DATE == vo.getIsBlock() && nowTime.after(vo.getEndTime())) {
				vo.setActState(FriendPromoteListParam.OUT_OF_DATE);
			}
		}
		return pageResultVo;
	}

	/**
	 * 启用或停用活动
	 *
	 * @param FriendPromoteOptionParam
	 * @return void
	 */
	public void startOrBlock(FriendPromoteOptionParam param) {
		/* 查询当前停用状态 */
		int isBlock = db().select(fpa.IS_BLOCK).from(fpa).where(fpa.ID.eq(param.getId()))
				.fetchOptionalInto(Integer.class).get();
		/* 若未停用，则停用 */
		if (isBlock == FriendPromoteOptionParam.BLOCKED) {
			db().update(fpa).set(fpa.IS_BLOCK, (byte) FriendPromoteOptionParam.NOT_BLOCK)
					.where(fpa.ID.eq(param.getId())).execute();
		}
		/* 若已停用，则启用 */
		if (isBlock == FriendPromoteOptionParam.NOT_BLOCK) {
			db().update(fpa).set(fpa.IS_BLOCK, (byte) FriendPromoteOptionParam.BLOCKED).where(fpa.ID.eq(param.getId()))
					.execute();
		}
	}

	/**
	 * 删除单个活动
	 *
	 * @param FriendPromoteOptionParam
	 * @return void
	 */
	public void deleteAct(FriendPromoteOptionParam param) {
		/* 修改del_flag */
		db().update(fpa).set(fpa.DEL_FLAG, (byte) FriendPromoteOptionParam.DELETED).where(fpa.ID.eq(param.getId()))
				.execute();
	}

	/**
	 * 查询领取明细
	 *
	 * @param FriendPromoteReceiveParam
	 * @return PageResult<FriendPromoteReceiveVo>
	 */
	public PageResult<FriendPromoteReceiveVo> receiveDetail(FriendPromoteReceiveParam param) {
		/* 设置查询条件 */
		int rewardType = db().select(fpa.REWARD_TYPE).from(fpa).where(fpa.ID.eq(param.getPromoteId()))
				.fetchOptionalInto(Integer.class).get();
		SelectConditionStep<Record6<String, String, Integer, Byte, Timestamp, String>> sql;
		/* 助力优惠券 */
		SelectConditionStep<Record6<String, String, Integer, Byte, Timestamp, String>> couponSql = db()
				.select(USER.USERNAME, USER.MOBILE, fpl.ID, fpl.PROMOTE_STATUS, fpl.SUCCESS_TIME.as("recTime"),
						fpl.ORDER_SN)
				.from(USER, fpl).where(fpl.PROMOTE_ID.eq(param.getPromoteId())).and(USER.USER_ID.eq(fpl.USER_ID));
		/* 助力商品 */
		SelectConditionStep<Record6<String, String, Integer, Byte, Timestamp, String>> goodSql = db()
				.select(USER.USERNAME, USER.MOBILE, fpl.ID, fpl.PROMOTE_STATUS, ORDER_INFO.PAY_TIME.as("recTime"),
						fpl.ORDER_SN)
				.from(fpl).leftJoin(USER).on(USER.USER_ID.eq(fpl.USER_ID)).leftJoin(ORDER_INFO)
				.on(ORDER_INFO.ORDER_SN.eq(fpl.ORDER_SN)).where(fpl.PROMOTE_ID.eq(param.getPromoteId()));

		/* 判断是商品还是优惠券 */
		sql = (rewardType == FriendPromoteReceiveParam.COUPON) ? couponSql : goodSql;
		/* 查询条件 */
		if (!StringUtils.isNullOrEmpty(param.getUsername())) {
			sql.and(USER.USERNAME.like(this.likeValue(param.getUsername())));
		}
		if (!StringUtils.isNullOrEmpty(param.getMobile())) {
			sql.and(USER.MOBILE.like(this.likeValue(param.getMobile())));
		}
		if (param.getId() != null) {
			sql.and(fpl.ID.eq(param.getId()));
		}
		if (!StringUtils.isNullOrEmpty(param.getOrderSn())) {
			sql.and(ORDER_INFO.ORDER_SN.like(this.likeValue(param.getOrderSn())));
		}
		if (param.getPromoteStatus() != FriendPromoteReceiveParam.PROMOTE_STATUS_DEFAULT) {
			sql.and(fpl.PROMOTE_STATUS.equal((byte) param.getPromoteStatus()));
		}
		/* 整合分页信息 */
		PageResult<FriendPromoteReceiveVo> pageResult = getPageResult(sql, param.getCurrentPage(), param.getPageRows(),
				FriendPromoteReceiveVo.class);

		return pageResult;
	}

	/**
	 * 发起明细
	 *
	 * @param FriendPromoteLaunchParam
	 * @return PageResult<FriendPromoteLaunchVo>
	 */
	public PageResult<FriendPromoteLaunchVo> launchDetail(FriendPromoteLaunchParam param) {
		/* 设置查询条件 */
		SelectHavingStep<Record7<Integer, String, String, Integer, Integer, BigDecimal, Byte>> sql = db()
				.select(fpl.ID, USER.USERNAME, USER.MOBILE, DSL.count(fpd.USER_ID).as("joinNum"),
						DSL.count(fpd.USER_ID).as("promoteTimes"), DSL.sum(fpd.PROMOTE_VALUE).as("promoteValue"),
						fpl.PROMOTE_STATUS)
				.from(fpl).leftJoin(USER).on(fpl.USER_ID.eq(USER.USER_ID)).leftJoin(fpd).on(fpl.ID.eq(fpd.LAUNCH_ID))
				.where(fpl.PROMOTE_ID.eq(param.getPromoteId())).groupBy(fpl.ID);
		/* 查询条件 */
		if (!StringUtils.isNullOrEmpty(param.getUsername())) {
			sql.having(USER.USERNAME.like(this.likeValue(param.getUsername())));
		}
		if (!StringUtils.isNullOrEmpty(param.getMobile())) {
			sql.having(USER.MOBILE.like(this.likeValue(param.getMobile())));
		}
		if (param.getId() != null) {
			sql.having(fpl.ID.eq(param.getId()));
		}
		if (param.getPromoteStatus() != FriendPromoteReceiveParam.PROMOTE_STATUS_DEFAULT) {
			sql.having(fpl.PROMOTE_STATUS.equal((byte) param.getPromoteStatus()));
		}
		/* 整合分页信息 */
		PageResult<FriendPromoteLaunchVo> pageResult = getPageResult(sql, param.getCurrentPage(), param.getPageRows(),
				FriendPromoteLaunchVo.class);

		return pageResult;
	}

	/**
	 * 参与明细
	 *
	 * @param param
	 * @return
	 */
	public PageResult<FriendPromoteParticipateVo> participateDetail(FriendPromoteParticipateParam param) {
		User a = USER.as("a");
		User b = USER.as("b");
		SelectHavingStep<Record7<Integer, String, String, String, String, Integer, BigDecimal>> sql = db()
				.select(fpd.LAUNCH_ID, a.USERNAME, a.MOBILE, a.INVITE_SOURCE,
						b.USERNAME.as("launchUsername"), DSL.count(fpd.USER_ID).as("promoteTimes"),
						DSL.sum(fpd.PROMOTE_VALUE).as("promoteValue"))
				.from(fpd)
				.leftJoin(a).on(fpd.USER_ID.eq(a.USER_ID))
				.leftJoin(fpl).on(fpd.LAUNCH_ID.eq(fpl.ID))
				.leftJoin(b).on(fpl.USER_ID.eq(b.USER_ID))
				.where(fpl.PROMOTE_ID.eq(param.getPromoteId()))
				.groupBy(fpd.ID);
		/* 查询条件 */
		if (!StringUtils.isNullOrEmpty(param.getUsername())) {
			sql.having(USER.USERNAME.like(this.likeValue(param.getUsername())));
		}
		if (!StringUtils.isNullOrEmpty(param.getMobile())) {
			sql.having(USER.MOBILE.like(this.likeValue(param.getMobile())));
		}
		if (param.getId() != null) {
			sql.having(fpl.ID.eq(param.getId()));
		}
		if (FriendPromoteParticipateParam.PROMOTE.equalsIgnoreCase(param.getInviteSource())) {
			sql.having(USER.INVITE_SOURCE.eq(FriendPromoteParticipateParam.PROMOTE));
		}
		/* 整合分页信息 */
		PageResult<FriendPromoteParticipateVo> pageResult = getPageResult(sql, param.getCurrentPage(), param.getPageRows(),
				FriendPromoteParticipateVo.class);

		return pageResult;

	}

	/**
	 * 添加好友助力活动
	 *
	 * @param FriendPromoteAddParam
	 * @return void
	 */
	public void addActivity(FriendPromoteAddParam param) {
		FriendPromoteActivityRecord record = new FriendPromoteActivityRecord();
		record.setShopId(getShopId());
		record.setActCode(getActCode());
		FieldsUtil.assignNotNull(param, record);
		db().executeInsert(record);

	}

	/**
	 * 生成助力活动编号
	 * 
	 */
	public static String getActCode() {
		System.currentTimeMillis();
		String sn = "FP" + System.currentTimeMillis();
		return sn;
	}

	/**
	 * 查询单个好友助力活动
	 *
	 * @param
	 * @return
	 */
	public List<FriendPromoteSelectVo> selectOne(FriendPromoteSelectParam param) {

		List<FriendPromoteSelectVo> vo = db().select().from(fpa).where(fpa.ID.eq(param.getId()))
				.fetchInto(FriendPromoteSelectVo.class);

		return vo;
	}

	/**
	 * 修改好友助力活动信息
	 *
	 * @param FriendPromoteUpdateParam
	 * @return void
	 */
	public void updateActivity(FriendPromoteUpdateParam param) {
		FriendPromoteActivityRecord record = new FriendPromoteActivityRecord();

		FieldsUtil.assignNotNull(param, record);
		db().executeUpdate(record);

	}
	
}
