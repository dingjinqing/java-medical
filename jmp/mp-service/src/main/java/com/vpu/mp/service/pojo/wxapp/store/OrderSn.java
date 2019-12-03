package com.vpu.mp.service.pojo.wxapp.store;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author liufei
 * @date 12/3/19
 */
@Data
public class OrderSn {
    @NotBlank
    private String orderSn;
    private String clientIp;
}
