package com.vpu.mp.service.shop.member.dao;

import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.UserScoreRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;

import static org.jooq.impl.DSL.sum;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import static com.vpu.mp.db.shop.Tables.USER_SCORE;
import static com.vpu.mp.service.pojo.shop.member.score.ScoreStatusConstant.NO_USE_SCORE_STATUS;
import static com.vpu.mp.service.pojo.shop.member.score.ScoreStatusConstant.REFUND_SCORE_STATUS;

/**
* @author 黄壮壮
* @Date: 2019年10月12日
* @Description: 积分Dao层
*/
@Service
public class ScoreDaoService extends ShopBaseService {
	/** -积分有效的状态 列表*/
	final static List<Byte> AVAILABLE_SCORE_STATUS_LIST = new ArrayList<>(Arrays.asList(NO_USE_SCORE_STATUS, REFUND_SCORE_STATUS ));
	/**
	 * 计算用户的所有累积分
	 * @param id
	 * @return
	 */
	public Integer calculateAccumulationScore(Integer userId) {
		 Integer accumulationScore = db().select(sum(USER_SCORE.SCORE))
									 	.from(USER_SCORE)
									 	.where(USER_SCORE.USER_ID.eq(userId))
									 	.and(USER_SCORE.STATUS.notIn(REFUND_SCORE_STATUS))
									 	.and(USER_SCORE.SCORE.greaterThan(0))
									 	.fetchAnyInto(Integer.class);
		 logger().info("计算用户累积积分为： "+accumulationScore);
		 return isNotNull(accumulationScore)?accumulationScore:NumberUtils.INTEGER_ZERO;
	}
	
	/**
	 * 计算用户的所有可使用的积分
	 */
	public Integer calculateAvailableScore(Integer userId) {
	
		/** 根据用户id,status,有效期 */
		Integer availableScore = db().select(DSL.sum(USER_SCORE.USABLE_SCORE))
									.from(USER_SCORE)
									.where(USER_SCORE.USER_ID.eq(userId))
									.and(USER_SCORE.STATUS.in(AVAILABLE_SCORE_STATUS_LIST))
									.and(USER_SCORE.EXPIRE_TIME.ge(DateUtil.getLocalDateTime()).or(USER_SCORE.EXPIRE_TIME.isNull()))
									.fetchOptionalInto(Integer.class)
									.orElse(NumberUtils.INTEGER_ZERO);
		logger().info("计算所有可用积分为： "+availableScore);
		return availableScore;
	} 
	
	/**
	 * 计算从现在到指定时间的可用积分
	 * @param endTime 指定的时间
	 * @return Integer 积分
	 */
	public Integer calculateWillExpireSoonScore(Timestamp endTime,Integer userId) {
		Integer willExpireSoonScore = db().select(sum(USER_SCORE.USABLE_SCORE))
			.from(USER_SCORE)
			.where(USER_SCORE.USER_ID.eq(userId))
			.and(USER_SCORE.STATUS.in(AVAILABLE_SCORE_STATUS_LIST))
			.and(USER_SCORE.EXPIRE_TIME.between(DateUtil.getLocalDateTime(), endTime))
			.fetchOptionalInto(Integer.class)
			.orElse(NumberUtils.INTEGER_ZERO);
	
		logger().info("计算在指定时间 "+endTime+" 所有可用积分为： "+willExpireSoonScore);
		return willExpireSoonScore;
	}
	
	/**
	 * 获取一条用户可用的最早积分记录
	 */
	public UserScoreRecord getTheEarliestUsableUserScoreRecord(Integer userId) {
		return db().selectFrom(USER_SCORE)
			.where(USER_SCORE.USER_ID.eq(userId))
			.and(USER_SCORE.USABLE_SCORE.greaterThan(0)).and(USER_SCORE.STATUS.in(AVAILABLE_SCORE_STATUS_LIST))
			.and(USER_SCORE.EXPIRE_TIME.ge(DateUtil.getLocalDateTime()).or(USER_SCORE.EXPIRE_TIME.isNull()))
			.orderBy(USER_SCORE.CREATE_TIME)
			.fetchAny();
	}

	private boolean isNotNull(Object obj) {
		return obj!=null;
	}
}
