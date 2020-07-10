package com.vpu.mp.service.pojo.shop.doctor;

import lombok.Data;

@Data
public class DoctorListParam {
    private Integer nav;
    private Integer currentPage;
    private Integer pageRows;
    private String name;
    private String doctorNo;
    private Integer departmentId;

    @Override
    public String toString() {
        return "DoctorListParam{" +
            "nav=" + nav +
            ", currentPage=" + currentPage +
            ", pageRows=" + pageRows +
            ", name=" + name +
            ", doctorNo=" + doctorNo +
            ", departmentId=" + departmentId +
            '}';
    }
}
