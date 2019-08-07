package com.vpu.mp.service.pojo.shop.order.write.operate.refund;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * 退款、退货
 * @author 王帅
 *
 */
@Data
public class RefundVo {
	/**是否存在可退商品*/
	private Boolean flag = false;
	/**0支持退款，1支持退货、退款(admin后台增加只退运费)*/
	private Byte returnType;
	/**
	 * mp查询如果无可退商品则refundGoods=null
	 * admin手工退款退货需要详细信息
	 */
	private List<RefundVoGoods> refundGoods;
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
		private Integer returnNumber;
		/**单价*/
		private BigDecimal goodsPrice;
		/**折后单价*/
		private BigDecimal discountedGoodsPrice;
		/**可退*/
		private Integer returnable;
		/**已提交*/
		private Integer submitted;
		/**总数*/
		private Integer total;
	}
}