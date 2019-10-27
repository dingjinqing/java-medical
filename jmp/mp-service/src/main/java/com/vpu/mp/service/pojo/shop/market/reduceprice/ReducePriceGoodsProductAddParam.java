package com.vpu.mp.service.pojo.shop.market.reduceprice;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author: 王兵兵
 * @create: 2019-08-13 18:01
 **/
@Data
public class ReducePriceGoodsProductAddParam {

    /** 规格ID */
    @JsonProperty("prdId")
    private Integer productId;

    /** 修改后的规格价 */
    private BigDecimal prdPrice;
}
