package com.vpu.mp.service.pojo.wxapp.distribution;

import com.vpu.mp.service.pojo.shop.distribution.RebateRatioVo;
import lombok.Data;

import java.util.List;

/**
 * @Author 常乐
 * @Date 2020-03-04
 */
@Data
public class GoodsDistributionVo {
    /**
     * 是否分销改价 0：否；1是
     */
    private Byte canRebate;
    private Byte isDistributor;
    private RebateRatioVo rebateRatio;
}
