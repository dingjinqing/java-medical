package com.vpu.mp.service.pojo.wxapp.goods.goods.activity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vpu.mp.service.pojo.shop.config.pledge.PledgeInfo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.GoodsDetailMpVo;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
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
    //************ElasticSearch中的数据**************start
    /** 平台、商家、品牌分类id */
    @JsonIgnore
    private Integer catId;
    @JsonIgnore
    private Integer sortId;
    /**商品初始销量*/
    @JsonIgnore
    private Integer baseSale;

    /**上下架状态*/
    @JsonIgnore
    private Byte isOnSale;
    @JsonIgnore
    private Integer goodsVideoId;
    @JsonIgnore
    private List<GradePrd> gradeCardPrice;
    //************ElasticSearch中的数据**************end

    @Data
    public static class GradePrd{
        private Integer prdId;
        private BigDecimal gradePrice;
        private String grade;
    }

    /** 商品已被哪些processor处理过（商品列表里面将处理的营销码值存入） */
    @JsonIgnore
    private Set<Byte> processedTypes = new HashSet<>();

    /**商品主图*/
    private String goodsImg;

    //**********服务承诺
    /**
     * 服务承诺是否开启
     */
    private Integer pledgeSwitch;
    /**
     * 服务承诺信息
     */
    private  List<PledgeInfo> pledgeList;

}
