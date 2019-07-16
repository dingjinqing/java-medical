package com.vpu.mp.service.shop.coupon;

import com.vpu.mp.db.shop.tables.records.MrkingVoucherRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.pojo.shop.coupon.CouponParam;

/**
 * 优惠券管理
 * @author 常乐
 * 2019年7月16日
 */
public class CouponService extends BaseService{
	/**
	 * 创建优惠券
	 * @param couponInfo
	 * @return
	 */
	public Boolean couponAdd(CouponParam couponInfo) {
		MrkingVoucherRecord record = new MrkingVoucherRecord();
		this.assign(couponInfo,record);
		return db().executeInsert(record) > 0 ? true : false;
	}
}
