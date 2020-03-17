package com.vpu.mp.service.shop.card;

import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.db.shop.tables.records.UserCardRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.member.card.create.CardFreeship;
/**
 *	卡的包邮服务
 *	@author 黄壮壮
 *
 */
@Service
public class CardFreeShipService extends ShopBaseService{
	
	/**
	 * 	返回会员卡的包邮配置
	 * @param card MemberCardRecord
	 * @return CardFreeship 包邮信息
	 */
	public CardFreeship getFreeshipData(MemberCardRecord card) {
		CardFreeship cardFreeship = new CardFreeship();
		Byte type = card.getFreeshipLimit();
		cardFreeship.setType(type);
		if(type != null && type>=CardFreeship.shipType.SHIP_IN_EFFECTTIME.getType()) {
			cardFreeship.setNum(card.getFreeshipNum());
		}
		return cardFreeship;
	}
	
	/**
	 * 	返回用户卡的包邮快照信息
	 * @param card UserCardRecord
	 * @return CardFreeship 包邮信息
	 */
	public CardFreeship getFreeshipData(UserCardRecord card) {
		return CardFreeship.builder()
						   .type(card.getFreeLimit())
						   .num(card.getFreeNum())
						   .build();
	}

}
