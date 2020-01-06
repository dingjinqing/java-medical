package com.vpu.mp.controller.wxapp;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.coupon.mpGetCouponParam;
import com.vpu.mp.service.pojo.wxapp.coupon.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

/**
 * 用户优惠券
 * @author 常乐
 * @date 2019年9月24日
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
	public JsonResult availCoupon(@RequestBody AvailCouponParam param) throws ParseException {
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

    /**
     * 积分兑换优惠券，兑换详情页
     * @param param
     * @return
     */
    @PostMapping("/detail/byScore")
    public JsonResult CouponDetailByScore(@RequestBody AvailCouponDetailParam param) {
        Integer userId = wxAppAuth.user().getUserId();
        Integer canUseScore = shop().member.score.getTotalAvailableScoreById(userId);
        AvailCouponDetailVo couponDetail = shop().coupon.getCouponDetailByScore(param);
        couponDetail.setCanUseScore(canUseScore);
        return this.success(couponDetail);
    }

	/**
	 * 用户立即领取优惠券
	 * @return
	 */
	@PostMapping("/get")
	public JsonResult getCoupon(@RequestBody mpGetCouponParam param) {
		Integer userId = wxAppAuth.user().getUserId();
        param.setUserId(userId);
        Byte fetchStatus = shop().mpCoupon.fetchCoupon(param);
        return this.success(fetchStatus);
	}

}
