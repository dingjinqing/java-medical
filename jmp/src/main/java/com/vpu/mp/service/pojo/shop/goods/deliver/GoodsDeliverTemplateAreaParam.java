package com.vpu.mp.service.pojo.shop.goods.deliver;
import java.math.BigDecimal;

import lombok.Data;


@Data
public class GoodsDeliverTemplateAreaParam {
	
	private String areaList;
	private String areaText;
	private Integer firstNum;
	private BigDecimal firstFee;
	private Integer continueNum;
	private BigDecimal continueFee;
	
}
