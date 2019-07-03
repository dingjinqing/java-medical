package com.vpu.mp.controller.admin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.order.OrderPageListQueryParam;

/**
 * 订单模块
 * 
 * @author 常乐
 * 2019年6月27日
 */
@RestController
public class AdminOrderController extends AdminBaseController{
	
	@PostMapping(value = "/admin/order/orderList")
	public JsonResult orderList(OrderPageListQueryParam param) {
		PageResult<Object> list = shop().order.getPageList(param);
		return success(list);
	}
}
