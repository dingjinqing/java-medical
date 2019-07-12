package com.vpu.mp.service.pojo.shop.config.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author:liufei
 * @Date:2019/7/11
 * @Description:
 */
@Data
public class OrderProcessParam {
    @JsonProperty(value = "express")
    private Byte express;
    @JsonProperty(value = "fetch")
    private Byte fetch;
    @JsonProperty(value = "drawback_days")
    private Byte drawbackDays;
    @JsonProperty(value = "order_timeout_days")
    private Byte orderTimeoutDays;
}
