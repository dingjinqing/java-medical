package com.vpu.mp.controller.admin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.pojo.shop.coupon.CouponParam;

/**
 * 优惠券管理
 * @author 常乐
 * 2019年7月16日
 */
@RestController
@RequestMapping("/api")
public class AdminCouponController extends AdminBaseController{
	/**
	 * 创建优惠券
	 * @param couponInfo
	 * @return
	 */
	@PostMapping("/admin/coupon/add")
	public JsonResult couponAdd(@RequestBody CouponParam couponInfo) {
		System.out.println(couponInfo);
		Boolean result = shop().coupon.couponAdd(couponInfo);
		return this.success(result);
	}
}
