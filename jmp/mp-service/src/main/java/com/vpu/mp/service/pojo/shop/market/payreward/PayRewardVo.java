package com.vpu.mp.service.pojo.shop.market.payreward;

import com.vpu.mp.service.pojo.shop.coupon.CouponVo;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author 孔德成
 * @date 2019/8/13 13:49
 */
@Data
public class PayRewardVo {


    private Integer id;
    private String actName;
    private Timestamp startTime;
    private Timestamp endTime;
    private Byte type;
    private Short everytimeAmount;
    private BigDecimal denomination;
    private String couponIds;
    private Integer lotteryId;
    private Byte recommendType;
    private String recommendGoodsId;
    private String recommendCatId;
    private String recommendSortId;
    private String imgUrl;
    private String linkPath;
    private Byte status;
    private Byte isDelete;
    private Timestamp createTime;
    private Timestamp updateTime;

    /**
     * 分裂优惠券
     */
    private CouponVo Coupon;
}