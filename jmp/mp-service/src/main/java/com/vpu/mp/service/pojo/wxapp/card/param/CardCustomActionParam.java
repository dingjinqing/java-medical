package com.vpu.mp.service.pojo.wxapp.card.param;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CardCustomActionParam {
	/**
	 * 	选项内容
	 */
	static class SingleOption{
		@JsonProperty("option_title")
		private String optionTitle;
		@JsonProperty("is_checked")
		private Byte isChecked;
	}
	/**
	 * 类型：0单选，1多选，2文本
	 */
	@JsonProperty("custom_type")
	private Byte customType;
	
	/**
	 * 标题
	 */
	@JsonProperty("custom_title")
	private String customTitle;
	
	/**
	 * 选项内容
	 */
	@JsonProperty("option_arr")
	private List<SingleOption> optionArr;
	
	/**
	 * 条件校验必须 
	 */
	@JsonProperty("option_ver")
	private Byte optionVer;
	
	/**
	 * 是否使用改激活项
	 */
	@JsonProperty("is_checked")
	private Byte isChecked;
	
	/**
	 * 文本内容
	 */
	private String text;
}
