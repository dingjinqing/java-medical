package com.vpu.mp.service.pojo.wxapp.distribution;

import lombok.Data;

import java.util.List;

/**
 * @Author 常乐
 * @Date 2020-04-06
 */
@Data
public class GoodsRebateChangePriceVo {
    /**
     * 商品基本信息
     */
    private BaseGoodsVo goods;
    /**
     * 商品分销改价信息
     */
    private List<RebateGoodsCfgVo> rebatePrice;
}
