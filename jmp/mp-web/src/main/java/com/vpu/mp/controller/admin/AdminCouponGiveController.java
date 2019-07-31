package com.vpu.mp.controller.admin;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveDetailParam;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveDetailVo;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveListParam;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveListVo;

/**
 * 发放优惠券控制器
 * @author liangchen
 * @date 2019年7月29日
 */
@RestController
public class AdminCouponGiveController extends AdminBaseController{
	/**
	 * 定向发放优惠券列表
	 *
	 * @param param
	 * @return
	 */
	@PostMapping("/api/admin/coupon/give/list")
	public JsonResult getPageList(@RequestBody CouponGiveListParam param) {

		PageResult<CouponGiveListVo> pageResult = shop().coupon.couponGiveService.getCouponGiveList(param);

		return success(pageResult);
	}
	/**
	 * 发放优惠券明细
	 *
	 * @param param
	 * @return
	 */
	@PostMapping("/api/admin/coupon/give/detail")
	public JsonResult getDetail(@RequestBody CouponGiveDetailParam param) {

		List<CouponGiveDetailVo> pageResult = shop().coupon.couponGiveService.getDetail(param);

		return success(pageResult);
	}
}
