package com.vpu.mp.service.pojo.shop.market.seckill;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author: 王兵兵
 * @create: 2019-10-28 18:21
 **/
@Data
public class SeckillProductAddParam {
    @NotNull
    private Integer    goodsId;
    @NotNull
    private Integer    productId;
    @NotNull
    private BigDecimal secKillPrice;
    @NotNull
    private Integer      stock;
}
