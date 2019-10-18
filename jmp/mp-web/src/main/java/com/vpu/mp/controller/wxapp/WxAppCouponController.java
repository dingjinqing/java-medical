package com.vpu.mp.controller.wxapp;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.distribution.DistributorGroupListVo;
import com.vpu.mp.service.pojo.wxapp.coupon.AvailCouponDetailParam;
import com.vpu.mp.service.pojo.wxapp.coupon.AvailCouponDetailVo;
import com.vpu.mp.service.pojo.wxapp.coupon.AvailCouponListVo;
import com.vpu.mp.service.pojo.wxapp.coupon.AvailCouponParam;
import com.vpu.mp.service.pojo.wxapp.coupon.AvailCouponVo;

/**
 * 用户优惠券
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
		Integer userId = wxAppAuth.user().getUserId();
		param.setUserId(userId);
		System.out.println(param);
		PageResult<AvailCouponVo> couponList = shop().coupon.getCouponByUser(param);
		return this.success(couponList);
	}
	
	/**
	 * 优惠券详情
	 * @param param
	 * @return
	 */
	@PostMapping("/detail")
	public JsonResult availCouponDetail(@RequestBody AvailCouponDetailParam param) {
		AvailCouponDetailVo couponDetail = shop().coupon.getCouponDetail(param);
		return this.success(couponDetail);
	}
}
