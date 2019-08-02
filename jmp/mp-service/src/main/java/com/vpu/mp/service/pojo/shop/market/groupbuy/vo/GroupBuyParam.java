package com.vpu.mp.service.pojo.shop.market.groupbuy.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vpu.mp.service.pojo.shop.market.groupbuy.param.GroupBuyProductParam;
import com.vpu.mp.service.pojo.shop.market.groupbuy.param.GroupBuyShareConfigParam;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author 孔德成
 * @date 2019/7/18 18:12
 */
@Data
@NoArgsConstructor
public class GroupBuyParam {



    private String name;
    /**
     * 商品id
     */
    private Integer goodsId;
    /**
     * 成团人数
     */
    private Short limitAmount;
    /**
     * 参团限制
     */
    private Short joinLimit;
    /**
     * 开团限制
     */
    private Short openLimit;
    /**
     * 默认成团
     */
    private Byte isDefault;
    /**
     * 有效期
     */
    private Timestamp startTime;
    private Timestamp endTime;
    /**
     * 总库存——不需要前端传
     */
    @JsonIgnore
    private Short stock;

    /**
     * 状态： 1：启用  0： 禁用
     */
    private Byte status;

    /**
     * 活动类型：1：普通拼团，2：老带新团
     */
    private Byte activityType;
    /**
     * 是否开启团长优惠：0：不开启，1：开启
     */
    private Byte isGrouperCheap;
    /**
     * 拼团失败发放优惠券
     */
    private String rewardCouponId;

    /**
     * 分享设置
     */
    private GroupBuyShareConfigParam share;

    private String shareConfig;
    /**
     * 产品规格配置
     */
    private List<GroupBuyProductParam> product;


}
