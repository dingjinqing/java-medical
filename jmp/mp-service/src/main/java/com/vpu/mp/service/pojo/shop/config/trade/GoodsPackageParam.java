package com.vpu.mp.service.pojo.shop.config.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author liufei
 * @date 2019/7/11
 */
@Data
public class GoodsPackageParam {
    @JsonProperty(value = "add_goods")
    private String addGoods;
    @JsonProperty(value = "add_cate")
    private String addCate;
    @JsonProperty(value = "add_sort")
    private String addSort;
    @JsonProperty(value = "add_label")
    private String addLabel;
    @JsonProperty(value = "add_brand")
    private String addBrand;
}
