package com.vpu.mp.service.pojo.shop.order;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

/**
 * 订单列表展示订单信息
 * @author wangshuai
 */

@Data
public class StoreOrderListInfoVo {

	private Integer orderId;
	private String orderSn;
	private Byte orderStatus;
	private Integer storeId;
	private String userName;
	private Timestamp payTime;
	private BigDecimal moneyPaid;
	private String payCode;
	private String payName;
}
