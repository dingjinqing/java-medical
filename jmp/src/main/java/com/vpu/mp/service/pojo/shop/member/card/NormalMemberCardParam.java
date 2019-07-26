package com.vpu.mp.service.pojo.shop.member.card;

import lombok.Data;

/**
 * 
 * @author 黄壮壮
 * @Date: 2019年7月26日
 * @Description: 普通会员卡
 */
@Data
public class NormalMemberCardParam {
	
	private Byte cardType;
	private String cardName;
	private Byte bgType;
	private String bgColor;
	private Byte expiredType;
	private Float disCount;
	private Byte discountIsAll;
	private Byte isPay;
	private String payOwnGood;
	private String endTime;
	private Integer[] goodsId;
	private Integer[] shopCategoryIds;
	private Integer[] platformCategoryIds;
	private Byte storeListType;
	private Integer[] storeList;
	
	
}
