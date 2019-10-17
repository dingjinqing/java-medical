package com.vpu.mp.service.pojo.shop.order.mp.order;

import com.vpu.mp.service.pojo.shop.order.mp.base.BaseParam;

import lombok.Getter;
import lombok.Setter;

/**
 * 微信小程序获取订单列表
 * @author 王帅
 *
 */
@Getter
@Setter
public class OrderListParam extends BaseParam{
	public Integer currentPage;
	public Integer pageRows;
	private Byte type;
	private String search; 
}
