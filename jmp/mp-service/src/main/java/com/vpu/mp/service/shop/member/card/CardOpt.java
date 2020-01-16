package com.vpu.mp.service.shop.member.card;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
/**
 *	 卡操作抽象类
 * @author 黄壮壮
 *
 */
@Slf4j
public abstract class CardOpt {
	
	public Logger logger() {
		return LoggerFactory.getLogger(getClass());
	}
	
	/**
	 * 	发卡
	 * @param userId 用户Id
	 * @param cardId 卡Id
	 * @param isActivate 是否直接激活 true: 是，false不是
	 * @return 卡号
	 */
	public abstract String sendCard(Integer userId,Integer cardId,boolean isActivate);

}
