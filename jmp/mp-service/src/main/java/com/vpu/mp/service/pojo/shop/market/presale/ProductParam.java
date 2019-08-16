package com.vpu.mp.service.pojo.shop.market.presale;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 活动商品规格入参
 *
 * @author 郑保乐
 */
@Data
public class ProductParam {

    /** 预售id **/
    @JsonIgnore
    private Integer presaleId;

    private Integer id;
    /** 商品id **/
    @NotNull
    private Integer goodsId;
    /** 产品id **/
    @NotNull
    private Integer productId;
    /** 活动价格 **/
    @NotNull
    private Double presalePrice;
    /** 活动库存 **/
    @NotNull
    private Integer presaleNumber;
    /** 定金 **/
    @NotNull
    private Double presaleMoney;
    /** 1段定金可抵扣金额 **/
    @NotNull
    private Double preDiscountMoney1;
    /** 2段定金可抵扣金额 **/
    private Double preDiscountMoney2;
}
