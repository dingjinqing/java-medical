package com.vpu.mp.service.pojo.shop.goods;

import lombok.Data;

/**
 * @author 李晓冰
 * @date 2019年07月08日
 */
@Data
public class GoodsColumnCheckExistParam {

    private ColumnCheckFor columnCheckFor;

    private Integer goodsId;
    //商品
    private String goodsName;
    private String goodsSn;

    //规格编号
    private Integer prdId;
    private String prdSn;

    public static enum ColumnCheckFor {
        E_GOODS, E_GOODS_SPEC_PRODUCTION;
    }

}
