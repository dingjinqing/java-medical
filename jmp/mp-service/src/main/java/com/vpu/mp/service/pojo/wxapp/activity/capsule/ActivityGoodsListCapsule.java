package com.vpu.mp.service.pojo.wxapp.activity.capsule;

import com.vpu.mp.service.pojo.wxapp.activity.info.ActivityForListInfo;
import com.vpu.mp.service.pojo.wxapp.activity.info.list.GoodsLabelForListInfo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 小程序-商品列表信息存储对象
 * @author 李晓冰
 * @date 2019年10月29日
 */
@Data
public class ActivityGoodsListCapsule {
    private Integer goodsId;
    private String goodsName;
    private Byte goodsType;
    private BigDecimal shopPrice;
    private BigDecimal marketPrice;
    private BigDecimal prdMaxPrice;
    /**商品最总显示价格和划线价格*/
    private BigDecimal realPrice;
    private BigDecimal linePrice;

    /**商品销售数量*/
    private Integer goodsSaleNum;
    /**商品初始销量*/
    private Integer baseSale;
    /**评价数量*/
    private Integer commentNum;
    /**商品数量*/
    private Integer goodsNumber;

    /**商品主图*/
    private String goodsImg;

    /**关系最紧密的标签信息*/
    GoodsLabelForListInfo goodsLabel;
    /** 平台、商家、品牌分类id */
    private Integer catId;
    private Integer sortId;
    private Integer brandId;
    /**是否是使用默认规格*/
    private Boolean defaultPrd;
    /** 商品已被哪些processor处理过（商品列表里面将处理的营销码值存入） */
    private Set<Byte> processedTypes = new HashSet<>();
    /** 商品拥有的营销信息，由各个processor添加 */
    private List<ActivityForListInfo> activities = new ArrayList<>(2);
}
