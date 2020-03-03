package com.vpu.mp.service.pojo.shop.member.card.create;

import lombok.Data;

/**
 * 	卡的包邮策略信息
 * @author 黄壮壮
 *
 */
@Data
public class CardFreeship {
	/**
	 *	 包邮类型
	 */
	enum shipType{
		/**
		 * 	不包邮
		 */
		SHIP_NOT_AVAIL((byte)-1),
		
		/**
		 * 	不限制
		 */
		 SHIP_VIP((byte)0),
		 
		 /**
		 *	 持卡有效期内
		 */
		 SHIP_IN_EFFECTTIME((byte)1),
		 
		/**
		 * 	包邮类型为年
		 */
		 SHIP_YEAR((byte)2),
		 
		/**
		 * 	包邮类型为季
		 */
		SHIP_SEASON((byte)3),
		
		/**
		 *	 包邮类型为月
		 */
		 SHIP_MONTH((byte)4),
		
		/**
		 * 	包邮类型为周
		 */
		 SHIP_WEEK((byte)5),
		 
		 /**
		  *	 包邮类型为日
		  */
		 SHIP_DAY((byte)6);
		private byte type;
		
		shipType(byte type) {
			this.type = type;
		}
	}
	
	/**
	 * 	包邮的次数
	 */
	private Integer num;
	
	/**
	 * 	包邮的类型
	 */
	private Byte type;
}
