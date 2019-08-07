package com.vpu.mp.service.pojo.shop.market.lottery;

import com.vpu.mp.service.foundation.util.Page;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author 孔德成
 * @date 2019/8/5 11:48
 */
@Data
public class LotteryPageListParam {

    /**
     * 活动状态过滤 ：0全部，1进行中，2未开始，3已过期，4已停用
     */
    @Max(4)
    @Min(0)
    private Byte state = (byte)1;

    /**
     * 	分页信息
     */
    private Integer currentPage;
    private Integer pageRows ;
}
