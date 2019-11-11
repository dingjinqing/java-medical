package com.vpu.mp.controller.wxapp;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.wxapp.coupon.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
		AvailCouponListVo list = new AvailCouponListVo();
		Integer userId = wxAppAuth.user().getUserId();
		param.setUserId(userId);
		PageResult<AvailCouponVo> couponList = shop().coupon.getCouponByUser(param);
		list = shop().coupon.getEachStatusNum(userId,list);
		list.setCouponList(couponList);
		return this.success(list);
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
