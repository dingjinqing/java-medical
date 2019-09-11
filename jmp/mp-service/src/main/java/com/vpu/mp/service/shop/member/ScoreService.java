package com.vpu.mp.service.shop.member;


import static com.vpu.mp.db.shop.Tables.USER;
import static com.vpu.mp.db.shop.Tables.USER_SCORE;
import static com.vpu.mp.service.pojo.shop.member.MemberOperateRecordEnum.ADMIN_OPERATION;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.LANGUAGE_TYPE_MEMBER;
import static com.vpu.mp.service.pojo.shop.member.score.ScoreStatusConstant.NO_USE_SCORE_STATUS;
import static com.vpu.mp.service.pojo.shop.member.score.ScoreStatusConstant.REFUND_SCORE_STATUS;
import static com.vpu.mp.service.pojo.shop.member.score.ScoreStatusConstant.USED_SCORE_STATUS;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.IS_FROM_REFUND_Y;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.TRADE_CONTENT_BY_SCORE;
import static org.jooq.impl.DSL.sum;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.jooq.Record7;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.vpu.mp.db.shop.tables.records.TradesRecordRecord;
import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.db.shop.tables.records.UserScoreRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.foundation.util.VoTranslator;
import com.vpu.mp.service.pojo.shop.member.account.ScoreParam;
import com.vpu.mp.service.pojo.shop.member.score.ScorePageListParam;
import com.vpu.mp.service.pojo.shop.member.score.ScorePageListVo;

import jodd.util.StringUtil;
/**
 * 
 * @author 黄壮壮
 * @Date:  2019年8月23日
 * @Description: 积分服务
 */
@Service
public class ScoreService extends ShopBaseService {
	
	/** -积分有效的状态 */
	final Byte[] AVAILABLE_STATUS = new Byte[] { NO_USE_SCORE_STATUS, REFUND_SCORE_STATUS };
	@Autowired
	private MemberService member;	
	
	/**
	 *   创建用户积分表
	 * @param param 积分变动相关数据
	 * @param subAccountId 操作员id
	 * @param userId 用户id
	 * @param tradeType  交易类型说明 如  微信支付类型{@link com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.WX_PAY }
	 * @param tradeFlow  资金流向类型  如收入 {@link com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.TRADE_FLOW_INCOME}
	 * @return JsonResultCode
	 * @throws MpException 
	 */
	public void updateMemberScore(ScoreParam param, Integer subAccountId, Integer userId, Byte tradeType,
			Byte tradeFlow,String language) throws MpException {

		/**  1. 校验userId是否存在数据库中 */
		if (userId <= 0) {
			//throw new MpException(JsonResultCode.CODE_MEMEBER_NOT_EXIST);
			throw new MpException(JsonResultCode.CODE_MEMEBER_NOT_EXIST);
		}
		/** 1.1 查询用户 */
		UserRecord dbUser = member.getUserRecordById(userId);

		if (dbUser == null) {
			//throw new MpException(JsonResultCode.CODE_MEMEBER_NOT_EXIST);
			throw new MpException(JsonResultCode.CODE_MEMEBER_NOT_EXIST);
		}

		/** 2. 验证积分与数据库保持一致，批量修改时为最小值，即大于等于 */
		Integer scoreDis = param.getScoreDis();
		
		/** -目前会员存在数据录中的积分 */
		Integer dbScore = dbUser.getScore();
		/** -校验数据库中的积分与目前从前前端传入的积分是否一致 */
		if (dbScore < scoreDis) {
			//throw new MpException(JsonResultCode.CODE_MEMBER_SCORE_NOT_SAME);

			throw new MpException(JsonResultCode.CODE_MEMBER_SCORE_NOT_SAME);
		}

		/** 3. 准备数据  */

		/** 3.1 处理备注  */
		final String remark ;
		if (StringUtils.isEmpty(param.getRemark())) {
			/** -默认管理员操作 国际化*/
			String value = ADMIN_OPERATION.getValue();
			remark = Util.translateMessage(language,value,LANGUAGE_TYPE_MEMBER);
			logger().info("remark: "+remark);
		}else {
			remark = param.getRemark();
		}

		/** 3.2 获取积分流水号  */
		String flowOn = generateFlowNo();

		/** 3.3 可用剩余积分  */
		/** -本条积分记录的积分变动数额 */
		Integer score = param.getScore();
		/** -本条积分记录可用的数额 */
		Integer usableScore = score > 0 ? score : 0;
		String orderSn = param.getOrderSn() == null ? "" : param.getOrderSn();
		
		try {
			this.transaction(()->{
				logger().info("开始执行事务");
				try {
					/** 4 如果时负数，则消耗积分 */
					if (score < 0) {
						/** 消耗的积分超出可用积分 */
						if(Math.abs(score)>dbScore) {
							throw new MpException(JsonResultCode.CODE_MEMBER_SCORE_ERROR);
						}
						/** -消耗积分 */
						//useUserScore(userId, Math.abs(score), orderSn);
						useUserScore(userId, Math.abs(score));
					}
			
				}catch(MpException e) {
					logger().info("正在处理异常");
					throw e;
				}
				
				/** 5. 添加积分记录  */
				UserScoreRecord userScoreRecord = new UserScoreRecord();
				/** 5.1 填充数据 */
				//TODO 还有一些数据不知道从哪些业务传递过来的如goods_id,desc,identity_id 
				userScoreRecord.setScore(score);
				userScoreRecord.setUserId(userId);
				userScoreRecord.setRemark(remark);
				userScoreRecord.setAdminUser(String.valueOf(subAccountId));
				userScoreRecord.setOrderSn(orderSn);
				userScoreRecord.setFlowNo(flowOn);
				userScoreRecord.setUsableScore(usableScore);
				logger().info(String.valueOf(param.getScoreStatus()));
				userScoreRecord.setStatus(param.getScoreStatus());
				userScoreRecord.setExpireTime(param.getExpiredTime());
				
				/** -判断是否为退款积分 */
				if(param.getIsFromRefund() !=null && param.getIsFromRefund()==IS_FROM_REFUND_Y.getValue()) {
					userScoreRecord.setStatus(REFUND_SCORE_STATUS);
					UserScoreRecord userScore = getScoreRecordByOrderSn(userId,orderSn);
					userScoreRecord.setExpireTime(userScore.getExpireTime());
				}
			
				
				/** 5.2 添加 */
				/** 在user_score中添加积分记录 */
				addUserScoreRecord(userScoreRecord);
		
				
				/** 6. 更新用户积分 */
				/** 6.1 获取操作后的积分 */
				Integer totalScore = getTotalAvailableScoreById(userId);
				/** 6.2 更新用户积分 */
				updateUserScore(userId, totalScore);
				
				
				/** 7. 记录更新record action */
				/** 7.1 准备数据 */
				TradesRecordRecord tradesRecord=new TradesRecordRecord();
			
				tradesRecord.setTradeTime(DateUtil.getLocalDateTime());
				tradesRecord.setTradeNum(BigDecimal.valueOf(Math.abs(score)));
				tradesRecord.setTradeSn(orderSn);
				tradesRecord.setUserId(userId);
				/** -这是更新积分的明细所以交易内容为积分 */
				tradesRecord.setTradeContent(TRADE_CONTENT_BY_SCORE.getValue());
				tradesRecord.setTradeType(tradeType);
				tradesRecord.setTradeFlow(tradeFlow);
				tradesRecord.setTradeStatus(tradeFlow);
				
				
				/** -交易记录表-记录交易的数据信息  */
				insertTradesRecord(tradesRecord);
		
			});
		}catch(DataAccessException e) {
			/** 从事务抛出的DataAccessException中获取我们自定义的异常*/
			Throwable cause = e.getCause();
			MpException ex = (MpException)cause;
			throw ex;
		}
		return ;
	}
	

	/**
	 * 通过订单编号获取用户积分记录
	 * @param userId 用户id
	 * @param orderSn 订单号
	 * @return UserScoreRecord
	 */
	private UserScoreRecord getScoreRecordByOrderSn(Integer userId, String orderSn) {
		 return db().selectFrom(USER_SCORE).where(USER_SCORE.USER_ID.eq(userId))
					.and(USER_SCORE.ORDER_SN.eq(orderSn))
					.and(USER_SCORE.SCORE.ge(0))
					.orderBy(USER_SCORE.CREATE_TIME)
					.limit(1)
					.fetchOne()
					.into(UserScoreRecord.class);
	}

	/**
	 * 更新交易记录
	 * @param tradesRecord
	 */
	private void insertTradesRecord(TradesRecordRecord tradesRecord) {
		
		db().executeInsert(tradesRecord);		
	}

	/**
	 * 更新用户积分
	 * 
	 * @param userId
	 * @param totalScore
	 */
	private void updateUserScore(Integer userId, Integer totalScore) {
		db().update(USER).set(USER.SCORE, totalScore).where(USER.USER_ID.eq(userId)).execute();
	}

	/**
	 * 获取用户可用积分
	 * 
	 * @param userId
	 */
	private Integer getTotalAvailableScoreById(Integer userId) {
		List<Byte> list = Arrays.asList(AVAILABLE_STATUS);
		Timestamp localDateTime = DateUtil.getLocalDateTime();
		/** 根据用户id,status,有效期 */
		Integer sum = db().select(DSL.sum(USER_SCORE.USABLE_SCORE)).from(USER_SCORE)
				.where(USER_SCORE.USER_ID.eq(userId)).and(USER_SCORE.STATUS.in(list))
				.and(USER_SCORE.EXPIRE_TIME.ge(localDateTime).or(USER_SCORE.EXPIRE_TIME.isNull()))
				.fetchOneInto(Integer.class);
		logger().info("计算积分为： "+sum);
		return sum;
	}

	
	
	/**
	 * 在user_score中添加积分记录 
	 * @param userScoreRecord
	 */
	private void addUserScoreRecord(UserScoreRecord record) {
		db().executeInsert(record);
//		db().insertInto(USER_SCORE, USER_SCORE.SCORE, USER_SCORE.USER_ID, USER_SCORE.REMARK, USER_SCORE.ADMIN_USER,
//				USER_SCORE.ORDER_SN, USER_SCORE.FLOW_NO, USER_SCORE.USABLE_SCORE)
//				.values(record.getScore(), record.getUserId(), record.getRemark(), record.getAdminUser(),
//						record.getOrderSn(), record.getFlowNo(), record.getUsableScore())
//				.execute();
	}

	
	/**
	 * 消耗积分记录
	 * @param userId 用户id
	 * @param score 消耗积分的绝对值
	 * @return true操作成功，false无可用积分
	 * @throws MpException
	 */
	public boolean useUserScore(Integer userId, int score) throws MpException {
	
		while (true) {

			/** 1 获取最早一条可用记录 */
			UserScoreRecord userRecord = getEarlyUsableRecord(userId);
			if (userRecord == null) {
				throw new MpException(JsonResultCode.CODE_MEMBER_SCORE_ERROR);
			}

			/** 2. 处理消耗score值 */
			if (score < userRecord.getUsableScore()) {
				int usableScore = userRecord.getUsableScore() - score;
				userRecord.setUsableScore(usableScore);
				/** 3. 更新记录 */
				updateUserScoreRecord(userRecord);

				break;
			} else {
				score = score - userRecord.getUsableScore();

				/** 4. 更新要插入的数据值,设置记录状态为已使用 并且 可用积分为0  */
				userRecord.setStatus(USED_SCORE_STATUS);
				userRecord.setUsableScore(0);

				updateUserScoreRecord(userRecord);
				if (score <= 0) {
					break;
				}
			}
			

		}
		return true;
	}

	/**
	 * 更新用户积分记录表b2c_user_score
	 * @param record 
	 * @return int 执行结果
	 */
	private int updateUserScoreRecord(UserScoreRecord record) {

		int result = db().update(USER_SCORE).set(USER_SCORE.USABLE_SCORE, record.getUsableScore())
				.set(USER_SCORE.STATUS, record.getStatus()).where(USER_SCORE.USER_ID.eq(record.getUserId()))
				.and(USER_SCORE.FLOW_NO.eq(record.getFlowNo())).execute();

		return result;
	}
 
	/**
	 * 获取最早可用的积分记录
	 * @param userId 用户id
	 * @return UserScoreRecord
	 */
	private UserScoreRecord getEarlyUsableRecord(Integer userId) {
		/** -通过score和status来筛选 */
		Timestamp localDateTime = DateUtil.getLocalDateTime();

		List<Byte> list = Arrays.asList(AVAILABLE_STATUS);

		UserScoreRecord userScoreRecord = db().selectFrom(USER_SCORE).where(USER_SCORE.USER_ID.eq(userId))
				.and(USER_SCORE.SCORE.greaterThan(0)).and(USER_SCORE.STATUS.in(list))
				.and(USER_SCORE.EXPIRE_TIME.ge(localDateTime).or(USER_SCORE.EXPIRE_TIME.isNull()))
				.orderBy(USER_SCORE.CREATE_TIME)
				.limit(1)
				.fetchOne();

		return userScoreRecord;
	}

	/**
	 * 生成流水号
	 * 
	 * @return string 流水号
	 */
	private String generateFlowNo() {
		DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		String result = null;
		Random random = new Random();
		while (true) {
			LocalDateTime now = LocalDateTime.now();
			/** 生成四位随机数字 */
			int num = random.nextInt(9000) + 1000;
			result = "S" + now.format(f) + num;

			/** 确保流水号的唯一性 */
			int count = db().fetchCount(USER_SCORE, USER_SCORE.FLOW_NO.eq(result));

			if (count == 0) {
				break;
			}
		}
		return result;
	}


	/**
	 * 分页查询会员用户积分详情
	 * @param param 
	 * @return PageResult<ScorePageListVo>
	 */
	public PageResult<ScorePageListVo> getPageListOfScoreDetails(ScorePageListParam param) {
		SelectJoinStep<Record7<String, String, String, Integer, Timestamp, Timestamp, String>> select = db()
				.select(USER.USERNAME,USER.MOBILE,USER_SCORE.ORDER_SN,USER_SCORE.SCORE,USER_SCORE.CREATE_TIME,USER_SCORE.EXPIRE_TIME,USER_SCORE.REMARK)
				.from(USER_SCORE.join(USER).on(USER.USER_ID.eq(USER_SCORE.USER_ID)));
		
		
		buildOptions(select,param);
		select.orderBy(USER_SCORE.CREATE_TIME.desc());
		
		return getPageResult(select, param.getCurrentPage(), param.getPageRows(),ScorePageListVo.class);
	}


	/**
	 * 分页查询用户积分明细-积分明细时其他查询条件
	 * @param select
	 * @param param  
	 */
	private void buildOptions(
			SelectJoinStep<Record7<String, String, String, Integer, Timestamp, Timestamp, String>> select,
			ScorePageListParam param) {
		logger().info("正在构建查询条件");
		
		/** 会员id-会员昵称，优先id */
		if(param.getUserId() != null) {
			select.where(USER_SCORE.USER_ID.eq(param.getUserId()));
		}else if(!StringUtil.isBlank(param.getUserName())){
			String likeValue = likeValue(param.getUserName());
			/** 查询出所有符合昵称的会员id */
			List<Integer> ids = db().select(USER.USER_ID).from(USER).where(USER.USERNAME.like(likeValue)).fetch().into(Integer.class);
			select.where(USER_SCORE.USER_ID.in(ids));
		}
		
		/** 订单号 */
		if(!StringUtils.isEmpty(param.getOrderSn())) {
			select.where(USER_SCORE.ORDER_SN.eq(param.getOrderSn()));
		}
		
		/** 时间范围 */
		/** 开始时间 */
		if(param.getStartTime() != null) {
			select.where(USER_SCORE.CREATE_TIME.ge(param.getStartTime()));
		}
		/** 结束时间 */
		if(param.getEndTime() != null) {
			select.where(USER_SCORE.CREATE_TIME.le(param.getEndTime()));
		}
		
	}

	
	/** 累计积分 */
	public BigDecimal getTotalScore(Integer userId) {
		
		BigDecimal totalScore = db().select(sum(USER_SCORE.SCORE))
								.from(USER_SCORE)
								.where(USER_SCORE.USER_ID.eq(userId).and(USER_SCORE.SCORE.greaterThan(0)))
								.fetchAny()
								.into(BigDecimal.class);
		return totalScore;
	}
}
