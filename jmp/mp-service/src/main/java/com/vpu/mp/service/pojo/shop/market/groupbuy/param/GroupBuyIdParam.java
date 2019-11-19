package com.vpu.mp.service.pojo.shop.market.groupbuy.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 孔德成
 * @date 2019/7/19 16:16
 */
@Data
public class GroupBuyIdParam {

    @NotNull
    private Integer id;

    private Integer status;
}
