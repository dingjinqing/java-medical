package com.vpu.mp.service.shop.member.dao;

import static com.vpu.mp.db.shop.Tables.MEMBER_CARD;
import static com.vpu.mp.db.shop.Tables.STORE;
import static com.vpu.mp.db.shop.Tables.USER;
import static com.vpu.mp.db.shop.Tables.USER_CARD;
import static com.vpu.mp.db.shop.Tables.USER_DETAIL;
import static com.vpu.mp.db.shop.Tables.USER_LOGIN_RECORD;

import java.sql.Timestamp;
import java.util.List;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Record2;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.User;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;

import static com.vpu.mp.service.pojo.shop.member.MemberConstant.INVITE_USERNAME;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.CARD_USING;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.DURING_TIME;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.FIX_DATETIME;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.FOREVER;

/**
 * @author 黄壮壮
 * @Date: 2019年9月10日
 * @Description: 查询数据库的sql语句
 */
@Service
public class MemberDaoService extends ShopBaseService {
	/**
	 * 获取会员用户的详细信息
	 * 
	 * @param userId
	 * @param user
	 * @param inviteName
	 * @return
	 */
	public Record getMemberInfo(
			Integer userId) {
		User a = USER.as("a");
		User b = USER.as("b");
		Field<?> inviteName = db().select(b.USERNAME).from(b).where(b.USER_ID.eq(a.INVITE_ID)).asField(INVITE_USERNAME);
		
		return db().select(a.USERNAME, a.WX_UNION_ID, a.CREATE_TIME, a.MOBILE, a.WX_OPENID,
				a.INVITE_ID, a.SOURCE, a.UNIT_PRICE, inviteName, USER_DETAIL.REAL_NAME, USER_DETAIL.EDUCATION,USER_DETAIL.INDUSTRY_INFO,
				USER_DETAIL.PROVINCE_CODE, a.IS_DISTRIBUTOR, USER_DETAIL.CITY_CODE, USER_DETAIL.DISTRICT_CODE,
				USER_DETAIL.BIRTHDAY_DAY, USER_DETAIL.BIRTHDAY_MONTH, USER_DETAIL.BIRTHDAY_YEAR, USER_DETAIL.SEX,
				USER_DETAIL.MARITAL_STATUS, USER_DETAIL.MONTHLY_INCOME, USER_DETAIL.CID)
				.from(a.leftJoin(USER_DETAIL).on(a.USER_ID.eq(USER_DETAIL.USER_ID)))
				.where(a.USER_ID.eq(userId)).fetchAny();
	}

	/**
	 * 查询会员最近浏览时间
	 * @param userId
	 * @return
	 */
	public Record2<Timestamp, Timestamp> getRecentBrowseTime(Integer userId) {
		return db().select(USER_LOGIN_RECORD.CREATE_TIME, USER_LOGIN_RECORD.UPDATE_TIME).from(USER_LOGIN_RECORD)
				.where(USER_LOGIN_RECORD.USER_ID.eq(userId)).orderBy(USER_LOGIN_RECORD.ID.desc()).limit(1).fetchOne();
	}
	
	/** 获取门店名称 */
	public Record1<String> getStoreName(String source) {
		return db().select(STORE.STORE_NAME).from(STORE).where(STORE.STORE_ID.eq(Integer.parseInt(source)))
				.fetchAny();
	}
	
	/** 
	 * 获取一张会员卡信息
	 * @param localDateTime
	 * @param inData 工作日，休息，或 无限制
	 * @param userId
	 * @return
	 */
	public Record getOneMemberCard(List<Integer> inData, Integer userId) {
		return db()
				.select(USER_CARD.asterisk(), MEMBER_CARD.CARD_NAME, MEMBER_CARD.CARD_TYPE, MEMBER_CARD.DISCOUNT,
						MEMBER_CARD.BG_TYPE, MEMBER_CARD.BG_COLOR, MEMBER_CARD.BG_IMG, MEMBER_CARD.BUY_SCORE,
						MEMBER_CARD.EXPIRE_TYPE, MEMBER_CARD.START_TIME, MEMBER_CARD.END_TIME,
						MEMBER_CARD.RECEIVE_DAY, MEMBER_CARD.DATE_TYPE, MEMBER_CARD.STORE_LIST,
						MEMBER_CARD.ACTIVATION)
				.from(USER_CARD.leftJoin(MEMBER_CARD).on(USER_CARD.CARD_ID.eq(MEMBER_CARD.ID)))
				.where(USER_CARD.USER_ID.eq(userId)).and(USER_CARD.FLAG.eq(CARD_USING))
				.and(USER_CARD.EXPIRE_TIME.greaterThan(DateUtil.getLocalDateTime()).or(MEMBER_CARD.EXPIRE_TYPE.eq(FOREVER)))
				.and(MEMBER_CARD.USE_TIME.in(inData).or(MEMBER_CARD.USE_TIME.isNull()))
				.and((MEMBER_CARD.EXPIRE_TYPE.eq(FIX_DATETIME).and(MEMBER_CARD.START_TIME.le(DateUtil.getLocalDateTime())))
						.or(MEMBER_CARD.EXPIRE_TYPE.in(DURING_TIME, FOREVER)))
				.orderBy(USER_CARD.IS_DEFAULT.desc(),MEMBER_CARD.CARD_TYPE.desc(), MEMBER_CARD.GRADE.desc()).limit(1).fetchAny();
	}
	
}
