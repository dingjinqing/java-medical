package com.vpu.mp.service.pojo.shop.market.payaward;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author 孔德成
 * @date 2019/8/12 18:38
 */
@Data
public class PayAwardListParam {

    /**
     * 活动状态过滤 ：1全部，2进行中，3未开始，4已过期，5已停用
     */
    @Max(5)
    @Min(0)
    private Byte navType;

    /**
     * 	分页信息
     */
    private Integer currentPage;
    private Integer pageRows ;
}
