package com.vpu.mp.service.pojo.wxapp.activity.info;

import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author 李晓冰
 * @date 2019年10月30日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CouponProcessorDataInfo extends ProcessorDataInfo {
    public CouponProcessorDataInfo() {
        super();
        dataType = GoodsConstant.ACTIVITY_TYPE_COUPON;
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
