package com.vpu.mp.service.shop.member.card;

import org.springframework.beans.factory.annotation.Autowired;

import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.service.foundation.util.CardUtil;
import com.vpu.mp.service.shop.member.MemberCardService;

/**
 *	 普通用户领卡操作
 * @author 黄壮壮
 *
 */
public class CardUserOpt extends CardOpt {
	@Autowired
	private MemberCardService mCardSvc;
	
	private CardOpt decorate;
	
	
	public CardUserOpt(Byte _type) {
		super(_type);
	}

	public CardUserOpt(CardOpt _cardOpt) {
		super(_cardOpt.getType());
		this.decorate = _cardOpt;
	}
	
	@Override
	protected String sendCard(Integer userId, Integer cardId, boolean isActivate) {
		return decorate.sendCard(userId, cardId, isActivate);
	}

	@Override
	public boolean canSendCard(Integer userId, Integer cardId) {
		MemberCardRecord card = mCardSvc.getCardById(cardId);
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

	
}
