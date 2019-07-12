package com.vpu.mp.service.pojo.shop.config.trade;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author:liufei
 * @Date:2019/7/11
 * @Description:
 */
@Data
public class WxpaySearchParam {
    @NotBlank
    private String appId;
}
