package com.vpu.mp.service.pojo.wxapp.goods.goods.list;

import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author 李晓冰
 * @date 2019年11月12日
 */
@Getter
@Setter
public class CouponListMpVo extends GoodsActivityBaseMpVo{
    public CouponListMpVo() {
        activityType = GoodsConstant.ACTIVITY_TYPE_COUPON;
    }
    /**优惠券类型voucher是减金额，discount打折*/
    private String actCode;
    /**优惠券面额*/
    private BigDecimal denomination;
    /**是否存在使用门槛 0否 1是*/
    private Byte useConsumeRestrict;
    /** 满多少可用*/
    private BigDecimal leastConsume;
}
