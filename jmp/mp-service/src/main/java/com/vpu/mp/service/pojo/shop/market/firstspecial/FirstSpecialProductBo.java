package com.vpu.mp.service.pojo.shop.market.firstspecial;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 秒杀商品
 * @author 孔德成
 * @date 2019/11/13 11:40
 */
@Getter
@Setter
public class FirstSpecialProductBo {
    /**
     * 规格`id
     */
    private Integer prdId;
    /**
     *  首单价格
     */
    private BigDecimal prdPrice;
    /**
     * 首单限制数量
     */
    private Integer limitAmount;
    /**
     * 超限购购买标记
     */
    private Byte limitFlag;
}
