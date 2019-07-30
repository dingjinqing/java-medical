package com.vpu.mp.service.pojo.shop.order.store;

import java.sql.Timestamp;

import com.vpu.mp.service.foundation.util.Page;

import lombok.Data;

/**
 * 
 * @author wangshuai
 */
@Data
public class StoreOrderPageListQueryParam {
	private Page page;
	private String orderSn;
	private Timestamp payTimeStart;
	private Timestamp payTimeEnd;
	private String userName;
	private Integer storeId;
	/**订单状态:0未支付,1已支付,2已退款*/
	private Byte[] orderStatus;
}
