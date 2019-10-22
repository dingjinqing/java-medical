package com.vpu.mp.service.shop.member.dao;

import static com.vpu.mp.db.shop.Tables.MEMBER_CARD;
import static com.vpu.mp.db.shop.Tables.ORDER_GOODS;
import static com.vpu.mp.db.shop.Tables.ORDER_INFO;
import static com.vpu.mp.db.shop.Tables.STORE;
import static com.vpu.mp.db.shop.Tables.USER;
import static com.vpu.mp.db.shop.Tables.USER_CARD;
import static com.vpu.mp.db.shop.Tables.USER_CART_RECORD;
import static com.vpu.mp.db.shop.Tables.USER_DETAIL;
import static com.vpu.mp.db.shop.Tables.USER_LOGIN_RECORD;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.SelectOnConditionStep;
import org.jooq.SelectSeekStep3;
import org.springframework.stereotype.Service;
import org.jooq.tools.StringUtils;
import com.vpu.mp.db.shop.tables.User;
import com.vpu.mp.db.shop.tables.records.UserDetailRecord;
import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.member.MemberPageListParam;
import com.vpu.mp.service.pojo.shop.member.MemberParam;
import com.vpu.mp.service.pojo.shop.member.card.UserCardDetailParam;

import static com.vpu.mp.service.pojo.shop.member.MemberConstant.INVITE_USERNAME;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.CARD_USING;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.DURING_TIME;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.FIX_DATETIME;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.FOREVER;
import static com.vpu.mp.service.pojo.shop.order.OrderConstant.ORDER_WAIT_DELIVERY;
import static org.jooq.impl.DSL.count;


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
	 * @return
	 */
	public UserRecord getUserInfo(Integer userId) {
		Record member = this.getMemberInfo(userId);
		if(member != null) {
			member.into(UserRecord.class);
		}
		return null;
	}
	/**
	 * 获取会员用户的详细信息
	 * @param userId
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
	 * 获取一张会员卡信息,此会员卡为等级会员，限次会员卡再到普通会员卡
	 * @param localDateTime
	 * @param inData 工作日，休息，或 无限制
	 * @param userId
	 * @return
	 */
	public Record getOneMemberCard(List<Integer> inData, Integer userId) {
			//.limit(1).fetchAny();
		SelectSeekStep3<Record, Byte, Byte, String> sql = getMemberCardSql(inData,userId);
		return sql.limit(1).fetchAny();
	}
	
	/**
	 * 获取用户的所有可用会员卡
	 * @param userId
	 * @return
	 */
	public Result<Record> getAllAvailableMemberCard(Integer userId) {
		List<Integer> data = Arrays.asList(new Integer[]{0,1,2});
		SelectSeekStep3<Record, Byte, Byte, String> sql = getMemberCardSql(data,userId);
		return sql.fetch();
	}
	
	/**
	 * 获取用户会员卡sql
	 * @param inData
	 * @param userId
	 * @return
	 */
	private SelectSeekStep3<Record, Byte, Byte, String> getMemberCardSql(List<Integer> inData, Integer userId) {
		return  db()
		.select(USER_CARD.asterisk(), MEMBER_CARD.ID,MEMBER_CARD.CARD_NAME, MEMBER_CARD.CARD_TYPE, MEMBER_CARD.DISCOUNT,
				MEMBER_CARD.BG_TYPE, MEMBER_CARD.BG_COLOR, MEMBER_CARD.BG_IMG, MEMBER_CARD.BUY_SCORE,
				MEMBER_CARD.EXPIRE_TYPE, MEMBER_CARD.START_TIME, MEMBER_CARD.END_TIME,
				MEMBER_CARD.RECEIVE_DAY, MEMBER_CARD.DATE_TYPE, MEMBER_CARD.STORE_LIST,
				MEMBER_CARD.ACTIVATION)
		.from(USER_CARD.leftJoin(MEMBER_CARD).on(USER_CARD.CARD_ID.eq(MEMBER_CARD.ID)))
		.where(USER_CARD.USER_ID.eq(userId)).and(USER_CARD.FLAG.eq(CARD_USING))
//		.and(USER_CARD.EXPIRE_TIME.greaterThan(DateUtil.getLocalDateTime()).or(MEMBER_CARD.EXPIRE_TYPE.eq(FOREVER)))
		.and(MEMBER_CARD.USE_TIME.in(inData).or(MEMBER_CARD.USE_TIME.isNull()))
		.and((MEMBER_CARD.EXPIRE_TYPE.eq(FIX_DATETIME).and(MEMBER_CARD.START_TIME.le(DateUtil.getLocalDateTime())))
				.or(MEMBER_CARD.EXPIRE_TYPE.in(DURING_TIME, FOREVER)))
		.orderBy(USER_CARD.IS_DEFAULT.desc(),MEMBER_CARD.CARD_TYPE.desc(), MEMBER_CARD.GRADE.desc());
	}
	
	
	
	/**
	 * 获取持有会员卡的用户id
	 * @return
	 */
	public Result<Record1<Integer>> getUserIdByCardExist() {
		Timestamp localDateTime = DateUtil.getLocalDateTime();
		return db().select(USER_CARD.USER_ID)
			.from(USER_CARD.leftJoin(MEMBER_CARD).on(USER_CARD.CARD_ID.eq(MEMBER_CARD.ID)))
			.where(USER_CARD.FLAG.eq(CARD_USING))
			.and(USER_CARD.EXPIRE_TIME.greaterThan(localDateTime).or(MEMBER_CARD.EXPIRE_TYPE.eq(FOREVER)))
			.and((MEMBER_CARD.EXPIRE_TYPE.eq(FIX_DATETIME).and(MEMBER_CARD.START_TIME.le(localDateTime)))
		            .or(MEMBER_CARD.EXPIRE_TYPE.in(DURING_TIME, FOREVER)))
			.fetch();
	}
	
	/**
	 * 通过商品id获取购买过该商品的用户id
	 * @param param
	 * @return
	 */
	public Result<Record1<Integer>> getMemberIdByGoodsId(MemberPageListParam param) {
		return db().select(ORDER_INFO.USER_ID)
			.from(ORDER_GOODS.leftJoin(ORDER_INFO).on(ORDER_GOODS.ORDER_SN.eq(ORDER_INFO.ORDER_SN)))
			.where(ORDER_INFO.ORDER_STATUS.ge(ORDER_WAIT_DELIVERY))
			.and(ORDER_GOODS.GOODS_ID.in(param.getGoodsId()))
			.groupBy(ORDER_INFO.USER_ID)
			.fetch();
	}
	
	/**
	 * 获取指定范围内最高次数
	 * @param hight 
	 * @param param
	 * @return
	 */
	public Result<Record1<Integer>> getBuyCountHight(Integer hight) {
		return db().select(ORDER_INFO.USER_ID)
				.from(ORDER_INFO)
				.groupBy(ORDER_INFO.USER_ID)
				.having(count(ORDER_INFO.USER_ID).le(hight))
				.fetch();
	}
	
	/**
	 * 获取指定范围内最低次数
	 * @param low
	 * @return
	 */
	public Result<Record1<Integer>> getBuyCountLow(Integer low) {
		return db().select(ORDER_INFO.USER_ID)
			.from(ORDER_INFO)
			.groupBy(ORDER_INFO.USER_ID)
			.having(count(ORDER_INFO.USER_ID).ge(low))
			.fetch();
	}
	
	/**
	 * 时间内有交易记录-开始时间
	 * @param startTime
	 * @return
	 */
	public Result<Record1<Integer>> getBuyStartTime(String startTime) {
		return db().select(ORDER_INFO.USER_ID)
			.from(ORDER_INFO)
			.where(ORDER_INFO.ORDER_STATUS.ge(ORDER_WAIT_DELIVERY))
			.and(ORDER_INFO.CREATE_TIME.ge(DateUtil.convertToTimestamp(startTime)))
			.groupBy(ORDER_INFO.USER_ID)
			.fetch();
	}
	
	/**
	 * 时间内有交易记录-结束时间
	 * @param startTime
	 * @return
	 */
	public Result<Record1<Integer>> getBuyEndTime(String endTime) {
		return db().select(ORDER_INFO.USER_ID)
			.from(ORDER_INFO)
			.where(ORDER_INFO.ORDER_STATUS.ge(ORDER_WAIT_DELIVERY))
			.and(ORDER_INFO.CREATE_TIME.le(DateUtil.convertToTimestamp(endTime)))
			.groupBy(ORDER_INFO.USER_ID)
			.fetch();
	}
	
	/**
	 * 指定时间内登录-开始时间
	 * @param startTime
	 * @return
	 */
	public Result<Record1<Integer>> getLoginStartTime(String startTime) {
		return db().select(USER_LOGIN_RECORD.USER_ID)
			.from(USER_LOGIN_RECORD)
			.where(USER_LOGIN_RECORD.CREATE_TIME.ge(DateUtil.convertToTimestamp(startTime)))
			.groupBy(USER_LOGIN_RECORD.USER_ID)
			.fetch();
	}


	

	/**
	 * 指定时间内登录-结束时间
	 * @param string
	 * @return
	 */
	public Result<Record1<Integer>> getLoginEndTime(String endTime) {
		return db().select(USER_LOGIN_RECORD.USER_ID)
			.from(USER_LOGIN_RECORD)
			.where(USER_LOGIN_RECORD.CREATE_TIME.le(DateUtil.convertToTimestamp(endTime)))
			.groupBy(USER_LOGIN_RECORD.USER_ID)
			.fetch();
	}

	/**
	 * 指定时间内有加购行为-开始时间
	 * @param string
	 * @return
	 */
	public Result<Record1<Integer>> getCartStartTime(String startTime) {
		return db().select(USER_CART_RECORD.USER_ID)
			.from(USER_CART_RECORD)
			.where(USER_CART_RECORD.CREATE_TIME.ge(DateUtil.convertToTimestamp(startTime)))
			.groupBy(USER_CART_RECORD.USER_ID)
			.fetch();
	}

	/**
	 * 指定时间内有加购行为-结束时间
	 * @param string
	 * @return
	 */
	public Result<Record1<Integer>> getCartEndTime(String endTime) {
		return db().select(USER_CART_RECORD.USER_ID)
			.from(USER_CART_RECORD)
			.where(USER_CART_RECORD.CREATE_TIME.le(DateUtil.convertToTimestamp(endTime)))
			.groupBy(USER_CART_RECORD.USER_ID)
			.fetch();
	}
	
	/**
	 * 更新用户的邀请人id
	 * @param userId
	 * @param invitedId
	 */
	public void updateMemberInviteId(Integer userId,Integer invitedId) {
		db().update(USER).set(USER.INVITE_ID, invitedId).where(USER.USER_ID.eq(userId)).execute();
	}
	
	/**
	 * 更新用户信息
	 * @param param
	 */
	public void updateMemberInfoSql(MemberParam param) {
		logger().info("正在更新用户信息");
		UserDetailRecord record = new UserDetailRecord();
		buildMemberInfoOptions(param, record);
		db().update(USER_DETAIL).set(record).where(USER_DETAIL.USER_ID.eq(param.getUserId())).execute();
	}

	/**
	 * 构建更新字段
	 * @param param
	 * @param record
	 */
	private void buildMemberInfoOptions(MemberParam param, UserDetailRecord record) {
		/** -生日-年 */
		if(param.getBirthdayYear() != null) {
			record.setBirthdayYear(param.getBirthdayYear());
		}
		/** -生日-月 */
		if(param.getBirthdayMonth() != null) {
			record.setBirthdayMonth(param.getBirthdayMonth());
		}
		/** -生日-天 */
		if(param.getBirthdayDay() != null) {
			record.setBirthdayDay(param.getBirthdayDay());
		}
		/** -婚姻状况*/
		if(param.getMaritalStatus() != null) {
			record.setMaritalStatus(param.getMaritalStatus());
		}
		/** -真实姓名 */
		if(!StringUtils.isBlank(param.getRealName())) {
			record.setRealName(param.getRealName());
		}
		/** -月收入 */
		if(param.getMonthlyIncome() != null) {
			record.setMonthlyIncome(param.getMonthlyIncome());
		}
		/** -身份证  */
		if(!StringUtils.isBlank(param.getCid())) {
			record.setCid(param.getCid());
		}
		/** -受教育程度 */
		if(param.getEducation()!=null) {
			record.setEducation(param.getEducation());
		}
		
		/** -行业 */
		if(param.getIndustory()!= null) {
			record.setIndustryInfo(param.getIndustory());
		}
		/** -性别 */
		if(!StringUtils.isBlank(param.getSex())) {
			record.setSex(param.getSex());
		}
		
	}
	
	/**
	 *  会员持有会员卡详情
	 * @param userId
	 * @return
	 */
	public Result<Record> getAllUserCardDetailSql(UserCardDetailParam param) {
		SelectOnConditionStep<Record> select = db().select(USER_CARD.asterisk(),MEMBER_CARD.CARD_NAME,MEMBER_CARD.CARD_TYPE,USER.USERNAME)
													.from(USER_CARD.leftJoin(MEMBER_CARD).on(USER_CARD.CARD_ID.eq(MEMBER_CARD.ID)))
													.leftJoin(USER).on(USER_CARD.USER_ID.eq(USER.USER_ID));
		buildOptionsForUserCard(select,param);
		return select.fetch();
			
	}
	/**
	 * 构建查询会员持有会员卡详情的参数
	 * @param select
	 * @param param
	 */
	private void buildOptionsForUserCard(SelectOnConditionStep<Record> select, UserCardDetailParam param) {
		/** - 用户id */
		if(param.getUserId()!=null) {
			select.where(USER_CARD.USER_ID.equal(param.getUserId()));
		}
		/** -手机号 */
		if(!StringUtils.isBlank(param.getMobile())) {
			select.where(USER.MOBILE.eq(param.getMobile()));
		}
		/** - 昵称 */
		if(!StringUtils.isBlank(param.getUsername())) {
			select.where(USER.USERNAME.eq(param.getUsername()));
		}
		/** -领取时间 -开始 */
		if(!StringUtils.isBlank(param.getCreateTimeFirst())) {
			select.where(USER_CARD.CREATE_TIME.ge(DateUtil.convertToTimestamp(param.getCreateTimeFirst())));
		}
		
		/** - 领取时间-结束 */
		if(!StringUtils.isBlank(param.getCreateTimeSecond())) {
			select.where(USER_CARD.CREATE_TIME.le(DateUtil.convertToTimestamp(param.getCreateTimeSecond())));
		}
		
		/** - 会员卡id */
		if(param.getCardId()!=null) {
			select.where(MEMBER_CARD.ID.eq(param.getCardId()));
		}
		
		/** - 会员卡类型 */
		if(param.getCardType() != null) {
			select.where(MEMBER_CARD.CARD_TYPE.eq(param.getCardType()));
		}
	}
	
	
	
}
