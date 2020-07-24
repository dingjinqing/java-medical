package com.vpu.mp.service.pojo.shop.doctor;

import com.vpu.mp.service.pojo.shop.department.DepartmentIdNameVo;
import lombok.Data;

import java.util.List;

/**
 * @author chenjie
 * @date 2020年07月23日
 */
@Data
public class DoctorRecommendVo {
    private List<DepartmentIdNameVo> recommendDepartment;
    private List<DoctorConsultationOneParam> doctorList;
}
