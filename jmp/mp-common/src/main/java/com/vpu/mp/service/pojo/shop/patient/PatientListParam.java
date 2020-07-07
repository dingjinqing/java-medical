package com.vpu.mp.service.pojo.shop.patient;

import lombok.Data;

@Data
public class PatientListParam {
    private Integer nav;
    private Integer currentPage;
    private Integer pageRows;

    @Override
    public String toString() {
        return "PatientListParam{" +
            "nav=" + nav +
            ", currentPage=" + currentPage +
            ", pageRows=" + pageRows +
            '}';
    }
}
