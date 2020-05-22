package com.vpu.mp.service.pojo.shop.coupon.hold;

import lombok.Data;

import java.math.BigDecimal;
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
    private String couponSn;
    private String couponName;
    private Integer delFlag;
    /**
     * 获取方式，0：发放，1：领取
     */
    private Integer accessMode;
    /**
     * 优惠券状态 0 未使用 1 使用
     */
    private Integer isUsed;
    /**
     * 优惠券状态 0 未使用 1 使用 2 过期 3 废除
     */
    private Integer status;
    /**优惠券类型 0：普通优惠券；1：分裂优惠券*/
    private Integer type;
    /**是否分享*/
    private Integer isShare;
    /**领取用户数*/
    private Integer hasReceive;
    private BigDecimal denomination;
    /**voucher减价；discount；打折*/
    private String actCode;
    private Integer scoreNumber;
    private String orderSn;
    private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp createTime;
    private Timestamp usedTime;
}
