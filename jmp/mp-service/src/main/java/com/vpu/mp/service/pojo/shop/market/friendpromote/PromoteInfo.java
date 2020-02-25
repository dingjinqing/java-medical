package com.vpu.mp.service.pojo.shop.market.friendpromote;

import lombok.Data;

/**
 * 小程序-好友助力展示信息
 * @author liangchen
 * @date 2020.02.25
 */
@Data
public class PromoteInfo {
    /** 奖励内容 */
    private FpRewardContent rewardContent;
    /** 助力次数 */
    private Integer hasLaunchNum;
    /** 活动库存 */
    private Integer marketStore;
    /** 商品详情 */
    private GoodsInfo goodsInfo;
}
