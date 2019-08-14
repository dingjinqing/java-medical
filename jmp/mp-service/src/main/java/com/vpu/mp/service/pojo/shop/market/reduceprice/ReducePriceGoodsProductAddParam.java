package com.vpu.mp.service.pojo.shop.market.reduceprice;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: 王兵兵
 * @create: 2019-08-13 18:01
 **/
@Data
public class ReducePriceGoodsProductAddParam {

    /** 规格ID */
    private Integer productId;

    /** 修改后的规格价 */
    private BigDecimal prdPrice;
}
