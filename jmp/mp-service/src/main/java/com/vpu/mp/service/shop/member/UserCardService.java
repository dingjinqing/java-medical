package com.vpu.mp.service.shop.member;

import static com.vpu.mp.db.shop.Tables.MEMBER_CARD;

import org.apache.commons.lang3.StringUtils;
import org.jooq.Record1;
import org.jooq.Record3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.CardUpgradeRecord;
import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.db.shop.tables.records.UserCardRecord;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.member.score.UserScoreVo;
import com.vpu.mp.service.pojo.shop.member.account.MemberCard;
import com.vpu.mp.service.pojo.shop.member.card.GradeConditionJson;
import com.vpu.mp.service.shop.distribution.DistributorLevelService;
import com.vpu.mp.service.shop.member.dao.CardDaoService;
import com.vpu.mp.service.shop.member.dao.UserCardDaoService;

import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.SEND_SCORE_BY_CREATE_CARD;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.TRADE_FLOW_INCOME;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.LOWEST_GRADE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.ACTIVE_FALSE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.LIMIT_NUM_TYPE;


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
	public DistributorLevelService distributorLevelService;
	public static final String DEFAULT_ADMIN = "1";
	// TODO 待国际化
	public static final String OPTIONINFO = "开卡赠送";
	public static final String DESC = "score_open_card";
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
					List<MemberCard> list = new ArrayList<>();
					list.add(new MemberCard(cardId));
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
					List<MemberCard> list = new ArrayList<>();
					list.add(new MemberCard(gradeCard.get(0).getId()));
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
							
							Integer cid = card.getId();
							
							if(!StringUtils.isBlank(grade) && (type == 1 || type == 2)) {
								
							}
							
						}
						
						
					}
				}
			}
		}
		
		return 1;
	}
	
	

	public void addUserCard(Integer userId, List<MemberCard> cardInfo) {
		
		addUserCard(userId,cardInfo,ACTIVE_FALSE);
	}
	
	/**
	 * 添加会员卡
	 * @param id
	 * @param integers
	 * @param isActive 是否直接激活  {@link com.vpu.mp.service.pojo.shop.member.card.CardConstant.ACTIVE_FALSE}
	 */
	public void addUserCard(Integer userId, List<MemberCard> cardInfo, boolean isActivate) {
		List<Integer> cardNos = cardInfo.stream().map(MemberCard::getId).collect(Collectors.toList());
		List<Integer> timeCardsId = memberCardService.getCardByType(LIMIT_NUM_TYPE);
		
		userCardDao.updateCardFlag(timeCardsId,cardNos);
		
		for(MemberCard card: cardInfo) {
			UserCardRecord rst = userCardDao.getUsableUserCard(userId, card);
			
			
		}
		
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

}
