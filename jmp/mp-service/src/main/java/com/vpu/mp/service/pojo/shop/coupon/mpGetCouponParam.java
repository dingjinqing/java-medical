package com.vpu.mp.service.pojo.shop.coupon;

import lombok.Data;

/**
 * 小程序端用户领取优惠券入参
 * user:常乐
 * date:2019-11-21
 */
@Data
public class mpGetCouponParam {
    private Integer couponId;
    private Integer userId;
}
