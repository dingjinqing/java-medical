package com.vpu.mp.service.pojo.shop.config.trade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:liufei
 * @Date:2019/7/11
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentConfigVo {
    private String payCode;
    private String payName;
    private String enabled;
}
