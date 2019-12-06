package com.vpu.mp.service.pojo.shop.distribution;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Auther 常乐
 * @Date 2019-12-06
 */
@Data
public class DistributionDocumentParam {
    /**
     * 页面标题
     */
    @JsonProperty(value = "title")
    public String title;

    /**
     * 文案模版
     */
    @JsonProperty(value = "document")
    public String document;
}
