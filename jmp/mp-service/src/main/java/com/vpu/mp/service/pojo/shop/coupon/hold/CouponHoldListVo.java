package com.vpu.mp.service.pojo.shop.coupon.hold;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author 孔德成
 * @date 2019/8/13 17:15
 */
@Data
public class CouponHoldListVo {

    private Integer id;
    private String username;
    private Integer userId;
    private String mobile;
    private String couponName;
    private Integer delFlag;
    /**
     * 获取方式，0：发放，1：领取
     */
    private Integer accessMode;
    /**
     * 优惠券状态 0 未使用 1 使用 2 过期 3 废除
     */
    private Integer isUsed;
    private Integer scoreNumber;
    private String orderSn;
    private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp createTime;
    private Timestamp usedTime;
}
