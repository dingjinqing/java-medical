package com.vpu.mp.service.pojo.shop.config;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 
 * @author 新国
 *
 */
@Data
public class SearchConfig {
	
	/**
	 *  0：不设置，1：全部商品，2：自定义
	 */
	@JsonProperty(value = "title_action")
	public Integer titleAction = 0;
	
	/**
	 * 自定义搜索值
	 */
	@JsonProperty(value = "title_custom")
	public String titleCustom;
	
	/**
	 * 是否开启搜索历史 0关闭，1开启
	 */
	@JsonProperty(value = "is_open_history")
	public Integer isOpenHistory = 0;
	
	/**
	 * 是否开启热词 0关闭，1开启
	 */
	@JsonProperty(value = "is_open_hot_words")
	public Integer isOpenHotWords = 0;
	
	/**
	 * 热词列表
	 */
	@JsonProperty(value = "hot_words")
	public List<String> hotWords = new ArrayList<String>(0);
}
