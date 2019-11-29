package com.vpu.mp.service.pojo.shop.member.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vpu.mp.service.pojo.shop.member.card.GradeConditionJson;
import com.vpu.mp.service.pojo.shop.member.card.ScoreJson;

import lombok.Data;

/**
* @author 黄壮壮
* @Date: 2019年11月29日
* @Description: 下一张等级卡
*/
@Data
public class NextGradeCardVo {
	/**
	 * 会员权益
	 */
	/** 会员折扣开关， 0表示关闭，1表示开启 */
	protected Byte powerCount;
	/** 会员折扣 值为 0-10之间 */
	@JsonProperty("disCount")
	protected BigDecimal discount;
	
	/**
	 * 积分获取开关， 0表示关闭，1表示开启
	 */
	protected Byte powerScore;
	/** 开卡送多少积分 */
	@JsonProperty("score")
	private Integer sorce;
	/** 购物送积分策略json序列化对象 */
	private ScoreJson scoreJson;
	
	/** 会员卡等级 */
	protected String grade;
	protected GradeConditionJson gradeConditionJson;
}
