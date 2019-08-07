package com.vpu.mp.service.pojo.shop.member.card;



import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 
 * @author 黄壮壮
 * @Date: 2019年8月1日
 * @Description: 会员卡出参
 */

@Data
@NoArgsConstructor
public abstract class BaseCardVo {
	/**
	 * 1-基本信息
	 */
	/** 会员卡id */
	private Integer id;
	/** 会员卡名称 */
	private String cardName;

	/** 背景类型 0： 背景色；1：背景图 */
	private Byte bgType;
	/** 背景色 */
	private String bgColor;
	/** 背景图 */
	private String bgImg;
	
	
	/**
	 * 领取设置
	 */
	/**
	 * 领取类型 0：直接领取；1：需要购买；2：需要领取码
	 */
	private Byte isPay;
	/**
	 * 是否需要激活 0： 否；1： 是
	 */
	private Byte activation;
	/** 是否审核 0： 否；1： 是 */
	private Byte examine;
	
	public abstract void changeJsonCfg();
	
}
