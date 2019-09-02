package com.vpu.mp.service.pojo.shop.order.write.operate.refund;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * 退款、退货
 * 
 * @author 王帅
 *
 */
@Data
public class RefundVo {
	/** 支持退款,支持退货,退运费,手动退款(新加); */
	private boolean[] returnType = { false, false, false, false};
	/**
	 * mp查询如果无可退商品则refundGoods=null admin手工退款退货需要详细信息
	 */
	private List<RefundVoGoods> refundGoods;
	/** 优先级退款信息 */
	private Map<String , BigDecimal> returnAmountMap;
	/** 可退运费 */
	private BigDecimal returnShippingFee;
	@Data
	public static class RefundVoGoods {

		private Integer recId;
		private Integer orderId;
		private String orderSn;
		private Integer productId;
		@JsonIgnore
		private Integer goodsId;
		private String goodsName;
		private String goodsAttr;
		@JsonIgnore
		private String goodsImg;
		@JsonIgnore
		private Integer goodsNumber;
		/** 已退完成（手工退款时需计算） */
		private Integer returnNumber;
		/** 市场价 */
		private BigDecimal marketPrice;
		/** 单价 */
		private BigDecimal goodsPrice;
		/** 折后单价 */
		private BigDecimal discountedGoodsPrice;
		/** 折后总价 */
		private BigDecimal discountedTotalPrice;
		/** 可退 */
		private Integer returnable;
		/** 已提交 */
		private Integer submitted;
		/** 总数 */
		private Integer total;
		/** 是否可退,小程序端判断 */
		private Byte isCanReturn;
		/** 赠品 */
		private Byte isGift;
		/**手动退款时已退金额*/
		private BigDecimal returnMoney;
		/** 发货数 */
		@JsonIgnore
		private Short sendNumber;
	}

	public Boolean mpIsReturn() {
		if(!returnType[0] && !returnType[1]) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	public Boolean adminIsReturn() {
		if(!returnType[0] && !returnType[1] && !returnType[2] && !returnType[3]) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
	public Boolean isReturn(Boolean isMap) {
		if(isMap) {
			return mpIsReturn();
		}else {
			return adminIsReturn();
		}
	}
}