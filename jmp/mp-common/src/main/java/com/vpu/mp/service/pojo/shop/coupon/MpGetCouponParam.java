package com.vpu.mp.service.pojo.shop.coupon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 小程序端用户领取优惠券入参
 * user:常乐
 * date:2019-11-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MpGetCouponParam {
    @NotNull
    private Integer couponId;
    private Integer userId;
}
