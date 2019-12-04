package com.vpu.mp.service.pojo.shop.market.freeshipping;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
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
    @DecimalMin("0")
    private BigDecimal money;
    private Integer num;
    private String  area;

}
