package com.vpu.mp.service.pojo.shop.market.groupbuy;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;

/**
 * @author 孔德成
 * @date 2019/7/18 18:12
 */
@Data
@NoArgsConstructor
public class GroupBuyParam {


    private Integer   id;
    private Integer   shopId;
    /**
     * 商品id
     */
    private Integer   goodsId;
    private String    name;
    /**
     *成团人数
     */
    private Short     limitAmount;
    /**
     * 参团限制
     */
    private Short     joinLimit;
    /**
     * 开团限制
     */
    private Short     openLimit;
    /**
     * 默认成团
     */
    private Byte      isDefault;
    /**
     * 有效期
     */
    private Timestamp startTime;
    private Timestamp endTime;
    /**
     *
     */
    private Short     stock;
    /**
     *
     */
    private Short     saleNum;
    private Byte      status;
    private Integer   delTime;
    private Byte      activityType;
    private Byte      isGrouperCheap;
    private String    rewardCouponId;
    private String    shareConfig;



}
