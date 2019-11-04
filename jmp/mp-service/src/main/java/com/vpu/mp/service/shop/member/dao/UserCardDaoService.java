package com.vpu.mp.service.shop.member.dao;

import static com.vpu.mp.db.shop.Tables.CARD_CONSUMER;
import static com.vpu.mp.db.shop.Tables.CHARGE_MONEY;
import static com.vpu.mp.db.shop.Tables.MEMBER_CARD;
import static com.vpu.mp.db.shop.Tables.SHOP_CFG;
import static com.vpu.mp.db.shop.Tables.USER;
import static com.vpu.mp.db.shop.Tables.USER_CARD;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_ACT_NO;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_ACT_YES;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.AVAILABLE_IN_STORE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.UCARD_FG_USING;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.UCARD_FG_STOP;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_DF_NO;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_DF_YES;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_ET_DURING;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_ET_FIX;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_ET_FOREVER;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_TP_GRADE;

import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.jooq.SelectSeekStep3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.CardConsumerRecord;
import com.vpu.mp.db.shop.tables.records.CardUpgradeRecord;
import com.vpu.mp.db.shop.tables.records.ChargeMoneyRecord;
import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.db.shop.tables.records.ShopCfgRecord;
import com.vpu.mp.db.shop.tables.records.UserCardRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.FieldsUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.member.account.UserCardParam;
import com.vpu.mp.service.pojo.shop.member.account.WxAppUserCardVo;
import com.vpu.mp.service.pojo.shop.member.card.SearchCardParam;
import com.vpu.mp.service.pojo.shop.member.card.UserCardConsumeBean;
import com.vpu.mp.service.pojo.shop.member.card.ValidUserCardBean;
import com.vpu.mp.service.shop.member.UserCardService;

/**
* @author 黄壮壮
* @Date: 2019年10月10日
* @Description:
*/
@Service
public class UserCardDaoService extends ShopBaseService{
	@Autowired private  UserCardService userCardService;
	
	
	
	/**
	 * 获取用户持有的等级卡
	 * @param userId
	 * @return 
	 * @return
	 */
	public MemberCardRecord getUserGradeCard(Integer userId) {
		return (MemberCardRecord) db().select(MEMBER_CARD.asterisk())
				.from(USER_CARD.leftJoin(MEMBER_CARD).on(MEMBER_CARD.ID.eq(USER_CARD.CARD_ID)))
				.where(MEMBER_CARD.CARD_TYPE.eq(MCARD_TP_GRADE))
				.and(USER_CARD.USER_ID.eq(userId))
				.fetchAny();
	}
	
	/**
	 * 获取会员卡
	 * @param cardId 会员卡Id
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
			.set(USER_CARD.CARD_ID,cardId).set(USER_CARD.FLAG,UCARD_FG_USING)
			.where(MEMBER_CARD.CARD_TYPE.eq(MCARD_TP_GRADE))
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
	

	public void updateCardFlag(List<Integer> cardIdList, List<String> cardNoList) {
		db().update(USER_CARD)
			.set(USER_CARD.FLAG, UCARD_FG_STOP)
			.where(USER_CARD.CARD_ID.in(cardIdList))
			.and(USER_CARD.CARD_NO.notIn(cardNoList))
			.execute();
	}
	
	/**
	 * 获取可用的用户卡
	 * @param userId
	 * @param card
	 */
	public UserCardRecord getUsableUserCard(Integer userId, UserCardParam card) {
		SelectConditionStep<UserCardRecord> sql = db().selectFrom(USER_CARD)
			.where(USER_CARD.CARD_ID.eq(userId))
			.and(USER_CARD.CARD_ID.eq(card.getCardId()))
			.and(USER_CARD.FLAG.eq(UCARD_FG_USING));
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
							.and(USER_CARD.FLAG.eq(MCARD_DF_NO))
							.and(MEMBER_CARD.CARD_TYPE.eq(cardType))
							.and(
									(USER_CARD.EXPIRE_TIME.greaterThan(DateUtil.getLocalDateTime()))
									.or(MEMBER_CARD.EXPIRE_TYPE.eq(MCARD_ET_FOREVER))
								)
							.and(
									(MEMBER_CARD.USE_TIME.in(userCardService.useInDate()))
									.or(MEMBER_CARD.USE_TIME.isNull())
								)
							.and(
									((MEMBER_CARD.EXPIRE_TYPE.eq(MCARD_ET_FIX)).and(MEMBER_CARD.START_TIME.le(DateUtil.getLocalDateTime())))
									.or(MEMBER_CARD.EXPIRE_TYPE.in(MCARD_ET_DURING,MCARD_ET_FOREVER))
								)
							.and(
									(MEMBER_CARD.ACTIVATION.eq(MCARD_ACT_YES).and(USER_CARD.ACTIVATION_TIME.isNotNull()))
									.or(MEMBER_CARD.ACTIVATION.eq(MCARD_ACT_NO))
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
			.and(USER_CARD.FLAG.eq(MCARD_DF_NO))
			.and(
					(USER_CARD.EXPIRE_TIME.isNull())
					.or(USER_CARD.EXPIRE_TIME.greaterThan(DateUtil.getLocalDateTime()))
				)
			.and(	
					((MEMBER_CARD.EXPIRE_TYPE.eq(MCARD_ET_FIX)).and(MEMBER_CARD.START_TIME.le(DateUtil.getLocalDateTime())))
					.or(MEMBER_CARD.EXPIRE_TYPE.in(MCARD_ET_DURING,MCARD_ET_FOREVER))
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
			.and(MEMBER_CARD.CARD_TYPE.eq(MCARD_TP_GRADE))
			.and(USER_CARD.FLAG.eq(UCARD_FG_USING))
			.fetchAnyInto(String.class);
	}

	/**
	 * 计算用户等级
	 * @param userId
	 * @return
	 */
	public String calcUserGrade(Integer userId) { 
		return db().select(MEMBER_CARD.GRADE)
				.from(USER_CARD.leftJoin(MEMBER_CARD).on(MEMBER_CARD.ID.eq(USER_CARD.CARD_ID)))
				.where(USER_CARD.FLAG.eq(UCARD_FG_USING))
				.and(MEMBER_CARD.CARD_TYPE.eq(MCARD_TP_GRADE))
				.and(USER_CARD.USER_ID.eq(userId))
				.and((MEMBER_CARD.ACTIVATION.eq(MCARD_ACT_NO)).or(MEMBER_CARD.ACTIVATION.eq(MCARD_ACT_YES).and(USER_CARD.ACTIVATION_TIME.isNotNull())))
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
	
	//编辑会员卡详情
	public int updateUserCardByNo(String cardNo,UserCardRecord record) {
		return  db().update(USER_CARD).set(record).where(USER_CARD.CARD_NO.eq(cardNo)).execute();
	}
	
	
	/**
	 * 获取会员卡详情
	 * @param cardNo
	 * @return
	 */
	public UserCardParam getUserCardInfo(String cardNo) {
		return db().select(USER_CARD.asterisk(),MEMBER_CARD.asterisk())
			.from(USER_CARD.leftJoin(MEMBER_CARD).on(USER_CARD.CARD_ID.eq(MEMBER_CARD.ID)))
			.where(USER_CARD.CARD_NO.eq(cardNo))
			.fetchAnyInto(WxAppUserCardVo.class);
	}
	
	public int getHasSend(Integer cardId){
		return db().selectCount().from(USER_CARD).where(USER_CARD.CARD_ID.eq(cardId)).execute();
	}
	
	/**
	 * 升级卡
	 * @param cardInfo
	 * @param userId
	 */
	public void updateUserRankCard(MemberCardRecord cardInfo,Integer userId) {
		db().update(USER_CARD.leftJoin(MEMBER_CARD).on(USER_CARD.CARD_ID.eq(MEMBER_CARD.ID)))
			.set(USER_CARD.CARD_ID,cardInfo.getId())
			.set(USER_CARD.UPDATE_TIME,DateUtil.getLocalDateTime())
			.set(USER_CARD.FLAG,UCARD_FG_USING)
			.where(MEMBER_CARD.CARD_TYPE.eq(MCARD_TP_GRADE))
			.and(USER_CARD.USER_ID.eq(userId))
			.and(USER_CARD.FLAG.eq(UCARD_FG_USING))
			.execute();
	}
	
	
	/**
	 * 更新卡余额
	 * @param data
	 * @param userInfo
	 */
	public int updateUserCardMoney(UserCardConsumeBean data, UserCardParam userInfo) {
		return db().update(USER_CARD)
			.set(USER_CARD.MONEY,userInfo.getMoney().add(data.getMoney()))
			.where(USER_CARD.CARD_NO.eq(data.getCardNo()))
			.execute();
	}
	/**
	 * 更新卡剩余次数
	 * @param data
	 * @param userInfo
	 * @return
	 */
	public int updateUserCardSurplus(UserCardConsumeBean data, UserCardParam userInfo) {
		return db().update(USER_CARD)
			.set(USER_CARD.SURPLUS,userInfo.getSurplus()+data.getCount())
			.where(USER_CARD.CARD_NO.eq(data.getCardNo()))
			.execute();
	}
	
	/**
	 * 更新卡剩余兑换次数
	 * @param data
	 * @param userInfo
	 * @return 
	 */
	public int updateUserCardExchangePlus(UserCardConsumeBean data, UserCardParam userInfo) {
		return db().update(USER_CARD)
			.set(USER_CARD.EXCHANG_SURPLUS,userInfo.getExchangSurplus()+data.getExchangCount())
			.where(USER_CARD.CARD_NO.eq(data.getCardNo()))
			.execute();
	}
	
	/**
	 * 消费记录
	 * @param data
	 */
	public void insertIntoCharge(UserCardConsumeBean data) {
		ChargeMoneyRecord chargeMoney = db().newRecord(CHARGE_MONEY);
		FieldsUtil.assignNotNull(data, chargeMoney);
		chargeMoney.insert();
	}
	/**
	 * 充值记录
	 * @param data
	 */
	public void insertConsume(UserCardConsumeBean data) {
		CardConsumerRecord cardConsumer = db().newRecord(CARD_CONSUMER);
		FieldsUtil.assignNotNull(data, cardConsumer);
		cardConsumer.insert();
	}
	
	/**
	 * 获取用户会员卡列表
	 */
	public PageResult<WxAppUserCardVo> getCardList(SearchCardParam param) {
		
		 SelectSeekStep3<Record, String, Byte, Timestamp> select = db().select(
				 	USER_CARD.USER_ID,USER_CARD.CARD_ID,USER_CARD.FLAG.as("userCardFlag"),USER_CARD.CARD_NO,USER_CARD.EXPIRE_TIME,
				 	USER_CARD.IS_DEFAULT,USER_CARD.MONEY,USER_CARD.SURPLUS,USER_CARD.ACTIVATION_TIME,USER_CARD.EXCHANG_SURPLUS,
				 	USER_CARD.CREATE_TIME.as("userCardCreateTime"),USER_CARD.UPDATE_TIME.as("userCardUpdateTime"),
				 MEMBER_CARD.asterisk())
				.from(USER_CARD)
				.leftJoin(MEMBER_CARD)
				.on(USER_CARD.CARD_ID.eq(MEMBER_CARD.ID))
				.where(USER_CARD.USER_ID.eq(param.getUserId()))
				.and(USER_CARD.FLAG.eq(UCARD_FG_USING))
				.orderBy(MEMBER_CARD.GRADE.desc(),USER_CARD.IS_DEFAULT.desc(),USER_CARD.CREATE_TIME.desc());
		 	return getPageResult(select, param.getCurrentPage(), param.getPageRows(), WxAppUserCardVo.class);		
	}
	
	/**
	 * get card type
	 * @param cardNo
	 * @return
	 */
	public Byte getCardType(String cardNo) {
		return db().select(MEMBER_CARD.CARD_TYPE)
			.from(USER_CARD.leftJoin(MEMBER_CARD).on(USER_CARD.CARD_ID.eq(MEMBER_CARD.ID)))
			.where(USER_CARD.CARD_NO.eq(cardNo))
			.fetchAnyInto(Byte.class);
	}
	
	public int calcNumCardById(Integer cardId) {
		return db().fetchCount(USER_CARD, USER_CARD.CARD_ID.eq(cardId));
	}

	public int updateUserGradeCardId(Integer userId,Integer cardId) {
		return  db().update(USER_CARD.leftJoin(MEMBER_CARD).on(USER_CARD.CARD_ID.eq(MEMBER_CARD.ID)))
			.set(USER_CARD.CARD_ID,cardId)
			.set(USER_CARD.UPDATE_TIME,DateUtil.getLocalDateTime())
			.set(USER_CARD.FLAG,UCARD_FG_USING)
			.where(MEMBER_CARD.CARD_TYPE.eq(MCARD_TP_GRADE))
			.and(USER_CARD.FLAG.eq(UCARD_FG_USING))
			.and(USER_CARD.USER_ID.eq(userId))
			.execute();
	}

	public int getNumHasSendUser(Integer userId, Integer cardId) {
		return db().fetchCount(USER_CARD, USER_CARD.USER_ID.eq(userId).and(USER_CARD.CARD_ID.eq(cardId)));
	}
}
