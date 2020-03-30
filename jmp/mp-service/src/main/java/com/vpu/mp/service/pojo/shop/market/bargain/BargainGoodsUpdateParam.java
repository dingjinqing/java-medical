package com.vpu.mp.service.pojo.shop.market.bargain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author: 王兵兵
 * @create: 2020-03-27 11:31
 **/
@Setter
@Getter
public class BargainGoodsUpdateParam {
    @NotNull
    private Integer id;
    private BigDecimal expectationPrice;
    private Integer stock;
}
