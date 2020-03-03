package com.vpu.mp.service.shop.market.friendpromote;

import com.mysql.cj.util.StringUtils;
import com.vpu.mp.db.shop.tables.FriendPromoteActivity;
import com.vpu.mp.db.shop.tables.FriendPromoteDetail;
import com.vpu.mp.db.shop.tables.FriendPromoteLaunch;
import com.vpu.mp.db.shop.tables.User;
import com.vpu.mp.db.shop.tables.records.FriendPromoteActivityRecord;
import com.vpu.mp.db.shop.tables.records.FriendPromoteLaunchRecord;
import com.vpu.mp.db.shop.tables.records.FriendPromoteTimesRecord;
import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.FieldsUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.market.friendpromote.*;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.shop.image.ImageService;
import com.vpu.mp.service.shop.member.MemberService;
import org.apache.commons.lang3.math.NumberUtils;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.vpu.mp.db.shop.Tables.*;

/**
 * 好友助力
 * 
 * @author liangchen
 * @date 2019年8月7日
 */
@Service
public class FriendPromoteService extends ShopBaseService {
    @Autowired ImageService imageService;
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
	 * @param param
	 * @return pageResult
	 */
	public PageResult<FriendPromoteListVo> friendPromoteList(FriendPromoteListParam param) {
		/* 查询好友助力活动 */
		Timestamp nowTime = new Timestamp(System.currentTimeMillis());
		SelectConditionStep<Record8<Integer, String,String, Timestamp, Timestamp, Byte, String, Byte>> sql = db()
				.select(fpa.ID, fpa.ACT_CODE,fpa.ACT_NAME, fpa.START_TIME, fpa.END_TIME, fpa.REWARD_TYPE, fpa.REWARD_CONTENT,
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
	 * @param param
	 * @return void
	 */
	public void startOrBlock(FriendPromoteOptionParam param) {
		/* 查询当前停用状态 */
		int isBlock = db().select(fpa.IS_BLOCK).from(fpa).where(fpa.ID.eq(param.getId()))
				.fetchOptionalInto(Integer.class).get();
		/* 若未停用，则停用 */
		if (isBlock == FriendPromoteOptionParam.BLOCKED) {
			db().update(fpa).set(fpa.IS_BLOCK, FriendPromoteOptionParam.NOT_BLOCK)
					.where(fpa.ID.eq(param.getId())).execute();
		}
		/* 若已停用，则启用 */
		if (isBlock == FriendPromoteOptionParam.NOT_BLOCK) {
			db().update(fpa).set(fpa.IS_BLOCK, FriendPromoteOptionParam.BLOCKED).where(fpa.ID.eq(param.getId()))
					.execute();
		}
	}

	/**
	 * 删除单个活动
	 *
	 * @param param
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
	 * @param param
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
	 * @param param
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
		if ( ! FriendPromoteReceiveParam.PROMOTE_STATUS_DEFAULT.equals(param.getPromoteStatus())) {
			if (FriendPromoteReceiveParam.RECEIVED.equals(param.getPromoteStatus())) {
				sql.having(fpl.PROMOTE_STATUS.equal( param.getPromoteStatus()));
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
	 * @param param
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
	 * @param param
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

    /**
     * 小程序-好友助力
     */
    public PromoteInfo promoteInfo(PromoteParam param){
        //助力活动展示信息
        PromoteInfo promoteInfo = getPromoteInfo(param.getActCode());
        //发起的好友助力
        FriendPromoteLaunchRecord launchInfo;
        if (param.getLaunchId()!=null&&param.getLaunchId()>0){
            launchInfo = getLaunchInfo(param.getLaunchId(),null,null);
        }
        else{
            launchInfo = getLaunchInfo(null,param.getUserId(),promoteInfo.getId());
        }
        //发起用户id
        Integer launchUserId = launchInfo!=null?launchInfo.getUserId():0;
        //发起or助力标识:1发起页面，2助力页面
        Integer launchFlag = (!param.getUserId().equals(launchUserId) && launchUserId>0)?2:1;
        //设置助力进度：-1未发起，0助力中，1助力完成待领取，2助力完成已领取，
        // 3助力未领取失效，4助力未完成失败，5取消订单未领取
        promoteInfo.setPromoteStatus(launchInfo!=null?launchInfo.getPromoteStatus():-1);
        //设置已被助力总次数
        promoteInfo.setHasPromoteTimes(0);
        if (launchInfo!=null){
            promoteInfo.setHasPromoteTimes(getHasPromoteTimes(launchInfo.getId(),null,null,null));
        }

        //如果是发起页面
        if(launchFlag==1){
            //是否可以再次发起好友助力
            CanLaunch canLaunch = canLaunch(promoteInfo,launchInfo,param.getUserId());
            promoteInfo.setCanLaunch(canLaunch.getCode());
            //助力完成或者失效装修页进入后
            if (param.getLaunchId()==null&&promoteInfo.getCanLaunch()==1){
                promoteInfo.setPromoteStatus((byte)-1);
            }
            //好友助力榜
            if(launchInfo!=null&&promoteInfo.getPromoteStatus()>=0){
                promoteInfo.setPromoteDetailList(friendPromoteDetail(launchInfo.getId()));
            }
            //设置订单
            promoteInfo.setOrderSn(launchInfo==null?null:launchInfo.getOrderSn());
            //助力完成订单操作标识：0不可下单，1立即下单，2查看订单详情
            Byte orderFlag = 0;
            //奖励类型不为优惠券
            if (promoteInfo.getRewardType()!=2){
                //助力进度为完成待领取
                if (promoteInfo.getPromoteStatus()==1){
                    OrderInfoRecord orderInfo = getOrder(launchInfo.getOrderSn());
                    if (orderInfo==null||orderInfo.getOrderStatus()== OrderConstant.ORDER_CANCELLED||orderInfo.getOrderStatus()==OrderConstant.ORDER_CLOSED||DelFlag.DISABLE_VALUE.equals(orderInfo.getDelFlag())){
                        orderFlag = 1;
                    }else {
                        orderFlag = 2;
                    }
                }else if (promoteInfo.getPromoteStatus()==2){
                    orderFlag = 2;
                }
            }
            promoteInfo.setOrderFlag(orderFlag);
        }

        //否则为助力界面
        else {
            //是否可以继续助力
            CanPromote canPromote = canPromote(promoteInfo,promoteInfo.getHasPromoteTimes(),param.getUserId(),param.getLaunchId());
            promoteInfo.setCanPromote(canPromote);
            //是否可以分享获取助力次数
            Byte canShareTimes = canShareTimes(promoteInfo.getShareCreateTimes(),param.getUserId(),param.getLaunchId());
            promoteInfo.setCanShare(canShareTimes>0?(byte)1:(byte)0);
        }

        //倒计时
        promoteDownTime(promoteInfo,launchInfo);
        //已助力总值
        if (promoteInfo.getPromoteStatus()>=0){
            promoteInfo.setHasPromoteValue(hasPromoteValue(launchInfo.getId()));
            promoteInfo.setLaunchId(launchInfo.getId());
        }else {
            promoteInfo.setHasPromoteValue(0);
            promoteInfo.setHasPromoteTimes(0);
            promoteInfo.setLaunchId(null);
        }

        //更多的助力活动
        promoteInfo.setPromoteActList(promoteActList(promoteInfo.getId()));
        promoteInfo.setLaunchFlag(launchFlag);
        //todo 生成助力图片

        return promoteInfo;
    }

    /**
     * 得到指定actCode活动的信息
     * @param actCode 唯一活动码
     * @return 活动详情
     */
    public FriendPromoteActivityRecord getInfo(String actCode){
        if (actCode!=null&&!actCode.isEmpty()){
            FriendPromoteActivityRecord record = db().select().from(FRIEND_PROMOTE_ACTIVITY)
                .where(FRIEND_PROMOTE_ACTIVITY.ACT_CODE.eq(actCode))
                .fetchOne().into(FriendPromoteActivityRecord.class);
            return record;
        }
        else {
            return null;
        }
    }

    /**
     * 小程序-得到助力信息
     * @param actCode 唯一活动码
     * @return 助力信息
     */
    public PromoteInfo getPromoteInfo(String actCode){
        //展示内容promoteInfo
        PromoteInfo promoteInfo = new PromoteInfo();
        //得到当前活动部分信息
        FriendPromoteActivityRecord record = getInfo(actCode);
        //设置奖励内容
        FpRewardContent rewardContent = Util.json2Object(record.getRewardContent().substring(1,record.getRewardContent().length()-1),FpRewardContent.class,false);
        promoteInfo.setRewardContent(rewardContent);
        //设置活动id
        promoteInfo.setId(record.getId());
        //设置助力次数
        promoteInfo.setHasLaunchNum(launchTotalTimes(record.getId()));
        //设置活动库存
        promoteInfo.setMarketStore(rewardContent.getMarketStore());
        //设置活动状态
        promoteInfo.setActStatus(getActStatus(actCode));
        //设置是否停用
        promoteInfo.setIsBlock(record.getIsBlock());
        //设置是否删除
        promoteInfo.setDelFlag(record.getDelFlag());
        //设置奖励类型
        promoteInfo.setRewardType(record.getRewardType());
        //设置发起限制次数
        promoteInfo.setLaunchLimitTimes(record.getLaunchLimitTimes());
        //设置发起次数限制时长
        promoteInfo.setLaunchLimitDuration(record.getLaunchLimitDuration());
        //设置发起次数限制时长单位
        promoteInfo.setLaunchLimitUnit(record.getLaunchLimitUnit());
        //设置所需助力次数
        promoteInfo.setPromoteTimes(record.getPromoteTimes());
        //设置单个用户每天最多可帮忙助力次数
        promoteInfo.setPromoteTimesPerDay(record.getPromoteTimesPerDay());
        //设置分享可获得助力次数
        promoteInfo.setShareCreateTimes(record.getShareCreateTimes());
        //设置结束时间
        promoteInfo.setEndTime(record.getEndTime());
        //设置所需助力总值
        promoteInfo.setPromoteAmount(record.getPromoteAmount());
        //设置助力类型 0平均 1随机
        promoteInfo.setPromoteType(record.getPromoteType());
        //判断奖励类型-为赠送商品或商品折扣时
        if(record.getRewardType()==ZERO||record.getRewardType()==ONE){
            GoodsInfo goodsInfo = getGoodsInfo(rewardContent.getGoodsIds());
            if (goodsInfo==null){
                goodsInfo = new GoodsInfo();
            }
            goodsInfo.setMarketPrice(record.getRewardType()==ZERO?rewardContent.getMarketPrice():BigDecimal.ZERO);
            //设置商品信息
            promoteInfo.setGoodsInfo(goodsInfo);
            //检查活动库存是否发完
            promoteInfo.setMarketStore(promoteInfo.getMarketStore()>promoteInfo.getHasLaunchNum()?promoteInfo.getMarketStore()-promoteInfo.getHasLaunchNum():0);
            //商品库存与活动库存比较 重新设置活动库存
            if (goodsInfo.getGoodsStore()!=null){
                promoteInfo.setMarketStore(goodsInfo.getGoodsStore()>promoteInfo.getMarketStore()?promoteInfo.getMarketStore():goodsInfo.getGoodsStore());
            }
        }
        //判断奖励类型-为赠送优惠券时
        else if (record.getRewardType()==TWO){
            CouponInfo couponInfo = getCouponById(rewardContent.getRewardIds());
            //设置优惠券信息
            promoteInfo.setCouponInfo(couponInfo);
            //检查活动库存是否发完
            promoteInfo.setMarketStore(promoteInfo.getMarketStore()>promoteInfo.getHasLaunchNum()?promoteInfo.getMarketStore()-promoteInfo.getHasLaunchNum():0);
        }
        //销毁奖励内容
        promoteInfo.setRewardContent(null);
        return promoteInfo;
    }

    /**
     * 得到当前活动助力次数
     * @param id 当前助力活动id
     * @return  活动助力次数
     */
    public Integer launchTotalTimes(Integer id){
        List<Byte> promoteStatus = new ArrayList<>();
        promoteStatus.add(ONE);
        promoteStatus.add(TWO);
        Integer hasLaunchNum = db().select(DSL.count(FRIEND_PROMOTE_LAUNCH.ID))
            .from(FRIEND_PROMOTE_LAUNCH)
            .where(FRIEND_PROMOTE_LAUNCH.PROMOTE_ID.eq(id))
            .and(FRIEND_PROMOTE_LAUNCH.PROMOTE_STATUS.in(promoteStatus))
            .fetchOneInto(Integer.class);
        return hasLaunchNum;
    }

    /**
     * 根据规格id返回商品信息
     * @param prdId 规格id
     * @return 商品信息
     */
    public GoodsInfo getGoodsInfo(Integer prdId){
        GoodsInfo goodsInfo = db().select(GOODS.GOODS_ID,GOODS_SPEC_PRODUCT.PRD_ID,GOODS.GOODS_NAME,
            GOODS_SPEC_PRODUCT.PRD_IMG.as("goods_img"),GOODS_SPEC_PRODUCT.PRD_PRICE.as("goods_price"),
            GOODS_SPEC_PRODUCT.PRD_NUMBER.as("goods_store"),GOODS.UPDATE_TIME)
            .from(GOODS)
            .leftJoin(GOODS_SPEC_PRODUCT)
            .on(GOODS.GOODS_ID.eq(GOODS_SPEC_PRODUCT.GOODS_ID))
            .where(GOODS_SPEC_PRODUCT.PRD_ID.eq(prdId))
            .fetchOneInto(GoodsInfo.class);
        //图片地址添加域名
        String goodsImg = imageService.getImgFullUrl(goodsInfo.getGoodsImg());
        goodsInfo.setGoodsImg(goodsImg);
        return goodsInfo;
    }

    /**
     * 根据id返回优惠券信息
     * @param couponId 优惠券id
     * @return 优惠券信息
     */
    public CouponInfo getCouponById(Integer couponId){
        CouponInfo couponInfo = db().select(MRKING_VOUCHER.ID.as("coupon_id"),MRKING_VOUCHER.ACT_NAME,
            MRKING_VOUCHER.ACT_CODE,MRKING_VOUCHER.ALIAS_CODE,MRKING_VOUCHER.DENOMINATION,
            MRKING_VOUCHER.USE_CONSUME_RESTRICT,MRKING_VOUCHER.LEAST_CONSUME)
            .from(MRKING_VOUCHER)
            .where(MRKING_VOUCHER.ID.eq(couponId))
            .fetchOneInto(CouponInfo.class);
        return couponInfo;
    }

    /**
     * 得到助力活动状态
     * @param actCode 唯一活动码
     * @return 活动状态：0未开始，1进行中，2已结束
     */
    public Byte getActStatus(String actCode){
        Byte actStatus = 0;
        Timestamp startTime = db().select(FRIEND_PROMOTE_ACTIVITY.START_TIME)
            .from(FRIEND_PROMOTE_ACTIVITY)
            .where(FRIEND_PROMOTE_ACTIVITY.ACT_CODE.eq(actCode))
            .fetchOneInto(Timestamp.class);
        Timestamp endTime = db().select(FRIEND_PROMOTE_ACTIVITY.END_TIME)
            .from(FRIEND_PROMOTE_ACTIVITY)
            .where(FRIEND_PROMOTE_ACTIVITY.ACT_CODE.eq(actCode))
            .fetchOneInto(Timestamp.class);
        Timestamp nowTime = Util.currentTimeStamp();
        if (startTime.before(nowTime)&&endTime.after(nowTime)){
            actStatus = 1;
        }
        else if (endTime.before(nowTime)){
            actStatus = 2;
        }
        return actStatus;
    }

    /**
     * 得到发起助力信息
     * @param launchId 发起id
     * @param userId 用户id
     * @param promoteId 活动id
     * @return 助力信息
     */
    public FriendPromoteLaunchRecord getLaunchInfo(Integer launchId,Integer userId,Integer promoteId){
        FriendPromoteLaunchRecord record;
        if (launchId!=null){
            record = db().select()
                .from(FRIEND_PROMOTE_LAUNCH)
                .where(FRIEND_PROMOTE_LAUNCH.ID.eq(launchId))
                .fetchOneInto(FriendPromoteLaunchRecord.class);
        }
        else {
            record =db().select()
                .from(FRIEND_PROMOTE_LAUNCH)
                .where(FRIEND_PROMOTE_LAUNCH.USER_ID.eq(userId))
                .and(FRIEND_PROMOTE_LAUNCH.PROMOTE_ID.eq(promoteId))
                .orderBy(FRIEND_PROMOTE_LAUNCH.ID.desc())
                .fetchOneInto(FriendPromoteLaunchRecord.class);
        }
        return record;
    }

    /**
     * 得到助力次数
     * @param launchId 助力发起id
     * @return 助力次数
     */
    public Integer getHasPromoteTimes(Integer launchId,Integer promoteId,Integer userId,Timestamp currentTime){
        SelectJoinStep<Record1<Integer>> select = db().select(DSL.count(FRIEND_PROMOTE_DETAIL.ID))
            .from(FRIEND_PROMOTE_DETAIL);
        if (launchId!=null){
            select.where(FRIEND_PROMOTE_DETAIL.LAUNCH_ID.eq(launchId));
        }
        if (promoteId!=null){
            select.where(FRIEND_PROMOTE_DETAIL.PROMOTE_ID.eq(promoteId));
        }
        if (userId!=null){
            select.where(FRIEND_PROMOTE_DETAIL.USER_ID.eq(userId));
        }
        if (currentTime!=null){
            Calendar start = Calendar.getInstance();
            start.set(Calendar.HOUR_OF_DAY, 0);
            start.set(Calendar.MINUTE, 0);
            start.set(Calendar.SECOND, 0);
            start.set(Calendar.MILLISECOND, 0);
            Timestamp startTime = new Timestamp(start.getTime().getTime());
            Calendar end = Calendar.getInstance();
            end.set(Calendar.HOUR_OF_DAY, 23);
            end.set(Calendar.MINUTE, 59);
            end.set(Calendar.SECOND, 59);
            end.set(Calendar.MILLISECOND, 999);
            Timestamp endTime = new Timestamp(end.getTime().getTime());
            select.where(FRIEND_PROMOTE_DETAIL.CREATE_TIME.between(startTime,endTime));
        }
        Integer hasPromoteTimes = select.fetchOptionalInto(Integer.class)
                .orElse(NumberUtils.INTEGER_ZERO);
        return hasPromoteTimes;
    }

    /**
     * 是否可以发起好友助力活动
     * @param promoteInfo 主力活动信息
     * @param launchInfo 该活动的发起信息
     * @param userId 当前用户id
     * @return 是否可发起及文字提示
     */
    public CanLaunch canLaunch(PromoteInfo promoteInfo,FriendPromoteLaunchRecord launchInfo,Integer userId){
        CanLaunch canLaunch = new CanLaunch();
        canLaunch.setCode(NumberUtils.BYTE_ZERO);
        //检查是否停用或者删除
        if(promoteInfo.getIsBlock().equals(NumberUtils.BYTE_ONE)||promoteInfo.getDelFlag().equals(NumberUtils.BYTE_ONE)){
            canLaunch.setMsg("活动已停用或删除");
            return canLaunch;
        }
        //检查活动库存
        if (promoteInfo.getMarketStore()<=0){
            canLaunch.setMsg("活动库存不足了");
            return canLaunch;
        }
        //检查商品库存
        if (promoteInfo.getRewardType()!=2){
            if (promoteInfo.getGoodsInfo().getGoodsStore()<=0){
                canLaunch.setMsg("活动商品库存不足了");
                return canLaunch;
            }
        }
        //检查有效期
        if (promoteInfo.getActStatus()==0){
            canLaunch.setMsg("活动未开始");
            return canLaunch;
        }
        if (promoteInfo.getActStatus()==2){
            canLaunch.setMsg("活动已结束");
            return canLaunch;
        }
        //检查当前发起状态
        if(promoteInfo.getPromoteStatus()==0||promoteInfo.getPromoteStatus()==1){
            canLaunch.setMsg("您已发起快邀请好友助力把");
            return canLaunch;
        }
        //检查可发起次数
        if(promoteInfo.getPromoteStatus()!=0&&promoteInfo.getPromoteStatus()!=1){
            //从未发起
            if (promoteInfo.getPromoteStatus()==-1){
                canLaunch.setCode(NumberUtils.BYTE_ONE);
            }else {
                //发起次数不限
                if (promoteInfo.getLaunchLimitTimes()==0){
                    canLaunch.setCode(NumberUtils.BYTE_ONE);
                }else {
                    //已发起助力次数
                    Integer launchTimes = promoteLaunchTimes(promoteInfo.getId(),userId,promoteInfo.getLaunchLimitDuration(),promoteInfo.getLaunchLimitUnit(),launchInfo.getLaunchTime());
                    if (launchTimes<promoteInfo.getLaunchLimitTimes()){
                        canLaunch.setCode(NumberUtils.BYTE_ONE);
                    }
                }
            }
        }
        return canLaunch;
    }

    /**
     * 某时间段内最多可发好友助力活动的次数
     * @param promoteId 活动id
     * @param userId 用户id
     * @param duration 限制时间数
     * @param unit 时间单位
     * @param launchTime 发起时间
     * @return 次数
     */
    public Integer promoteLaunchTimes(Integer promoteId,Integer userId,Integer duration,Byte unit,Timestamp launchTime){
        Duration timeDuration;
        switch (unit){
            //天
            case 1:
                timeDuration = getDurationDay(launchTime,duration);
                break;
            //周
            case 2:
                timeDuration = getDurationDay(launchTime,duration*7);
                break;
            //月
            case 3:
                timeDuration = getDurationDay(launchTime,duration*30);
                break;
            //年
            case 4:
                timeDuration = getDurationDay(launchTime,duration*365);
                break;
            default:
                return 0;
        }
        Integer launchTimes = db().select(DSL.count(FRIEND_PROMOTE_LAUNCH.ID))
            .from(FRIEND_PROMOTE_LAUNCH)
            .where(FRIEND_PROMOTE_LAUNCH.PROMOTE_ID.eq(promoteId))
            .and(FRIEND_PROMOTE_LAUNCH.USER_ID.eq(userId))
            .and(FRIEND_PROMOTE_LAUNCH.LAUNCH_TIME.between(timeDuration.getStartTime(),timeDuration.getEndTime()))
            .fetchOptionalInto(Integer.class)
            .orElse(0);
        return launchTimes;
    }

    /**
     * 任意一天和从此时间开始起多少天后的开始和结束时间
     * @param day 开始时间
     * @param limitDuration 持续时长
     * @return 起止时间
     */
    public Duration getDurationDay(Timestamp day,Integer limitDuration){
        Duration duration = new Duration();
        duration.setStartTime(day);
        Calendar c = Calendar.getInstance();
        c.setTime(day);
        c.add(Calendar.DATE,limitDuration);
        Timestamp endTime = new Timestamp(c.getTimeInMillis());
        duration.setEndTime(endTime);
        return duration;
    }

    /**
     * 好友助力明细列表
     * @param launchId 发起id
     * @return 助力详情
     */
    public List<PromoteDetail> friendPromoteDetail(Integer launchId){
        List<PromoteDetail> detailList = db().select(FRIEND_PROMOTE_DETAIL.PROMOTE_VALUE,
            USER.USERNAME,USER.USER_ID,USER_DETAIL.USER_AVATAR)
            .from(FRIEND_PROMOTE_DETAIL)
            .leftJoin(USER).on(FRIEND_PROMOTE_DETAIL.USER_ID.eq(USER.USER_ID))
            .leftJoin(USER_DETAIL).on(FRIEND_PROMOTE_DETAIL.USER_ID.eq(USER_DETAIL.USER_ID))
            .where(FRIEND_PROMOTE_DETAIL.LAUNCH_ID.eq(launchId))
            .orderBy(FRIEND_PROMOTE_DETAIL.ID.desc())
            .limit(10)
            .fetchInto(PromoteDetail.class);
        for(PromoteDetail item:detailList){
            item.setUsername((item.getUsername()!=null&&!item.getUsername().isEmpty())?item.getUsername():item.getUserId().toString());
            item.setUserAvatar((item.getUserAvatar()!=null&&!item.getUserAvatar().isEmpty())?item.getUserAvatar():"/image/admin/head_icon.png");
        }
        return detailList;
    }

    /**
     * 得到订单信息
     * @param orderSn 订单编号
     * @return 订单信息
     */
    public OrderInfoRecord getOrder(String orderSn){
        OrderInfoRecord orderInfo = db().select()
            .from(ORDER_INFO)
            .where(ORDER_INFO.ORDER_SN.eq(orderSn))
            .fetchOneInto(OrderInfoRecord.class);
        return orderInfo;
    }

    /**
     * 校验是否可助力
     * @param promoteInfo 助力详情
     * @param hasPromoteTimes 已经助力次数
     * @param userId 用户id
     * @param launchId 发起id
     * @return 是否可助力信息
     */
    public CanPromote canPromote(PromoteInfo promoteInfo,Integer hasPromoteTimes,Integer userId,Integer launchId){
        CanPromote canPromote = new CanPromote();
        //是否发起
        if(promoteInfo.getPromoteStatus()==-1){
            canPromote.setCode((byte)0);
            canPromote.setMsg("该助力申请未发起");
            return canPromote;
        }
        //是否已完成助力
        if (hasPromoteTimes>=promoteInfo.getPromoteTimes()||promoteInfo.getPromoteStatus()==1||promoteInfo.getPromoteStatus()==2||promoteInfo.getPromoteStatus()==3){
            canPromote.setCode((byte)0);
            canPromote.setMsg("助力已完成");
            return canPromote;
        }
        //判断当天助力次数限制
        if(promoteInfo.getPromoteTimesPerDay()>0){
            Integer usedPromoteTimesCurrentDay = getHasPromoteTimes(null,promoteInfo.getId(),userId,DateUtil.getLocalDateTime());
            if (usedPromoteTimesCurrentDay>=promoteInfo.getPromoteTimesPerDay()){
                canPromote.setCode((byte)0);
                canPromote.setMsg("今天的助力次数已经用完了");
                return canPromote;
            }
        }
        //获取所有可助力的次数
        Integer promoteTimesInfo = promoteTimesInfo(userId,launchId).getOwnPromoteTimes();
        Integer ownPromoteTimes = promoteTimesInfo+1;
        //判断所有的助力次数限制
        Integer usedPromoteTimes = getHasPromoteTimes(launchId,null,userId,null);
        if (usedPromoteTimes>ownPromoteTimes){
            canPromote.setCode((byte)0);
            canPromote.setMsg("助力次数已用完");
            return canPromote;
        }
        //活动是否失效
        if (promoteInfo.getPromoteTimes()==0&&promoteInfo.getActStatus()==2){
            canPromote.setCode((byte)0);
            canPromote.setMsg("助力次数已用完");
            return canPromote;
        }
        canPromote.setCode((byte)1);
        return canPromote;
    }

    /**
     * 助力次数详情
     * @param userId 用户id
     * @param launchId 发起id
     * @return 次数详情
     */
    public FriendPromoteTimesRecord promoteTimesInfo(Integer userId,Integer launchId){
        FriendPromoteTimesRecord record = db().select()
            .from(FRIEND_PROMOTE_TIMES)
            .where(FRIEND_PROMOTE_TIMES.USER_ID.eq(userId))
            .and(FRIEND_PROMOTE_TIMES.LAUNCH_ID.eq(launchId))
            .fetchOneInto(FriendPromoteTimesRecord.class);
        return record;
    }

    /**
     * 获得用户可分享次数
     * @param shareCreateTimes 分享可获得助力次数
     * @param userId 用户id
     * @param launchId 发起id
     * @return 可分享次数
     */
    public Byte canShareTimes(Byte shareCreateTimes,Integer userId,Integer launchId){
        if(shareCreateTimes==0){
            return 0;
        }
        Integer hasShareTimes = promoteTimesInfo(userId,launchId)!=null?promoteTimesInfo(userId,launchId).getShareTimes():0;
        hasShareTimes = (hasShareTimes!=null&&hasShareTimes>0)?hasShareTimes:0;
        Integer canShareTimes =shareCreateTimes-hasShareTimes;
        canShareTimes = canShareTimes>0?canShareTimes:0;
        return canShareTimes.byteValue();
    }

    /**
     * 倒计时
     * @param promoteInfo 活动信息
     * @param launchInfo 发起信息
     */
    public void promoteDownTime(PromoteInfo promoteInfo,FriendPromoteLaunchRecord launchInfo){
        long secEndTime = promoteInfo.getEndTime().getTime();
        //如果助力进度是完成待领取
        if (promoteInfo.getPromoteStatus()==1){
            Integer sec = promoteDurationSec(promoteInfo.getLaunchLimitUnit(),promoteInfo.getLaunchLimitDuration());
            Integer surplusSecond = (int)launchInfo.getCreateTime().getTime()+sec*1000-(int)DateUtil.getLocalDateTime().getTime();
            promoteInfo.setSurplusSecond(surplusSecond>0?surplusSecond/1000:0);
        }
        //活动进行中 助力未开始
        else if (promoteInfo.getPromoteStatus()==0&&promoteInfo.getActStatus()==1){
            long secDeadTime = 24*60*60;
            long secLaunchTime = launchInfo.getLaunchTime().getTime();
            if (secEndTime<secDeadTime+secLaunchTime){
                promoteInfo.setSurplusSecond(((int)secEndTime-(int)DateUtil.getLocalDateTime().getTime())/1000);
            }else {
                promoteInfo.setSurplusSecond(((int)(secDeadTime+secLaunchTime)-(int)DateUtil.getLocalDateTime().getTime())/1000);
            }
        }
        else {
            promoteInfo.setSurplusSecond(promoteInfo.getActStatus()==1?((int)secEndTime-(int)DateUtil.getLocalDateTime().getTime())/1000:0);
        }
    }

    /**
     * 计算奖励有效期有多少秒
     * @param unit 时间单位
     * @param duration 持续时长
     * @return second
     */
    public Integer promoteDurationSec(Byte unit,Integer duration){
        Integer sec ;
        switch (unit){
            //小时
            case 0:
                sec=duration*3600;
                break;
            //天
            case 1:
                sec=duration*24*3600;
                break;
            //周
            case 2:
                sec=duration*7*24*3600;
                break;
            default:
                sec = 0;
                break;
        }
        return sec;
    }

    /**
     * 得到助力总值
     * @param launchId 发起id
     * @return 助力总值
     */
    public Integer hasPromoteValue(Integer launchId){
        Integer promoteValue = db().select(DSL.sum(FRIEND_PROMOTE_DETAIL.PROMOTE_VALUE))
            .from(FRIEND_PROMOTE_DETAIL)
            .where(FRIEND_PROMOTE_DETAIL.LAUNCH_ID.eq(launchId))
            .fetchOptionalInto(Integer.class)
            .orElse(0);
        return promoteValue;
    }
    public List<PromoteActList> promoteActList(Integer id){
        List<PromoteActList> promoteActList = db().select()
            .from(FRIEND_PROMOTE_ACTIVITY)
            .where(FRIEND_PROMOTE_ACTIVITY.IS_BLOCK.eq((byte)0))
            .and(FRIEND_PROMOTE_ACTIVITY.DEL_FLAG.eq((byte)0))
            .and(FRIEND_PROMOTE_ACTIVITY.END_TIME.greaterThan(DateUtil.getSqlTimestamp()))
            .and(FRIEND_PROMOTE_ACTIVITY.ID.notEqual(id))
            .orderBy(FRIEND_PROMOTE_ACTIVITY.ID.desc())
            .limit(10)
            .fetchInto(PromoteActList.class);
        if (promoteActList==null){
            return null;
        }
        for (PromoteActList item:promoteActList){
            Integer receiveNum = db().select(DSL.count(FRIEND_PROMOTE_LAUNCH.ID))
                .from(FRIEND_PROMOTE_LAUNCH)
                .where(FRIEND_PROMOTE_LAUNCH.PROMOTE_STATUS.eq((byte)1).or(FRIEND_PROMOTE_LAUNCH.PROMOTE_STATUS.eq((byte)2)))
                .and(FRIEND_PROMOTE_LAUNCH.PROMOTE_ID.eq(item.getId()))
                .fetchOneInto(Integer.class);
            //活动状态
            item.setActStatus(getActStatus(item.getActCode()));
            //活动奖励
            item.setFpRewardContent(Util.json2Object(item.getRewardContent().substring(1,item.getRewardContent().length()-1),FpRewardContent.class,false));
            //奖励内容
            if (item.getRewardType()==TWO){
                CouponInfo couponInfo = getCouponById(item.getFpRewardContent().getRewardIds());
                couponInfo.setMarketStore(item.getFpRewardContent().getMarketStore()>receiveNum?item.getFpRewardContent().getMarketStore()-receiveNum:0);
                item.setCouponInfo(couponInfo);
            }else{
                GoodsInfo goodsInfo = getGoodsInfo(item.getFpRewardContent().getGoodsIds());
                goodsInfo.setMarketPrice(item.getRewardType()==ONE?item.getFpRewardContent().getMarketPrice():BigDecimal.ZERO);
                goodsInfo.setMarketStore(item.getFpRewardContent().getMarketStore());
                //设置库存
                goodsInfo.setMarketStore(goodsInfo.getGoodsStore()>goodsInfo.getMarketStore()?goodsInfo.getMarketStore():goodsInfo.getGoodsStore());
                goodsInfo.setMarketStore(goodsInfo.getMarketStore()>receiveNum?goodsInfo.getMarketStore()-receiveNum:0);
                item.setGoodsInfo(goodsInfo);
            }
        }
        return promoteActList;
    }

    /**
     * 小程序-发起好友助力
     *
     */
    public void friendPromoteLaunch(PromoteParam param){
        PromoteInfo  promoteInfo = getPromoteInfo(param.getActCode());
        //最新一次的发起的好友助力
        FriendPromoteLaunchRecord launchInfo = getLaunchInfo(null,param.getUserId(),promoteInfo.getId());
        //助力进度：-1未发起，0助力中，1助力完成待领取，2助力完成已领取,3助力未领取失效，4助力未完成失败，5取消订单未领取
        promoteInfo.setPromoteStatus(launchInfo!=null?launchInfo.getPromoteStatus():-1);
        //是否可以再次发起好友助力
        CanLaunch canLaunch = canLaunch(promoteInfo,launchInfo,param.getUserId());
        promoteInfo.setCanLaunch(canLaunch.getCode());
    }
}
