package com.vpu.mp.service.pojo.shop.market.friendpromote;

import lombok.Data;

/**
 * 小程序-好友助力展示信息
 * @author liangchen
 * @date 2020.02.25
 */
@Data
public class PromoteInfo {
    /** 活动id */
    private Integer id;
    /** 奖励内容 */
    private FpRewardContent rewardContent;
    /** 助力次数 */
    private Integer hasLaunchNum;
    /** 活动库存 */
    private Integer marketStore;
    /** 商品详情 */
    private GoodsInfo goodsInfo;
    /** 优惠券详情 */
    private CouponInfo couponInfo;
    /** 活动状态：0未开始，1进行中，2已结束 */
    private Byte actStatus;
    /** 助力进度 */
    private Byte promoteStatus;
    /** 已被助力次数总次数 */
    private Integer hasPromoteTimes;
    /** 是否可以再次助力 */
    private Byte canLaunch;
}
