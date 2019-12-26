package com.vpu.mp.service.pojo.wxapp.order;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vpu.mp.service.pojo.shop.market.groupbuy.vo.GroupOrderVo;
import com.vpu.mp.service.pojo.wxapp.order.goods.OrderGoodsMpVo;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author 王帅
 *
 */
@Getter
@Setter
public class OrderListMpVo{

	private Integer orderId;
	private String orderSn;
	private Byte orderStatus;
    /** 配送方式:0 快递 1 自提 */
    private Byte deliverType;
    /** 主支付方式 */
    private String payCode;
    /**实际付款*/
    private BigDecimal moneyPaid;
	@JsonIgnore
	private String goodsType;
	private Byte bkOrderPaid;
	private Byte refundStatus;
	private Timestamp expireTime;
	private Timestamp shippingTime;
	@JsonIgnore
	private Byte returnDaysCfg;
	@JsonIgnore
	private Byte commentFlag;
	@JsonIgnore
	private Integer activityId;
	@JsonIgnore
	private Byte returnTypeCfg;
	private Byte partShipFlag;
	@JsonIgnore
	private Timestamp extendReceiveTime;
	@JsonIgnore
    private Integer storeId;

	/**预售、定金支付区间*/
	private Timestamp[] preSaleTimeInterval;
	/**支付按钮是否显示*/
	private Byte isShowPay;
	/**是否奖品订单*/
	private Byte isLotteryGift;
	/**是否退过*/
	private Byte isReturn;
	/**是否可以付补款*/
	private Byte isPayEndPayment;
	/**延迟收货*/
	private Byte isExtendReceive;
	/**再次购买*/
	private Byte isShowAgainBuy;
	/**是否提醒发货*/
	private Byte isRemindShip;
	/**评价显示*/
	private Byte isShowCommentType;
	/**待支付状态时剩余支付时间*/
	private Long payOperationTime;
	/**退款*/
	private Byte isReturnMoney;
	/**退货*/
	private Byte isReturnGoods;
	/**取消*/
	private Byte isCancel;
	/**删除*/
	private Byte isDelete;
	private List<OrderGoodsMpVo> goods;
	private GroupOrderVo groupBuyInfo;
	private List<String> orderType;
}
