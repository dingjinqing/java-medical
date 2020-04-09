package com.vpu.mp.service.pojo.shop.market.friendpromote;

import lombok.Data;
/**
  *  好友助力发起明细
 * @author liangchen
 * @date 2019年8月12日
 */
@Data
public class FriendPromoteLaunchVo {
	/** 发起用户昵称 */
	private String username;
	
	/** 发起用户手机号 */
	private String mobile;
	
	/** 助力活动Id（用户发起） */
	private Integer id;
	
	/** 参与人数 */
	private Integer joinNum;
	
	/** 助力次数 */
	private Integer promoteTimes;
	
	/** 助力值 */
	private Integer promoteValue;
	
	/** 是否助力成功 0:未成功 1、2:成功 */
	private Integer promoteStatus;
}
