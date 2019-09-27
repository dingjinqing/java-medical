package com.vpu.mp.controller.wxapp;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.wxapp.coupon.AvailCouponListVo;
import com.vpu.mp.service.pojo.wxapp.coupon.AvailCouponParam;

/**
 * wxapp用户优惠券
 * @author 常乐
 * 2019年9月24日
 */
@RestController
@RequestMapping("/api/wxapp/coupon")
public class WxAppCouponController extends WxAppBaseController {
	
	/**
	 * 用户优惠券列表
	 * @param param
	 * @return
	 */
	@PostMapping("/list")
	public JsonResult availCoupon(@RequestBody AvailCouponParam param) {
		AvailCouponListVo couponList = shop().coupon.getCouponByUser(param);
		return this.success(couponList);
	}
}
