package com.vpu.mp.service.pojo.shop.market.groupbuy.param;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 孔德成
 * @date 2019/7/19 10:50
 */
@Setter
@Getter
public class GroupBuyProductParam extends BasePageGroupBuyParam{

    private Integer  id;
    /**
     * 拼团定义Id
     */
    private Integer  activityId;

    /**
     * 商品规格id
     */
    @JsonProperty(value = "prdId")
    private Integer productId;

    /**
     * 拼团价格
     */

    private BigDecimal groupPrice;

    /**
     * 库存
     */
    private Short stock;


    /**
     * 销量
     */
    private Short saleNum;

    /**
     * 团长价格
     */
    private BigDecimal grouperPrice;


}
