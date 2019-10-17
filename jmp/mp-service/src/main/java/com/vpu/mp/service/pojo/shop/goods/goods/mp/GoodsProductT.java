package com.vpu.mp.service.pojo.shop.goods.goods.mp;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 李晓冰
 * @date 2019年10月15日
 * 商品规格
 */
@Data
public class GoodsProductT {
    private Iterable prdId;
    private String prdDesc;
    private BigDecimal prdPrice;
}
