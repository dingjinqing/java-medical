package com.vpu.mp.service.pojo.shop.member.card.create;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author 黄壮壮
 * @Desc 会员卡自定义审核数据
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardCustomAction {
	/**
	 * 类型：0单选，1多选，2文本
	 */
	@JsonAlias({"type"})
	@JsonProperty("custom_type")
	private Byte type;
	
	/**
	 * 标题
	 */
	@JsonAlias({"title"})
	@JsonProperty("custom_title")
	private String title;
	
	/**
	 * 选项内容
	 */
	@JsonAlias({"content"})
	@JsonProperty("option_arr")
	private List<String> content;
	
	/**
	 * 条件校验必须 
	 */
	@JsonAlias({"conditionChecked"})
	@JsonProperty("option_ver")
	private Byte conditionChecked;
	
	/**
	 * 是否使用改激活项
	 */
	@JsonAlias({"checked"})
	@JsonProperty("is_checked")
	private Byte checked;
}
