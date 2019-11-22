package com.vpu.mp.service.pojo.shop.market.groupbuy.param;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vpu.mp.service.pojo.shop.base.BasePageParam;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author 孔德成
 * @date 2019/7/19 10:50
 */
@Setter
@Getter
public class GroupBuyProductParam extends BasePageParam{

    private Integer  id;
    /**
     * 拼团定义Id
     */
    private Integer  activityId;

    /**
     * 商品规格id
     */
    @NotNull
    @JsonProperty(value = "prdId")
    private Integer productId;

    /**
     * 拼团价格
     */
    @NotNull
    private BigDecimal groupPrice;

    /**
     * 库存
     */
    @NotNull
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
