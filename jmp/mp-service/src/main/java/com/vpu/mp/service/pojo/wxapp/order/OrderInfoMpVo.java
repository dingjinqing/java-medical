package com.vpu.mp.service.pojo.wxapp.order;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vpu.mp.service.pojo.shop.order.invoice.InvoiceVo;
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
public class OrderInfoMpVo extends OrderListMpVo{

	/**配送信息*/
	private List<ShippingInfoVo> shippingInfo;
	/**子单信息*/
	private List<OrderInfoMpVo> subOrder;
	/**发票信息*/
	private InvoiceVo invoiceInfo;
	private String verifierName;
	private String verifierMobile;
	/**昵称*/
	private String username;
	
	/***/
	@JsonIgnore
	private String mainOrderSn;
	private Timestamp createTime;
	private Integer storeId;
	private StorePojo storeInfo;
	private Integer invoiceId;
	private Integer verifierId;
	private Byte orderPayWay;
	private String consignee;
	private String mobile;
	private String completeAddress;
	/** 主支付方式 */
	private String payCode;
	/** 支付金额 */
	private BigDecimal moneyPaid;
	private BigDecimal scoreDiscount;
	/**用户消费余额*/
	private BigDecimal useAccount;
	/**会员卡消费金额*/
	private BigDecimal memberCardBalance;
	/**会员卡优惠金额*/
    private BigDecimal memberCardReduce;
    /**优惠券优惠金额*/
    private BigDecimal discount;
    /**运费*/
    private BigDecimal shippingFee;
	@JsonIgnore
	private BigDecimal subGoodsPrice;
	@JsonIgnore
	private Timestamp orderRemindTime;
	@JsonIgnore
	private Byte orderRemind;
	@JsonIgnore
	private Byte extendReceiveAction;
	@JsonIgnore
	private Timestamp extendReceiveTime;
	public void setVerifierInfo(String verifierName, String verifierMobile){
		this.verifierName = verifierName;
		this.verifierMobile = verifierMobile;
	}
}
