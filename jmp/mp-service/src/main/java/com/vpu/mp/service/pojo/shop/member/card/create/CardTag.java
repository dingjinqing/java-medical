package com.vpu.mp.service.pojo.shop.member.card.create;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 	会员卡同步用户打标签
 * @author 黄壮壮
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardTag {
	/**
	 * 	同步打标签是否开启
	 */
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	public static enum CardTagSwitch{
		OFF,ON
	}
	/**
	 * 	同步打标签是否开启
	 */
	private CardTagSwitch cardTag;
	
	/**
	 * 	标签ID
	 */
	private List<Integer> cardTagId;
}
