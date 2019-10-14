package com.vpu.mp.service.shop.member.dao;

import static com.vpu.mp.db.shop.Tables.MEMBER_CARD;
import static com.vpu.mp.db.shop.Tables.USER_CARD;
import static com.vpu.mp.db.shop.Tables.SHOP_CFG;
import static com.vpu.mp.db.shop.Tables.USER;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.CardUpgradeRecord;
import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.db.shop.tables.records.ShopCfgRecord;
import com.vpu.mp.db.shop.tables.records.UserCardRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.member.account.MemberCard;
import com.vpu.mp.service.pojo.shop.member.card.ValidUserCardBean;
import com.vpu.mp.service.shop.member.UserCardService;

import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.ACTIVE_NO;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.ACTIVE_YES;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.CARD_USING;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.RANK_TYPE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.DELETE_YES;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.DELETE_NO;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.FIX_DATETIME;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.DURING_TIME;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.FOREVER;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.AVAILABLE_IN_STORE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MEMBER_CARD_USING;

/**
* @author 黄壮壮
* @Date: 2019年10月10日
* @Description:
*/
@Service
public class UserCardDaoService extends ShopBaseService{
	@Autowired private  UserCardService userCardService;
	
	public Record1<String> getUserGradeRecord(Integer userId) { 
		return db().select(MEMBER_CARD.GRADE).from(USER_CARD.leftJoin(MEMBER_CARD).on(MEMBER_CARD.ID.eq(USER_CARD.CARD_ID)))
				.where(USER_CARD.FLAG.eq(CARD_USING))
				.and(MEMBER_CARD.CARD_TYPE.eq(RANK_TYPE))
				.and(USER_CARD.USER_ID.eq(userId))
				.and((MEMBER_CARD.ACTIVATION.eq(ACTIVE_NO)).or(MEMBER_CARD.ACTIVATION.eq(ACTIVE_YES).and(USER_CARD.ACTIVATION_TIME.isNotNull())))
				.fetchAny(); 
	}
	
	/**
	 * 返回旧的等级卡： {id,cardName,grade}
	 * @param userId
	 * @return
	 */
	public Record3<Integer, String, String> fetchOldGradeCard(Integer userId) {
		 return db().select(MEMBER_CARD.ID,MEMBER_CARD.CARD_NAME,MEMBER_CARD.GRADE)
			.from(USER_CARD.leftJoin(MEMBER_CARD).on(MEMBER_CARD.ID.eq(USER_CARD.CARD_ID)))
			.where(MEMBER_CARD.CARD_TYPE.eq(RANK_TYPE))
			.and(USER_CARD.USER_ID.eq(userId))
			.fetchAny();
			
	}
	/**
	 * 获取会员卡
	 * @param cardId
	 * @return
	 */
	public MemberCardRecord getMemberCardById(Integer cardId) {
		  return db().selectFrom(MEMBER_CARD).where(MEMBER_CARD.ID.eq(cardId)).fetchAny();
	}
	
	/**
	 * 更新userCard
	 * @param userId
	 * @param cardId
	 */
	public void updateUserCard(Integer userId,Integer cardId) {
		db().update(USER_CARD.leftJoin(MEMBER_CARD).on(MEMBER_CARD.ID.eq(USER_CARD.CARD_ID)))
			.set(USER_CARD.CARD_ID,cardId).set(USER_CARD.FLAG,CARD_USING)
			.where(MEMBER_CARD.CARD_TYPE.eq(RANK_TYPE))
			.and(USER_CARD.USER_ID.eq(userId))
			.execute();
	}
	
	/**
	 * 插入会员持卡升级记录
	 * @param r
	 */
	public void insertIntoCardUpGrade(CardUpgradeRecord r) {
		
		db().executeInsert(r);
	}
	
	/**
	 * 获取店铺的积分限制配置信息
	 * @return 
	 */
	public String getScoreLimit() {
		String val = "score_limit";
		ShopCfgRecord res = db().selectFrom(SHOP_CFG).where(SHOP_CFG.K.eq(val)).fetchAny();
		if(res != null) {
			return res.getV();
		}else {
			return null;
		}
		
	}
	

	public void updateCardFlag(List<Integer> cardIdList, List<Integer> cardNoList) {
		db().update(USER_CARD)
			.set(USER_CARD.FLAG, DELETE_YES)
			.where(USER_CARD.CARD_ID.in(cardIdList))
			.and(USER_CARD.CARD_NO.notIn(cardNoList))
			.execute();
	}
	
	/**
	 * 获取可用的用户卡
	 * @param userId
	 * @param card
	 */
	public UserCardRecord getUsableUserCard(Integer userId, MemberCard card) {
		SelectConditionStep<UserCardRecord> sql = db().selectFrom(USER_CARD)
			.where(USER_CARD.CARD_ID.eq(userId))
			.and(USER_CARD.CARD_ID.eq(card.getId()))
			.and(USER_CARD.FLAG.eq(CARD_USING));
		if(StringUtils.isBlank(card.getCardName())) {
			sql.and((USER_CARD.EXPIRE_TIME.isNull()).or(USER_CARD.EXPIRE_TIME.ge(DateUtil.getLocalDateTime())));
		}
		
		 return sql.fetchAny();
	}
	
	/**
	 * 获取有效用户会员卡列表
	 * @param userId
	 * @param cardType -1所有可用卡  卡类型
	 * @param type 0线上 1线下
	 */
	public List<ValidUserCardBean> getValidCardList(Integer userId,Byte cardType,Integer type) {
		if(cardType == -1) {
			// 所有可用卡
			 return getAllValidCardList(userId);	
		}
		
		List<Integer> inDate = userCardService.useInDate();
		
		if(type == 1) {
			// 线下处理
			
			return getOfflineValidCardList(userId, cardType);
							
		}
		
		// 线上
		return getOnlineValidCardList(userId, cardType);
	}
	
	private List<ValidUserCardBean> getOnlineValidCardList(Integer userId, Byte cardType){
		return selectValidCardCondition(userId, cardType)
				.orderBy(USER_CARD.IS_DEFAULT.desc(),MEMBER_CARD.GRADE.desc())
				.fetchInto(ValidUserCardBean.class);
	}
	
	private List<ValidUserCardBean> getOfflineValidCardList(Integer userId, Byte cardType) {
		return selectValidCardCondition(userId, cardType)
							.and(MEMBER_CARD.STORE_USE_SWITCH.eq(AVAILABLE_IN_STORE))
							.orderBy(USER_CARD.IS_DEFAULT.desc(),MEMBER_CARD.GRADE.desc())
							.fetchInto(ValidUserCardBean.class);
	}

	private SelectConditionStep<Record> selectValidCardCondition(Integer userId, Byte cardType) {
		return selectValidCardSQL().where(USER_CARD.USER_ID.eq(userId))
							.and(USER_CARD.FLAG.eq(DELETE_NO))
							.and(MEMBER_CARD.CARD_TYPE.eq(cardType))
							.and(
									(USER_CARD.EXPIRE_TIME.greaterThan(DateUtil.getLocalDateTime()))
									.or(MEMBER_CARD.EXPIRE_TYPE.eq(FOREVER))
								)
							.and(
									(MEMBER_CARD.USE_TIME.in(userCardService.useInDate()))
									.or(MEMBER_CARD.USE_TIME.isNull())
								)
							.and(
									((MEMBER_CARD.EXPIRE_TYPE.eq(FIX_DATETIME)).and(MEMBER_CARD.START_TIME.le(DateUtil.getLocalDateTime())))
									.or(MEMBER_CARD.EXPIRE_TYPE.in(DURING_TIME,FOREVER))
								)
							.and(
									(MEMBER_CARD.ACTIVATION.eq(ACTIVE_YES).and(USER_CARD.ACTIVATION_TIME.isNotNull()))
									.or(MEMBER_CARD.ACTIVATION.eq(ACTIVE_NO))
									.or(MEMBER_CARD.ACTIVATION.isNull())
								);
	}

	/**
	 * 获取用户所有的可用卡列表
	 * @param userId
	 * @return
	 */
	private List<ValidUserCardBean> getAllValidCardList(Integer userId) {
		
		return selectValidCardSQL()
			.where(USER_CARD.USER_ID.eq(userId))
			.and(USER_CARD.FLAG.eq(DELETE_NO))
			.and(
					(USER_CARD.EXPIRE_TIME.isNull())
					.or(USER_CARD.EXPIRE_TIME.greaterThan(DateUtil.getLocalDateTime()))
				)
			.and(	
					((MEMBER_CARD.EXPIRE_TYPE.eq(FIX_DATETIME)).and(MEMBER_CARD.START_TIME.le(DateUtil.getLocalDateTime())))
					.or(MEMBER_CARD.EXPIRE_TYPE.in(DURING_TIME,FOREVER))
				)
			.orderBy(USER_CARD.IS_DEFAULT.desc(),MEMBER_CARD.GRADE.desc())
			.fetchInto(ValidUserCardBean.class);
	}
	
	/**
	 * 查询用户有效卡的信息
	 * @return
	 */
	private SelectJoinStep<Record> selectValidCardSQL() {
		 return db().select(USER_CARD.asterisk(),MEMBER_CARD.CARD_NAME,MEMBER_CARD.CARD_TYPE,MEMBER_CARD.DISCOUNT,MEMBER_CARD.BG_TYPE,MEMBER_CARD.BG_COLOR,
				MEMBER_CARD.BG_IMG,MEMBER_CARD.BUY_SCORE,MEMBER_CARD.EXPIRE_TYPE,MEMBER_CARD.START_TIME,MEMBER_CARD.END_TIME,MEMBER_CARD.RECEIVE_DAY,
				MEMBER_CARD.DATE_TYPE,MEMBER_CARD.STORE_LIST,MEMBER_CARD.ACTIVATION,MEMBER_CARD.GRADE)
			.from(USER_CARD.leftJoin(MEMBER_CARD).on(USER_CARD.CARD_ID.eq(MEMBER_CARD.ID)));
	}
	
	/**
	 * 获取用户持有会员卡的等级
	 */
	public String getUserCardGrade(Integer userId) {
		return db().select(MEMBER_CARD.GRADE).from(USER_CARD.leftJoin(MEMBER_CARD).on(USER_CARD.CARD_ID.eq(MEMBER_CARD.ID)))
			.where(USER_CARD.USER_ID.eq(userId))
			.and(MEMBER_CARD.CARD_TYPE.eq(RANK_TYPE))
			.and(USER_CARD.FLAG.eq(MEMBER_CARD_USING))
			.fetchAnyInto(String.class);
	}

	/**
	 * 根据id获取用户名
	 * @param id
	 * @return
	 */
	public String getUserName(Integer id) {
		return db().select(USER.USERNAME).from(USER).where(USER.USER_ID.eq(id)).fetchAnyInto(String.class);
	}
}
