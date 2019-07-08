package com.vpu.mp.service.pojo.shop.goods.spec;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 货品SKU
 * @author 李晓冰
 * @date 2019年07月05日
 */
@Data
public class GoodsSpecProduct {
    private static final long serialVersionUID = 1710413918;

    private Integer    prdId;

    private Integer    goodsId;

    private String     prdSpecs;
    private String     prdDesc;

    private Timestamp createTime;
    private Timestamp  updateTime;
}
