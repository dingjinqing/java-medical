package com.vpu.mp.service.pojo.shop.order.mp.order;

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
	
	private Boolean canExtendReceive;
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
	public void setVerifierInfo(String verifierName, String verifierMobile){
		
	}
}
