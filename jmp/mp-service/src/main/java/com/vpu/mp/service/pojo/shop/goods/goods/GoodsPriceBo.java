package com.vpu.mp.service.pojo.shop.goods.goods;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 考虑限时降价、首单特惠、等级会员价三种情况下，得出的商品价格
 * @author: 王兵兵
 * @create: 2020-02-19 10:54
 **/
@Getter
@Setter
public class GoodsPriceBo {
    private Byte goodsPriceAction;
    private BigDecimal goodsPrice;
}
