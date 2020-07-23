package com.vpu.mp.service.pojo.shop.doctor;

import lombok.Data;

@Data
public class DoctorConsultationParam{
    private String keyword;
    private Integer departmentId;
    private Integer titleId;
}
