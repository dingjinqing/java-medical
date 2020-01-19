package com.vpu.mp.service.shop.member.card;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *	 卡操作抽象类
 * @author 黄壮壮
 *
 */
public abstract class CardOpt {
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

}
