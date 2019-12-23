package com.vpu.mp.service.pojo.shop.market.friendpromote;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * reward_content字段的类
 * 
 * @author zhaojianqiang
 * @time 下午5:53:37
 */
@Data
public class FpRewardContent {

	@JsonProperty(value = "goods_ids")
	private Integer goodsIds;
	@JsonProperty(value = "reward_ids")
	private String rewardIds;
	@JsonProperty(value = "market_price")
	private String marketPrice;
	@JsonProperty(value = "market_store")
	private String marketStore;
}
