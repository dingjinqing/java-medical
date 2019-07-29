package com.vpu.mp.service.pojo.shop.member.card;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 
 * @author 黄壮壮
 * @Date: 2019年7月29日
 * @Description: 购物送积分策略json数据
 */
@Data
public class ScoreJson {
	private Byte offset;
	private BigDecimal[] goodsMoney;
	private BigDecimal[] getScores;
	private BigDecimal perGoodsMoney;
	private BigDecimal perGetScores;
}
