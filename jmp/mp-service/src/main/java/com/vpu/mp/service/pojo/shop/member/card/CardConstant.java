package com.vpu.mp.service.pojo.shop.member.card;

import java.math.BigDecimal;
import java.time.LocalDate;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author 黄壮壮
 * @Date: 2019年8月6日R
 * @Description: 会员卡用到的常量定义
 */
public class CardConstant {
	/** 所有会员卡类型 */
	public static final Byte MCARD_TP_ALL = -1;
	/** 普通会员卡 */
	public static final Byte MCARD_TP_NORMAL = 0;
	/** 限次会员卡 */
	public static final Byte MCARD_TP_LIMIT = 1;
	/** 等级会员卡 */
	public static final Byte MCARD_TP_GRADE = 2;
	
	/** is_pay 直接购买 */
	public static final Byte MCARD_ISP_DEFAULT = 0;
	/** is_pay 需要购买 */
	public static final Byte MCARD_ISP_BUY = 1;
	/** is_pay 需要领取码 */
	public static final Byte MCARD_ISP_CODE = 2;
	
	/** 过期类型  */
	/** expire_type固定日期 */
	public static final Byte MCARD_ET_FIX = 0;
	/** expire_type 自领取多少内有效 */
	public static final Byte MCARD_ET_DURING = 1;
	/** expire_type 永久有效 */
	public static final Byte MCARD_ET_FOREVER = 2;
	
	/** 背景色类型 */
	public static final Byte MCARD_BGT_COLOR = 0;
	/** 背景图片类型 */
	public static final Byte MCARD_BGT_IMG = 1;
	
	/** discount_is_all 全部商品 打折 */
	public static final Byte MCARD_DIS_ALL = 1;
	/** discount_is_all 部分商品 打折 */
	public static final Byte MCARD_DIS_PART = 0;
	
	/**
	 * 门店类型 全部门店，部分门店，不可在门店使用
	 */
	// 全部门店
	public static final String MCARD_STP_ALL= "0";
	// 部分门店
	public static final String MCARD_STP_PART = "1";
	//不可在门店使用
	public static final String MCARD_STP_BAN = "-1";
	
	/**
	 * receive_action 领取方式 1:领取码 2：卡号+密码
	 */
	// 领取方式 1:领取码
	public static final Byte MCARD_RA_CODE = 1;
	// 领取方式 2:卡号+密码
	public static final Byte MCARD_RA_PWD = 2;
	
	/**
	 * 激活： 0：不用激活，1：需要激活
	 */
	// 不用激活
	public static final Byte MCARD_ACT_NO = 0;
	// 需要激活
	public static final Byte MCARD_ACT_YES = 1;
	
	/** date_type 天数类型 0:日*/
	public static final Byte MCARD_DT_DAY = 0;
	/** date_type 天数类型 1:周  */
	public static final Byte MCARD_DT_WEEK = 1;
	/** date_type 天数类型2: 月 */
	public static final Byte MCARD_DT_MONTH = 2;
	
	/**
	 * is_exchang 限次会员卡适用商品 0： 不可兑换商品 ；1 ：部分商品；2：全部商品
	 */
	// 不可兑换商品
	public static final Byte MCARD_ISE_NON = 0;
	// 部分商品
	public static final Byte MCARD_ISE_PART = 1;
	// 全部商品
	public static final Byte MCARD_ISE_ALL = 2;
	
	/** del_flag 删除状态： 0：没有删除 */
	public static final Byte MCARD_DF_NO = 0;
	/** del_flag 删除状态： 1：确定删除 */
	public static final Byte MCARD_DF_YES = 1;
	
	/** 会员卡已经过期 */
	public static final Byte MCARD_EXPIRED = 3;
	
	/** member_card表flag 1正常使用，2停止使用 */
	public static final Byte MCARD_FLAG_USING = 1;
	public static final Byte MCARD_FLAG_STOP = 2;
	
	/** 专享商品:标签关联类型  */
	/** 1：关联商品  */
	public static final Byte COUPLE_TP_GOODS = 1;
	/**  2：关联商家分类  */
	public static final Byte COUPLE_TP_STORE = 2;
	/** 3：关联平台分类  */
	public static final Byte COUPLE_TP_PLAT = 3;
	/** 4， 关联品牌分类 */
	public static final Byte COUPLE_TP_BRAND = 4;
	
	
	/** 按钮打开 */
	public static final String BUTTON_ON = "on";
	

	
	/** 天数 */
	public static final Byte DAY = 1;
	public static final Byte WEEK = 7;
	public static final Byte MONTH = 30;

	/** 勾选 */
	public static final Byte CHECKED = 1;

	/** 现金购买 */
	public static final Byte BUY_BY_CRASH = 0;
	/** 积分购买 */
	public static final Byte BUY_BY_SCORE = 1;


	
	/** jackson */
	public static final ObjectMapper MAPPER = new ObjectMapper();
	/** 当前时间 */
	public static final LocalDate CURRENT_DATE = LocalDate.now();
	

	
	/** 可否在门店使用  0可以 1不可以 */
	public static final Byte AVAILABLE_IN_STORE = 0;
	public static final Byte UNAVAILABLE_IN_STORE = 1;
	
	/** 余额为 0 */
	public static final BigDecimal ZERO = new BigDecimal(0);
	
	/** 国际化语言前缀 */
	public static final String LANGUAGE_TYPE_MEMBER="member";
	
	/** 是否专属购买商品 0不是 1是*/
	public static final Byte PAY_OWN_GOOD_ON = 1 ;
	
	
	
	/** user_card表 flag 0 正常使用，1已经删除  2 已过期*/
	public static final Byte UCARD_FG_USING = 0;
	public static final Byte UCARD_FG_STOP = 1;
	public static final Byte UCARD_FG_EXPIRED = 2;
	/** user_card 激活 */
	public static final Boolean UCARD_ACT_NO = false;
	public static final Boolean UCARD_ACT_TRUE = true;
	

	
	/** -----------------member_card表常量--------------------------*/

	/** 是否专属购买商品 0不是 1是 */
	public static final Byte PAY_OWN_GOOD_YES=1; 
	public static final String LOWEST_GRADE = "v1";
	public static final Byte SUPPORT_PAY_BY_CASH = 1;
	public static final Byte NOT_SUPPORT_PAY_BY_CASH = 0;
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
