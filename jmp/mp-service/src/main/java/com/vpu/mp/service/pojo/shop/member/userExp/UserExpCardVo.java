package com.vpu.mp.service.pojo.shop.member.userExp;

import lombok.Data;

/**
 * 会员导出优化
 * @author 黄壮壮
 *
 */
@Data
public class UserExpCardVo {
	/**
	 * 用户ID
	 */
	private Integer userId;
	/**
	 * 卡ID
	 */
	private Integer cardId;
	/**
	 * 会员卡名称
	 */
	private String cardName;

}
