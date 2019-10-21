package com.vpu.mp.service.pojo.shop.member.card;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.BUY_BY_CRASH;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.BUY_BY_SCORE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.PART_SHOP;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.ALL_SHOP;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.PROHIBITED;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MAPPER;

/**
* @author 黄壮壮
* @Date: 2019年8月7日
* @Description:
*/
@Getter
@Setter
@Slf4j
public class NormalCardToVo extends NormalCardVo {
	
	/** 使用须知 */
	private String desc;
	/** 联系方式 */
	private String mobile;

	/** 会员折扣: 全部商品；1代表全部商品，0代表指定商品 */
	private Byte discountIsAll;
	/** 指定商品时的： 商品id */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String discountGoodsId;
	private String[] goodsId;
	/** 指定商品时的： 商家分类id */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String discountSortId;
	private String[] shopCategoryIds;
	/** 指定商品时的: 平台分类id */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String discountCatId;
	private String[] platformCategoryIds;
	/** TODO指定商品时的 : 品牌分类id */
	
	/**
	 * 积分具体详情
	 */
	/** 开卡送多少积分 */
	@JsonProperty("score")
	private Integer sorce;
	/** 购物送积分策略json序列化对象 */
	private ScoreJson scoreJson;
	
	
	/**
	 * 卡充值
	 */
	/** 开卡送多少元 */
	private Integer sendMoney;
	/** 卡充值送积分策略json数据 */
	private PowerCardJson powerCardJson;
	
	
	/**
	 * 使用门店
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
	
	
	/** 购买类型 */
	private Byte payType;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private BigDecimal payFee;
	/**购买类型0 为 现金购买金额 */
	private BigDecimal payMoney;
	/**购买类型1 积分购买 */
	private BigDecimal payScore;
	
	/** 激活需要的信息 */
	private String[] activationCfgBox;
	
	/**
	 * 处理策略
	 */
	@Override
	public void changeJsonCfg() {
		log.info("执行NormalCardToVo的处理策略");
		super.changeJsonCfg();
		
		/** 积分指定商品 处理 */
		/** 商品id */
		if(discountGoodsId != null) {
			discountGoodsId.replaceAll("\\s+","");
			goodsId = discountGoodsId.split(",");
		}
		/** 商家分类id */
		if(discountSortId != null) {
			discountSortId.replaceAll("\\s+","");
			shopCategoryIds = discountSortId.split(",");
		}
		/**平台分类id */
		if(discountCatId != null) {
			discountCatId.replaceAll("\\s+","");
			platformCategoryIds = discountCatId.split(",");
		}
		
		
		/** 购物送积分策略json对象 */
		String buyScore = this.getBuyScore();
		if (buyScore != null && !buyScore.equals("")) {
			try {
				log.info("正在解析数据");
				scoreJson = MAPPER.readValue(buyScore, ScoreJson.class);
			} catch (Exception e) {
				log.info("购物积分策略json解析失败");
			}
		}

		/** 卡充值策略 */
		String chargeMoney = this.getChargeMoney();
		if (chargeMoney != null && !chargeMoney.equals("")) {
	
			try {
				powerCardJson = MAPPER.readValue(chargeMoney, PowerCardJson.class);
			} catch (Exception e) {
				log.info("卡充值策略json解析失败");
			}
		}
		
		
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
		
		/** 激活需要填写的信息 */
		String activationCfg = getActivationCfg();
		if(null != activationCfg) {
			activationCfgBox = activationCfg.replaceAll("\\s+","").split(",");
		}
		
		/** 购买类型 */
		if(BUY_BY_CRASH.equals(payType)) {
			payMoney = payFee;
		}else if(BUY_BY_SCORE.equals(payType)) {
			payScore = payFee;
		}
		
		
	}
}
