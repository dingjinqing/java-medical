package com.vpu.mp.service.pojo.shop.coupon;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author 孔德成
 * @date 2019/10/21 10:50
 */
@Getter
@Setter
public class CouponAllParam {

    @Builder.Default
    private Boolean isHasStock =true;

}
