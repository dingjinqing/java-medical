package com.vpu.mp.service.pojo.wxapp.coupon;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author: 王兵兵
 * @create: 2020-02-27 11:29
 **/
@Setter
@Getter
public class CouponPackIdParam {
    @NotNull
    private Integer packId;
}
