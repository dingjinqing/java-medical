package com.vpu.mp.service.pojo.shop.order;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
	private String goodsType;
	private List<? extends OrderListInfoVo> childOrders;
	private List<? extends OrderGoodsVo> goods;
	private Byte orderStatus;
	/** 收件人姓名 */
	private String consignee;
	/** 收件人手机 */
	private String mobile;
	/** 支付方式 */
	private String payCode;
	/** 配送方式:0 快递 1 自提 */
	private Byte deliverType;
	/** 下单时间 */
	private Timestamp createTime;
	/** 快递费金额 */
	private BigDecimal shippingFee;
	/** 支付金额 */
	private BigDecimal moneyPaid;
	/**是否部分发货:0否，1是*/
	private Byte partShipFlag;
	/**是否支持mp端退款*/
	@JsonIgnore
	private Byte returnTypeCfg;
	/**积分抵扣金额*/
	@JsonIgnore
	private BigDecimal scoreDiscount;
	/**用户消费余额*/
	@JsonIgnore
	private BigDecimal useAccount;
	/**会员卡消费金额*/
	@JsonIgnore
	private BigDecimal memberCardBalance;
	/**TODO 数据库没字段子单金额*/
	@JsonIgnore
	private BigDecimal subGoodsPrice;
	/**退款/退货状态*/
	@JsonIgnore
	private Byte refundStatus;
	/**补款金额*/
	@JsonIgnore
	private BigDecimal bkOrderMoney;
	@JsonIgnore
	private Integer userId;
}
