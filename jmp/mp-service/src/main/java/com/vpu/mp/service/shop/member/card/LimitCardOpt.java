package com.vpu.mp.service.shop.member.card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.shop.member.MemberCardService;
import com.vpu.mp.service.shop.member.UserCardService;

/**
 * 	限次卡操作
 * @author 黄壮壮
 *
 */
@Service
public class LimitCardOpt extends CardOpt {
	@Autowired
	private UserCardService userCard;
	@Autowired
	private MemberCardService card;
	@Override
	public String sendCard(Integer userId,Integer cardId,boolean isActivate) {
		logger().info("发送限次会员卡");
		card.getCardById(cardId);
		
		
		
		
		
		
		
		return null;
	}

}
