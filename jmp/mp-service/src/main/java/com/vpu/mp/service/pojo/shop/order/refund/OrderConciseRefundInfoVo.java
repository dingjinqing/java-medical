package com.vpu.mp.service.pojo.shop.order.refund;

import org.jooq.types.UShort;

import lombok.Data;

/**
 * 订单详情页面展示退货款信息列表
 * @author 王帅
 *
 */
@Data
public class OrderConciseRefundInfoVo {
	private Integer orderId;
	private String orderSn;
	private String goodsId;
	/**return_order_goods表id*/
	private Integer id;
	/**return_order表id*/
	private Integer retId;
	private String goodsName;
	/**退货数量*/
	private UShort goodsNumber;
	/**属性（规格）*/
	private String goodsAttr;
}
