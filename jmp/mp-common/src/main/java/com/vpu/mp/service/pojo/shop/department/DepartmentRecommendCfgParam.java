package com.vpu.mp.service.pojo.shop.department;

import lombok.Data;

/**
 * @author chenjie
 * @date 2020年09月14日
 */
@Data
public class DepartmentRecommendCfgParam {
    private Byte doctorRecommendType;
    private Byte departmentRecommendType;
    private Integer doctorRecommendConsultationRate;
    private Integer doctorRecommendInquiryRate;
    private Integer departmentRecommendConsultationRate;
    private Integer departmentRecommendInquiryRate;
    private Integer departmentRecommendDoctorRate;
}
