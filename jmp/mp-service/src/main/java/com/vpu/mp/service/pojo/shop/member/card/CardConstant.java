package com.vpu.mp.service.pojo.shop.member.card;

import java.time.LocalDate;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author 黄壮壮
 * @Date: 2019年8月6日
 * @Description: 会员卡用到的常量定义
 */
public class CardConstant {
	/** 普通会员卡 */
	public static final Byte NORMAL_TYPE = 0;
	/** 限次会员卡 */
	public static final Byte LIMIT_NUM_TYPE = 1;
	/** 等级会员卡 */
	public static final Byte RANK_TYPE = 2;
	/** 需要购买 */
	public static final Byte NEED_BUY = 1;
	/** 需要领取码 */
	public static final Byte PICK_UP_CODE = 2;
	/** 按钮打开 */
	public static final String BUTTON_ON = "on";
	/** 固定日期 */
	public static final Byte FIX_DATETIME = 0;
	/** 自领取多少内有效 */
	public static final Byte DURING_TIME = 1;
	/** 永久有效 */
	public static final Byte FOREVER = 2;

	/** 背景色类型 */
	public static final Byte BG_COLOR_TYPE = 0;
	/** 背景图片类型 */
	public static final Byte BG_IMG_TYPE = 1;
	/** 勾选 */
	public static final Byte CHECKED = 1;
	/** 全部商品 打折 */
	public static final Byte DISCOUNT_ALL_GOODS = 1;
	/** 指定商品 打折 */
	public static final Byte DISCOUNT_PART_GOODS = 0;

	/**
	 * 门店类型 全部门店，部分门店，不可在门店使用
	 */
	public static final String ALL_SHOP = "0";
	public static final String PART_SHOP = "1";
	public static final String PROHIBITED = "-1";

	/**
	 * 领取类型 直接领取，需要领取码
	 */
	public static final Byte GET_DIRECTLY = 0;
	public static final Byte NEED_CODE = 2;

	/** 现金购买 */
	public static final Byte BUY_BY_CRASH = 0;
	/** 积分购买 */
	public static final Byte BUY_BY_SCORE = 1;

	/**
	 * 是否激活 否，是
	 */
	public static final Byte ACTIVE_NO = 0;
	public static final Byte ACTIVE_YES = 1;

	/**
	 * 限次会员卡适用商品 0： 不可兑换商品 ；1 ：部分商品；2：全部商品
	 */
	public static final Byte NONE_GOODS = 0;
	public static final Byte PART_GOODS = 1;
	public static final Byte ALL_GOODS = 2;

	/** 没有删除 */
	public static final Byte DELETE_NO = 0;
	/** 确定删除 */
	public static final Byte DELETE_YES = 1;
	
	/** 停止使用 */
	public static final Byte STOP_USING = 2;
	
	/** 过期 */
	public static final Byte EXPIRED = 3;
	
	/** jackson */
	public static final ObjectMapper MAPPER = new ObjectMapper();
	/** 当前时间 */
	public static final LocalDate CURRENT_DATE = LocalDate.now();
}
