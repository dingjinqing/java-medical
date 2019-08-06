package com.vpu.mp.service.pojo.shop.member.card;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
* @author 黄壮壮
* @Date: 2019年8月6日
* @Description: 限次会员卡出参
*/
@Data
public class LimitNumCardVo extends CardVo {
	
	/**
	 * 会员有效期类型 0：固定日期；1：自领取多少内有效；2：永久有效
	 */
	private Integer expireType;
	/** 开始时间 */
	private Timestamp startTime;
	/** 结束时间 */
	private Timestamp endTime;
	/** 自领取之日内多少时间 */
	private Integer receiveDay;
	/** 时间类型 */
	private Byte dateType;
	

	/** 可以领取的总次数 */
	private Integer stock;
	/** 已经领取的次数 */
	private Integer hasSend;
	
	
	
	/** 商品兑换次数 */
	@JsonProperty("goodsExCount")
	private Integer exchangCount;

	/** 门店兑换次数 */
	@JsonProperty("storeExCount")
	private Integer count;
	
	
	
	@Override
	public void changeJsonCfg() {
		
	}

}
