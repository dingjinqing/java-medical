package com.vpu.mp.service.pojo.wxapp.card.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


@Data
public class CardCustomActionVo {
	/**
	 * 类型：0单选，1多选，2文本
	 */
	@JsonProperty("customType")
	private Byte type;
	
	/**
	 * 标题
	 */
	@JsonProperty("customTitle")
	private String title;
	
	/**
	 * 选项内容
	 */
	@JsonProperty("optionArr")
	private List<String> content;
	
	/**
	 * 条件校验必须 
	 */
	@JsonProperty("optionVer")
	private Byte conditionChecked;
	
	/**
	 * 是否使用改激活项
	 */
	@JsonProperty("isChecked")
	private Byte checked;
}
