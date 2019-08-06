package com.vpu.mp.service.pojo.shop.member.card;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.ALL_SHOP;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.PART_SHOP;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.PROHIBITED;

/**
 * 
 * @author 黄壮壮
 * @Date: 2019年8月1日
 * @Description: 会员卡出参
 */

@Data
@Slf4j
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardVo {

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
	 * 会员有效期类型 0：固定日期；1：自领取多少内有效；2：永久有效
	 */
	private Integer expireType;
	/** 开始时间 */
	private Timestamp startTime;
	/** 结束时间 */
	private Timestamp endTime;
	/** 自领取之日内多少时间 */
	private Integer receiveDay;
	/** 时间类型 */
	private Byte dateType;

	/** 使用须知 */
	private String desc;
	/** 联系电话 */
	private String mobile;

	// TODO 根据会员折扣来定义会员折扣的开关
	/**
	 * 2-会员权益
	 */
	/** 会员折扣开关， 0表示关闭，1表示开启 */
	private Byte powerCount;
	/** 会员折扣 值为 0-10之间 */
	@JsonProperty("disCount")
	private BigDecimal discount;
	/** 会员折扣: 全部商品；1代表全部商品，0代表指定商品 */
	private Byte discountIsAll;

	/** 会员专享商品 on表示打开 */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Byte payOwnGood;
	private String powerPayOwnGood;

	// 更具积分策略来确定开关
	/**
	 * 积分获取开关， 0表示关闭，1表示开启
	 */
	private Byte powerScore;
	/** 开卡送多少积分 */
	@JsonProperty("score")
	private Integer sorce;
	/** 购物送积分策略json序列化对象 */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String buyScore;
	private ScoreJson scoreJson;

	// TODO 根据卡的策略来确定开关
	/** 卡充值开关 0关闭；1开启 */
	private Byte powerCard;
	/** 开卡送多少元 */
	private Integer sendMoney;
	/** 卡充值送积分策略json数据 */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String chargeMoney;
	private PowerCardJson powerCardJson;

	/**
	 * 3-使用门店
	 */
	/**
	 * 使用门店类型 0：全部门店；1：部分门店；-1：不可在门店使用
	 */
	private String storeListType;
	/** 门店Id */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String storeList;
	@JsonProperty("storeList")
	private String[] storeListArray;

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

	
	/**
	 * 限次会员卡
	 */
	
	
	/** 已经领取，未领取 */
	private Integer stock;
	private Integer hasSend;

	/** 商品兑换次数 */
	@JsonProperty("goodsExCount")
	private Integer exchangCount;

	/** 门店兑换次数 */
	@JsonProperty("storeExCount")
	private Integer count;
	
	
	
	/** 普通会员卡 处理开关以及json配置文件 */
	public void changeJsonCfgToArray() {
		ObjectMapper mapper = new ObjectMapper();
		/** 门店策略处理 */
		if (storeList != null) {
			storeList = storeList.replaceAll("\\s+", "");
			storeListArray = storeList.split(",");
			/** 门店类型 */
			if (PROHIBITED.equals(storeListArray[0]) || ALL_SHOP.equals(storeListArray[0])) {
				storeListType = storeListArray[0];
			} else {
				storeListType = PART_SHOP;
			}
		}

		/** 积分折扣 开关 */
		powerCount = (byte) (discount == null ? 0 : 1);

		/** 会员专享开关 */
		powerPayOwnGood = payOwnGood.equals(Byte.valueOf((byte) 1)) ? "on" : "";

		/** 购物送积分策略json对象 */
		powerScore = 0;
		if (buyScore != null && !buyScore.equals("")) {
			powerScore = 1;
			try {
				log.info("正在解析数据");
				scoreJson = mapper.readValue(buyScore, ScoreJson.class);
			} catch (Exception e) {
				log.info("购物积分策略json解析失败");
			}
		}

		/** 卡充值策略 */
		if (chargeMoney != null && !chargeMoney.equals("")) {
			powerCard = 1;
			try {
				powerCardJson = mapper.readValue(chargeMoney, PowerCardJson.class);
			} catch (Exception e) {
				log.info("卡充值策略json解析失败");
			}
		}

	}
}
