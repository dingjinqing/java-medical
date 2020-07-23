package com.vpu.mp.service.pojo.shop.doctor;

import com.vpu.mp.service.pojo.shop.department.DepartmentOneParam;
import com.vpu.mp.service.pojo.shop.title.TitleOneParam;
import lombok.Data;

import java.util.List;

/**
 * @author chenjie
 */
@Data
public class DoctorConsultationVo {
    private List<DoctorConsultationOneParam> doctorList;
    private List<DepartmentOneParam> departmentList;
    private List<TitleOneParam> titleList;
}
