package com.vpu.mp.service.pojo.shop.order.write.operate.refund;

import java.util.List;

import com.vpu.mp.service.pojo.shop.order.goods.OrderGoodsVo;

import lombok.Data;

/**
 * 退款、退货
 * @author 王帅
 *
 */
@Data
public class RefundVo {
	/**是否支持退货、退款*/
	private Boolean flag = false;
	/**0支持退款，1支持退货、退款(admin后台增加只退运费)*/
	private Byte returnType;
	private List<OrderGoodsVo> orderGoodsVo;
}