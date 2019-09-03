package com.vpu.mp.service.pojo.shop.member.tag;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
* @author 黄壮壮
* @Date: 2019年8月13日
* @Description: 标签通用弹窗
*/
@Getter
@Setter
public class CommonTagVo {
	/** 标签Id */
	private Integer tagId;
	/** 标签名 */
	@JsonProperty("value")
	private String tagName;
}
