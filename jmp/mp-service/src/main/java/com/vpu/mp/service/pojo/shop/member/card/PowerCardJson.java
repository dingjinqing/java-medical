package com.vpu.mp.service.pojo.shop.member.card;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 
 * @author 黄壮壮
 * @Date: 2019年7月29日
 * @Description: 卡充值送积分策略json数据
 */
@Data
public class PowerCardJson {

	private Byte offsetMoney;
	private BigDecimal[] money;
	private BigDecimal[] getMoney;
	private BigDecimal perMoney;
	private BigDecimal perGetMoney;
}
