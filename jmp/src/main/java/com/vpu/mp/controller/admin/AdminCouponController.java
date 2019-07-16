package com.vpu.mp.controller.admin;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.coupon.CouponListParam;
import com.vpu.mp.service.pojo.shop.coupon.CouponListVo;
import com.vpu.mp.service.pojo.shop.coupon.CouponParam;
import com.vpu.mp.service.shop.ShopApplication;

/**
 * 优惠券管理
 * @author 常乐
 * 2019年7月16日
 */
@RestController
//@RequestMapping("/api")
public class AdminCouponController extends AdminBaseController{
	@Override
    protected ShopApplication shop() {
        return saas.getShopApp(471752);
    }
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
	public JsonResult oneCouponInfo(Integer couponId) {
		List<CouponParam> couponInfo = shop().coupon.getOneCouponInfo(couponId);
		return this.success(couponInfo);
	}
	
	/**
	 * 保存编辑信息
	 * @param param
	 * @return
	 */
	public JsonResult couponInfoSave(CouponParam param) {
		Boolean result = shop().coupon.saveCouponInfo(param);
		return this.success(result);
	}
	
	
	/**
	 * 停用优惠券
	 * @param couponId
	 * @return
	 */
	public JsonResult couponPause(Integer couponId) {
		boolean result = shop().coupon.couponPause(couponId);
		if(result) {
			return this.success(result);
		}else {
			return this.fail();
		}
	}
}
