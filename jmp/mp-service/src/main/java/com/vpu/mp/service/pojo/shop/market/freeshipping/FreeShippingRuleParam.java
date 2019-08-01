package com.vpu.mp.service.pojo.shop.market.freeshipping;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 孔德成
 * @date 2019/7/29 18:41
 */
@Data
public class FreeShippingRuleParam {

    private Integer id;
    private Integer shippingId;
    /**
     * 包邮条件 0满金额 1满件数
     */
    private Integer conType;
    private BigDecimal money;
    private Integer num;
    private String  area;

}
