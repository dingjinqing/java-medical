package com.vpu.mp.service.pojo.shop.member.card;

import java.sql.Timestamp;

import lombok.Data;

/**
* @author 黄壮壮
* @Date: 2019年9月24日
* @Description: 持卡会员-出参
*/
@Data
public class CardHolderVo {
	/** - 会员ID */
	private Integer userId;
	/** - 昵称 */
	private String username;
	/** - 手机号 */
	private String mobile;
	/** - 邀请人 */
	private String invitedName;
	/** - 会员卡号 */
	private String cardNo;
	/** - 0:正常，1:删除 2 已过期{@link com.vpu.mp.service.pojo.shop.member.card.CardConstant.CARD_USING } */
	private Byte flag;
	/** -领卡时间 */
	private Timestamp createTime;
	/** -过期时间 */
	private Timestamp expireTime;
	/** 更新时间 */
	private Timestamp updateTime;
}
