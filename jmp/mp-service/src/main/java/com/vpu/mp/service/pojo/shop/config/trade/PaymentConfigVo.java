package com.vpu.mp.service.pojo.shop.config.trade;

import lombok.Data;

/**
 * @Author:liufei
 * @Date:2019/7/11
 * @Description:
 */
@Data
public class PaymentConfigVo {
    public String payCode;
    public String payName;
    public Byte enabled;
}
