package com.vpu.mp.service.pojo.shop.market.groupbuy.vo;

import java.sql.Timestamp;

import lombok.Data;

/**
 * 
 * @author 王帅
 *
 */
@Data
public class GroupOrderVo {

	private Integer id;
	private Integer activityId;
	private Integer goodsId;
	private Integer groupId;
	private Integer userId;
	private Byte isGrouper;
	private String orderSn;
	private Byte status;
	private Timestamp startTime;
	private Timestamp endTime;
}
