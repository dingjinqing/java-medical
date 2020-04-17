package com.vpu.mp.service.shop.member.card;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.service.shop.member.MemberCardService;
import com.vpu.mp.service.shop.member.UserCardService;
import com.vpu.mp.service.shop.member.dao.UserCardDaoService;
import com.vpu.mp.service.shop.member.tag.UserTagService;

/**
 *	 卡操作抽象类
 * @author 黄壮壮
 *
 */
@Service
public abstract class CardOpt {
	@Autowired protected UserTagService userTagSvc;
	@Autowired protected MemberCardService cardService;
	@Autowired protected UserCardService userCardService;
	@Autowired protected UserCardDaoService userCardDao;
	/**	卡类型	*/
	private Byte type;
	
	public CardOpt(Byte _type) {
		this.type = _type;
	}
	
	public Byte getType() {
		return this.type;
	}
	public void setType(Byte _type) {
		this.type = _type;
	}
	
	public Logger logger() {
		return LoggerFactory.getLogger(getClass());
	}
	/**
	 * 	发卡
	 * @param userId 用户Id
	 * @param cardId 卡Id
	 * @param isActivate 是否直接激活 true: 是，false不是 
	 * @return 卡号 null为发卡失败
	 */
	public final String handleSendCard(Integer userId,Integer cardId,boolean isActivate) {
		
		if(canSendCard(userId,cardId)) {
			String cardNo = sendCard(userId,cardId,isActivate);
			return cardNo;
		}
		return null;
	}
	
	
	/**
	 * 	发卡
	 * @param userId 用户Id
	 * @param cardId 卡Id
	 * @param isActivate 是否直接激活 true: 是，false不是
	 * @return 卡号
	 */
	protected abstract String sendCard(Integer userId,Integer cardId,boolean isActivate);
	
	/**
	 * 	是否可以发放此卡给用户
	 * @param userId
	 * @param cardId
	 * @return true 可以 false 不可以
	 */
	public abstract boolean canSendCard(Integer userId,Integer cardId);
	
	/**
	 * 	同步用户打标签
	 * @param userId
	 * @param mCard
	 */
	protected void addAcitivityTag(Integer userId,MemberCardRecord mCard) {
		logger().info("同步用户打标签");
		
		List<Integer> tagIdList = cardService.cardDetailSvc.getCardTag(mCard).getCardTagId();
		if(tagIdList!=null && tagIdList.size()>0) {
			userTagSvc.addActivityTag(userId, tagIdList, userTagSvc.SRC_CARD, mCard.getId());
		}
	}

}
