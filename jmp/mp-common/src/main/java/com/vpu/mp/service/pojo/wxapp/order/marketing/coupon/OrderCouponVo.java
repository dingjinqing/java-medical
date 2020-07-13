package com.vpu.mp.service.pojo.wxapp.order.marketing.coupon;

import com.vpu.mp.service.pojo.wxapp.coupon.AvailCouponVo;
import com.vpu.mp.service.pojo.wxapp.order.marketing.base.BaseMarketingBaseVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 王帅
 */
@Getter
@Setter
@ToString
public class OrderCouponVo extends BaseMarketingBaseVo {
   private AvailCouponVo info;
    public OrderCouponVo init(AvailCouponVo source){
        setInfo(source);
        this.setIdentity(source.getCouponSn());
        return this;
    }
}
