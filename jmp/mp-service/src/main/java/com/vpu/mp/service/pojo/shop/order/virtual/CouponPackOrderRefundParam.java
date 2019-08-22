package com.vpu.mp.service.pojo.shop.order.virtual;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huangronggang
 * @date 2019年8月2日
 */
@Data
@NoArgsConstructor
public class CouponPackOrderRefundParam {
	
	/** 订单ID */
	private Integer orderId;
	/** 用户ID*/
	private Integer userId;
	
	/** 会员卡号 */
	private String cardNo;
	
	/** 订单号 */
	private String orderSn;

	private VirtualOrderRefundParam virtualOrderRefundParam;

	/** 退款后是否仍然发放优惠劵 */
	private Byte stillSendFlag=1;
}

