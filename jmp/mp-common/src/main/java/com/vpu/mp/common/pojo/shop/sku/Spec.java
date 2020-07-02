package com.vpu.mp.common.pojo.shop.sku;

import lombok.Data;

import java.util.List;

/**
 * @author 李晓冰
 * @date 2020年07月02日
 */
@Data
public class Spec {
    private Integer  specId;
    private Integer goodsId;
    private String specName;

    /**
     * 规格值
     */
    List<SpecVal> specVals;
}
