package com.vpu.mp.service.pojo.shop.order.report;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author 孔德成
 * @date 2020/7/31 18:26
 */
@Data
public class MedicalOrderReport {

    private Timestamp createTime;
    private String time;

    private Integer orderNumber;

    private BigDecimal orderAmount;
    private BigDecimal orderAvg;

    private Integer orderMedicalNumber;

    private BigDecimal orderMedicalAmount;

    private Integer returnNumber;

    private BigDecimal returnAmount;

}
