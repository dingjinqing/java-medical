package com.vpu.mp.service.pojo.shop.doctor;

import com.vpu.mp.service.pojo.shop.department.DepartmentIdNameVo;
import com.vpu.mp.service.pojo.shop.department.DepartmentOneParam;
import lombok.Data;

import java.util.List;

/**
 * @author chenjie
 * @date 2020年07月23日
 */
@Data
public class DoctorRecommendVo {
    private List<DepartmentOneParam> recommendDepartment;
    private List<DoctorConsultationOneParam> doctorList;
}
