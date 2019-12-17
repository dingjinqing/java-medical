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
	/**
	 * 逻辑订单号
	 * 普通订单LogicMainOrderSn=orderSn
	 * 拆单主订单LogicMainOrderSn=orderSn=mainOrderSn
	 * 拆单子订单LogicMainOrderSn=mainOrderSn
	 */
	@JsonIgnore
	private String logicMainOrderSn;
	private String mainOrderSn;
	private String goodsType;
	private List<? extends OrderListInfoVo> childOrders;
	private List<? extends OrderGoodsVo> goods;
	private Byte orderStatus;
	/** 收件人姓名 */
	private String consignee;
	/** 收件人手机 */
	private String mobile;
	/** 主支付方式 */
	private String payCode;
	/** 支付方式列表 */
	private List<Byte> payCodeList;
	/** 配送方式:0 快递 1 自提 */
	private Byte deliverType;
	/** 下单时间 */
	private Timestamp createTime;
	/** 快递费金额(退款时为退运费金额) */
	private BigDecimal shippingFee;
	/** 支付金额 */
	private BigDecimal moneyPaid;
	/**是否部分发货:0否，1是*/
	private Byte partShipFlag;
	/**是否支持mp端退款*/
	@JsonIgnore
	private Byte returnTypeCfg;
	/**积分抵扣金额*/
	private BigDecimal scoreDiscount;
	/**用户消费余额*/
	/**918*/
	private BigDecimal useAccount;
	/**会员卡消费金额*/
	/**918*/
	private BigDecimal memberCardBalance;
	private BigDecimal subGoodsPrice;
	/**退款/退货状态*/
	private Byte refundStatus;
	/**补款金额*/
	/**918*/
	private BigDecimal bkOrderMoney;
	@JsonIgnore
	private Integer userId;
	@JsonIgnore
	private String cardNo;
	@JsonIgnore
	private Integer memberCardId;
	@JsonIgnore
	private String bkOrderSn;
	@JsonIgnore
	private Integer activityId;
	/**918*/
	private Byte bkOrderPaid;
	/**币种*/
	private String currency;
	private Byte starFlag;
	/**发货时间*/
	private Timestamp shippingTime;
	/**确认收货时间*/
	private Timestamp confirmTime;
	/**定金膨胀订单自提时间*/
	private Timestamp pickupTime;
	/**定金订单发货时间*/
	private Timestamp bkShippingTime;
	@JsonIgnore
	/**确认收货后自动订单完成时间,单位天*/
	private Short orderTimeoutDays;
	private Boolean canClose;
	private Boolean canFinish;
	private Boolean canDeliver;
	private Boolean canVerify;
    private Boolean showReturnInfo;
}
