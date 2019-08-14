package com.vpu.mp.service.pojo.shop.market.increasepurchase;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author liufei
 * @date 2019/8/14
 * @description
 */
@Data
public class UpdatePurchaseParam extends AddPurchaseParam{
    /** 加价购活动id */
    @NotNull
    private Integer id;
}
