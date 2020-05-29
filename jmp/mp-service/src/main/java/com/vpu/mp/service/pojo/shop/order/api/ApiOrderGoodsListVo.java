package com.vpu.mp.service.pojo.shop.order.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 王帅
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ApiOrderGoodsListVo {
    @JsonProperty("sku_id")
    private String productId;
    @JsonProperty("prd_sn")
    private String productSn;
    private String goodsId;
    private String goodsSn;
    private String goodsName;
    @JsonProperty("prd_desc")
    private String goodsAttr;
    private String goodsPrice;
    private String goodsNumber;
    @JsonProperty("goods_refund_status")
    private String refundStatus;
    @JsonProperty("shipping_no")
    private String shippingNo;
    @JsonProperty("shipping_name")
    private String shippingName;
    @JsonProperty("shipping_time")
    private String shippingTime;


}
