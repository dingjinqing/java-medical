package com.vpu.mp.controller.admin;

import org.jooq.tools.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.order.OrderListInfoOutput;
import com.vpu.mp.service.pojo.shop.order.OrderPageListQueryParam;

/**
 * 订单模块
 * 
 * @author 常乐
 * 2019年6月27日
 */
@RestController
@RequestMapping("/api/admin/order")
public class AdminOrderController extends AdminBaseController{
	
	@PostMapping("/list")
	public JsonResult orderList(@RequestBody OrderPageListQueryParam param) {
		PageResult<OrderListInfoOutput> result = shop().order.getPageList(param);
		return success(result);
	}
	
	@PostMapping("/get")
	public JsonResult get(@RequestBody String orderSn) {
		if(StringUtils.isEmpty(orderSn)) {
			//todo
			return fail();
		}
		return success(shop().order.get(orderSn));
	}
}
