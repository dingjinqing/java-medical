package com.vpu.mp.service.pojo.shop.goods;

import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpec;
import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpecProduct;
import lombok.Data;

import java.util.List;

/**
 * @author 李晓冰
 * @date 2019年07月05日
 */
@Data
public class Goods {
    private static final long serialVersionUID = 1778325631;

    private Integer    goodsId;
    private String     goodsName;

    private List<GoodsSpec> goodsSpecs;
    private List<GoodsSpecProduct> goodsSpecProducts;
}
