package com.vpu.mp.service.pojo.shop.order.refund;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vpu.mp.service.pojo.shop.order.goods.OrderGoodsVo;

import lombok.Getter;
import lombok.Setter;
/**
 * 
 * @author wangshuai
 */
@Getter
@Setter
public class OrderReturnGoodsVo extends OrderGoodsVo{
    private Integer id;
	private Integer retId;
	/**0代表退货申请被拒绝，1代表正在退货中，2代表退货成功'*/
	private Byte success;
	@JsonIgnore
	private BigDecimal returnMoney;
	private Integer isGift;
}
