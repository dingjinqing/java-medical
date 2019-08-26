package com.vpu.mp.service.pojo.shop.order.write.operate.refund;

import java.math.BigDecimal;

import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author 王帅
 */
@Getter
@Setter
public final class RefundParam extends OrderOperateQueryParam {
	/** 退-退款类型:0仅退款 1:退货退款 2:仅退运费 3:手动退款 */
	private Byte returnType;
	private ReturnGoods[] returnGoods;
	private BigDecimal returnMoney;
	/**退运费金额*/
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
}
