package com.vpu.mp.service.pojo.shop.coupon.give;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * 发送优惠卷信息
 * @author 孔德成
 * @date 2019/12/20 15:10
 */
@Getter
@Setter
public class CouponGiveQueueBo {
    /**
     * 发送成功数量
     */
    private Integer successSize;
    /**
     * 发送成功的优惠卷set
     */
    private Set<Integer> couponSet =new HashSet<>();
}
