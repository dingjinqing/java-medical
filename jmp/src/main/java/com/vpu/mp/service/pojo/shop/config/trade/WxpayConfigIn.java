package com.vpu.mp.service.pojo.shop.config.trade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @Author:liufei
 * @Date:2019/7/9
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxpayConfigIn {
    @NotBlank
    private String appId;
    @NotBlank
    private String payMchId;
    @NotBlank
    private String payKey;
    @NotBlank
    private String payCertContent;
    @NotBlank
    private String payKeyContent;
}
