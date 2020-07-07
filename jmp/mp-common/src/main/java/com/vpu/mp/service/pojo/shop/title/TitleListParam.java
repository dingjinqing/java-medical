package com.vpu.mp.service.pojo.shop.title;

import lombok.Data;

@Data
public class TitleListParam {
    private Integer nav;
    private Integer currentPage;
    private Integer pageRows;

    @Override
    public String toString() {
        return "TitleListParam{" +
            "nav=" + nav +
            ", currentPage=" + currentPage +
            ", pageRows=" + pageRows +
            '}';
    }
}
