package com.vpu.mp.service.shop.member.dao;

import static com.vpu.mp.db.shop.Tables.MEMBER_CARD;
import static com.vpu.mp.db.shop.Tables.USER_CARD;
import static com.vpu.mp.db.shop.Tables.SHOP_CFG;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.SelectConditionStep;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.CardUpgradeRecord;
import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.db.shop.tables.records.ShopCfgRecord;
import com.vpu.mp.db.shop.tables.records.UserCardRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.member.account.MemberCard;

import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.ACTIVE_NO;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.ACTIVE_YES;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.CARD_USING;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.RANK_TYPE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.DELETE_YES;

/**
* @author 黄壮壮
* @Date: 2019年10月10日
* @Description:
*/
@Service
public class UserCardDaoService extends ShopBaseService{
	
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

}
