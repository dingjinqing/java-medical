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
	/** 支持退款,支持退货,退运费; */
	private boolean[] returnType = { false, false, false };
	/**
	 * mp查询如果无可退商品则refundGoods=null admin手工退款退货需要详细信息
	 */
	private List<RefundVoGoods> refundGoods;
	/**  */
	private Map<String , BigDecimal> returnAmountMap;
	@Data
	public static class RefundVoGoods {

		private Integer recId;
		private Integer orderId;
		private String orderSn;
		private Integer productId;
		private String goodsName;
		private String goodsAttr;
		@JsonIgnore
		private Integer goodsNumber;
		@JsonIgnore
		private Integer returnNumber;
		/** 单价 */
		private BigDecimal goodsPrice;
		/** 折后单价 */
		private BigDecimal discountedGoodsPrice;
		/** 可退 */
		private Integer returnable;
		/** 已提交 */
		private Integer submitted;
		/** 总数 */
		private Integer total;
		/** 是否可退,小程序端判断 */
		private Byte isCanReturn;
	}

	public Boolean mpIsReturn() {
		if(!returnType[0] && !returnType[1]) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	public Boolean adminIsReturn() {
		if(!returnType[0] && !returnType[1] && !returnType[2]) {
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