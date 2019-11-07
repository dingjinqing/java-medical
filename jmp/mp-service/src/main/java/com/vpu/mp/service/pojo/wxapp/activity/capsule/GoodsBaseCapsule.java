package com.vpu.mp.service.pojo.wxapp.activity.capsule;

import lombok.Data;

/**
 * @author 李晓冰
 * @date 2019年11月06日
 */
@Data
public class GoodsBaseCapsule {
    protected Integer goodsId;
    protected String goodsName;
    protected Byte goodsType;

    /**商品销售数量*/
    protected Integer goodsSaleNum;
    /**商品初始销量*/
    protected Integer baseSale;
    /**商品数量*/
    protected Integer goodsNumber;

    /** 平台、商家、品牌分类id */
    protected Integer catId;
    protected Integer sortId;
    protected Integer brandId;

    /**是否是使用默认规格*/
    protected Boolean defaultPrd;
}
