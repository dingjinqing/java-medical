package com.vpu.mp.service.pojo.shop.member.card.base;
/**
 * 	用户卡状态常量
 * @author 黄壮壮
 *
 */
public class UserCardConstant {
	/*********************************************
	 *  flag
	 **********************************************/
	/**
	 * 	正常使用
	 */
	public static final Byte FLAG_NORMAL = 0;
	/**
	 * 	不可用状态
	 */
	public static final Byte FLAG_CANNOT_USE = 2;
	
	
	
	/*********************************************
	 *  give_away_status
	 **********************************************/
	/**
	 * 	正常,即没有进行转赠
	 */
	public static final Byte GIVE_AWAY_NORMAL = 0;
	/**
	 * 	转赠中
	 */
	public static final Byte GIVE_AWAY_ING = 1;
	/**
	 * 	转赠成功
	 */
	public static final Byte GIVE_WAY_SUCCESS = 2;
	
	
	 
}
