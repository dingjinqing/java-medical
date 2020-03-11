package com.vpu.mp.service.pojo.shop.member.card.create;

import java.util.ArrayList;
import java.util.List;

import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.util.Util;

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
	public static enum shipType{
		/**
		 * 	不包邮
		 */
		SHIP_NOT_AVAIL((byte)-1,JsonResultMessage.CARD_SHIP_NOT_AVAIL),
		
		/**
		 * 	不限制
		 */
		 SHIP_VIP((byte)0,JsonResultMessage.CARD_SHIP_VIP),
		 
		 /**
		 *	 持卡有效期内
		 */
		 SHIP_IN_EFFECTTIME((byte)1,JsonResultMessage.CARD_SHIP_IN_EFFECTTIME),
		 
		/**
		 * 	包邮类型为年
		 */
		 SHIP_YEAR((byte)2,JsonResultMessage.CARD_SHIP_YEAR),
		 
		/**
		 * 	包邮类型为季
		 */
		SHIP_SEASON((byte)3,JsonResultMessage.CARD_SHIP_SEASON),
		
		/**
		 *	 包邮类型为月
		 */
		 SHIP_MONTH((byte)4,JsonResultMessage.CARD_SHIP_MONTH),
		
		/**
		 * 	包邮类型为周
		 */
		 SHIP_WEEK((byte)5,JsonResultMessage.CARD_SHIP_WEEK),
		 
		 /**
		  *	 包邮类型为日
		  */
		 SHIP_DAY((byte)6,JsonResultMessage.CARD_SHIP_DAY);
		private byte type;
		private String desc;
		shipType(byte type,String desc) {
			this.type = type;
			this.desc = desc;
		}
		public String getDesc() {
			return desc;
		}
		
		public byte getType() {
			return this.type;
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
	
	private static final List<String> CONS = new ArrayList<String>();;

	/**
	 * 
	 * @param lang 语言类型，默认为中文
	 * @return 返回包邮可使用的类型，[无限包邮, 有效期内, 今年, 本季度, 本月, 本周, 今日]
	 */
	public static List<String> getFreeShipDesc(String lang) {
		if(CONS.size()==0) {
			for(shipType e: shipType.values()) {
				if(e.type>-1) {
					String res = Util.translateMessage(lang, e.getDesc(),"","messages");
					CONS.add(res);
				}
			}
		}
		return CONS;
	}
	
	public static void main(String[] args) {
		System.out.println(CONS);
		getFreeShipDesc(null);
		System.out.println(CONS);
	}
}
