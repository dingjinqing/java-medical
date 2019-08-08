package com.vpu.mp.service.pojo.shop.member.card;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.BUY_BY_CRASH;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.BUY_BY_SCORE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.ALL_SHOP;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.PART_SHOP;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.PROHIBITED;

import java.math.BigDecimal;
/**
* @author 黄壮壮
* @Date: 2019年8月7日
* @Description: 处理策略的限次会员卡出参
*/
@Getter
@Setter
@Slf4j
public class LimitNumCardToVo extends LimitNumCardVo {
	/** 使用须知 */
	private String desc;
	/** 联系方式 */
	private String mobile;
	
	/** 适用商品 0： 不可兑换商品 ；1 ：部分商品；2：全部商品 */
	@JsonProperty("isExchange")
	private Byte isExchang;
	
	
	/**运费策略  0: 免运费 ; 1: 使用商品运费策略*/
	private Byte exchangFreight;
	
	/** 可兑换商品id */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String exchangGoods;
	@JsonProperty("exchangGoods")
	private String[] exchangGoodsArr;
	
	
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
	
	/** 激活需要的信息 */
	private String[] activationCfgBox;
	
	
	/** 使用时间 1工作日 2双休 0不限制 */
	private Byte useTime;
	
	/** 购买类型 */
	private Byte payType;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private BigDecimal payFee;
	/**购买类型0 为 现金购买金额 */
	private BigDecimal payMoney;
	/**购买类型1 积分购买 */
	private BigDecimal payScore;
	
	
	/** 领取限制 填0为不限制 */
	private Integer limits;
	
	/**
	 * 处理策略
	 */
	@Override
	public void changeJsonCfg() {
		
		log.info("执行LimitCardToVo的处理策略");
		super.changeJsonCfg();
		
		/** 处理可兑换商品id */
		if(exchangGoods != null) {
			exchangGoods = exchangGoods.replaceAll("\\s+","");
			exchangGoodsArr = exchangGoods.split(",");
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
