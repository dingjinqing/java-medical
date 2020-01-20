package com.vpu.mp.service.shop.member.card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.db.shop.tables.records.UserCardRecord;
import com.vpu.mp.service.foundation.util.CardUtil;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.member.builder.UserCardRecordBuilder;
import com.vpu.mp.service.pojo.shop.member.card.CardConstant;
import com.vpu.mp.service.shop.member.MemberCardService;
import com.vpu.mp.service.shop.member.UserCardService;
import com.vpu.mp.service.shop.member.dao.UserCardDaoService;

/**
 * 	限次卡操作
 * @author 黄壮壮
 *
 */
@Service
public class LimitCardOpt extends CardOpt {
	@Autowired
	private UserCardService userCardService;
	@Autowired
	private UserCardDaoService userCardDao;
	@Autowired
	private MemberCardService cardService;
	
	
	public LimitCardOpt() {
		super(CardConstant.MCARD_TP_LIMIT);
	}
	
	
	@Override
	protected String sendCard(Integer userId,Integer cardId,boolean isActivate) {
		logger().info("发送限次会员卡");
		MemberCardRecord card = cardService.getCardById(cardId);
		
		UserCardRecord newCard = UserCardRecordBuilder.create()
			.userId(userId)
			.cardId(cardId)
			.cardNo(cardService.generateCardNo(cardId))
			.createTime(DateUtil.getLocalDateTime())
			.expireTime(userCardService.calcCardExpireTime(card))
			.build();
		
		//	门店兑换次数
		if(CardUtil.canUseInStore(card.getStoreUseSwitch())) {
			newCard.setSurplus(card.getCount());
		}
		//	适用商品兑换次数
		if(CardUtil.canExchangGoods(card.getIsExchang())) {
			newCard.setExchangSurplus(card.getExchangCount());
		}
		
		if(isActivate || !CardUtil.isNeedActive(card.getActivation())) {
			newCard.setActivationTime(DateUtil.getLocalDateTime());
		}
		
		Integer result = userCardService.insertRow(newCard);
		logger().info(String.format("成功向ID为%d的用户，发送了%d张限次会员卡：%s", userId, result,card.getCardName()));
		
		//	门店,商品兑换记录
		userCardService.addChargeMoney(card,newCard);
		
		return newCard.getCardNo();
	}
	
	
	@Override
	public boolean canSendCard(Integer userId, Integer cardId) {
		logger().info("检测是否能够发放该先此限次卡");
		//	获取要发送卡
		MemberCardRecord card = cardService.getCardById(cardId);
		if(card == null && CardUtil.isCardDeleted(card.getDelFlag())) {
			logger().info("该卡不存在或已经删除");
			return false;
		}
		//	该用户已经领取该限次卡的张数
		int personCardNum = userCardDao.getNumHasSendUser(userId, card.getId());
		//	该卡已经发放的总次数
		int totalSendCard = userCardDao.getHasSend(card.getId());
		
		//	校验是否满足限次卡的领取条件
		boolean personGetFlag = true;
		boolean sendTotalCardFlag = true;
		
		if(card.getLimit()>0 && personCardNum>=card.getLimit()) {
			logger().info("个人领取次数达到上限");
			personGetFlag = false;
		}
		if(card.getStock()>0 && totalSendCard >= card.getStock()) {
			logger().info("卡发放达到上限");
			sendTotalCardFlag = false;
		}
		
		return personGetFlag && sendTotalCardFlag;
	}

}
