package com.vpu.mp.service.pojo.shop.market.friendpromote;
import java.sql.Timestamp;

import lombok.Data;
/**
 * 好友助力领取明细
 * @author liangchen
 * @date 2019年8月8日
 */
@Data
public class FriendPromoteReceiveVo {
	
	/** 领取用户昵称 */
	private String username;
	
	/** 领取用户手机号 */
	private String mobile;
	
	/** 助力活动Id（用户发起） */
	private Integer id;

	/** 是否已领取 */
	private Integer promoteStatus;
	
	/** 领取时间 */
	private Timestamp recTime;
	
	/** 订单号 */
	private String orderSn;
}
