package com.vpu.mp.service.pojo.shop.market.payaward;

import com.vpu.mp.service.pojo.shop.coupon.CouponView;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author 孔德成
 * @date 2019/12/20 15:50
 */
@Getter
@Setter
public class PayAwardPrizeVo {
    /**
     * 奖励类型
     */
    private Byte giftType;
    /**
     * 优惠卷
     */
    private List<CouponView> couponView;
}
