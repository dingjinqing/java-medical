package com.vpu.mp.service.pojo.shop.goods.goods;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 1.商品列表批量操作中的批量修改规格价格
 * 2.商品列表单独修改某一项商品或规格的价格或数量
 * @author 李晓冰
 * @date 2019年09月18日
 */
@Data
public class PrdPriceNumberParam {
    private Integer prdId;
    private BigDecimal shopPrice;
    private Integer goodsNumber;
}
