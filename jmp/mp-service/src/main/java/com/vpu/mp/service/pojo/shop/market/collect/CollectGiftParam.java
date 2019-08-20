package com.vpu.mp.service.pojo.shop.market.collect;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 *	收藏有礼
 * @author liangchen
 * @date 2019年8月20日
 */
@Data
public class CollectGiftParam {
	
	/** 开关配置 0：关闭 1：开启*/
	@JsonProperty(value = "status")
	private Integer status;
	/** 开始时间*/
	@JsonProperty(value = "start_time")
	private Timestamp startTime;
	/** 结束时间*/
	@JsonProperty(value = "end_time")
	private Timestamp endTime;
	/** 图标*/
	@JsonProperty(value = "logo")
	private Integer logo;
	/** 自定义图标路径*/
	@JsonProperty(value = "logo_src")
	private String logoSrc;
	/** 积分*/
	@JsonProperty(value = "score")
	private Integer score;
	/** 优惠券id*/
	@JsonProperty(value = "coupon_ids")
	private String couponIds;
}
