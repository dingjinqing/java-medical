package com.vpu.mp.service.pojo.shop.config;

import com.vpu.mp.service.foundation.validator.CharacterValid;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Max;


/**
 *
 * @author 新国
 *
 */
@Getter
@Setter
public class BottomNavigatorConfig {
	/**
	 * 导航按钮文本
	 */
	@CharacterValid(max = 10)
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
