package com.vpu.mp.service.pojo.shop.member.card;

import java.math.BigDecimal;
import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.BUTTON_ON;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.FIX_DATETIME;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.CURRENT_DATE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.EXPIRED;

/**
 * 
 * @author 黄壮壮
 * @Date: 2019年8月6日
 * @Description: 普通会员卡查询出参
 */
@Data
@Slf4j
public class NormalCardVo extends BaseCardVo {
	/**
	 * 会员有效期类型 0：固定日期；1：自领取多少内有效；2：永久有效
	 */
	private Byte expireType;
	/** 开始时间 */
	private Timestamp startTime;
	/** 结束时间 */
	private Timestamp endTime;
	/** 自领取之日内多少时间 */
	private Integer receiveDay;
	/** 时间类型 */
	private Byte dateType;

	/**
	 * 2-会员权益
	 */
	/** 会员折扣开关， 0表示关闭，1表示开启 */
	private Byte powerCount;
	/** 会员折扣 值为 0-10之间 */
	@JsonProperty("disCount")
	private BigDecimal discount;

	/** 会员专享商品 on表示打开 */
	/** 是否专属购买商品 0不是 1是 */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Byte payOwnGood;
	private String powerPayOwnGood;

	/**
	 * 积分获取开关， 0表示关闭，1表示开启
	 */
	private Byte powerScore;
	/** 购物送积分策略json序列化对象 */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String buyScore;

	/** 卡充值开关 0关闭；1开启 */
	private Byte powerCard;
	/** 卡充值送积分策略json数据 */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String chargeMoney;

	/** 1:使用中，2:停止使用 3：过期 */
	private Byte flag;

	/**
	 * 设置开关及是否过期
	 */
	public void changeJsonCfg() {
		log.info("正在执行NormalCardVo的changeJsonCfg.");
		/** 会员折扣开关， 0表示关闭，1表示开启 */
		powerCount = (byte) (discount == null ? 0 : 1);

		/** 会员专享商品 on表示打开 */
		powerPayOwnGood = payOwnGood.equals((byte) 0) ? "" : BUTTON_ON;
		/** 积分获取开关， 0表示关闭，1表示开启 */
		powerScore = (byte) (buyScore == null ? 0 : 1);
		/** 卡充值开关 0关闭；1开启 */
		powerCard = (byte) (chargeMoney == null ? 0 : 1);
		
		/** 处理固定时间段，是否过期 */
		if (FIX_DATETIME.equals(expireType) && endTime != null) {
			log.info("进入到if判断");
			boolean isExpired = endTime.toLocalDateTime().toLocalDate().isBefore(CURRENT_DATE);
			flag = isExpired ? EXPIRED : flag;
		}

	}
}
