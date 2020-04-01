package com.vpu.mp.service.pojo.shop.member.order;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 用户订单数量与消费金额统计
 * @author 黄壮壮
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserOrderBean {
	/**
	 * 订单数量
	 */
	private Integer orderNum;
	
	/**
	 * 订单消费的总金额
	 */
	private BigDecimal totalMoneyPaid;
	/**
	 * 客单价
	 */
	private BigDecimal unitPrice;
}
