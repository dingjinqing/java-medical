package com.vpu.mp.service.shop.member;


import static com.vpu.mp.db.shop.Tables.USER;
import static com.vpu.mp.db.shop.Tables.USER_SCORE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.DAY;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MONTH;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.WEEK;
import static com.vpu.mp.service.pojo.shop.member.score.ScoreStatusConstant.NO_USE_SCORE_STATUS;
import static com.vpu.mp.service.pojo.shop.member.score.ScoreStatusConstant.REFUND_SCORE_STATUS;
import static com.vpu.mp.service.pojo.shop.member.score.ScoreStatusConstant.USED_SCORE_STATUS;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.IS_FROM_REFUND_Y;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.TRADE_CONTENT_SCORE;
import static com.vpu.mp.service.shop.member.BaseScoreCfgService.SCORE_LT_NOW;
import static com.vpu.mp.service.shop.member.BaseScoreCfgService.SCORE_LT_YMD;
import static com.vpu.mp.service.shop.member.UserCardService.UPGRADE;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectJoinStep;
import org.jooq.exception.DataAccessException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.ShopCfgRecord;
import com.vpu.mp.db.shop.tables.records.TradesRecordRecord;
import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.db.shop.tables.records.UserScoreRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.FieldsUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.RemarkUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.member.account.ScoreParam;
import com.vpu.mp.service.pojo.shop.member.card.CardConstant;
import com.vpu.mp.service.pojo.shop.member.score.CheckSignVo;
import com.vpu.mp.service.pojo.shop.member.score.ScorePageInfo;
import com.vpu.mp.service.pojo.shop.member.score.ScorePageListParam;
import com.vpu.mp.service.pojo.shop.member.score.ScorePageListVo;
import com.vpu.mp.service.pojo.shop.member.score.SignData;
import com.vpu.mp.service.pojo.shop.member.score.UserScoreSetValue;
import com.vpu.mp.service.pojo.shop.member.score.UserScoreVo;
import com.vpu.mp.service.pojo.shop.operation.RecordContentTemplate;
import com.vpu.mp.service.pojo.shop.operation.RemarkTemplate;
import com.vpu.mp.service.pojo.wxapp.score.ExpireVo;
import com.vpu.mp.service.shop.member.dao.ScoreDaoService;
import com.vpu.mp.service.shop.order.trade.TradesRecordService;
/**
 * 
 * @author 黄壮壮
 * @Date:  2019年8月23日
 * @Description: 积分服务
 */
@Service
public class ScoreService extends ShopBaseService {
	
	@Autowired
	private TradesRecordService tradesRecord;
	@Autowired
	private ScoreDaoService scoreDao;
	
	@Autowired
	public ScoreCfgService score;
	@Autowired
	public UserCardService userCardService;
	
	@Autowired
	public ScoreCfgService scoreCfgService;
	@Autowired
	public MemberService member;	
	/**
	 *   创建用户积分表,增加，消耗用户积分
	 * @param param 积分变动相关数据
	 * @param subAccountId 操作员id
	 * @param userId 用户id
	 * @param tradeType  交易类型说明 如  微信支付类型{@link com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.WX_PAY }
	 * @param tradeFlow  资金流向类型  如收入 {@link com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.TRADE_FLOW_INCOME}
	 * @return JsonResultCode
	 * @throws MpException 
	 */
	
	public void updateMemberScore(ScoreParam param, Integer adminUser,Byte tradeType,
			Byte tradeFlow) throws MpException{
		if(param.getUserId() != null) {
			for(Integer userId: param.getUserId()) {
				updateMemberScore(param,adminUser,userId,tradeType,tradeFlow);
			}
		}
	}
	
	public void updateMemberScore(ScoreParam param, Integer subAccountId, Integer userId, Byte tradeType,
			Byte tradeFlow) throws MpException {

		/**  1. 校验userId是否存在数据库中 */
		if (userId <= 0) {
			throw new MpException(JsonResultCode.CODE_MEMEBER_NOT_EXIST);
		}
		/** 1.1 查询用户 */
		UserRecord dbUser = member.getUserRecordById(userId);

		if (dbUser == null) {
			throw new MpException(JsonResultCode.CODE_MEMEBER_NOT_EXIST);
		}

		/** 2. 验证积分与数据库保持一致，批量修改时为最小值，即大于等于 */
		//Integer scoreDis = param.getScoreDis();
		
		/** -目前会员存在数据录中的积分 */
		Integer dbScore = dbUser.getScore();

		/** 3. 准备数据  */

		/** 3.1 处理备注  */
		if (param.getRemarkCode() == null) {
			if(StringUtils.isBlank(param.getRemarkData())) {
				// 默认管理员操作
				param.setRemarkCode(RemarkTemplate.ADMIN_OPERATION.code);
			}else {
				// 用户输入
				param.setRemarkCode(RemarkTemplate.USER_INPUT_MSG.code);
			}
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
						if(Math.abs(score)>dbScore) {
							logger().info("消耗的积分超出可用积分");
							throw new MpException(JsonResultCode.CODE_MEMBER_SCORE_ERROR);
						}
						/** -消耗积分 */
						//useUserScore(userId, Math.abs(score), orderSn);
						useUserScore(userId, Math.abs(score),orderSn);
					}
				}catch(MpException e) {
					logger().info("正在处理异常");
					throw e;
				}
				

				logger().info("添加积分记录");
				UserScoreRecord userScoreRecord = db().newRecord(USER_SCORE);
				/** 5.1 填充数据 */
				//TODO 还有一些数据不知道从哪些业务传递过来的如goods_id,desc,identity_id 
				userScoreRecord.setScore(score);
				userScoreRecord.setUserId(userId);
				userScoreRecord.setRemarkId(String.valueOf(param.getRemarkCode()));
				userScoreRecord.setRemarkData(param.getRemarkData());
				userScoreRecord.setAdminUser(String.valueOf(subAccountId));
				userScoreRecord.setOrderSn(orderSn);
				userScoreRecord.setFlowNo(flowOn);
				userScoreRecord.setUsableScore(usableScore);
				logger().info(String.valueOf(param.getScoreStatus()));
				userScoreRecord.setStatus(param.getScoreStatus());
				userScoreRecord.setExpireTime(param.getExpiredTime());
				
				/** -判断是否为退款积分 */
				if(param.getIsFromRefund() !=null && IS_FROM_REFUND_Y.val().equals(param.getIsFromRefund())) {
					logger().info("正在处理退款积分");
					userScoreRecord.setStatus(REFUND_SCORE_STATUS);
					UserScoreRecord userScore = getScoreRecordByOrderSn(userId,orderSn);
					if(userScore != null) {
						userScoreRecord.setScore(Math.abs(userScore.getScore()));
						userScoreRecord.setUsableScore(Math.abs(userScore.getScore()));
						userScoreRecord.setExpireTime(userScore.getExpireTime());
					}
				}else if(score < 0) {
					userScoreRecord.setStatus(USED_SCORE_STATUS);
				}else {
					userScoreRecord.setStatus(NO_USE_SCORE_STATUS);
				}
			
				userScoreRecord.insert();
		
				
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
				tradesRecord.setTradeContent(TRADE_CONTENT_SCORE.val());
				tradesRecord.setTradeType(tradeType);
				tradesRecord.setTradeFlow(tradeFlow);
				tradesRecord.setTradeStatus(tradeFlow);
				
				/** -交易记录表-记录交易的数据信息  */
				insertTradesRecord(tradesRecord);
				
				
				//TODO  admin 操作记录
				if (subAccountId == 0) {
					String strScore = score>=0? "+"+score:""+score;
					saas().getShopApp(getShopId()).record.insertRecord(
							Arrays.asList(new Integer[] { RecordContentTemplate.MEMBER_INTEGRALT.code }),
							String.valueOf(dbUser.getUserId()), dbUser.getUsername(), strScore);
				}
				});
		}catch(DataAccessException e) {
			logger().info("从事务抛出的DataAccessException中获取我们自定义的异常");
			throw e;
		}
		return ;
	}
	

	/**
	 * 通过订单编号获取用户积分记录
	 * @param userId 用户id
	 * @param orderSn 订单号
	 * @return UserScoreRecord
	 */
	public UserScoreRecord getScoreRecordByOrderSn(Integer userId, String orderSn) {
		 return db().selectFrom(USER_SCORE).where(USER_SCORE.USER_ID.eq(userId))
					.and(USER_SCORE.ORDER_SN.eq(orderSn))
					.orderBy(USER_SCORE.CREATE_TIME)
					.fetchAnyInto(UserScoreRecord.class);
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
	public void updateUserScore(Integer userId, Integer totalScore) {
		db().update(USER).set(USER.SCORE, totalScore).where(USER.USER_ID.eq(userId)).execute();
	}

	/**
	 * 获取用户可用积分
	 * 
	 * @param userId
	 */
	public Integer getTotalAvailableScoreById(Integer userId) {
		return scoreDao.calculateAvailableScore(userId);
	}

	
	
	/**
	 * 消耗积分记录
	 * @param userId 用户id
	 * @param score 消耗积分的绝对值
	 * @param identityId 关联其他属性：例如order_sn
	 * @return true操作成功，false无可用积分
	 * @throws MpException
	 */
	public boolean useUserScore(Integer userId, int score,String identityId) throws MpException {
	
		while (true) {

			/** 1 获取最早一条可用记录 */
			UserScoreRecord userRecord = getEarlyUsableRecord(userId);
			/** 更新 关联其他属性：例如order_sn */
			if(!StringUtils.isBlank(identityId)) {
				userRecord.setIdentityId(userRecord.getIdentityId()+","+identityId);
			}
			if (userRecord == null) {
				logger().info("暂无可用积分");
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
	 * 获取一条用户可用的最早积分记录
	 * @param userId 用户id
	 * @return UserScoreRecord
	 */
	public UserScoreRecord getEarlyUsableRecord(Integer userId) {
		return scoreDao.getTheEarliestUsableUserScoreRecord(userId);
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
	public PageResult<ScorePageListVo> getPageListOfScoreDetails(ScorePageListParam param,String language) {
		SelectJoinStep<? extends Record> select = db()
				.select(USER.USERNAME,USER.MOBILE,USER_SCORE.ORDER_SN,USER_SCORE.SCORE,USER_SCORE.CREATE_TIME,USER_SCORE.EXPIRE_TIME
						,USER_SCORE.REMARK_DATA,USER_SCORE.REMARK_ID)
				.from(USER_SCORE.join(USER).on(USER.USER_ID.eq(USER_SCORE.USER_ID)));
		
		
		buildOptions(select,param);
		select.orderBy(USER_SCORE.CREATE_TIME.desc());
		
		PageResult<ScorePageInfo> resultBefore = getPageResult(select, param.getCurrentPage(), param.getPageRows(),ScorePageInfo.class);
		
		return scoreInfoToScoreVo(language, resultBefore);

	}
	
	/**
	 *	数据类型转换
	 */
	public PageResult<ScorePageListVo> scoreInfoToScoreVo(String language, PageResult<ScorePageInfo> resultBefore) {
		PageResult<ScorePageListVo> resultAfter = new PageResult<>();
		List<ScorePageListVo> dataList = new ArrayList<>();
		for(ScorePageInfo scoreItem: resultBefore.dataList) {
			ScorePageListVo scoreVo = new ScorePageListVo();
			BeanUtils.copyProperties(scoreItem, scoreVo);
			String remark = RemarkUtil.remarkI18N(language, scoreItem.getRemarkId(),scoreItem.getRemarkData());
			scoreVo.setRemark(remark);
			dataList.add(scoreVo);
		}
		resultAfter.setPage(resultBefore.getPage());
		resultAfter.setDataList(dataList);
		return resultAfter;
	}
	
	


	/**
	 * 分页查询用户积分明细-积分明细时其他查询条件
	 * @param select
	 * @param param  
	 */
	private void buildOptions(
			SelectJoinStep<? extends Record> select,
			ScorePageListParam param) {
		logger().info("正在构建查询条件");
		
		/** 会员id-会员昵称，优先id */
		if(param.getUserId() != null) {
			select.where(USER_SCORE.USER_ID.eq(param.getUserId()));
		}else if(!StringUtils.isBlank(param.getUserName())){
			String likeValue = likeValue(param.getUserName());
			/** 查询出所有符合昵称的会员id */
			List<Integer> ids = db().select(USER.USER_ID).from(USER).where(USER.USERNAME.like(likeValue)).fetchInto(Integer.class);
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
		
		/**类型 */
		if("wxapp".equals(param.getType())) {
			if(param.getUserId()!=null) {
				select.where(USER.USER_ID.eq(param.getUserId()));
			}
		}
	}

	
	/**
	 * 累计积分
	 * @return 返回用户累计积分，没有则为0
	 */
	public Integer getAccumulationScore(Integer userId) {
		logger().info("获取用户的所有累积积分");
		return scoreDao.calculateAccumulationScore(userId);
	}
	
	/**
	 * 查询今天是否有某种积分
	 * @param userId
	 * @param desc
	 * @return 
	 */
	public UserScoreRecord getScoreInDay(Integer userId, String desc) {
		return db()
				.selectFrom(
						USER_SCORE)
				.where(USER_SCORE.USER_ID.eq(userId).and(
						USER_SCORE.DESC.eq(desc).and((dateFormat(USER_SCORE.CREATE_TIME, DateUtil.DATE_MYSQL_SIMPLE))
								.eq(DateUtil.dateFormat(DateUtil.DATE_FORMAT_SIMPLE)))))
				.fetchAny();
	}
	
	/**
	 * 创建用户积分记录，发返1 为成功
	 * @param data
	 * @param adminUser
	 * @param tradeType
	 * @param tradeFlow
	 */
	public Integer addUserScore(UserScoreVo data, String adminUser, Byte tradeType, Byte tradeFlow) {
		// 生成新的充值记录
		// 验证现有积分跟提交的积分是否一致
		// 销毁score_dis
		int insert=0;
		try {

			if (data.getRemarkCode()==null) {
				//data.setRemark("管理员操作");
				data.setRemarkCode(RemarkTemplate.ADMIN_OPERATION.code);
			}
			UserScoreRecord record =db().newRecord(USER_SCORE);
			FieldsUtil.assignNotNull(data, record);
			record.setRemarkId(String.valueOf(data.getRemarkCode()));
			record.setAdminUser(adminUser);
			record.setFlowNo(generateFlowNo());
			record.setUsableScore(data.getScore() > 0 ? data.getScore() : 0);
			if (data.getIsFromCrm()) {
				record.setExpireTime(getScoreExpireTime());
			}
			if (data.getScore() < 0) {
				if (data.getIsFromOverdue()) {
					boolean useUserScore = useUserScore(data.getUserId(), Math.abs(data.getScore()),data.getOrderSn());
					if(!useUserScore) {
						logger().info("消耗积分异常："+data.toString());
					}
				}
			}else if(data.getIsFromRefund()) {
				 // 退款处理积分
				logger().info("退款处理积分");
				record.setStatus(REFUND_SCORE_STATUS);
				UserScoreRecord scoreRecordByOrderSn = getScoreRecordByOrderSn(data.getUserId(), data.getOrderSn());
				record.setExpireTime(scoreRecordByOrderSn.getExpireTime()!=null?scoreRecordByOrderSn.getExpireTime():data.getExpireTime());
			}
			//TODO  UserScore.php $crmResult = shop($this->getShopId())->serviceRequest->crmApi->init();
			
			logger().info("开始插入");
			insert = record.insert();
			if(insert<=0) {
				logger().info("插入失败");
			}
			//TODO    shop($this->getShopId())->serviceRequest->crmApi->syncUserScore($data['user_id'], $data);
		} catch (Exception e) {
			logger().error("addUserScore error, message:");
			logger().error(e.getMessage(),e);
			return -2;
		}
		//更新用户积分
		Integer canUseScore  = getTotalAvailableScoreById(data.getUserId());
		db().update(USER).set(USER.SCORE,canUseScore).where(USER.USER_ID.eq(data.getUserId())).execute();
		if(data.getScore()<0) {
			tradeFlow=0;
		}
		tradesRecord.addRecord(new BigDecimal(Math.abs(data.getScore())), data.getOrderSn()==null?"":data.getOrderSn(), data.getUserId(), (byte)1, tradeType, tradeFlow, tradeFlow);
		String userGrade = member.card.getUserGrade(data.getUserId());
		if(!userGrade.equals(CardConstant.LOWEST_GRADE)) {
			try {
				// 升级
				userCardService.updateGrade(data.getUserId(),null, UPGRADE);
			} catch (MpException e) {
				logger().info("没有可升级的会员卡");
				return -1;
			}
		}
		return insert;
	}
	
	
	/**
	 * 获取积分过期时间
	 * @return
	 */
	public Timestamp getScoreExpireTime() {
		Timestamp expireTime = null;
		Byte scoreLimit = scoreCfgService.getScoreLimit();
		LocalDate date=LocalDate.now();		
		
		if (SCORE_LT_YMD.equals(scoreLimit)) {
			Integer scoreYear = scoreCfgService.getScoreYear();
			Integer scoreMonth = scoreCfgService.getScoreMonth();
			Integer scoreDay = scoreCfgService.getScoreDay();
			LocalDateTime localDateTime = LocalDateTime.of(date.getYear()+scoreYear,scoreMonth,scoreDay, 23, 59, 59);
			expireTime = Timestamp.valueOf(localDateTime);
		}
		
		if (SCORE_LT_NOW.equals(scoreLimit)) {
			Integer scoreLimitNumber = scoreCfgService.getScoreLimitNumber();
			Integer scorePeriod = scoreCfgService.getScorePeriod();
			
			LocalDateTime localDateTime=LocalDateTime.now();
			
			if(DAY.equals(scorePeriod)) {
				localDateTime.plusDays(scoreLimitNumber);
			}else if(WEEK.equals(scorePeriod)) {
				localDateTime.plusWeeks(scoreLimitNumber);
			}else if(MONTH.equals(scorePeriod)) {
				localDateTime.plusMonths(scoreLimitNumber);
			}
			expireTime = Timestamp.valueOf(localDateTime);
		}
		logger().info("获取积分过期时间"+expireTime);
		return expireTime;
	}
	
	/**
	 * 检查签到送积分
	 */
	public CheckSignVo checkSignInScore(Integer userId) {
		logger().info("进入检查签到送积分");
		UserScoreSetValue signInScore = score.getScoreValueThird("sign_in_score");
		int days = 0;
		int scoreValue = 0;
		int isSignIn = 0;
		int day = 0;
		int isOpenSign=0;
		SignData signData = new SignData();
		if (signInScore != null) {
			if (signInScore.getScore()!=null) {
				for (String value : signInScore.getScore()) {
					days++;
					scoreValue += Integer.parseInt(value);
				}
			}
			String receiveScore = null;
			if (signInScore.getEnable()==(byte)1) {
				 isOpenSign = 1;
				if (checkUserIsSign(userId)) {
					// 未签到
					logger().info("未签到");
					isSignIn = 0;
					// 判断当前是第几天领取
					day = checkDayByUserSignIn(userId, false);
					// 今天领取多少积分
					String[] score2 = signInScore.getScore();
					receiveScore=getReceiveScore(score2, day, 1);
				} else {
					// 已签到
					logger().info("已签到");
					isSignIn = 1;
					day = checkDayByUserSignIn(userId, true) - 1;
					UserScoreRecord scoreInDay = getScoreInDay(userId, "sign_score");
					if(scoreInDay==null) {
						logger().error("已签到时表中为空，请检查日志");
						receiveScore="0";
					}else {
						receiveScore = String.valueOf(scoreInDay.getScore());						
					}
				}
				signData.setIsSignIn(isSignIn);
				signData.setDay(day);
				signData.setReceiveScore(receiveScore);
				signData.setTomoroowReceive(getReceiveScore(signInScore.getScore(), day, 0));
				signData.setMaxSignDay(days);
				signData.setScoreValue(scoreValue);

			} else {
				 isOpenSign = 0;
			}
		}
		logger().info("signInScore是空的");
		CheckSignVo vo=new CheckSignVo();
		vo.setIsOpenSign(isOpenSign);
		if(signInScore!=null) {
			vo.setSignData(signData);
			vo.setSignRule(signInScore.getScore().length<=0?new String[0]:signInScore.getScore());			
		}else {
			vo.setSignData(null);
			vo.setSignRule(new String[0]);
		}
		logger().info("进入检查签到送积分结束");
		return vo;
	}
	
	private String getReceiveScore(String[] score2, Integer day,Integer option) {
		String receiveScore = null;
		if (score2.length <= 0) {
			receiveScore = "0";
		} else {
			if (day < 1 || day >= score2.length) {
				receiveScore = score2[score2.length - 1];
			} else {
				receiveScore = score2[day - option];
			}
		}
		return receiveScore;
	}
	
	/**
	 * 检查用户是否已经签到  true: 未签到
	 * @param userId
	 * @return
	 */
	public boolean checkUserIsSign(Integer userId) {
		UserScoreRecord record = db().selectFrom(USER_SCORE).where(USER_SCORE.USER_ID.eq(userId))
				.and(USER_SCORE.DESC.eq("sign_score")).orderBy(USER_SCORE.ID.desc()).fetchAny();
		if(record!=null) {
			if(DateUtil.TimestampIsNowDay(record.getCreateTime())) {
				 return false;
			}
		}
		 return true;
	}
	
	/**
	 * 判断用户是第几天签到
	 * @param userId
	 * @param signOk
	 * @return
	 */
	public int checkDayByUserSignIn(Integer userId, Boolean signOk) {
		Result<UserScoreRecord> list = db().selectFrom(USER_SCORE).where(USER_SCORE.USER_ID.eq(userId))
				.and(USER_SCORE.DESC.eq("sign_score")).orderBy(USER_SCORE.ID.desc()).fetch();
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime localDateTime=LocalDateTime.now();
		int day=1;
		String date=signOk?df.format(localDateTime):df.format(localDateTime.minusDays(1L));
		int flag=0;
		for(UserScoreRecord record:list) {
			if(flag==1) {
				break;
			}
			if(date.equals(df.format(record.getCreateTime().toLocalDateTime()))) {
				++day;
			}else {
				flag=1;
			}
			date=df.format(localDateTime.minusDays(1L));
		}
		return day;
	}
	
	/**
	 * 获取即将过期积分
	 */
	public Integer getExpireScore(Integer userId,Timestamp endTime) {
		logger().info("获取即将过期积分");

		if(endTime == null) {		
			// endTime为空，那么就默设置为一年后
			LocalDateTime oneYearLater = LocalDateTime.now().plusYears(1L);
			endTime = Timestamp.valueOf(oneYearLater);
		}
		return scoreDao.calculateWillExpireSoonScore(endTime, userId);
	}
	
	/**
	 * 获取用户积分数据
	 * @param userId
	 * @return 
	 */
	public ExpireVo getUserScoreCfg(Integer userId) {
		ShopCfgRecord scoreLimitRecord = score.getScoreNum("score_limit");
		ExpireVo vo=new ExpireVo();
		if (scoreLimitRecord != null) {
			if (scoreLimitRecord.getV().equals("1")) {
				int scoreYear = Integer.parseInt(score.getScoreNum("score_year").getV());
				int year = LocalDateTime.now().getYear();
				year=year+scoreYear;
				String month = score.getScoreNum("score_month").getV();
				String day = score.getScoreNum("score_day").getV();
				if(Integer.parseInt(month)<10) {
					month="0"+month;
				}
				if(Integer.parseInt(day)<10) {
					day="0"+day;
				}
				vo.setTime(String.valueOf(year)+"-"+month+"-"+day+" 23:59:59");
				vo.setScore(getExpireScore(userId,  Timestamp.valueOf(vo.getTime())));
			}
			if (scoreLimitRecord.getV().equals("0")) {
				DateTimeFormatter df = DateTimeFormatter.ofPattern(DateUtil.DATE_FORMAT_SIMPLE);
				LocalDateTime localDateTime=LocalDateTime.now();
				vo.setTime(df.format(localDateTime));
				vo.setScore(-1);
			}
		}
		return vo;
	}
	
}
