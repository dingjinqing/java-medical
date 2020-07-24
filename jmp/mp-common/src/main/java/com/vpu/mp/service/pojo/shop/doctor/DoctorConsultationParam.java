package com.vpu.mp.service.pojo.shop.doctor;

import lombok.Data;

/**
 * @author chenjie
 */
@Data
public class DoctorConsultationParam{
    private String keyword;
    private Integer departmentId;
    private Integer titleId;
}
