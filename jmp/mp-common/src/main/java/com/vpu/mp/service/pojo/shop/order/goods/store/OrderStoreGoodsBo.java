package com.vpu.mp.service.pojo.shop.order.goods.store;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 李晓冰
 * @date 2020年08月27日
 */
@Data
public class OrderStoreGoodsBo {
    private String goodsCommonName;
    private String goodsQualityRatio;
    private String goodsProductionEnterprise;
    private String goodsApprovalNumber;
    /**药房方提供的唯一值*/
    private String goodsCode;
    private Integer goodsNumber;
    private BigDecimal goodsPrice;
}
