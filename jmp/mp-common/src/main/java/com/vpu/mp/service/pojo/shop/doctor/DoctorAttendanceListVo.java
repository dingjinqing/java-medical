package com.vpu.mp.service.pojo.shop.doctor;

import com.vpu.mp.common.foundation.util.PageResult;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author chenjie
 */
@Data
public class DoctorAttendanceListVo {
    private PageResult<DoctorAttendanceOneParam> doctorList;
    private Integer lastRank=0;
    private BigDecimal lastRate= new BigDecimal(2);
}
