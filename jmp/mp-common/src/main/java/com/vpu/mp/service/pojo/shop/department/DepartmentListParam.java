package com.vpu.mp.service.pojo.shop.department;

import lombok.Data;

/**
 * 科室列表入参
 *
 * @author chenjie
 * 2020年7月2日
 */
@Data
public class DepartmentListParam {
    private Integer nav;
    private Integer currentPage;
    private Integer pageRows;
    private String keyword;

    @Override
    public String toString() {
        return "DepartmentListParam{" +
            "nav=" + nav +
            ", currentPage=" + currentPage +
            ", pageRows=" + pageRows +
            ", keyword='" + keyword + '\'' +
            '}';
    }
}
