package com.vpu.mp.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.shop.order.OrderService.PageListQueryParam;

/**
 * 订单模块
 * 
 * @author 常乐
 * 2019年6月27日
 */
@Controller
public class AdminOrderController extends AdminBaseController{
	
	@ResponseBody
	@RequestMapping(value = "/admin/order/orderList")
	public JsonResult orderList(PageListQueryParam param) {
		PageResult list = shop().order.getPageList(param);
		return success(list);
	}
}
