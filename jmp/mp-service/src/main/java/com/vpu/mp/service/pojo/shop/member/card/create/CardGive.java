package com.vpu.mp.service.pojo.shop.member.card.create;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 	会员卡转赠数据
 * @author 黄壮壮
 *
 */
@Data
public class CardGive {
	/**
	 * 	会员卡转赠开关
	 */
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	public static enum CardGiveSwitch{
		OFF,ON
	}
	
	/**
	 * 	会员卡转赠是否开启
	 */
	private CardGiveSwitch cardGiveAway;
	
	/**
	 * 	会员卡用户转赠给好友，好友是否还可以继续转赠给其他人
	 */
	private CardGiveSwitch cardGiveContinue;
	
	/**
	 * 	最大转赠次数
	 */
	private Integer mostGiveAway;
}
