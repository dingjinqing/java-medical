package com.vpu.mp.service.pojo.wxapp.cart;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author 孔德成
 * @date 2019/10/16 14:22
 */
@Getter
@Setter
public class WxAppAddGoodsToCartParam {

    @NotNull
    @Min(0)
    private  Integer goodsNumber;
    @NotNull
    private Integer prdId;


}
