package com.vpu.mp.service.pojo.shop.order;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.vpu.mp.service.pojo.shop.order.goods.OrderGoodsVo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单列表展示订单信息
 * @author wangshuai
 */

@Data
@NoArgsConstructor
public class OrderListInfoVo {

	private Integer orderId;
	private String orderSn;
	private String mainOrderSn;
	private List<? extends OrderListInfoVo> childOrders;
	private List<? extends OrderGoodsVo> goods;
	private Byte orderStatus;
	//private String orderStatusName;
	/** 收件人姓名 */
	private String consignee;
	/** 收件人手机 */
	private String mobile;
	/** 支付方式 */
	private String payCode;
	/** 支付名称 */
	//private String payName;
	/** 配送方式:0 快递 1 自提 */
	private Byte deliverType;
	/** 配送类型名称 */
	//private String deliverTypeName;
	/** 下单时间 */
	private Timestamp createTime;
	/** 快递费金额 */
	private BigDecimal shippingFee;
	/** 支付金额 */
	private BigDecimal moneyPaid;
	/**是否部分发货:0否，1是*/
	private Byte partShipFlag;
}
