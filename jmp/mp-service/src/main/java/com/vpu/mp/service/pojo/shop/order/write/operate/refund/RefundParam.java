package com.vpu.mp.service.pojo.shop.order.write.operate.refund;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author 王帅
 */
@Getter
@Setter
@ToString
public final class RefundParam extends OrderOperateQueryParam {
	/**退款退货订单id*/
	private Integer retId;
	/** 退-退款类型:0仅退款 1:退货退款 2:仅退运费 3:手动退款 */
	@NotNull(message = JsonResultMessage.MSG_ORDER_RETURN_NOT_NULL_RETURNTYPE)
	private Byte returnType;
	private List<ReturnGoods> returnGoods;
	@NotNull(message = JsonResultMessage.MSG_ORDER_RETURN_NOT_NULL_RETURNMONEY)
	private BigDecimal returnMoney;
	/**退运费金额*/
	@NotNull(message = JsonResultMessage.MSG_ORDER_RETURN_NOT_NULL_SHIPPINGFEE)
	private BigDecimal shippingFee;
	private Byte reasonType;
	private String reasonDesc;
	/**除提交物流时的图片都在这个字段*/
	private String goodsImages;
	/**提交物流时的图片在这个字段*/
	private String voucherImages;
	@Getter
	@Setter
	public static class ReturnGoods {
		/** order_goods主键 */
		private Integer recId;
		private Integer returnNumber;
		private BigDecimal money;
	}
	/**
	 * 买家发起：
	 * 		不需走退款逻辑：0退货提交物流、1撤销退款
	 * 商家发起：
	 * 		不需走退款逻辑：2拒绝仅退款请求与买家提交物流商家拒绝退款、3同意退货请求、4拒绝退货申请、
	 * 		走退款逻辑（param.）：商家同意买家退款、商家确认收货并退款
	 */
	private Byte returnOperate;
	/**
	 * 对退款退货订单的操作flag
	 */
	private Byte returnOperateFlag;
	/**
	 * mp端操作（除申请请求参数）参数：退货提交物流、撤销退款
	 */
	private String shippingType;
	private String shippingNo;
	private String phone;
	/**
	 * 拒绝退款/退货原因
	 */
	private String refundRefuseReason;
	/**
	 * 同意退货时商家收货详情
	 */
	private String consignee;
	private String returnAddress;
	private String merchantTelephone;
	private String zipCode;
	/**
	 * 拒绝退货申请原因
	 */
	private String applyNotPassReason;
}
