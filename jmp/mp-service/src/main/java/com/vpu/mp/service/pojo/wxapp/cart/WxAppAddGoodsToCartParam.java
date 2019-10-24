package com.vpu.mp.service.pojo.wxapp.cart;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 孔德成
 * @date 2019/10/16 14:22
 */
@Data
public class WxAppAddGoodsToCartParam {


    private  Integer goodsNumber;
    private Integer prdId;

    private Integer userId;

}
