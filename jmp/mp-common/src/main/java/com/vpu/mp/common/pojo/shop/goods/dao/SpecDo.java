package com.vpu.mp.common.pojo.shop.goods.dao;

import lombok.Data;

/**
 * 规格名
 * @author 李晓冰
 * @date 2020年07月02日
 */
@Data
public class SpecDo {
    private Integer  specId;
    private Integer goodsId;
    private String specName;

    @Override
    public String toString() {
        return "Spec{" +
            "specId=" + specId +
            ", goodsId=" + goodsId +
            ", specName='" + specName + '\'' +
            '}';
    }
}
