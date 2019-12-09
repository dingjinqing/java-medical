package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.coupon.*;
import com.vpu.mp.service.pojo.shop.coupon.hold.CouponHoldListVo;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 优惠券管理
 * @author 常乐
 * 2019年7月16日
 */
@RestController
@RequestMapping("/api")
public class AdminCouponController extends AdminBaseController{
//
//	@Override
//	protected ShopApplication shop() {
//		return saas.getShopApp(471752);
//	}
	/**
	 * 创建优惠券
	 * @param couponInfo
	 * @return
	 */
	@PostMapping("/admin/coupon/add")
	public JsonResult couponAdd(@RequestBody CouponParam couponInfo) {
		Boolean result = shop().coupon.couponAdd(couponInfo);
		if(result) {
			return this.success(result);
		}else {
			return this.fail();
		}
	}

	/**
	 * 优惠券分页列表
	 * @param param
	 * @return
	 */
	@PostMapping("/admin/coupon/list")
	public JsonResult couponList(@RequestBody CouponListParam param) {
		PageResult<CouponListVo> couponList = shop().coupon.getCouponList(param);
		return this.success(couponList);
	}

	/**
	 *获得所有可发放优惠券 -下拉框
	 * @return
	 */
	@PostMapping("/admin/coupon/all")
	public JsonResult getCouponAll (@RequestBody CouponAllParam param){
		List<CouponAllVo> couponAll = shop().coupon.getCouponAll(param.getIsHasStock());
		return this.success(couponAll);
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
	public JsonResult couponInfoSave(@RequestBody CouponParam param) {
		Boolean result = shop().coupon.saveCouponInfo(param);
		return this.success(result);
	}

	/**
	 * 停用优惠券
	 * @param couponId
	 * @return
	 */
	@GetMapping("/admin/coupon/pause")
	public JsonResult couponPause(@NotNull Integer couponId) {
		System.out.println(111);
		boolean result = shop().coupon.couponPause(couponId);
		if(result) {
			return this.success(result);
		}else {
			return this.fail();
		}
	}

	/**
	 * 启用优惠券
	 * @param couponId
	 * @return
	 */
	@GetMapping("/admin/coupon/open")
	public JsonResult couponOpen(@NotNull Integer couponId) {
		boolean result = shop().coupon.couponOpen(couponId);
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
	public JsonResult couponGetDetail(@RequestBody CouponGetDetailParam param) {
		PageResult<CouponHoldListVo> detail = shop().coupon.getDetail(param);
		return this.success(detail);
	}
}
