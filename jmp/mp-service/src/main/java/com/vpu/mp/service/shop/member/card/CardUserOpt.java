package com.vpu.mp.service.shop.member.card;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.foundation.util.CardUtil;
import com.vpu.mp.service.pojo.shop.member.card.GradeConditionJson;
import com.vpu.mp.service.shop.member.MemberCardService;
import com.vpu.mp.service.shop.member.ScoreService;
import com.vpu.mp.service.shop.member.UserCardService;

/**
 *	 普通用户领卡操作
 * @author 黄壮壮
 *
 */
@Service
public class CardUserOpt extends CardOpt {
	@Autowired
	private MemberCardService mCardSvc;
	@Autowired
	private UserCardService uCardSvc;
	@Autowired
	private ScoreService scoreSvc;
	
	private CardOpt decorate;
	
	
	public CardUserOpt() {
		super(null);
	}

	
	public void setDecorate(CardOpt _cardOpt) {
		this.setType(_cardOpt.getType());
		this.decorate = _cardOpt;
	}
	
	@Override
	protected String sendCard(Integer userId, Integer cardId, boolean isActivate) {
		return decorate.sendCard(userId, cardId, isActivate);
	}

	@Override
	public boolean canSendCard(Integer userId, Integer cardId) {
		MemberCardRecord card = mCardSvc.getCardById(cardId);
		
		//	等级卡
		if(CardUtil.isGradeCard(card.getCardType())) {
			return canSendGradeCard(userId,cardId);
		}
		
		//	添加额外附加条件
		
		//	需要购买
		if(CardUtil.isNeedToBuy(card.getIsPay())) {
			return false;
		}
		//	需要领取码
		if(CardUtil.isNeedReceiveCode(card.getIsPay())) {
			return false;
		}
		
		return decorate.canSendCard(userId, cardId);
	}

	/**
	 * 	是否可以发放等级卡
	 * @param userId
	 * @param cardId
	 * @return true是，false否
	 */
	private boolean canSendGradeCard(Integer userId, Integer cardId) {
		//	用户领取等级卡需要满足一定的条件
		
		Integer userTotalScore = scoreSvc.getAccumulationScore(userId);
		BigDecimal amount = uCardSvc.getUserTotalSpendAmount(userId);
		MemberCardRecord mCard = mCardSvc.getCardById(cardId);
		GradeConditionJson gradeCondition = uCardSvc.getGradeCondition(userTotalScore, amount, mCard);
		boolean result =  gradeCondition.getGradeScore().intValue() <= userTotalScore
				|| BigDecimalUtil.compareTo(gradeCondition.getGradeMoney(), amount) <= 0;
		logger().info("判断是否可以领取等级会员卡"+result);
		return result;
	}
	
	
	

	
}
