package com.vpu.mp.service.pojo.shop.goods.goods;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 李晓冰
 * @date 2019年09月18日
 */
@Data
public class PrdPriceNumberParam {
    private Integer prdId;
    private BigDecimal shopPrice;
    private Integer goodsNumber;
}
