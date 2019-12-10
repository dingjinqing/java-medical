package com.vpu.mp.service.pojo.shop.member.account;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;
@Data
public class UserCardVo {
	private Integer userId;
	private Integer cardId;
	private Byte uflag;
	private String cardNo;
	private Timestamp expireTime;
	private Byte isDefault;
	private BigDecimal money;
	private Integer surplus;
	private Integer exchangSurplus;
	private Timestamp activationTime;
	private Timestamp uCreateTime;
	
	
	private Integer id;
	private String cardName;
	private Byte cardType;
	private Byte bgType;
	private String bgColor;
	private String bgImg;
	private BigDecimal discount;
	private Integer sorce;
	private String buyScore;
	private Byte expireType;
	private Timestamp startTime;
	private Timestamp endTime;
	private Integer receiveDay;
	private Byte dateType;
	private Byte activation;
	private String receiveCode;
	private String desc;
	private String mobile;
	private Timestamp createTime;
	private Timestamp updateTime;
	private Byte flag;
	private Integer sendMoney;
	private String chargeMoney;
	private Integer useTime;
	private String storeList;
	private Integer count;
	private Byte delFlag;
	private String grade;
	private String gradeCondition;
	private String activationCfg;
	private Byte examine;
	private String discountGoodsId;
	private String discountCatId;
	private String discountSortId;
	private Byte discountIsAll;
	private Byte isPay;
	private Byte payType;
	private BigDecimal payFee;
	private Byte payOwnGood;
	private Byte receiveAction;
	private Byte isExchang;
	private Byte storeUseSwitch;
	private String exchangGoods;
	private Byte exchangFreight;
	private Integer exchangCount;
	private Integer stock;
	private Integer limit;
	private String discountBrandId;
	private Byte sendCouponSwitch;
	private Byte sendCouponType;
	private String sendCouponIds;
	
	// 用户是否有此卡
	private Boolean isGet;

}
