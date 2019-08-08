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
	/**
	 * 会员卡是否启用 1:使用中，2:停止使用
	 */
	private Byte flag;
	
	/** 会员折扣: 全部商品；1代表全部商品，0代表指定商品 */
	private Byte discountIsAll;
	
	/**
	 * 积分获取开关， 0表示关闭，1表示开启
	 */
	/** 开卡送多少积分 */
	@JsonProperty("score")
	private Integer sorce;
	/** 购物送积分策略json序列化对象 */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String buyScore;
	/** 购物送积分策略json序列化对象 */
	private ScoreJson scoreJson;
	
	/** 等级卡升级策略 */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String gradeCondition;
	private GradeConditionJson gradeConditionJson;
	
	
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
		
		
		/**
		 * 等级会员卡升级策略
		 */
		/** 等级卡升级策略 */
		if(gradeCondition !=null && !gradeCondition.equals("")) {
			try {
				gradeConditionJson = MAPPER.readValue(gradeCondition, GradeConditionJson.class);
			} catch (Exception e) {
				log.info("等级卡升级策略json解析失败");
			} 
		}
		
	}
	
}
