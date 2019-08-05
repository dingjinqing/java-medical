package com.vpu.mp.controller.admin;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.order.virtual.CouponPackOrderPageParam;
import com.vpu.mp.service.pojo.shop.order.virtual.CouponPackOrderRefundParam;

/**
 * @author huangronggang
 * @date 2019年8月2日
 * 虚拟订单 ---优惠劵
 */
@RestController
@RequestMapping("/api/admin/order/coupon")
public class AdminCouponPackOrderController extends AdminBaseController {
	
	/** 
	 * 分页查询 优惠劵包 虚拟订单列表
	 * */
	@PostMapping("/list")
	public JsonResult couponPackOrderList(@RequestBody CouponPackOrderPageParam param) {
		return success(shop().couponPackOrder.getPageList(param));
	}
	/**
	 * 虚拟商品订单 优惠劵包订单 退款
	 * @param param
	 * @return
	 */
	@PostMapping("/refund")
	public JsonResult refundPackOrder(@RequestBody @Valid CouponPackOrderRefundParam param) {
		Integer subAccountId = adminAuth.user().getSubAccountId();
		shop().couponPackOrder.refundCouponPackOrder(param, subAccountId);
		shop().couponPackOrder.updateSendFlag(param.getStillSendFlag(), param.getOrderId());
		return success();
	}
	
}

