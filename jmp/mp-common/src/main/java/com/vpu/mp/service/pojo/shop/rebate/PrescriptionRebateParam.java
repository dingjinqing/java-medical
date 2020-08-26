package com.vpu.mp.service.pojo.shop.rebate;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author yangpengcheng
 * @date 2020/8/26
 **/
@Data
public class PrescriptionRebateParam {
    private String prescriptionCode;
    private Integer doctorId;
    private BigDecimal totalMoney;
    private BigDecimal totalRebateMoney;
    /**
     * 返利状态
     */
    private Byte status;
}
