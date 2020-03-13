package com.vpu.mp.service.pojo.shop.market.friendpromote;

import lombok.Data;
/**
  *  好友助力参与明细
 * @author liangchen
 * @date 2019年8月12日
 */
@Data
public class FriendPromoteParticipateVo {
	/** 参与用户昵称 */
	private String username;
	
	/** 参与用户手机号 */
	private String mobile;
	
	/** 是否是新用户 promote:是  else：否 */
	private String inviteSource;
	
	/** 助力活动Id（用户发起） */
	private Integer launchId;
	
	/** 活动发起人 */
	private String launchUsername;
	
	/** 助力次数 */
	private Integer promoteTimes;
	
	/** 助力值 */
	private Integer promoteValue;
}
