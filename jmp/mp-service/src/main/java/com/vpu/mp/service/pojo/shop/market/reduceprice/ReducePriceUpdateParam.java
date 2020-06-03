package com.vpu.mp.service.pojo.shop.market.reduceprice;

import com.vpu.mp.service.pojo.shop.config.PictorialShareConfig;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author: 王兵兵
 * @create: 2019-08-15 14:55
 **/
@Data
public class ReducePriceUpdateParam {

    /**
     * 活动主键
     */
    @NotNull
    private Integer id;

    /**
     * 状态：1：启用 0：禁用
     */
    @Min(0)
    @Max(1)
    private Byte status;

    private String name;
    private Byte first;
    private Byte limitFlag;
    private Integer limitAmount;
    private PictorialShareConfig shareConfig;
}
