package com.vpu.mp.service.pojo.shop.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderReturnGoodsVo extends OrderGoodsVo{
	/**0代表退货申请被拒绝，1代表正在退货中，2代表退货成功'*/
	private Byte success;
	/**发货商品数量*/
	private Short sendNumber;
}
