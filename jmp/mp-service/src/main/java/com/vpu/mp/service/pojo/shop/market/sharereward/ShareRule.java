package com.vpu.mp.service.pojo.shop.market.sharereward;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author liufei
 * @date 2019/8/20
 * @description 分享规则明细
 */
@Data
public class ShareRule {
    /**
     * 邀请数量
     */
    @JsonProperty(value = "invite_num")
    private Integer inviteNum;
    /**
     * 奖励类型，有1积分，2优惠券，3幸运大抽奖
     */
    @JsonProperty(value = "reward_type")
    private Byte rewardType;
    /**
     * 当奖励类型为积分时，奖励积分值
     */
    @JsonProperty(value = "score")
    private Integer score;
    /**
     * 积分奖励总份数
     */
    @JsonProperty(value = "score_num")
    private Integer scoreNum;
    /**
     * 当奖励类型为优惠券时，奖励优惠券的id
     */
    @JsonProperty(value = "coupon")
    private Integer coupon;
    /**
     * 优惠券奖励总份数
     */
    @JsonProperty(value = "coupon_num")
    private Integer couponNum;
    /**
     * 当奖励类型为幸运大抽奖时，奖励幸运大抽奖的id
     */
    @JsonProperty(value = "lottery")
    private Integer lottery;
    /**
     * 幸运大抽奖奖励总份数
     */
    @JsonProperty(value = "lottery_num")
    private Integer lotteryNum;

    // 奖品剩余份数
    private Integer stock;

}
