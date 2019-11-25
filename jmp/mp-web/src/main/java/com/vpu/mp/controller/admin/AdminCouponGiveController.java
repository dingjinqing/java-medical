package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.coupon.give.*;
import com.vpu.mp.service.pojo.shop.coupon.hold.CouponHoldListVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 发放优惠券控制器
 * @author liangchen
 * @date 2019年7月29日
 */
@RestController
@RequestMapping("/api/admin/coupon/give")
public class AdminCouponGiveController extends AdminBaseController{
	/**
	 * 定向发放优惠券列表
	 *
	 * @param param 选填项：活动名称
	 * @return 对应的发券活动信息
	 */
	@PostMapping("/list")
	public JsonResult getPageList(@RequestBody CouponGiveListParam param) {

		PageResult<CouponGiveListVo> pageResult = shop().coupon.couponGiveService.getCouponGiveList(param);

		return success(pageResult);
	}
	/**
	 * 优惠券明细
	 *
	 * @param param
	 * @return
	 */
	@PostMapping("/detail")
	public JsonResult getDetail(@RequestBody CouponGiveDetailParam param) {

		PageResult<CouponHoldListVo> pageResult = shop().coupon.couponGiveService.getDetail(param);

		return success(pageResult);
	}

	/**
	 * 发优惠券
	 *
	 * @param param
	 * @return
	 */
	@PostMapping("/grant")
	public JsonResult insertGrant(@RequestBody CouponGiveGrantParam param) {

		shop().coupon.couponGiveService.insertGrant(param);

		return success();
	}
	/**
	 * 优惠券弹窗
	 *
	 * @param param
	 * @return
	 */
	@PostMapping("/pop")
	public JsonResult getPopWindow(@RequestBody CouponGivePopParam param) {
		List<CouponGivePopVo> pageResult = shop().coupon.couponGiveService.popWindows(param);
		return success(pageResult);
	}
	/**
	 * 废除优惠券
	 *
	 * @param param
	 * @return
	 */
	@PostMapping("/delete")
	public JsonResult deleteCoupon(@RequestBody CouponGiveDeleteParam param) {
		shop().coupon.couponGiveService.deleteCoupon(param);
		return success();
	}
}
