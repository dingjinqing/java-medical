package com.vpu.mp.service.pojo.wxapp.coupon.pack;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: 王兵兵
 * @create: 2020-03-02 16:02
 **/
@Setter
@Getter
public class CouponPackCheckVo {
    /**
     * 活动状态，0正常，1活动已结束，2活动未开始，3领取礼包达到上限，4单用户领取礼包达到上限，5积分不足
     */
    private Byte state;
}
