package com.vpu.mp.service.pojo.shop.market.groupbuy.vo;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vpu.mp.service.pojo.shop.coupon.CouponView;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 孔德成
 * @date 2019/7/19 17:40
 */
@Data
@NoArgsConstructor
public class GroupBuyDetailVo {

    private Integer id;
    /**
     * 名称
     */
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
     * 最少购买数 0不限制
     */
    private Short limitBuyNum;
    /**
     * 最多购买数 0 不限制
     */
    private Short limitMaxNum;

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
     * 总库存
     */
    private Short stock;
    /**
     * 销量
     */
    private Short saleNum;
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
     * 是否免运费 1 免运费 2 使用原商品运费模板
     */
    private Byte shippingType;

    /**
     * 拼团失败发放优惠券
     */
    private String rewardCouponId;
    /**
     * 初始化等级
     */
    private Integer level=0;
    /**
     * 初始化数量
     */
    private Integer beginNum=0;
    /**
     * 分享设置
     */
    private GroupBuyShareConfigVo share;

    @JsonIgnore
    private String shareConfig;
    /**
     * 产品规格配置
     */
    private List<GroupBuyProductVo> productList;

    /**
     * 优惠卷信息
     */
    private List<CouponView> couponViews;
}
