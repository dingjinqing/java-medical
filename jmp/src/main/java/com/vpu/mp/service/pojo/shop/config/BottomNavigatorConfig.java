package com.vpu.mp.service.pojo.shop.config;

import lombok.Data;


/**
 * 
 * @author 新国
 *
 */
@Data
public class BottomNavigatorConfig {
	/**
	 * 导航按钮文本
	 */
	public String text;
	
	/**
	 * 按钮类型
	 */
	public Integer btn = 0;
	
	/**
	 * 按钮上方图片
	 */
	public String normal;
	
	/**
	 * 按钮上方选时的图片
	 */
	public String hover;
	
	/**
	 * 点击按钮跳转小程序路径
	 */
	public String page;
}
