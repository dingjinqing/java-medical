package com.vpu.mp.service.pojo.shop.order.mp.order;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vpu.mp.service.pojo.shop.market.groupbuy.vo.GroupOrderVo;
import com.vpu.mp.service.pojo.shop.order.invoice.InvoiceVo;
import com.vpu.mp.service.pojo.shop.order.mp.goods.OrderGoodsMpVo;
import com.vpu.mp.service.pojo.shop.order.shipping.ShippingInfoVo;
import com.vpu.mp.service.pojo.shop.store.store.StorePojo;

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
	
	private Byte isExtendReceive;
	/**是否奖品订单*/
	private Byte isLotteryGift;
	private List<OrderGoodsMpVo> goods;
	private GroupOrderVo groupBuyInfo;
	/**预售、定金支付区间*/
	private Timestamp[] preSaleTimeInterval;
	private Byte isReturn;
	/**是否可以付补款*/
	private Byte isPayEndPayment;
	/**是否评价有礼*/
	private Byte isCommentAward;
	/**是否提醒发货*/
	private Byte isRemindShip;
	/**支付按钮是否显示*/
	private Byte isShowPay;
	/**配送信息*/
	private List<ShippingInfoVo> shippingInfo;
	/**子单信息*/
	private List<OrderListMpVo> subOrder;
	/**发票信息*/
	private InvoiceVo invoiceInfo;
	private String verifierName;
	private String verifierMobile;
	/**待支付状态时剩余支付时间*/
	private Long payOperationTime;
	/**昵称*/
	private String username;
	/**退款*/
	private Byte isReturnMoney;
	/**退货*/
	private Byte isReturnGoods;
	/**取消*/
	private Byte isCancel;
	/**删除*/
	private Byte isDelete;
	/**再次购买*/
	private Byte isShowAgainBuy;
	/**评价显示*/
	private Byte isShowCommentType;
	/***/
	private Integer orderId;
	private String orderSn;
	private String mainOrderSn;
	@JsonIgnore
	private Byte returnDaysCfg;
	private Timestamp createTime;
	@JsonIgnore
	private Integer activityId;
	/**发货时间*/
	private Timestamp shippingTime;
	private Byte orderStatus;
	private Byte refundStatus;
	private Integer storeId;
	private StorePojo storeInfo;
	private Integer invoiceId;
	private String goodsType;
	private Integer verifierId;
	private Byte bkOrderPaid;
	private Timestamp expireTime;
	private Byte orderPayWay;
	@JsonIgnore
	private Byte commentFlag;
	@JsonIgnore
	private Byte returnTypeCfg;
	/** 主支付方式 */
	private String payCode;
	/** 支付金额 */
	private BigDecimal moneyPaid;
	private BigDecimal scoreDiscount;
	/**用户消费余额*/
	/**918*/
	private BigDecimal useAccount;
	/**会员卡消费金额*/
	/**918*/
	private BigDecimal memberCardBalance;
	/**TODO 数据库没字段子单金额*/
	@JsonIgnore
	private BigDecimal subGoodsPrice;
	public void setVerifierInfo(String verifierName, String verifierMobile){
		
	}
}
