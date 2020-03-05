package com.vpu.mp.service.pojo.wxapp.market.lottery;

import com.vpu.mp.service.pojo.shop.base.BasePageParam;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 用户的中奖列表
 * @author 孔德成
 * @date 2020/2/10
 */
@Getter
@Setter
public class LotteryListUserParam  extends BasePageParam {
    /**
     * 活动id
     */
    @NotNull
    private Integer lotteryId;


    private Integer userId;
}
