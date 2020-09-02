package com.vpu.mp.service.pojo.shop.patient;

import com.vpu.mp.common.pojo.shop.table.PatientDo;
import lombok.Data;

/**
 * @author chenjie
 * @date 2020年07月28日
 */
@Data
public class PatientAddParam extends PatientDo {
    private Integer userId;
}
