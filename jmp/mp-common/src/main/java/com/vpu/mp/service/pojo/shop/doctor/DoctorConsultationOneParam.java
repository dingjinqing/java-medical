package com.vpu.mp.service.pojo.shop.doctor;

import com.vpu.mp.common.pojo.shop.table.DoctorDo;
import lombok.Data;

/**
 * @author chenjie
 */
@Data
public class DoctorConsultationOneParam extends DoctorDo {
    private String departmentName;
    private String titleName;
    private Integer departmentId;
    private String hospitalName;
}
