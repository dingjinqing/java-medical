package com.vpu.mp.service.shop.coupon;


import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.coupon.CouponListVo;
import com.vpu.mp.service.pojo.shop.coupon.mpGetCouponParam;
import org.springframework.stereotype.Service;

import static com.vpu.mp.db.shop.Tables.CUSTOMER_AVAIL_COUPONS;
import static com.vpu.mp.db.shop.Tables.MRKING_VOUCHER;

/**
 * 小程序端-优惠券相关service
 * user：常乐
 * date：2019-11-20
 */
@Service
public class MpCouponService extends ShopBaseService {
    /**
     * 查询优惠券基本信息
     * @param param
     * @return
     */
    public CouponListVo getCouponData(mpGetCouponParam param){
        CouponListVo couponData = db().select().from(MRKING_VOUCHER).where(MRKING_VOUCHER.ID.eq(param.getCouponId()))
                .fetchOne().into(CouponListVo.class);
        return couponData;
    }

    /**
     * 用户已领取某优惠券数量
     * @param userId
     * @param couponId
     */
    public Integer couponAlreadyGet(Integer userId,Integer couponId){
        int res = db().selectCount().from(CUSTOMER_AVAIL_COUPONS).where(CUSTOMER_AVAIL_COUPONS.ACT_ID.eq(couponId)).and(CUSTOMER_AVAIL_COUPONS.USER_ID.eq(userId))
                .fetchOne().into(Integer.class);
        return res;
    }

    //TODO:将优惠券插入用户领取优惠券表
    public void setCouponToCustomer(){

    }
}
