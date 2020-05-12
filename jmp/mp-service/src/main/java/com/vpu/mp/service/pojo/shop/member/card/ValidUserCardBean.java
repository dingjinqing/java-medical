package com.vpu.mp.service.pojo.shop.member.card;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vpu.mp.service.pojo.shop.member.card.create.CardFreeship;

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
	/**
	 * 	用户卡的快照包邮信息
	 */
	// 包邮周期类型
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	protected Byte freeLimit;
	// 包邮次数
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	protected Integer freeNum;
	
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
	/**
	 * freeship_limit包邮周期类型 -1：不包邮，0:不限制，1：持卡有效期内，2：年，3：季，4：月，5：周，6：日
	 */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	protected Byte freeshipLimit;
	/**
	 * freeship_num 周期内包邮次数
	 */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	protected Integer freeshipNum;
	
	// 头像
	private String avatar;
    private LocalDate startDate;
    private LocalDate endDate;
    
    /**
     * 	包邮信息描述
     */
    private CardFreeship cardFreeShip;
}
