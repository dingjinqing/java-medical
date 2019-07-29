package com.vpu.mp.service.pojo.shop.member.card;

import java.math.BigDecimal;
import java.sql.Timestamp;

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
	private String bgImg;

	private BigDecimal disCount;
	private Byte discountIsAll;

	private Integer[] goodsId;
	private Integer[] shopCategoryIds;
	private Integer[] platformCategoryIds;

	private Byte powerScore;
	private Byte powerCount;
	private String powerPayOwnGood;

	private Integer score;
	private BigDecimal[] goodsMoney;
	private BigDecimal[] getScores;
	private Byte offset;
	private BigDecimal perGoodsMoney;
	private BigDecimal perGetScores;
	
	private Byte powerCard;
	private Integer sendMoney;
	private Byte offsetMoney;
	private BigDecimal[] money;
	private BigDecimal[] getMoney;
	private BigDecimal perMoney;
	private BigDecimal perGetMoney;
	
	private Byte expiredType;
	private Timestamp startTime;
	private Timestamp endTime;
	private Integer receiveDay;
	private Byte dateType;
	
	private String storeListType;
	private String[] storeList;
	
	private String desc;
	private String mobile;
	
	private Byte isPay;
	private Byte payType;
	private BigDecimal payMoney;
	private BigDecimal payScore;
	
	private Byte activation;
	private String[] activationCfgBox;
	private Byte examine;
}
