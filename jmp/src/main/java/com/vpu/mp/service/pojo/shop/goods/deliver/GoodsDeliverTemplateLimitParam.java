package com.vpu.mp.service.pojo.shop.goods.deliver;
import java.math.BigDecimal;

import lombok.Data;

/**
 * @author liangchen
 * @date 2019年7月12日
 */
@Data
public class GoodsDeliverTemplateLimitParam {
	
	private Integer limitDeliverArea;
	private String areaList;
	private String areaText;
	private Integer firstNum;
	private BigDecimal firstFee;
	private Integer continueNum;
	private BigDecimal continueFee;
	
}
