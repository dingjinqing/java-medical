package com.vpu.mp.controller.admin;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.coupon.CouponGetDetailParam;
import com.vpu.mp.service.pojo.shop.coupon.CouponGetDetailVo;
import com.vpu.mp.service.pojo.shop.coupon.CouponListParam;
import com.vpu.mp.service.pojo.shop.coupon.CouponListVo;
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
		if(result) {
			return this.success(result);
		}else {
			return this.fail();
		}
	}
	
	/**
	 * 优惠券分页列表
	 * @param parma
	 * @return
	 */
	@PostMapping("/admin/coupon/list")
	public JsonResult couponList(CouponListParam parma) {
		PageResult<CouponListVo> couponList = shop().coupon.getCouponList(parma);
		return this.success(couponList);
	}
	
	/**
	 * 单条优惠券信息
	 * @param couponId
	 * @return
	 */
	@GetMapping("/admin/coupon/update/info")
	public JsonResult oneCouponInfo(Integer couponId) {
		List<CouponParam> couponInfo = shop().coupon.getOneCouponInfo(couponId);
		return this.success(couponInfo);
	}
	
	/**
	 * 保存编辑信息
	 * @param param
	 * @return
	 */
	@PostMapping("/admin/coupon/update/save")
	public JsonResult couponInfoSave(CouponParam param) {
		Boolean result = shop().coupon.saveCouponInfo(param);
		return this.success(result);
	}
	
	/**
	 * 停用优惠券
	 * @param couponId
	 * @return
	 */
	@PostMapping("/admin/coupon/pause")
	public JsonResult couponPause(Integer couponId) {
		boolean result = shop().coupon.couponPause(couponId);
		if(result) {
			return this.success(result);
		}else {
			return this.fail();
		}
	}
	
	/**
	 * 删除优惠券（假删除）
	 * @param couponId
	 * @return
	 */
	@GetMapping("/admin/coupon/delete")
	public JsonResult couponDel(Integer couponId) {
		boolean result = shop().coupon.couponDel(couponId);
		if(result) {
			return this.success(result);
		}else {
			return this.fail();
		}
	}
	
	/**
	 * 优惠券领取明细
	 * @param param
	 * @return
	 */
	@PostMapping("/admin/coupon/get/detail")
	public JsonResult couponGetDetail(CouponGetDetailParam param) {
		PageResult<CouponGetDetailVo> detail = shop().coupon.getDetail(param);
		return this.success(detail);
	}
}
