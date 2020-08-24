package com.vpu.mp.service.pojo.shop.config.rebate;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author yangpengcheng
 * @date 2020/8/24
 **/
@Data
@NoArgsConstructor
public class RebateConfig {
    /**
     * 开关
     */
    private Byte status;
    /**
     * 商品订单分成比例
     */
    private Double goodsOrderProportion;
    /**
     *处方药医生佣金比例
     */
    private Double rxMedicalDoctorProportion;
    /**
     *处方药平台佣金比例
     */
    private Double rxMedicalPlatformProportion;
    /**
     *非处方药医生佣金比例
     */
    private Double noRxMedicalDoctorProportion;
    /**
     *非处方药平台佣金比例
     */
    private Double noRxMedicalPlatformProportion;
    /**
     *医生佣金比例
     */
    private BigDecimal inquiryOrderDoctorProportion;
    /**
     *平台佣金比例
     */
    private BigDecimal inquiryOrderPlatformProportion;


}
