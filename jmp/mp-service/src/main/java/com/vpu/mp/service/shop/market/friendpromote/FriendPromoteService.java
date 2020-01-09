package com.vpu.mp.service.shop.market.friendpromote;

import com.mysql.cj.util.StringUtils;
import com.vpu.mp.db.shop.tables.FriendPromoteActivity;
import com.vpu.mp.db.shop.tables.FriendPromoteDetail;
import com.vpu.mp.db.shop.tables.FriendPromoteLaunch;
import com.vpu.mp.db.shop.tables.User;
import com.vpu.mp.db.shop.tables.records.FriendPromoteActivityRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.FieldsUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.friendpromote.*;
import com.vpu.mp.service.shop.member.MemberService;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.vpu.mp.db.shop.Tables.ORDER_INFO;
import static com.vpu.mp.db.shop.Tables.USER;

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
	
	private static final Byte[] s=new Byte[] {(byte)0,(byte)1};
	private static final byte ZERO = 0;
	private static final byte ONE = 1;
	private static final byte TWO = 2;
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
		/* 活动状态0全部 */
		if (FriendPromoteListParam.ALL == param.getActState()) {
		}
		/* 活动状态1进行中 */
		if (FriendPromoteListParam.DOING == param.getActState()) {
			sql = sql.and(fpa.IS_BLOCK.eq(ZERO)).and(fpa.START_TIME.lessOrEqual(nowTime))
					.and(fpa.END_TIME.greaterOrEqual(nowTime));
		}
		/* 活动状态2未开始 */
		if (FriendPromoteListParam.TODO == param.getActState()) {
			sql = sql.and(fpa.IS_BLOCK.eq(ZERO)).and(fpa.START_TIME.greaterOrEqual(nowTime));
		}
		/* 活动状态3已结束 */
		if (FriendPromoteListParam.OUT_OF_DATE == param.getActState()) {
			sql = sql.and(fpa.IS_BLOCK.eq(ZERO)).and(fpa.END_TIME.lessOrEqual(nowTime));
		}
		/* 活动状态4已停用 */
		if (FriendPromoteListParam.STOPPED == param.getActState()) {
			sql = sql.and(fpa.IS_BLOCK.eq((byte) 1));
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
			if (1 == vo.getIsBlock()) {
				vo.setActState(FriendPromoteListParam.STOPPED);
			}
			else if ( nowTime.after(vo.getStartTime())&& nowTime.before(vo.getEndTime())) {
				vo.setActState(FriendPromoteListParam.DOING);
			}
			else if (nowTime.before(vo.getStartTime())) {
				vo.setActState(FriendPromoteListParam.TODO);
			}
			else if (nowTime.after(vo.getEndTime())) {
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
			if (param.getPromoteStatus()==FriendPromoteReceiveParam.RECEIVED) {
				sql.and(fpl.PROMOTE_STATUS.equal((byte) param.getPromoteStatus()));
			}else {
				sql.and(fpl.PROMOTE_STATUS.notEqual((byte) FriendPromoteReceiveParam.RECEIVED));
			}
			
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
				.where(fpl.PROMOTE_ID.eq(param.getPromoteId())).groupBy(fpl.ID,USER.USERNAME, USER.MOBILE,fpl.PROMOTE_STATUS);
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
			if (param.getPromoteStatus()==FriendPromoteReceiveParam.RECEIVED) {
				sql.having(fpl.PROMOTE_STATUS.equal((byte) param.getPromoteStatus()));
			}else {
				sql.having(fpl.PROMOTE_STATUS.notEqual((byte) FriendPromoteReceiveParam.RECEIVED));
			}
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
				.groupBy(fpd.LAUNCH_ID,a.USERNAME, a.MOBILE, a.INVITE_SOURCE,
						b.USERNAME.as("launchUsername"));
		/* 查询条件 */
		if (!StringUtils.isNullOrEmpty(param.getUsername())) {
			sql.having(a.USERNAME.like(this.likeValue(param.getUsername())));
		}
		if (!StringUtils.isNullOrEmpty(param.getMobile())) {
			sql.having(a.MOBILE.like(this.likeValue(param.getMobile())));
		}
		if (param.getLaunchId() != null) {
			sql.having(fpd.LAUNCH_ID.eq(param.getLaunchId()));
		}
		if (!StringUtils.isNullOrEmpty(param.getInviteSource())) {
			if (MemberService.INVITE_SOURCE_PROMOTE.equalsIgnoreCase(param.getInviteSource())) {
				sql.having(a.INVITE_SOURCE.eq(MemberService.INVITE_SOURCE_PROMOTE));
			}else {
				sql.having(a.INVITE_SOURCE.notEqual(MemberService.INVITE_SOURCE_PROMOTE).or(a.INVITE_SOURCE.isNull()));
			}
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
	
	/**
	 * 助力活动信息
	 * @param promoteId
	 * @param actCode
	 * @return
	 */
	public FriendPromoteActivityRecord promoteInfo(Integer promoteId,String actCode) {
		SelectWhereStep<FriendPromoteActivityRecord> selectFrom = db().selectFrom(fpa);
		if(promoteId>0) {
			selectFrom.where(fpa.ID.eq(promoteId));
		}if(!org.springframework.util.StringUtils.isEmpty(actCode)) {
			selectFrom.where(fpa.ACT_CODE.eq(actCode));
		}
		return selectFrom.fetchAny();
	}
	
	/**
	 * 获取结束前后
	 * @param hours
	 * @return
	 */
	public List<FriendPromoteSelectVo> getLaunchListByHour(Integer hours) {
		Timestamp timeStampPlus = DateUtil.getTimeStampPlus(hours, ChronoUnit.HOURS);
		String date = DateUtil.dateFormat("yyyy-MM-dd HH:mm", timeStampPlus);
		Result<Record> fetch = db()
				.select(fpl.asterisk(),fpa.ACT_CODE,fpa.ACT_NAME,fpa.REWARD_CONTENT,fpa.REWARD_TYPE).from(fpl,
						fpa)
				.where(fpl.PROMOTE_ID.eq(fpa.ID).and(fpa.DEL_FLAG.eq(ZERO)).and(fpl.DEL_FLAG.eq(ZERO)).and(fpl.PROMOTE_STATUS.eq(ZERO))
						.and(dateFormat(fpa.END_TIME, DateUtil.DATE_MYSQL_DAY).eq(date)))
				.fetch();
		List<FriendPromoteSelectVo> into = new ArrayList<FriendPromoteSelectVo>();
		if (fetch != null) {
			into = fetch.into(FriendPromoteSelectVo.class);
		}
		return into;
	}
	
	/**
	 * 获取助力失败列表
	 * @param hours
	 * @return
	 */
	public List<FriendPromoteSelectVo> getPromoteFailedList(Integer hours) {
		Timestamp timeStampPlus = DateUtil.getTimeStampPlus(hours, ChronoUnit.HOURS);
		String date = DateUtil.dateFormat("yyyy-MM-dd HH:mm", timeStampPlus);
		Result<Record> fetch = db()
				.select(fpl.asterisk(),fpa.ACT_CODE,fpa.ACT_NAME,fpa.REWARD_CONTENT,fpa.REWARD_TYPE,fpa.FAILED_SEND_TYPE,fpa.FAILED_SEND_CONTENT).from(fpl,
						fpa)
				.where(fpl.PROMOTE_ID.eq(fpa.ID).and(
						fpa.DEL_FLAG.eq(ZERO)).and(fpl.DEL_FLAG.eq(ZERO)).and(fpl.PROMOTE_STATUS.eq(ZERO))
								.and(dateFormat(fpl.LAUNCH_TIME, DateUtil.DATE_MYSQL_DAY).eq(date).or(fpa.END_TIME.eq(DateUtil.getLocalDateTime()))))
				.fetch();
		List<FriendPromoteSelectVo> into = new ArrayList<FriendPromoteSelectVo>();
		if (fetch != null) {
			into = fetch.into(FriendPromoteSelectVo.class);
		}
		return into;
	}
	
	/**
	 * 修改助力状态
	 * @param staus
	 * @param id
	 * @return
	 */
	public int upPromoteInfo(Byte staus,Integer id) {
		return db().update(fpl).set(fpl.PROMOTE_STATUS,staus ).where(fpl.ID.eq(id)).execute();
	}
	
	/**
	 * 助力失效前一小时
	 * @param hours
	 * @return
	 */
	public List<FriendPromoteSelectVo> getPromoteWaitReceiveList(Integer hours) {
		logger().info("运行助力失效前一小时的sql");
		Timestamp timeStampPlus = DateUtil.getTimeStampPlus(hours, ChronoUnit.HOURS);
		String date = DateUtil.dateFormat("yyyy-MM-dd HH:mm", timeStampPlus);
		SelectConditionStep<Record> where = db()
				.select(fpl.asterisk(), fpa.ACT_CODE, fpa.ACT_NAME, fpa.REWARD_CONTENT, fpa.REWARD_TYPE).from(fpl, fpa)
				.where(fpl.PROMOTE_ID.eq(fpa.ID).and(fpa.REWARD_TYPE.in(s)).and(fpa.DEL_FLAG.eq(ZERO))
						.and(fpl.DEL_FLAG.eq(ZERO)).and(fpl.PROMOTE_STATUS.eq(ONE)));
		Field<String> left = DSL.left(DSL.dateAdd(fpl.SUCCESS_TIME.cast(Date.class),
				DSL.when(fpa.REWARD_DURATION_UNIT.eq(ZERO), fpa.REWARD_DURATION)
						.when(fpa.REWARD_DURATION_UNIT.eq(ONE), fpa.REWARD_DURATION.multiply(24))
						.when(fpa.REWARD_DURATION_UNIT.eq(TWO), fpa.REWARD_DURATION.multiply(7).multiply(24)).otherwise(1),
				DatePart.HOUR).cast(String.class), 16);
		where.and(left.eq(date));
		Result<Record> fetch = where.fetch();
		List<FriendPromoteSelectVo> into = new ArrayList<FriendPromoteSelectVo>();
		if (fetch != null) {
			into = fetch.into(FriendPromoteSelectVo.class);
		}
		return into;
	}
	
	public FriendPromoteSelectVo getUserLaunchInfo(Integer launchId) {
		Record fetchAny = db()
				.select(fpl.asterisk(), fpa.ACT_CODE, fpa.ACT_NAME, fpa.REWARD_CONTENT, fpa.REWARD_TYPE,
						fpa.FAILED_SEND_TYPE, fpa.FAILED_SEND_CONTENT)
				.from(fpl, fpa).where(fpl.PROMOTE_ID.eq(fpa.ID).and(fpl.ID.eq(launchId))).fetchAny();
		FriendPromoteSelectVo vo = null;
		if (fetchAny != null) {
			vo = fetchAny.into(FriendPromoteSelectVo.class);
		}
		return vo;
	}
}
