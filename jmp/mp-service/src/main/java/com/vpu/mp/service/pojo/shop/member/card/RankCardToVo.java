package com.vpu.mp.service.pojo.shop.member.card;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MAPPER;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
/**
* @author 黄壮壮
* @Date: 2019年8月7日
* @Description:
*/
@Getter
@Setter
@Slf4j
public class RankCardToVo extends RankCardVo {
	
	/** 使用须知 */
	private String desc;
	/** 联系方式 */
	private String mobile;
	
	/**
	 * 会员卡是否启用 1:使用中，2:停止使用
	 */
	//private Byte flag;
	
	/** 会员折扣: 全部商品；1代表全部商品，0代表指定商品 */
	private Byte discountIsAll;
	/** -折扣： 商品id */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String discountGoodsId;
	private String[] goodsId;
	/** -折扣： 商家分类id */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String discountSortId;
	private String[] shopCategoryIds;
	/** -折扣: 平台分类id */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String discountCatId;
	private String[] platformCategoryIds;
	/** -折扣 : 品牌分类id */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String discountBrandId;
	private String[] brandId;
	/**
	 * 积分获取开关， 0表示关闭，1表示开启
	 */
	/** 开卡送多少积分 */
	@JsonProperty("score")
	private Integer sorce;
	/** 购物送积分策略json序列化对象 */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	//private String buyScore;
	/** 购物送积分策略json序列化对象 */
	private ScoreJson scoreJson;
		
	
	/** 激活需要的信息 */
	private String[] activationCfgBox;
	
	/**
	 * 处理策略
	 */
	@Override
	public void changeJsonCfg() {
		
		log.info("执行RankCardToVo的处理策略");
		super.changeJsonCfg();
		
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
		
		
		/** 激活需要填写的信息 */
		String activationCfg = getActivationCfg();
		if(null != activationCfg) {
			activationCfgBox = activationCfg.replaceAll("\\s+","").split(",");
		}
		
		/** 积分指定商品 处理 */
		/** 商品id */
		if(discountGoodsId != null) {
			goodsId = discountGoodsId.replaceAll("\\s+","").split(",");
		}
		/** 商家分类id */
		if(discountSortId != null) {
			shopCategoryIds = discountSortId.replaceAll("\\s+","").split(",");
		}
		/**平台分类id */
		if(discountCatId != null) {
			platformCategoryIds = discountCatId.replaceAll("\\s+","").split(",");
		}
		/** 品牌分类id */
		if(discountBrandId != null) {
			brandId = discountBrandId.replaceAll("\\s+","").split(",");
		}
		
	}
	
}
