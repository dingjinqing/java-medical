package com.vpu.mp.service.pojo.shop.market.lottery;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 孔德成
 * @date 2019/8/6 17:10
 */
@Data
public class  JoinLotteryParam {

    private Integer userId;
    @NotNull
    private Integer lotteryId;

    /**
     * 抽奖类型 share
     *          score
     *          free
     */
    private Byte lotteryType;
}
