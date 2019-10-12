package com.vpu.mp.service.pojo.shop.member.card;

import java.math.BigDecimal;
import java.time.LocalDate;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author 黄壮壮
 * @Date: 2019年8月6日
 * @Description: 会员卡用到的常量定义
 */
public class CardConstant {
	
	/** -----------------member_card表常量--------------------------*/
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
	/** expire_type 过期类型 */
	/** 固定日期 */
	public static final Byte FIX_DATETIME = 0;
	/** 自领取多少内有效 */
	public static final Byte DURING_TIME = 1;
	/** 永久有效 */
	public static final Byte FOREVER = 2;
	/** DURING_TIME的天数类型 0:日，1:周 2: 月 */
	public static final Byte DAY_DATE_TYPE = 0;
	public static final Byte WEEK_DATE_TYPE = 1;
	public static final Byte MONTH_DATE_TYPE = 2;
	
	/** 天数 */
	public static final Byte DAY = 1;
	public static final Byte WEEK = 7;
	public static final Byte MONTH = 30;

	
	
	
	
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
	public static final boolean ACTIVE_FALSE = false;
	public static final boolean ACTIVE_TRUE = true;
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
	
	/** 专享商品:标签关联类型  */
	/** 1：关联商品  */
	public static final Byte RELATED_GOODS_TYPE = 1;
	/**  2：关联商家分类  */
	public static final Byte RELATED_STORE_CATEGORY_TYPE = 2;
	/** 3：关联平台分类  */
	public static final Byte RELATED_PLATFORM_CATEGORY_TYPE = 3;
	/** 可否在门店使用  0可以 1不可以 */
	public static final Byte AVAILABLE_IN_STORE = 0;
	public static final Byte UNAVAILABLE_IN_STORE = 1;
	
	/** 余额为 0 */
	public static final BigDecimal ZERO = new BigDecimal(0);
	
	/** 国际化语言前缀 */
	public static final String LANGUAGE_TYPE_MEMBER="member";
	
	/** 是否专属购买商品 0不是 1是*/
	public static final Byte PAY_OWN_GOOD_ON = 1 ;
	
	/** -----------------user_card表常量--------------------------*/
	/** user_card表 flag 0 正常使用，1已经删除  2 已过期*/
	public static final Byte CARD_USING = 0;
	public static final Byte CARD_DELETE = 1;
	public static final Byte CARD_EXPIRED = 2;
	/** --------------------------------------------------------*/
	
	/** -----------------member_card表常量--------------------------*/
	/** member_card表flag 1正常使用，2停止使用 */
	public static final Byte MEMBER_CARD_USING = 1;
	public static final Byte MEMBER_CARD_DELETE = 2;
	/** 是否专属购买商品 0不是 1是 */
	public static final Byte PAY_OWN_GOOD_YES=1; 
	public static final String LOWEST_GRADE = "0";
	/** ----------------------------------------------------------*/
	
	
	/** -----------------goods_card_couple表常量--------------------------*/
	/**标签关联类型type： 1：关联商品 2：关联商家分类 3：关联平台分类 */
	public static final Byte GOODS_TYPE = 1;
	public static final Byte STORE_CATEGORY_TYPE=2;
	public static final Byte PLATFORM_CATEGORY_TYPE=3; 
	
	/** ----------------------------------------------------------------*/
	
	/** -----------------card_receive_code表常量--------------------------*/
	public static final Integer ALL_BATCH = 0;
	/** ----------------------------------------------------------------*/
	
	/** -------------------card_examine表常量---------------------------------------*/
	public static final Byte UNDER_REVIEW = 1;
	public static final Byte VERIFIED = 2;
	public static final Byte REFUSED = 3;
	
	/** --------------------------------------------------------------------------*/
	
	/** -------------------card_consumer表常量---------------------------------------*/
	public static final Short SHORT_ZERO = 0;
	public static final Byte EXCHANG_COUNT_TYPE = 1;
	public static final Byte COUNT_TYPE = 2;
	/** --------------------------------------------------------------------------*/
	
	public static final String NUM_LETTERS = "123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
}
