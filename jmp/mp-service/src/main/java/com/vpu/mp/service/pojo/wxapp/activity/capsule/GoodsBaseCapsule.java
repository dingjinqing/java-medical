package com.vpu.mp.service.pojo.wxapp.activity.capsule;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

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
    /**商品主图*/
    protected String goodsImg;


    /** 商品已被哪些processor处理过（商品列表里面将处理的营销码值存入） */
    private Set<Byte> processedTypes = new HashSet<>();
}
