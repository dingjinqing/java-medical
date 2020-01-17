package com.vpu.mp.service.shop.member.card;

import org.springframework.stereotype.Service;

import com.vpu.mp.service.pojo.shop.member.card.CardConstant;

/**
 * 	等级卡操作
 * @author 黄壮壮
 *
 */
@Service
public class GradeCardOpt extends CardOpt {

	public GradeCardOpt() {
		super(CardConstant.MCARD_TP_GRADE);
	}

	@Override
	protected String sendCard(Integer userId, Integer cardId, boolean isActivate) {
		
		return null;
	}

	@Override
	protected boolean canSendCard(Integer userId, Integer cardId) {
		
		return true;
	}

}
