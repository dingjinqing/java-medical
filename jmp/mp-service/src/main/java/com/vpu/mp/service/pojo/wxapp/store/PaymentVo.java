package com.vpu.mp.service.pojo.wxapp.store;

import lombok.Data;

/**
 * @author liufei
 * @date 11/7/19
 */
@Data
public class PaymentVo {
    private Byte id;
    private Integer shopId;
    private String payName;
    private String payCode;
    private String payFee;
    private String payDesc;
    private Byte enabled;
    private Byte isCod;
    private Byte isOnlinePay;
}
