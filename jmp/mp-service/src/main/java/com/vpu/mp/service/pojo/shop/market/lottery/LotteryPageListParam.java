package com.vpu.mp.service.pojo.shop.market.lottery;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.Data;

/**
 * @author 孔德成
 * @date 2019/8/5 11:48
 */
@Data
public class LotteryPageListParam {

    /**
     * 活动状态过滤 ：1全部，2进行中，3未开始，4已过期，5已停用
     */
    @Max(5)
    @Min(0)
    private Byte state = (byte)1;

    /**
     * 	分页信息
     */
    private Integer currentPage;
    private Integer pageRows ;
}
