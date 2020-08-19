package com.vpu.mp.service.pojo.shop.patient;

import lombok.Data;

import java.util.List;

/**
 * @author chenjie
 */
@Data
public class PatientListParam {
    private Integer nav;
    private Integer currentPage;
    private Integer pageRows;
    private String name;
    private String mobile;
    private Integer userId=0;
    private List<Integer> patientIds;

    @Override
    public String toString() {
        return "PatientListParam{" +
            "nav=" + nav +
            ", currentPage=" + currentPage +
            ", pageRows=" + pageRows +
            ", name='" + name + '\'' +
            ", mobile='" + mobile + '\'' +
            ", userId=" + userId +
            ", patientIds=" + patientIds +
            '}';
    }
}
