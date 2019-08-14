package com.vpu.mp.service.pojo.shop.coupon.hold;

import lombok.Data;

import java.util.List;

/**
 * @author 孔德成
 * @date 2019/8/13 16:46
 */
@Data
public class CouponHoldListParam {

    /* 优惠券规则Id */
    private Integer actId;
    /* 发放活动id*/
    private Integer accessId;
    /**
     * 获取方式，0：发放，1：领取
     */
    private Byte accessMode;

    /**
     * 用户id
     */
    private Integer userId;
    /* 手机号 */
    private String mobile;
    /* 用户昵称 */
    private String username;
    /**
     * 是否已经使用 1 未使用 2 已使用 3 已过期 4已废除
     */
    private Byte status;


    /**
     * 	分页信息
     */
    private int currentPage;
    private int pageRows;

    private String actName;




}
