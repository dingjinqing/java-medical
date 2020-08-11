package com.vpu.mp.service.pojo.shop.doctor;

import lombok.Data;

import java.util.List;

/**
 * @author chenjie
 */
@Data
public class DoctorConsultationParam{
    private String keyword;
    private Integer departmentId;
    private Integer titleId;
    private List doctorIds;
    private List departmentDoctorIds;
    private Integer currentPage=1;
    private Integer pageRows=20;
}
