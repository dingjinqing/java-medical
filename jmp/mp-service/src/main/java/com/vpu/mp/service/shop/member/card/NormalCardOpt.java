package com.vpu.mp.service.shop.member.card;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.db.shop.tables.records.UserCardRecord;
import com.vpu.mp.service.foundation.util.CardUtil;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.member.builder.UserCardRecordBuilder;
import com.vpu.mp.service.pojo.shop.member.card.CardConstant;
import com.vpu.mp.service.shop.member.MemberCardService;
import com.vpu.mp.service.shop.member.ScoreService;
import com.vpu.mp.service.shop.member.UserCardService;
import com.vpu.mp.service.shop.member.dao.UserCardDaoService;

/**
 * 	普通卡操作
 * @author 黄壮壮
 *
 */
@Service
public class NormalCardOpt extends CardOpt {
	
	@Autowired
	private UserCardService userCardService;
	@Autowired 
	private UserCardDaoService userCardDao;
	
	@Autowired
	private MemberCardService cardService;
	
	@Autowired
	private ScoreService scoreService;

	
	public NormalCardOpt() {
		super(CardConstant.MCARD_TP_NORMAL);
	}

	@Override
	protected String sendCard(Integer userId,Integer cardId,boolean isActivate) {
		logger().info("发放普通卡");
		//	获取卡
		MemberCardRecord card = cardService.getCardById(cardId);
		UserCardRecord uCard = UserCardRecordBuilder.create()
			.userId(userId)
			.cardId(cardId)
			.cardNo(cardService.generateCardNo(cardId))
			.freeLimit(card.getFreeshipLimit())
			.freeNum(card.getFreeshipNum())
			.createTime(DateUtil.getLocalDateTime())
			.expireTime(userCardService.calcCardExpireTime(card))
			.build();
			
		
		//	开卡送卡余额
		if(card.getSendMoney() != null) {
			uCard.setMoney(new BigDecimal(card.getSendMoney()));
		}		
		
		//	设置激活
		if(isActivate || !CardUtil.isNeedActive(card.getActivation())) {
			uCard.setActivationTime(DateUtil.getLocalDateTime());
		}
		
		Integer result = userCardService.insertRow(uCard);
		logger().info(String.format("成功向ID为%d的用户，发送了%d张普通会员卡：%s", userId, result,card.getCardName()));
		
		//	开卡送积分
		userCardService.addUserCardScore(userId, card);
		//	赠送卡余额记录
		userCardService.addChargeMoney(card, uCard);
		//	TODO 开卡送优惠券
		
		//	发送模板消息
		userCardService.sendScoreTemplateMsg(card);
		return uCard.getCardNo();
	}

	@Override
	public boolean canSendCard(Integer userId, Integer cardId) {
		//	用户目前是否拥有该类型会员卡
		UserCardRecord card = userCardDao.getUsableUserCard(userId, cardId);
		return card == null;
	}

}
