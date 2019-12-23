package com.vpu.mp.service.pojo.shop.market.friendpromote;

import java.sql.Timestamp;

import lombok.Data;

/**
 * 好友助力活动公众号需要用
 * 
 * @author zhaojianqiang
 * @time 下午2:59:17
 */
@Data
public class FriendPromoteSelectVo {
	private Integer id;
	private Integer userId;
	private Integer promoteId;
	private Byte promoteStatus;
	private String orderSn;
	private Timestamp launchTime;
	private Timestamp successTime;
	private Byte delFlag;
	private Timestamp delTime;
	private Timestamp createTime;
	private Timestamp updateTime;
	private String actCode;
	private String actName;
	private String rewardContent;
	private Byte rewardType;
	
	private Byte failedSendType;
	private Integer failedSendContent;

}
