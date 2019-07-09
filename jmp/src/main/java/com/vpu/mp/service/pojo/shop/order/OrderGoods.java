package com.vpu.mp.service.pojo.shop.order;

import java.math.BigDecimal;

import org.jooq.types.UShort;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author wangshuai
 */

@Data
@NoArgsConstructor
public class OrderGoods {
	private Integer orderId;
	private String orderSn;
	private String goodsId;
	/**货号*/
	private String goodsSn;
	private String goodsName;
	private UShort goodsNumber;
	/**单价*/
	private BigDecimal goodsPrice;
	/**属性（规格）*/
	private String goodsAttr;
	
}
