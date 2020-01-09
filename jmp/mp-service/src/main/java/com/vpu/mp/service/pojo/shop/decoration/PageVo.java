package com.vpu.mp.service.pojo.shop.decoration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author: 王兵兵
 * @create: 2020-01-09 17:23
 **/
@Data
public class PageVo {
    @JsonProperty("page_id")
    private Integer   pageId;
    @JsonProperty("page_name")
    private String    pageName;
    @JsonProperty("page_type")
    private Byte      pageType;
    @JsonProperty("page_tpl_type")
    private Byte      pageTplType;
    @JsonProperty("page_content")
    private String    pageContent;
    @JsonProperty("page_publish_content")
    private String    pagePublishContent;
    @JsonProperty("cat_id")
    private Integer   catId;
}
