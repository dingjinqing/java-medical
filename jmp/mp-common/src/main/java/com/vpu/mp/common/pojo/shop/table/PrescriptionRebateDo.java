package com.vpu.mp.common.pojo.shop.table;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author yangpengcheng
 * @date 2020/8/26
 **/
@Data
public class PrescriptionRebateDo {
    private Integer    id;
    private String     prescriptionCode;
    private Integer    doctorId;
    private BigDecimal totalMoney;
    private BigDecimal totalRebateMoney;
    private Byte       status;
    private Timestamp rebateTime;
    private Byte       isDelete;
    private Timestamp  createTime;
    private Timestamp  updateTime;
}
