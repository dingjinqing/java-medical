package com.vpu.mp.service.pojo.shop.config.trade;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vpu.mp.service.foundation.util.Util;
import lombok.Data;

/**
 * @author liufei
 * @date 2019/7/11
 */
@Data
public class GoodsPackageParam {
    @JsonProperty(value = "add_goods")
    @JsonAlias({"add_goods", "addGoods"})
    private Integer[] addGoods;
    /**
     * 平台分类
     */
    @JsonProperty(value = "add_cate")
    @JsonAlias({"add_cate", "addCate"})
    private Integer[] addCate;
    /** 商家分类 */
    @JsonProperty(value = "add_sort")
    @JsonAlias({"add_sort", "addSort"})
    private Integer[] addSort;
    @JsonProperty(value = "add_label")
    @JsonAlias({"add_label", "addLabel"})
    private Integer[] addLabel;
    @JsonProperty(value = "add_brand")
    @JsonAlias({"add_brand", "addBrand"})
    private Integer[] addBrand;

    @Override
    public String toString() {
        return Util.toJson(this);
    }
}
