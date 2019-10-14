package com.vpu.mp.service.pojo.shop.member.account;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
* @author 黄壮壮
* @Date: 2019年8月8日
* @Description:
*/
@Data
@Slf4j
@ToString
@NoArgsConstructor
public class MemberCard {
	private Integer id;
	private String cardName;

	/** 创建时间 */
	private Timestamp createTime;
	
	/** 会员折扣数额 */
	private BigDecimal discount;
	
	/** 
	 * 会员卡权益 0关闭，1开启 
	 * 积分奖励  | 专属商品 | 卡充值 | 会员折扣
	 * */
	private Byte powerScore; 
	private Byte payOwnGood;
	private Byte powerCard;  
	private Byte powerCount; 
	
	/** 积分策略 */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String buyScore;
	/** 卡充值策略 */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String chargeMoney;
	

	/**
	 * 设置开关及是否过期
	 */
	public void changeJsonCfg() {
		log.info("正在执行changeJsonCfg.");
		/** 会员折扣开关， 0表示关闭，1表示开启 */
		this.powerCount = (byte) (this.discount == null ? 0 : 1);
		/** 积分获取开关， 0表示关闭，1表示开启 */
		this.powerScore = (byte) (this.buyScore == null ? 0 : 1);
		/** 卡充值开关 0关闭；1开启 */
		this.powerCard = (byte) (this.chargeMoney == null ? 0 : 1);
	}


	public MemberCard(Integer id) {
		super();
		this.id = id;
	}
}
