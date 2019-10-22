package com.vpu.mp.service.shop.member;

import static com.vpu.mp.db.shop.Tables.MEMBER_CARD;
import static com.vpu.mp.db.shop.Tables.USER_CARD;
import static com.vpu.mp.db.shop.Tables.CARD_UPGRADE;
import static com.vpu.mp.db.shop.Tables.CHARGE_MONEY;
import org.apache.commons.lang3.StringUtils;

import org.jooq.Record1;
import org.jooq.Record3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.CardUpgradeRecord;
import com.vpu.mp.db.shop.tables.records.ChargeMoneyRecord;
import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.db.shop.tables.records.UserCardRecord;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.FieldsUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.member.score.UserScoreVo;
import com.vpu.mp.service.pojo.shop.member.account.UserCardParam;
import com.vpu.mp.service.pojo.shop.member.card.GradeConditionJson;
import com.vpu.mp.service.shop.distribution.DistributorLevelService;
import com.vpu.mp.service.shop.member.dao.CardDaoService;
import com.vpu.mp.service.shop.member.dao.MemberDaoService;
import com.vpu.mp.service.shop.member.dao.UserCardDaoService;

import jodd.util.StringUtil;

import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.SEND_SCORE_BY_CREATE_CARD;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.TRADE_FLOW_INCOME;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.TRADE_STATUS_ARRIVE_ACCOUNT;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.LOWEST_GRADE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.ACTIVE_FALSE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.LIMIT_NUM_TYPE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.NORMAL_TYPE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.RANK_TYPE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.DELETE_YES;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.DURING_TIME;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.FIX_DATETIME;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.DAY_DATE_TYPE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.WEEK_DATE_TYPE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MONTH_DATE_TYPE;
import static com.vpu.mp.service.pojo.shop.member.card.CardMessage.STORE_PAYMEMBT;
import static com.vpu.mp.service.pojo.shop.member.card.CardMessage.SEND_CARD_REASON;
import static com.vpu.mp.service.pojo.shop.member.card.CardMessage.OPEN_CARD_SEND;
import static com.vpu.mp.service.pojo.shop.member.card.CardMessage.SYSTEM_UPGRADE;
import static com.vpu.mp.service.pojo.shop.member.card.CardMessage.ADMIN_SEND_CARD;
import static com.vpu.mp.service.pojo.shop.member.card.CardMessage.ADMIN_OPTION;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.ACTIVE_NO;


/**
* @author 黄壮壮
* @Date: 2019年10月10日
* @Description:
*/
@Service
public class UserCardService extends ShopBaseService{
	@Autowired
	public UserCardDaoService userCardDao;
	@Autowired
	public CardDaoService cardDao;
	@Autowired
	public ScoreService scoreService;
	@Autowired
	public MemberCardService memberCardService;
	@Autowired
	public MemberDaoService memberDaoService;
	@Autowired
	public DistributorLevelService distributorLevelService;
	public static final String DEFAULT_ADMIN = "0";

	public static final String OPTIONINFO = OPEN_CARD_SEND;
	public static final String DESC = "score_open_card";
	public static final String SYSTEM_UP_GRADE = SYSTEM_UPGRADE;
	/**
	 * 返回会员等级-按照持有会员等级卡划分，若无持有等级会员卡，则返回null
	 * @param user_id
	 * @return
	 */
	public String getUserGrade(Integer userId) {
		Record1<String> result = userCardDao.getUserGradeRecord(userId);
		if(result != null) {
			return result.get(MEMBER_CARD.GRADE);
		}else {
			return LOWEST_GRADE;
		}
	}
	
	/**
	 * 会员卡升级检测并升级
	 * @param userId
	 * @param cardId
	 * @param type  是否领取 1领取 0只是检测
	 * @throws MpException 
	 */
	public int updateGrade(Integer[] userId,Integer cardId,Byte type) throws MpException {
		if(userId == null || userId.length<=0) {
			return 1;
		}
		// 后台用户列表发卡（不需判断条件）
		if(cardId != null) {
		
			for(Integer id: userId) {
				String grade = getUserGrade(id);
				// 有过等级卡
				if(!StringUtils.isBlank(grade) && grade.equals(LOWEST_GRADE)) {
					Record3<Integer, String, String> old = userCardDao.fetchOldGradeCard(id);
					MemberCardRecord newData = userCardDao.getMemberCardById(id);
					CardUpgradeRecord cardUpgradeRecord = new CardUpgradeRecord();
					
					cardUpgradeRecord.setUserId(id);
					cardUpgradeRecord.setOldCardId(old.get(MEMBER_CARD.ID));
					cardUpgradeRecord.setNewCardId(cardId);
					cardUpgradeRecord.setOldGrade(old.get(MEMBER_CARD.GRADE));
					cardUpgradeRecord.setNewGrade(newData.getGrade());
					cardUpgradeRecord.setOldCardName(old.get(MEMBER_CARD.CARD_NAME));
					cardUpgradeRecord.setNewCardName(newData.getCardName());
					cardUpgradeRecord.setGradeCondition(newData.getGradeCondition());
					cardUpgradeRecord.setOperate("Admin option");
					
					userCardDao.insertIntoCardUpGrade(cardUpgradeRecord);
					/** 处理开卡赠送积分 */
					if(newData.getSorce() != null) {
						//ScoreParam data = new ScoreParam();
						UserScoreVo data = new UserScoreVo();
						// 增加积分时会自动地计算data.setScoreDis(0);
						data.setScore(newData.getSorce());
						data.setRemark(OPTIONINFO);
						data.setDesc(DESC);
						//scoreService.updateMemberScore(data,DEFAULT_ADMIN, id, SEND_SCORE_BY_CREATE_CARD.getValue(), TRADE_FLOW_INCOME.getValue());
						scoreService.addUserScore(data,DEFAULT_ADMIN,SEND_SCORE_BY_CREATE_CARD.getValue(), TRADE_FLOW_INCOME.getValue());
					}
				}else {
					List<UserCardParam> list = new ArrayList<>();
					UserCardParam tmpCard = new UserCardParam();
					tmpCard.setCardId(cardId);
					list.add(tmpCard);
					addUserCard(id,list);
				}
			}
			
		}else {
			for(Integer id: userId) {
				//获取用户累积获得积分和累积消费总额
				Integer userTotalScore = scoreService.getUserTotalScore(id);
				BigDecimal amount = distributorLevelService.getTotalSpend(id).getTotal();
				// 获取等级卡列表等循环判断符合的等级
				List<MemberCardRecord> gradeCard = cardDao.getAllUsingGradeCard();
				if(gradeCard.size()==0) {
					return -1;
				}
				///获取该用户的会员卡等级 
				String grade = userCardDao.getUserCardGrade(id);
				if(!StringUtils.isBlank(grade) && type == 1 ) {
					List<UserCardParam> list = new ArrayList<>();
					UserCardParam newCard = new UserCardParam();
					newCard.setCardId(gradeCard.get(0).getId());
					list.add(newCard);
					addUserCard(id,list);
					grade = userCardDao.getUserCardGrade(id);
				}
				
				for(MemberCardRecord card: gradeCard) {
		
					GradeConditionJson gradeCondition = Util.parseJson(card.getGradeCondition(), GradeConditionJson.class);
					if((!StringUtils.isBlank(grade) && !StringUtils.isBlank(card.getGrade()))
							|| (StringUtils.isBlank(grade))) {
						
						if(BigDecimalUtil.compareTo(gradeCondition.getGradeScore(), BigDecimal.ZERO)<1) {
							gradeCondition.setGradeScore(new BigDecimal(userTotalScore+1000));
						}
						
						if(BigDecimalUtil.compareTo(gradeCondition.getGradeMoney(),BigDecimal.ZERO)<1) {
							gradeCondition.setGradeMoney(amount.add(new BigDecimal(1000)));
						}
						
						if(gradeCondition.getGradeScore().intValue() <= userTotalScore || 
								BigDecimalUtil.compareTo(gradeCondition.getGradeMoney(),amount)<=0) {
							
							cardId = card.getId();
							
							if(!StringUtils.isBlank(grade) && (type == 1 || type == 2)) {
								// 升级
								UserCardParam oldCard = userCardDao.getUserRankCardDetailInfo(id);
								if(oldCard != null) {
									userCardDao.updateUserCardByCardIdAndNo(cardId, oldCard.getCardNo());
									if(card.getSorce()!=null) {
										sendScore(id, card);
									}
									InsertCardUpGradeRecord(id, card, oldCard,SYSTEM_UP_GRADE);
								}
								
							}
							
						}else {
							break;
						}
					}
				}
				if(cardId == null ) {
					// 没有可用的会员卡
					return -5;
				}
				
				if(grade == null && (type==1 || type == 2)) {
					//首次领取
					List<UserCardParam> list = new ArrayList<UserCardParam>();
					UserCardParam card = new UserCardParam();
					card.setCardId(cardId);
					list.add(card);
					addUserCard(id,list);
				}
			}
		}
		
		return 1;
	}

	/**
	 * 开卡赠送积分
	 * @param id
	 * @param card
	 */
	public void sendScore(Integer id, MemberCardRecord card) {
		UserScoreVo data = new UserScoreVo();
		data.setUserId(id);
		data.setScoreDis(memberDaoService.getUserInfo(id)!=null?memberDaoService.getUserInfo(id).getScore():null);
		data.setScore(card.getSorce());
		data.setDesc("score_open_card");
		data.setRemark(OPTIONINFO);
		data.setShopId(this.getShopId());
		data.setExpireTime(scoreService.getScoreExpireTime());
		scoreService.addUserScore(data,DEFAULT_ADMIN,SEND_SCORE_BY_CREATE_CARD.getValue(), TRADE_FLOW_INCOME.getValue());
	}

	private void InsertCardUpGradeRecord(Integer userId, MemberCardRecord card, UserCardParam oldCard,String option) {
		CardUpgradeRecord cardUpgrade = db().newRecord(CARD_UPGRADE);
		cardUpgrade.setUserId(userId);
		cardUpgrade.setOldCardId(oldCard.getCardId());
		cardUpgrade.setNewCardId(card.getId());
		cardUpgrade.setOldGrade(oldCard.getGrade());
		cardUpgrade.setNewGrade(card.getGrade());
		cardUpgrade.setOldCardName(oldCard.getCardName());
		cardUpgrade.setNewCardName(card.getCardName());
		cardUpgrade.setGradeCondition(card.getGradeCondition());
		cardUpgrade.setCreateTime(DateUtil.getLocalDateTime());
		cardUpgrade.setOperate(option);
		cardUpgrade.insert();
	}
	
	

	public void addUserCard(Integer userId, List<UserCardParam> cardInfo) {
		
		addUserCard(userId,cardInfo,ACTIVE_FALSE);
	}
	
	/**
	 * 添加会员卡
	 * @param id
	 * @param integers
	 * @param isActive 是否直接激活  {@link com.vpu.mp.service.pojo.shop.member.card.CardConstant.ACTIVE_FALSE}
	 */
	public void addUserCard(Integer userId, List<UserCardParam> cardList, boolean isActivate) {
		List<Integer> cardNos = cardList.stream().map(UserCardParam::getCardId).collect(Collectors.toList());
		List<Integer> timeCardsId = memberCardService.getCardByType(LIMIT_NUM_TYPE);
		
		userCardDao.updateCardFlag(timeCardsId,cardNos);
		
		for(UserCardParam card: cardList) {
			//查询是否已有这个会员卡,去掉过期和删除的
			UserCardRecord rst = userCardDao.getUsableUserCard(userId, card);
			MemberCardRecord cardInfo = cardDao.getCardInfoById(card.getCardId());
			if(NORMAL_TYPE.equals(cardInfo.getCardType())) {
				int hasSend = userCardDao.countCardByCardId(card.getCardId());
				if(StringUtils.isBlank(card.getCardNo()) && (cardInfo.getStock()>0 && hasSend<cardInfo.getStock())) {
					sendCard(userId,cardInfo,isActivate);
				}
			}else if(StringUtils.isBlank(card.getCardNo())){
				sendCard(userId,cardInfo,isActivate);
			}else {
				return;
			}
			
		}
		
	}
	
	/**
	 * 发卡
	 * @param userId
	 * @param cardInfo
	 * @param isActivate
	 */
	public int sendCard(Integer userId, MemberCardRecord cardInfo, boolean isActivate) {
		// 新增会员卡
		UserCardParam data = new UserCardParam();
		data.setUserId(userId);
		data.setCardId(cardInfo.getId());
		data.setUserCardCreateTime(DateUtil.getLocalDateTime());
		data.setCardNo(getRandomCardId(cardInfo.getId()));
		// 获取卡信息
		if(DELETE_YES.equals(cardInfo.getDelFlag())) {
			return -1;
		}
		if(DURING_TIME.equals(cardInfo.getExpireType())) {
			//'0:固定日期 1：自领取之日起N单位内有效',
            //date_type 0:日，1:周 2: 月
			LocalDateTime expireTime = null;
			LocalDateTime now = LocalDateTime.now();
			if(DAY_DATE_TYPE.equals(cardInfo.getDateType())) {
				expireTime = now.plusDays(cardInfo.getReceiveDay());
			}else if(WEEK_DATE_TYPE.equals(cardInfo.getDateType())) {
				expireTime = now.plusWeeks(cardInfo.getReceiveDay());
			}else if(MONTH_DATE_TYPE.equals(cardInfo.getDateType())) {
				expireTime = now.plusMonths(cardInfo.getReceiveDay());
			}
			data.setExpireTime(Timestamp.valueOf(expireTime));
		}else if(FIX_DATETIME.equals(cardInfo.getExpireType())) {
			//0:固定日期
			data.setExpireTime(cardInfo.getEndTime());
		}
		
		if(LIMIT_NUM_TYPE.equals(cardInfo.getCardType())) {
			data.setSurplus(cardInfo.getCount());
			
			ChargeMoneyRecord chargeMoney = db().newRecord(CHARGE_MONEY);
			chargeMoney.setUserId(userId);
			chargeMoney.setCardId(cardInfo.getId());
			chargeMoney.setCreateTime(DateUtil.getLocalDateTime());
			chargeMoney.setCount(cardInfo.getCount().shortValue());
			chargeMoney.setPayment(STORE_PAYMEMBT);
			chargeMoney.setReason(SEND_CARD_REASON);
			chargeMoney.setType(cardInfo.getCardType());
			chargeMoney.setCardNo(data.getCardNo());
			chargeMoney.insert();
			
			if(cardInfo.getIsExchang()!=null) {
				data.setExchangSurplus(cardInfo.getExchangCount());
				chargeMoney = db().newRecord(CHARGE_MONEY);
				chargeMoney.setUserId(userId);
				chargeMoney.setCardId(cardInfo.getId());
				chargeMoney.setCreateTime(DateUtil.getLocalDateTime());
				chargeMoney.setExchangCount(cardInfo.getExchangCount().shortValue());
				chargeMoney.setPayment(STORE_PAYMEMBT);
				chargeMoney.setReason(SEND_CARD_REASON);
				chargeMoney.setType(cardInfo.getCardType());
				chargeMoney.setCardNo(data.getCardNo());
				chargeMoney.insert();
			}
		}else if(NORMAL_TYPE.equals(cardInfo.getCardType()) && cardInfo.getSendMoney() != null) {
			data.setMoney(new BigDecimal(cardInfo.getSendMoney()));
			ChargeMoneyRecord chargeMoney = db().newRecord(CHARGE_MONEY);
			chargeMoney.setUserId(userId);
			chargeMoney.setCardId(cardInfo.getId());
			chargeMoney.setCreateTime(DateUtil.getLocalDateTime());
			chargeMoney.setCharge(new BigDecimal(cardInfo.getSendMoney()));
			chargeMoney.setPayment(STORE_PAYMEMBT);
			chargeMoney.setReason(ADMIN_SEND_CARD);
			chargeMoney.setType(cardInfo.getCardType());
			chargeMoney.setCardNo(data.getCardNo());
			chargeMoney.insert();
		}
		UserCardParam old = userCardDao.getUserRankCardDetailInfo(userId);
		if(RANK_TYPE.equals(cardInfo.getCardType()) && !StringUtils.isBlank(old.getGrade())) {
			// 升级
			userCardDao.updateUserRankCard(cardInfo,userId);
			// 记录
			InsertCardUpGradeRecord(userId,cardInfo,old,ADMIN_OPTION);
		}else {
			if(isActivate || (cardInfo.getActivation()!=null && ACTIVE_NO == cardInfo.getActivation())) {
				data.setActivationTime(DateUtil.getLocalTimeDate());
			}
			UserCardRecord userCardRecord = db().newRecord(USER_CARD);
			FieldsUtil.assignNotNull(data, userCardRecord);
			userCardRecord.insert();
			
		}
		
		if(cardInfo.getSorce()!=null) {
			sendScore(userId,cardInfo);
		}
		
		
		
		String content = null;
		if(LIMIT_NUM_TYPE.equals(cardInfo.getCardType())) {
			content = "限次卡"+cardInfo.getCount()+"次";
		}else {
			content = cardInfo.getDiscount().intValue()>0?"打"+cardInfo.getDiscount()+"折": "";
		}
		String expireTime = null;
		if(data.getExpireTime() == null) {
			expireTime = "永久有效";
		}else {
			expireTime = data.getExpireTime().toLocalDateTime()+"过期";
		}
		
		// TODO 发送模板消息
		return 1;
		
	}


	/**
	 * 重载方法
	 * @param userId
	 * @param cardInfo
	 */
	public  void sendCard(Integer userId, MemberCardRecord cardInfo) {
		sendCard(userId,cardInfo,false);
	}

	/**
	 * 计算用户卡使用的时间段
	 * @return
	 */
	public List<Integer> useInDate() {
		LocalDate now = DateUtil.getLocalDate();

		DayOfWeek dayOfWeek = now.getDayOfWeek();
		List<Integer> inData = new ArrayList<>(Arrays.asList(new Integer[] { 0, 1 }));

		if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
			inData.clear();
			inData.addAll(Arrays.asList(new Integer[] { 0, 2 }));
		}
		
		return inData;
	}
	
	//编辑会员卡详情
	public int updateUserCardByNo(String cardNo,UserCardRecord record) {
		return  userCardDao.updateUserCardByNo(cardNo, record);
	}
	
	/**
	 * Get Random card_no
	 * @param cardId
	 * @return
	 */
	public String getRandomCardId(int cardId){
		return memberCardService.generateCardNo(cardId);
	}
	
}
