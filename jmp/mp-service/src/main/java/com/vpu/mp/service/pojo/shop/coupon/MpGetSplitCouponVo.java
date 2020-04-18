package com.vpu.mp.service.pojo.shop.coupon;

import lombok.Getter;
import lombok.Setter;

/**
 * 领取分裂
 * @author 孔德成
 * @date 2020/4/17
 */
@Getter
@Setter
public class MpGetSplitCouponVo {

    private Integer haveNum;
    private Byte  isOneself;
    /**
     * 状态
     */
    private Byte status;

}
