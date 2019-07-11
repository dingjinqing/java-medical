package com.vpu.mp.service.pojo.shop.config.trade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @Author:liufei
 * @Date:2019/7/11
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxpaySearchParam {
    @NotBlank
    private String appId;
}
