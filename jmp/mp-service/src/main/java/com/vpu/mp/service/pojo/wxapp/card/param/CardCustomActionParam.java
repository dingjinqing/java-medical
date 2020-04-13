package com.vpu.mp.service.pojo.wxapp.card.param;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CardCustomActionParam {
	/**
	 * 	选项内容
	 */
	static class SingleOption{
		@JsonAlias({"option_title","optionTitle"})
		@JsonProperty("option_title")
		private String optionTitle;
		
		@JsonAlias({"is_checked","isChecked"})
		@JsonProperty("is_checked")
		private Byte isChecked;
	}
	/**
	 * 类型：0单选，1多选，2文本
	 */
	@JsonAlias({"custom_type","customType"})
	@JsonProperty("custom_type")
	private Byte customType;
	
	/**
	 * 标题
	 */
	@JsonAlias({"custom_title","customTitle"})
	@JsonProperty("custom_title")
	private String customTitle;
	
	/**
	 * 选项内容
	 */
	@JsonAlias({"option_arr","optionArr"})
	@JsonProperty("option_arr")
	private List<SingleOption> optionArr;
	
	/**
	 * 条件校验必须 
	 */
	@JsonAlias({"option_ver","optionVer"})
	@JsonProperty("option_ver")
	private Byte optionVer;
	
	/**
	 * 是否使用改激活项
	 */
	@JsonAlias({"is_checked","isChecked"})
	@JsonProperty("is_checked")
	private Byte isChecked;
	
	/**
	 * 文本内容
	 */
	private String text;
}
