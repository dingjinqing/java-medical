package com.vpu.mp.service.pojo.shop.order.goods;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author wangshuai
 */

@Data
@NoArgsConstructor
public class OrderGoodsVo {
	private Integer recId;
	private Integer orderId;
	private String orderSn;
	private Integer goodsId;
	/**货号*/
	private String goodsSn;
	private String goodsName;
	/**退货时为退货数量,发货时为可发货数量*/
	private Integer goodsNumber;
	/**单价*/
	private BigDecimal goodsPrice;
	/**属性（规格）*/
	private String goodsAttr;
	/**SKU*/
	private Integer productId;
	private String goodsImg;
	/**
	 * 以下为发货使用的参数，不参加序列化
	 */
	@JsonIgnore
	private Integer returnNumber;
	private Integer sendNumber;
}
