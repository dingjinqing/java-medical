package com.vpu.mp.service.pojo.shop.config.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:liufei
 * @Date:2019/7/11
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnPackageParam {
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
