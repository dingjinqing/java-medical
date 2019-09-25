package com.vpu.mp.service.pojo.shop.member.card;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
* @author 黄壮壮
* @Date: 2019年9月25日
* @Description: 会员卡批次 列表
*/
@Data
public class CardBatchVo {
	
	/** 批次id */
	@JsonProperty("batchId")
	private Integer id;
	/** 批次名称 */
	private String name;
}
