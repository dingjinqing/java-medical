package com.vpu.mp.service.pojo.shop.decoration.module;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author lixinguo
 *
 */
@Getter
@Setter
public class ModuleImageGuide extends ModuleBase {
	/**
	 * 导航样式：1圆形图片导航，2方形图片导航
	 */
	@JsonProperty(value = "nav_style")
	Byte navStyle = 0;
	
	/**
	 * 前景色值
	 */
	@JsonProperty(value = "font_color")
	String fontColor;
	
	/**
	 * 背景色值
	 */
	@JsonProperty(value = "bg_color")
	String bgColor;
	
	/**
	 * 导航链接组
	 */
	@JsonProperty(value = "nav_group")
	List<NavItem> navGroup = new ArrayList<>();
	
	@Data
	public static class NavItem{
		
		/**
		 * 导航名称
		 */
		@JsonProperty(value = "nav_name")
		String navName;
		
		/**
		 * 导航链接
		 */
		@JsonProperty(value = "nav_link")
		String navLink;
		
		/**
		 * 图片地址
		 */
		@JsonProperty(value = "nav_src")
        String navSrc;
	}
}
