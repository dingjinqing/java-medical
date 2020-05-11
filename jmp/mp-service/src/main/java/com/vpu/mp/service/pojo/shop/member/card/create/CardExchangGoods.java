package com.vpu.mp.service.pojo.shop.member.card.create;

import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 会员卡兑换商品对象
 * @author 黄壮壮
 *
 */
@Data
public class CardExchangGoods {
	/**
	 * 兑换的商品配置
	 */
	@Data
	public class GoodsCfg{
		/**
		 * 商品Id
		 */
		private List<Integer> goodsId;
		/**
		 * 每件商品可兑换的最大次数
		 */
		private Integer maxNum;
	}
	
	/**
	 * 兑换时间类型
	 */
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	public enum TimeType{
		/**
		 * 不限制
		 */
		NO_LIMIT((byte)0),
		/**
		 * 天
		 */
		DAY((byte)1),
		/**
		 * 周
		 */
		WEEK((byte)2),
		/**
		 * 月
		 */
		MONTH((byte)3),
		/**
		 * 季度
		 */
		SEASON((byte)4),
		/**
		 * 年
		 */
		YEAR((byte)5);
		private Byte val;
		TimeType(byte val){
			this.val = val;
		}
	}
	/**
	 * 兑换商品类型 0： 不可兑换商品 ；1 ：部分商品；2：全部商品
	 */
	private Byte isExchange;
	
	/** 
	 * 商品兑换总次数
	 * 
	 */
	private Integer exchangCount;
	
	/**
	 * 每件商品可兑换
	 */
	private Integer everyGoodsMaxNum;
	
	/**
	 * 兑换的商品列表
	 */
	private List<GoodsCfg> exchangGoods;
	
	/**
	 * 兑换时间类型
	 */
	private TimeType exchangeTimeType;
	
	/**
	 * 有效时间内的兑换次数
	 */
	private Integer exchangeTimeNum;
	
	/**
	 *  运费策略 0: 免运费 ; 1: 使用商品运费策略
	 */
	private Byte exchangFreight;
}
