package com.vpu.mp.service.pojo.shop.member.card;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

/**
* @author 黄壮壮
* @Date: 2019年10月12日
* @Description: 用户有效会员卡
*/
@Data
public class ValidUserCardBean {
	
	// userCard表字段映射
	private Integer  userId;
	private Integer  cardId;
	private Byte  flag;
	private String  cardNo;
	private Timestamp  expireTime;
	private Byte  isDefault;
	private BigDecimal  money;
	private Integer  surplus;
	private Timestamp  activationTime;
	private Integer  exchangSurplus;
	private Timestamp  createTime;
	private Timestamp  updateTime;
	
	// memberCard表字段映射
	private String cardName;
    private Byte cardType;
	private BigDecimal discount;
    private Byte bgType;
    private String bgColor;
    private String bgImg;
	private String buyScore;
	private Byte expireType;
	private Timestamp startTime;
    private Timestamp endTime;
	private Integer receiveDay;
	private Byte dateType;
	private Byte storeUseSwitch;
	private String storeList;
	private String grade;
	
	// 头像
	private String avatar;
    private LocalDate startDate;
    private LocalDate endDate;
}
