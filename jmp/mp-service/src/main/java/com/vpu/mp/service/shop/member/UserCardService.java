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
import com.vpu.mp.service.pojo.shop.member.UserScoreVo;
import com.vpu.mp.service.pojo.shop.member.account.MemberCard;
import com.vpu.mp.service.shop.member.dao.UserCardDaoService;

import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.SEND_SCORE_BY_CREATE_CARD;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.TRADE_FLOW_INCOME;

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
	@Autowired private UserCardDaoService userCardDao;
	@Autowired private ScoreService scoreService;
	@Autowired private MemberCardService memberCardService;
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
	public void updateGrade(Integer[] userId,Integer cardId,Byte type) throws MpException {
		if(cardId != null) {
			if(userId == null) {
				return;
			}
			
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
			
		}
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
}
