package com.vpu.mp.service.shop.member;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.jooq.impl.DSL;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.vpu.mp.service.shop.member.MemberService;
import com.vpu.mp.db.shop.tables.records.TradesRecordRecord;
import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.db.shop.tables.records.UserScoreRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.member.account.ScoreParam;
import static com.vpu.mp.db.shop.Tables.USER_SCORE;
import static com.vpu.mp.db.shop.Tables.USER;
import static com.vpu.mp.db.shop.Tables.TRADES_RECORD;

/**
 * @author 黄壮壮 2019-07-19 15:03
 */
@Service
public class ScoreService extends ShopBaseService {
	/**
	 * 未使用
	 */
	final Byte NO_USE_SCORE_STATUS = 0;
	/**
	 * 已使用
	 */
	final Byte USED_SCORE_STATUS = 1;
	/**
	 * 已过期
	 */
	final Byte EXPIRE_SCORE_STATUS = 2;
	/**
	 * 已退款
	 */
	final Byte REFUND_SCORE_STATUS = 3;
	/**
	 * 积分有效的状态
	 */
	final Byte[] AVAILABLE_STATUS = new Byte[] { NO_USE_SCORE_STATUS, REFUND_SCORE_STATUS };

	private MemberService member;

	public JsonResultCode updateMemberScore(ScoreParam param, Integer subAccountId, Integer userId, Byte tradeType,
			Byte tradeFlow) {

		// 1. 校验userId是否存在数据库中
		if (userId <= 0) {
			return JsonResultCode.CODE_MEMEBER_NOT_EXIST;
		}
		// 1.1 查询用户
		UserRecord dbUser = member.getUserRecordById(userId);

		if (dbUser == null) {
			return JsonResultCode.CODE_MEMEBER_NOT_EXIST;
		}

		// 2. 验证积分与数据库保持一致，批量修改时为最小值，即大于等于
		Integer scoreDis = param.getScoreDis();
		Integer dbScore = dbUser.getScore();
		if (dbScore < scoreDis) {
			return JsonResultCode.CODE_MEMBER_SCORE_NOT_SAME;
		}

		// 3. 准备数据

		// 3.1 处理备注
		String remark = param.getRemark();
		if (StringUtils.isEmpty(remark)) {
			remark = "管理员操作";
		}

		// 3.2 获取积分流水号
		String flowOn = generateFlowNo();

		// 3.3 可用剩余积分
		Integer score = param.getScore();
		Integer usableScore = score > 0 ? score : 0;
		String orderSn = param.getOrderSn() == null ? "" : param.getOrderSn();

		// 4 如果时负数，则消耗积分
		if (score < 0) {
			// 消耗积分
			boolean result = useUserScore(userId, Math.abs(score), orderSn);
			if (!result) {
				return JsonResultCode.CODE_MEMBER_SCORE_ERROR;
			}

		}

		// 5. 添加积分记录

		UserScoreRecord userScoreRecord = new UserScoreRecord();
		// 5.1 填充数据
		userScoreRecord.setScore(score);
		userScoreRecord.setUserId(userId);
		userScoreRecord.setRemark(remark);
		userScoreRecord.setAdminUser(String.valueOf(subAccountId));
		userScoreRecord.setOrderSn(orderSn);
		userScoreRecord.setFlowNo(flowOn);
		userScoreRecord.setUsableScore(usableScore);

		// 5.2 添加
		addUserScoreRecord(userScoreRecord);

		// 6. 更新用户积分
		// 6.1 获取操作后的积分
		Integer totalScore = getTotalAvailableScoreById(userId);
		// 6.2 更新用户积分
		updateUserScore(userId, totalScore);
		
		
		// 7. 记录更新record action
		TradesRecordRecord tradesRecord=new TradesRecordRecord();
		tradesRecord.setTradeTime(Util.getLocalDateTime());
		tradesRecord.setTradeNum(BigDecimal.valueOf(Math.abs(score)));
		tradesRecord.setTradeSn(orderSn);
		tradesRecord.setUserId(userId);
		tradesRecord.setTradeContent((byte) 1);
		tradesRecord.setTradeType(tradeType);
		tradesRecord.setTradeFlow(tradeFlow);
		tradesRecord.setTradeStatus(tradeFlow);
		insertTradesRecord(tradesRecord);

		return JsonResultCode.CODE_SUCCESS;
	}

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
		Timestamp localDateTime = Util.getLocalDateTime();
		// 根据用户id,status,有效期
		Integer sum = db().select(DSL.sum(USER_SCORE.USABLE_SCORE)).from(USER_SCORE)
				.where(USER_SCORE.USER_ID.eq(userId)).and(USER_SCORE.STATUS.in(list))
				.and(USER_SCORE.EXPIRE_TIME.ge(localDateTime).or(USER_SCORE.EXPIRE_TIME.isNull()))
				.fetchOneInto(Integer.class);

		return sum;
	}

	/**
	 * 插入修改记录
	 * 
	 * @param userScoreRecord
	 */
	private void addUserScoreRecord(UserScoreRecord record) {

		db().insertInto(USER_SCORE, USER_SCORE.SCORE, USER_SCORE.USER_ID, USER_SCORE.REMARK, USER_SCORE.ADMIN_USER,
				USER_SCORE.ORDER_SN, USER_SCORE.FLOW_NO, USER_SCORE.USABLE_SCORE)
				.values(record.getScore(), record.getUserId(), record.getRemark(), record.getAdminUser(),
						record.getOrderSn(), record.getFlowNo(), record.getUsableScore())
				.execute();
	}

	/**
	 * 消耗积分记录
	 * 
	 * @param userId
	 * @param abs
	 * @param orderSn
	 */
	private boolean useUserScore(Integer userId, int score, String orderSn) {

		while (true) {
			// 1 获取最早一条记录
			UserScoreRecord userRecord = getEarlyUsableRecord(userId);
			if (userRecord == null) {
				return false;
			}

			// 2. 处理消耗score值
			if (score < userRecord.getUsableScore()) {
				int usableScore = userRecord.getUsableScore() - score;
				userRecord.setUsableScore(usableScore);
				// 3. 更新记录
				updateUserScoreRecord(userRecord);

				break;
			} else {
				score = score - userRecord.getUsableScore();

				// 4. 更新要插入的数据值,设置记录状态为已使用，可用积分为0
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
	 * 更新用户积分记录表
	 * 
	 * @param userId
	 * @param flowNo
	 * @param usableScore
	 */
	private int updateUserScoreRecord(UserScoreRecord record) {

		int result = db().update(USER_SCORE).set(USER_SCORE.USABLE_SCORE, record.getUsableScore())
				.set(USER_SCORE.STATUS, record.getStatus()).where(USER_SCORE.USER_ID.eq(record.getUserId()))
				.and(USER_SCORE.FLOW_NO.eq(record.getFlowNo())).execute();

		return result;
	}

	/**
	 * 获取最早可用的积分记录
	 * 
	 * @param userId
	 */
	private UserScoreRecord getEarlyUsableRecord(Integer userId) {
		// 通过score和status来筛选
		Timestamp localDateTime = Util.getLocalDateTime();

		List<Byte> list = Arrays.asList(AVAILABLE_STATUS);

		UserScoreRecord userScoreRecord = db().selectFrom(USER_SCORE).where(USER_SCORE.USER_ID.eq(userId))
				.and(USER_SCORE.SCORE.greaterThan(0)).and(USER_SCORE.STATUS.in(list))
				.and(USER_SCORE.EXPIRE_TIME.ge(localDateTime).or(USER_SCORE.EXPIRE_TIME.isNull()))
				.orderBy(USER_SCORE.CREATE_TIME).fetchOne();

		return userScoreRecord;
	}

	/**
	 * 生成流水号
	 * 
	 * @return string
	 */
	private String generateFlowNo() {
		DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		String result = null;
		Random random = new Random();
		while (true) {
			LocalDateTime now = LocalDateTime.now();
			// 生成四位随机数字
			int num = random.nextInt(9000) + 1000;
			result = "S" + now.format(f) + num;

			// 确保流水号的唯一性
			int count = db().fetchCount(USER_SCORE, USER_SCORE.FLOW_NO.eq(result));

			if (count == 0) {
				break;
			}
		}
		return result;
	}

}
