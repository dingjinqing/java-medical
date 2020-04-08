package com.vpu.mp.service.pojo.wxapp.cart;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author 孔德成
 * @date 2019/10/16 14:22
 */
@Getter
@Setter
public class WxAppAddGoodsToCartParam {

    private Integer userId;
    private Integer cartId;
    @NotNull
    @Min(0)
    private  Integer goodsNumber;
    @NotNull
    private Integer prdId;
    /**
     * 活动类型
     */
    private Byte activityType;
    /**
     * 活动id
     */
    private Integer activityId;


}
