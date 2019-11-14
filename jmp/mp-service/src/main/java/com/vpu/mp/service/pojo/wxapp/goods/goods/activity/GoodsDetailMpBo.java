package com.vpu.mp.service.pojo.wxapp.goods.goods.activity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vpu.mp.db.shop.tables.records.GradePrdRecord;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.GoodsDetailMpVo;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 小程序详情页面信息存储对象
 * @author 李晓冰
 * @date 2019年11月07日
 */
@Getter
@Setter
public class GoodsDetailMpBo extends GoodsDetailMpVo{

    /** 平台、商家、品牌分类id */
    @JsonIgnore
    private Integer catId;
    @JsonIgnore
    private Integer sortId;
    /**商品初始销量*/
    @JsonIgnore
    private Integer baseSale;
    /** 商品已被哪些processor处理过（商品列表里面将处理的营销码值存入） */
    @JsonIgnore
    private Set<Byte> processedTypes = new HashSet<>();

    /**商品主图*/
    @JsonIgnore
    private String goodsImg;
    /**上下架状态*/
    @JsonIgnore
    private Byte isOnSale;
    @JsonIgnore
    private Integer goodsVideoId;
    @JsonIgnore
    private List<GradePrdRecord> gradeCardPrice;
}
