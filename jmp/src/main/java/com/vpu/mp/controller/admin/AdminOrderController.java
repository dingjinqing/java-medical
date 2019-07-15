package com.vpu.mp.controller.admin;

import org.jooq.tools.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.order.OrderListInfoVo;
import com.vpu.mp.service.pojo.shop.order.OrderPageListQueryParam;
import com.vpu.mp.service.pojo.shop.order.OrderParam;

/**
 * 订单模块
 * 
 * @author 常乐,王帅
 * 2019年6月27日
 */
@RestController
@RequestMapping("/api/admin/order")
public class AdminOrderController extends AdminBaseController{
	
	@PostMapping("/list")
	public JsonResult orderList(@RequestBody OrderPageListQueryParam param) {
		PageResult<? extends OrderListInfoVo> result = shop().order.getPageList(param);
		return success(result);
	}
	
	@PostMapping("/get")
	public JsonResult get(@RequestBody OrderParam order) {
		if(StringUtils.isEmpty(order.getMainOrderSn())) {
			//TODO
			return fail();
		}
		return success(shop().order.get(order.getMainOrderSn()));
	}
}
