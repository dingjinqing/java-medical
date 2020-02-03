package com.vpu.mp.service.pojo.wxapp.goods.groupDraw;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 小程序拼团抽奖参团详情出参
 * 
 * @author zhaojianqiang
 * @time 下午2:18:35
 */
@Data
public class GroupDrawInfoVo {
	private Integer productId;
	private Integer id;
	private String name;
	private Timestamp startTime;
	private Timestamp endTime;
	private String goodsId;
	private Short minJoinNum;
	private BigDecimal payMoney;
	private Short joinLimit;
	private Short openLimit;
	private Short limitAmount;
	private Short toNumShow;
	private Byte status;
	private Byte isDraw;
	private Timestamp createTime;
	private Timestamp updateTime;
	private Byte delFlag;
	private Timestamp delTime;
	private String rewardCouponId;
	private Integer rewardNum;
	private Long surplusSecond;
}
