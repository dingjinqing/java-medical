package com.vpu.mp.service.pojo.shop.order.goods.store;

import lombok.Data;

/**
 * 药房同步药品信息基类
 * @author 李晓冰
 * @date 2020年09月03日
 */
@Data
public class OrderStoreGoodsBase {
    private String goodsCommonName;
    private String goodsQualityRatio;
    private String goodsProductionEnterprise;
    private String goodsApprovalNumber;
    /**药房方提供的唯一值*/
    private String goodsCode;
}
